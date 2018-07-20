package cn.oriki.data.generate.base.sort;

import cn.oriki.data.generate.base.sort.enumeration.Direction;

import java.util.Arrays;

/**
 * 定义排序接口
 *
 * @author oriki.wang
 */
public interface Sort {

    /**
     * 排序列和排序方向
     *
     * @param key       键
     * @param direction 排序方向
     */
    void sort(String key, Direction direction);

    /**
     * 排序的数量
     * <p>
     * 用于判断是否有排序条件，传 size （比起 boolean ） 更通用一些
     *
     * @return 参与排序列的数量
     */
    Integer sortSize();

    /**
     * 对列进行升序排序
     * <p>
     * 为防止 keys 相同会出现随机排序问题（某些情况），建议 keys 传值 > 2
     *
     * @param keys 列
     */
    default void orderAsc(String... keys) {
        Arrays.stream(keys).forEach((key) -> sort(key, Direction.ASC));
    }

    /**
     * 对列进行降序排序
     * <p>
     * 为防止 keys 相同会出现随机排序问题（某些情况），建议 keys 传值 > 2
     *
     * @param keys 列
     */
    default void orderDesc(String... keys) {
        Arrays.stream(keys).forEach((key) -> sort(key, Direction.DESC));
    }

}
