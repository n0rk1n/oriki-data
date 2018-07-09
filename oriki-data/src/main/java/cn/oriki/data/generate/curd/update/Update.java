package cn.oriki.data.generate.curd.update;

import java.io.Serializable;

public interface Update {

    void update(String key, Serializable value); // 修改具体 key 为 value

}
