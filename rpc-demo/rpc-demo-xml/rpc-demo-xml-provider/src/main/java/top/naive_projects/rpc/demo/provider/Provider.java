package top.naive_projects.rpc.demo.provider;

import top.naive_projects.rpc.common.utils.Resources;
import top.naive_projects.rpc.config.builders.NaiveRpcStarterBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/7 下午7:37
 */
public class Provider {

    public static void main(String[] args) throws IOException {
        String resource = "rpc-provider.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        NaiveRpcStarterBuilder.build(inputStream).start();

        System.in.read();
    }
}
