package top.naive_projects.rpc.support.http;

import top.naive_projects.rpc.support.Invocation;
import top.naive_projects.rpc.support.Invoker;
import top.naive_projects.rpc.support.Result;
import top.naive_projects.rpc.support.RpcContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/11 上午11:12
 */
public class HttpServerHandler {

    public void handle(HttpServletRequest request, HttpServletResponse response) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(request.getInputStream());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(response.getOutputStream())) {
            Invocation invocation = (Invocation) objectInputStream.readObject();
            String serviceKey = invocation.getServiceKey();
            Invoker<?> invoker = RpcContext.getExporter(serviceKey).getInvoker();
            Result result = invoker.invoke(invocation);

            objectOutputStream.writeObject(result);
            objectOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
