package cn.oriki.datasource.jpa.container.imp;

import cn.oriki.datasource.jpa.container.Container;
import com.google.common.collect.Maps;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * 简单 Container 实现
 *
 * @author oriki.wang
 */
public class SimpleContainerImpl implements Container {

    /**
     * 容器映射
     */
    private Map<String, DataSource> contains;

    public SimpleContainerImpl() {
        checkContains();
    }

    @Override
    public Map<String, DataSource> getContains() {
        checkContains();
        return Collections.unmodifiableMap(contains);
    }

    @Override
    public void setContain(String key, DataSource dataSource) {
        checkContains();
        this.contains.put(key, dataSource);
    }

    private void checkContains() {
        if (Objects.isNull(contains)) {
            contains = Maps.newHashMap();
        }
    }

}
