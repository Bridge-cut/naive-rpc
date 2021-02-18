package top.naive_projects.rpc.common.utils;

import org.junit.Test;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/11 下午2:40
 */
public class InternetUtilsTest {

    @Test
    public void getLocalHostAddress() {
        System.err.println(InternetUtils.getLocalHostAddress());
    }
}