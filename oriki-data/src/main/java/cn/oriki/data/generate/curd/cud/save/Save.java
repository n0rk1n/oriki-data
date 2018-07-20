package cn.oriki.data.generate.curd.cud.save;

import java.io.Serializable;

public interface Save {

    /**
     * 来源名，可以是列名或者键名
     *
     * @param key   键
     * @param value 存入值
     */
    void save(String key, Serializable value);

}
