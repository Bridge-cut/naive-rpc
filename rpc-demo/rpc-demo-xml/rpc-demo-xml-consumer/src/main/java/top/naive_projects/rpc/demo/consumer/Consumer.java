package top.naive_projects.rpc.demo.consumer;

import top.naive_projects.rpc.common.utils.Resources;
import top.naive_projects.rpc.config.Configuration;
import top.naive_projects.rpc.config.builders.NaiveRpcStarterBuilder;
import top.naive_projects.rpc.demo.DemoService;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/11 下午2:57
 */
public class Consumer {

    public static void main(String[] args) throws IOException {
        String resource = "rpc-consumer.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        Configuration configuration = NaiveRpcStarterBuilder.build(inputStream);
        configuration.start();

        DemoService demoService = (DemoService) configuration.getBean("demoService");
        System.err.println(demoService.sayHello("Bridge-cut"));

        System.in.read();
    }

}
