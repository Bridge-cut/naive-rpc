package top.naive_projects.rpc.registry.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import top.naive_projects.rpc.common.URL;
import top.naive_projects.rpc.common.utils.StringUtils;
import top.naive_projects.rpc.config.RegistryConfig;
import top.naive_projects.rpc.registry.RegistryService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/9 下午8:37
 */
public class ZookeeperRegistry implements RegistryService {

    private static final int DEFAULT_SESSION_TIMEOUT = 500000;
    private final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

    @Override
    public void register(RegistryConfig registryConfig, URL url) {
        String host = registryConfig.getHost();
        Integer port = registryConfig.getPort();

        try {
            ZooKeeper zooKeeper = getConnection(host, port);
            createNode(zooKeeper, url);
            closeConnection(zooKeeper);
        } catch (IOException | BrokenBarrierException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unregister(URL url) {

    }

    @Override
    public List<String> poll(RegistryConfig registryConfig, String interfaceName) {
        String host = registryConfig.getHost();
        Integer port = registryConfig.getPort();

        List<String> result = new ArrayList<>();
        try {
            ZooKeeper zooKeeper = getConnection(host, port);
            result.addAll(getData(zooKeeper, interfaceName));
            closeConnection(zooKeeper);
        } catch (IOException | BrokenBarrierException
                | InterruptedException | KeeperException e) {
            e.printStackTrace();
        }

        return result;
    }

    private ZooKeeper getConnection(String host, Integer port) throws IOException,
            BrokenBarrierException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper(host + ":" + port, DEFAULT_SESSION_TIMEOUT, event -> {
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });
        cyclicBarrier.await();

        return zooKeeper;
    }

    private void createNode(ZooKeeper zooKeeper, URL url) {
        createNode(zooKeeper, composePath(url.getServiceInterfaceName()) + "/"
                        + url.getProtocol() + ":" + url.getHost(), url.url2string());
    }

    private void createNode(ZooKeeper zooKeeper, String path, String value) {
        try {
            if (path.length() > 0 && zooKeeper.exists(path, false) == null) {
                String tmp = StringUtils.greedSubstring(0, "/", path);
                createNode(zooKeeper, tmp, StringUtils.lastSubstring("/", "", tmp));
                zooKeeper.create(path, value.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String composePath(String interfaceName) {
        return "/rpc" + "/" + interfaceName + "/providers";
    }

    private List<String> getData(ZooKeeper zooKeeper, String interfaceName)
            throws KeeperException, InterruptedException {
        Stat stat = new Stat();
        String path = composePath(interfaceName);
        List<String> result = new ArrayList<>();
        List<String> children = zooKeeper.getChildren(path, false);
        children.forEach(child -> {
            try {
                result.add(new String(zooKeeper.getData(path + "/" + child, false, stat)));
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        return result;
    }

    private void closeConnection(ZooKeeper zooKeeper) throws InterruptedException {
        zooKeeper.close();
    }
}
