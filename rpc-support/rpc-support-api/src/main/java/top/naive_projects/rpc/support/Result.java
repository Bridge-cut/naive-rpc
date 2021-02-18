package top.naive_projects.rpc.support;

import java.io.Serializable;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/11 上午8:46
 */
public class Result implements Serializable {

    private final Object returnValue;

    public Result(Object returnValue) {
        this.returnValue = returnValue;
    }

    public Object getReturnValue() {
        return returnValue;
    }
}
