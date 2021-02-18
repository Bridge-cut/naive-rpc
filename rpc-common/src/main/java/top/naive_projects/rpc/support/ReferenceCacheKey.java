package top.naive_projects.rpc.support;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/8 下午7:32
 */
public class ReferenceCacheKey extends CacheKey {

    private static final String TYPE = "reference";

    public ReferenceCacheKey() {
        super();
    }

    public ReferenceCacheKey(Object[] objects) {
        super(objects);
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
