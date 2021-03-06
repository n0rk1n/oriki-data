package cn.oriki.data.generate.curd.query;

public interface Query {

    void query(String key); // 查询特定字段（不推荐使用，对于非对应关系数据可能会存在不能赋值情况）

    void query(String key, String alias); // 查询特定 key ，并使用别名

    void count(); // 查询数量

}
