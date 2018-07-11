package cn.oriki.datasource.jpa.container;

import com.google.common.collect.Maps;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class Container {

    private Map<String, DataSource> contains;

    public Container() {
        checkContains();
    }

    public Map<String, DataSource> getContains() {
        return Collections.unmodifiableMap(contains);
    }

    public void setContains(Map<String, DataSource> contains) {
        checkContains();
        this.contains.putAll(contains);
    }

    public void setContains(String key, DataSource dataSource) {
        checkContains();
        this.contains.put(key, dataSource);
    }

    // 校验
    private void checkContains() {
        if (Objects.isNull(contains)) {
            contains = Maps.newHashMap();
        }
    }

}
