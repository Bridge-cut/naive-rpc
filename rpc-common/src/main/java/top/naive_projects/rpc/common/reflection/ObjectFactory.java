package top.naive_projects.rpc.common.reflection;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/8 下午7:55
 */
public class ObjectFactory {

    public static Object createObject(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException
                | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object createObject(Class<?> clazz, Object... args) {
        try {
            List<Class<?>> parameterTypes = new ArrayList<>();
            for (Object each : args) {
                parameterTypes.add(each.getClass());
            }
            return clazz.getDeclaredConstructor(parameterTypes.toArray(new Class[0])).newInstance(args);
        } catch (InstantiationException | IllegalAccessException
                | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
