package top.naive_projects.rpc.registry.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;
import top.naive_projects.rpc.common.utils.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/10 下午4:20
 */
public class ZookeeperRegistryTest {

    @Test
    public void register() throws InterruptedException, IOException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper("10.211.55.43:2181", 5000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                    System.err.println("connection build success");
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();
        System.err.println(zooKeeper.getSessionId());

        String path = "/rpc/top.naive_projects.rpc.demo.DemoService/providers/128.0.0.1";
        createNode(zooKeeper, path, "injvm://127.0.0.1:21880");

        zooKeeper.close();
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

    @Test
    public void unregister() {

    }

    @Test
    public void poll() throws IOException, InterruptedException, KeeperException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper("10.211.55.43:2181", 5000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                    System.err.println("connection build success");
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();

        String path = "/rpc/top.naive_projects.rpc.demo.DemoService/providers";
        List<String> result = new ArrayList<>();
        List<String> children = zooKeeper.getChildren(path, false);
        Stat stat = new Stat();
        children.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                try {
                    result.add(new String(zooKeeper.getData(path + "/" + s, false, stat)));
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        System.err.println(result);
    }
}