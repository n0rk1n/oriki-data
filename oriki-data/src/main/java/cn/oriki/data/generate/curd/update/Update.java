package cn.oriki.data.generate.curd.update;

import cn.oriki.data.generate.Generate;

import java.io.Serializable;

public interface Update extends Generate {

    void update(String key, Serializable value); // 修改具体 key 为 value

}
