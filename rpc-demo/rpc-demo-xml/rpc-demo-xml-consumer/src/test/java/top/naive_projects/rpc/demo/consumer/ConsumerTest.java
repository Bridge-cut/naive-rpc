package top.naive_projects.rpc.demo.consumer;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/2/18 下午2:38
 */
public class ConsumerTest {

    interface A {
        public void say();
    }

    static class InvocationHandlerDemo implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.err.println("InvocationHandlerDemo");
            return null;
        }
    }

    @Test
    public void main() {
        A proxy = (A) Proxy.newProxyInstance(A.class.getClassLoader(),
                new Class[]{ A.class }, new InvocationHandlerDemo());
        proxy.say();
    }
}