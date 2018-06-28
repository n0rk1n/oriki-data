package cn.oriki.data.generate.curd.save;

import cn.oriki.data.generate.Generate;

import java.io.Serializable;

public interface Save extends Generate {

    void save(String key, Serializable value); // 来源名，可以是列名或者键名

}
