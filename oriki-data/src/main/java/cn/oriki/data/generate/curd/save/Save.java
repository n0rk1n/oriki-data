package cn.oriki.data.generate.curd.save;

import java.io.Serializable;

public interface Save {

    void save(String key, Serializable value); // 来源名，可以是列名或者键名

}
