package top.naive_projects.rpc.support.http;

import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import top.naive_projects.rpc.common.URL;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/10 下午9:15
 */
public class HttpServer {

    public static void startServer(URL url) throws LifecycleException, IOException {
        Tomcat tomcat = new Tomcat();

        Connector connector = tomcat.getConnector();
        if (url.getPort() == -1) {
            url.setPort(new ServerSocket(0).getLocalPort());
        }
        connector.setPort(url.getPort());
        connector.setProperty("URIEncoding", "UTF-8");
        connector.setProperty("connectionTimeout", "60000");

        String baseDir = new File(System.getProperty("java.io.tmpdir")).getAbsolutePath();
        tomcat.setBaseDir(baseDir);
        tomcat.setPort(url.getPort());

        Context context = tomcat.addContext("/", baseDir);
        Tomcat.addServlet(context, "dispatcher", new DispatcherServlet());
        context.addServletMappingDecoded("/*", "dispatcher");

        tomcat.start();
    }
}
