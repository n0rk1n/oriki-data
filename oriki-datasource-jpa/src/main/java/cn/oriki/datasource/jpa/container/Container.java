package cn.oriki.datasource.jpa.container;

import com.google.common.collect.Maps;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * 数据源容器
 *
 * @author oriki.wang
 */
public class Container {

    private Map<String, DataSource> contains;

    public Container() {
        checkContains();
    }

    public Map<String, DataSource> getContains() {
        checkContains();
        return Collections.unmodifiableMap(contains);
    }

    public void setContain(String key, DataSource dataSource) {
        checkContains();
        this.contains.put(key, dataSource);
    }

    /**
     * 校验
     */
    private void checkContains() {
        if (Objects.isNull(contains)) {
            contains = Maps.newHashMap();
        }
    }

}
