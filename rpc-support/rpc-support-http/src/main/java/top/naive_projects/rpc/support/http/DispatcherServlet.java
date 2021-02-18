package top.naive_projects.rpc.support.http;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/10 下午10:25
 */
public class DispatcherServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        new HttpServerHandler().handle(request, response);
    }
}
