package top.naive_projects.rpc.demo.provider;

import top.naive_projects.rpc.demo.DemoService;
import top.naive_projects.rpc.support.RpcContext;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/7 下午7:37
 */
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {

        return "Hello " + name + ", response from provider: " + RpcContext.getContext();
    }
}
