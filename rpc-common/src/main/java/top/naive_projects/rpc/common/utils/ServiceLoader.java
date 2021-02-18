package top.naive_projects.rpc.common.utils;

import top.naive_projects.rpc.common.reflection.ObjectFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/12 上午10:45
 */
public class ServiceLoader<T> implements Iterable<T> {

    private static final String PREFIX = "META-INF/rpc_services/";
    private final String key;
    private final Class<T> interfaceClass;
    private final List<T> implementObjects = new ArrayList<>();

    private ServiceLoader(ClassLoader classLoader, Class<T> interfaceClass, String key) {
        this.key = key;
        this.interfaceClass = interfaceClass;
        load(classLoader, interfaceClass, key);
    }

    public static <T> ServiceLoader<T> load(Class<T> interfaceClass, String key) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return new ServiceLoader<>(classLoader, interfaceClass, key);
    }

    @SuppressWarnings("unchecked")
    private void load(ClassLoader classLoader, Class<T> interfaceClass, String key) {
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(Resources.getResourceAsStream(PREFIX + interfaceClass.getName())
                        , StandardCharsets.UTF_8))) {
            String line;
            while (null != (line = bufferedReader.readLine())){
                String[] strings = line.split("=");
                if (strings[0].strip().equals(key)) {
                    String[] values = strings[1].split(",");
                    for (String value : values) {
                        implementObjects
                                .add((T) ObjectFactory.createObject(Resources.classForName(value.strip())));
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public T get(int index) {
        return implementObjects.get(index);
    }

    @Override
    public Iterator<T> iterator() {
        return implementObjects.iterator();
    }
}
