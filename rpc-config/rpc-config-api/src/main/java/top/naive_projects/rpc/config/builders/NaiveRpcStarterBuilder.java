package top.naive_projects.rpc.config.builders;

import top.naive_projects.rpc.config.Configuration;

import java.io.InputStream;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/8 下午4:25
 */
public class NaiveRpcStarterBuilder {

    public static Configuration build(InputStream inputStream) {
        try (inputStream) {
            RpcConfigBuilder parser = new RpcConfigBuilder(inputStream);
            return parser.parse();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
