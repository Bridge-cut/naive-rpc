package top.naive_projects.rpc.support.http;

import top.naive_projects.rpc.support.Client;
import top.naive_projects.rpc.support.Invocation;
import top.naive_projects.rpc.support.Result;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/11 下午9:33
 */
public class HttpClient implements Client {

    @Override
    public Object send(Invocation invocation) {
        String host = invocation.getProps("host");
        String port = invocation.getProps("port");

        Result result = null;
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try {
            URL url = new URL("http", host, Integer.parseInt(port), "/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            outputStream = new ObjectOutputStream(connection.getOutputStream());
            outputStream.writeObject(invocation);
            outputStream.flush();

            inputStream = new ObjectInputStream(connection.getInputStream());
            result = (Result) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }
}
