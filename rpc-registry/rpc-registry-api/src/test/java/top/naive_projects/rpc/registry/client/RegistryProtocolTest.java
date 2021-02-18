package top.naive_projects.rpc.registry.client;

import org.junit.Test;
import top.naive_projects.rpc.common.utils.StringUtils;

import java.io.IOException;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/11 下午8:37
 */
public class RegistryProtocolTest {

    @Test
    public void refer() throws IOException {
        String url = "injvm://127.0.0.1:6713/top.naive_projects.rpc.common.ClassLoaderWrapper";
        String protocol = StringUtils.substring(":", url);
        String host = StringUtils.substring("://", ":", url);
        Integer port = StringUtils.parseInteger(StringUtils.lastSubstring(":", "/", url));
        String serviceClassName = StringUtils.lastSubstring("/", "", url);

        System.in.read();
    }
}