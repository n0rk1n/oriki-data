package cn.oriki.data.generate.curd.query;

import cn.oriki.data.generate.Generate;

public interface Query extends Generate {

    void query(String key); // 查询特定字段（不推荐使用）

    void query(String key, String alias); // 查询特定 key ，并使用别名

}
