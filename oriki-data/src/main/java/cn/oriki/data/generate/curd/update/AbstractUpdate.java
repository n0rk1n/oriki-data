package cn.oriki.data.generate.curd.update;

import cn.oriki.data.generate.curd.from.From;
import cn.oriki.data.generate.curd.where.Where;
import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractUpdate implements Update {

    private Where where;
    private From from;

    private Map<String, Serializable> setParams;

    public AbstractUpdate(Where where, From from) {
        this.where = where;
        this.from = from;
        setParams = Maps.newHashMap();
    }

    @Override
    public void update(String key, Serializable value) {
        if (Objects.isNull(setParams)) {
            setParams = Maps.newHashMap();
        }
    }

    public Where getWhere() {
        return where;
    }

    public From getFrom() {
        return from;
    }

    public Map<String, Serializable> getSetParams() {
        return Collections.unmodifiableMap(setParams); // 不可被修改映射
    }

}
