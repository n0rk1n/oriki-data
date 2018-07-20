package cn.oriki.data.generate.curd.cud.update;

import java.io.Serializable;

/**
 * Update
 *
 * @author oriki.wang
 */
public interface Update {

    /**
     * 修改具体 key 为 value
     *
     * @param key   键
     * @param value 修改后的值
     */
    void update(String key, Serializable value);

}
