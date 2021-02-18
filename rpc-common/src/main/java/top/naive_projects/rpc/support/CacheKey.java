package top.naive_projects.rpc.support;

import top.naive_projects.rpc.common.utils.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/1/8 下午8:30
 */
public abstract class CacheKey {

    private static final int DEFAULT_MULTIPLIER = 37;
    private static final int DEFAULT_HASHCODE = 17;

    private int hashcode;
    private long checksum;
    private final int multiplier;

    // updateList 中所有元素均参与 hashcode 计算
    private List<Object> updateList;
    private int count;

    public CacheKey() {
        this.hashcode = DEFAULT_HASHCODE;
        this.multiplier = DEFAULT_MULTIPLIER;
        this.count = 0;
        this.updateList = new ArrayList<>();

        update(getType());
    }

    public CacheKey(Object[] objects) {
        this();
        updateAll(objects);
    }

    public void updateAll(Object[] objects) {
        for (Object object : objects) {
            update(object);
        }
    }

    /**
     * 更新 hashcode
     *
     * @param object 待 add 进 updateList 的 object，对于原始类型需要事先装箱
     */
    public void update(Object object) {
        int baseHashCode = object == null ? 1 :  ObjectUtils.hashCode(object);

        count++;
        checksum += baseHashCode;
        baseHashCode *= count;

        hashcode = multiplier * hashcode + baseHashCode;

        updateList.add(object);
    }

    public abstract String getType();

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;

        if (!(object instanceof CacheKey)) return false;

        final CacheKey otherKey = (CacheKey) object;

        if (hashcode != otherKey.hashcode || checksum != otherKey.checksum
                || count != otherKey.count) {
            return false;
        }

        for (int i = 0; i < updateList.size(); i++) {
            Object thisObject = updateList.get(i);
            Object thatObject = otherKey.updateList.get(i);

            if (!ObjectUtils.equals(thisObject, thatObject)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        return hashcode;
    }
}
