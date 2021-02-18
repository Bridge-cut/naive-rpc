package top.naive_projects.rpc.demo.provider;

import top.naive_projects.rpc.demo.GreetingService;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/7 下午7:37
 */
public class GreetingServiceImpl implements GreetingService {

    @Override
    public String hello() {
        return "Greetings!";
    }
}
