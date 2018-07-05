package cn.oriki.data.generate.base.sort;

import cn.oriki.data.generate.base.sort.enumeration.Direction;

public interface Sort {

    void addSort(String key, Direction direction);

    default void orderAsc(String... keys) {
        for (String key : keys) {
            addSort(key, Direction.ASC);
        }
    }

    default void orderDesc(String... keys) {
        for (String key : keys) {
            addSort(key, Direction.DESC);
        }
    }

    Integer size();

}
