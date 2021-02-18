package top.naive_projects.rpc.demo.provider;

import org.junit.Test;

import java.net.UnknownHostException;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/9 下午2:48
 */
public class ProviderTest {

    @Test
    public void main() throws UnknownHostException {
        String ans = "123";
        Object[] args = new Object[]{ ans };
        for (Object each : args) {
            System.err.println(ans.getClass());
        }
    }
}