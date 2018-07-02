package cn.oriki.data.generate.curd.sort;

import cn.oriki.data.generate.Generate;
import cn.oriki.data.generate.curd.sort.enumeration.Direction;

public interface Sort extends Generate {

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
