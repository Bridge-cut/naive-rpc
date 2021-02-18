package top.naive_projects.rpc.support.netty;

import top.naive_projects.rpc.common.URL;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/10 下午10:46
 */
public class NettyServer {

    public static void startServer(URL url) throws IOException {
        if (url.getPort() == -1) {
            url.setPort(new ServerSocket(0).getLocalPort());
        }
    }
}
