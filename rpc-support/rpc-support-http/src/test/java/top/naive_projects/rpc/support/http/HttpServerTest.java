package top.naive_projects.rpc.support.http;

import org.apache.catalina.LifecycleException;
import org.junit.Test;
import top.naive_projects.rpc.common.URL;

import java.io.IOException;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/10 下午10:11
 */
public class HttpServerTest {

    @Test
    public void startServer() throws IOException, LifecycleException {
        URL url = new URL("http", "127.0.0.1",
                -1, "javax.servlet.http.HttpServlet",
                "top.naive_projects.rpc.support.http.DispatcherServlet");
        HttpServer.startServer(url);
        System.in.read();
    }
}