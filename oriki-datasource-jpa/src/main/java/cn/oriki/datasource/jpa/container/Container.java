package cn.oriki.datasource.jpa.container;

import com.google.common.collect.Maps;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * 数据源容器接口
 *
 * @author oriki.wang
 */
public interface Container {

    /**
     * 获取不可变容器方法
     *
     * @return 容器对象，不可修改
     */
    Map<String, DataSource> getContains();

    /**
     * 添加数据源映射方法
     *
     * @param key        键
     * @param dataSource 数据源
     */
    void setContain(String key, DataSource dataSource);

}
