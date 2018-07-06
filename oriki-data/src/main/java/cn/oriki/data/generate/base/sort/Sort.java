package cn.oriki.data.generate.base.sort;

import cn.oriki.data.generate.base.sort.enumeration.Direction;

public interface Sort {

    void addSort(String key, Direction direction);

    default void orderAsc(String... keys) { // 为防止 keys 相同会出现随机排序问题（某些情况），建议 keys 传值 > 2
        for (String key : keys) {
            addSort(key, Direction.ASC);
        }
    }

    default void orderDesc(String... keys) { // 为防止 keys 相同会出现随机排序问题（某些情况），建议 keys 传值 > 2
        for (String key : keys) {
            addSort(key, Direction.DESC);
        }
    }

    Integer size(); // 用于判断是否有排序条件，传 size （比起 boolean ） 更通用一些

}
