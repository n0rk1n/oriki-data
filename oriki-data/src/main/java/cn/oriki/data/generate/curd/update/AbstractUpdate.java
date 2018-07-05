package cn.oriki.data.generate.curd.update;

import cn.oriki.data.generate.base.from.AbstractFrom;
import cn.oriki.data.generate.base.where.AbstractWhere;
import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractUpdate implements Update {

    private AbstractWhere where;
    private AbstractFrom from;
    private Map<String, Serializable> setParams;

    public AbstractUpdate(AbstractWhere where, AbstractFrom from) {
        this.where = where;
        this.from = from;
        setParams = Maps.newHashMap();
    }

    @Override
    public void update(String key, Serializable value) {
        if (Objects.isNull(setParams)) {
            setParams = Maps.newHashMap();
        }
        setParams.put(key, value);
    }

    public AbstractWhere getWhere() {
        return where;
    }

    public AbstractFrom getFrom() {
        return from;
    }

    public Map<String, Serializable> getSetParams() {
        return Collections.unmodifiableMap(setParams); // 不可被修改映射
    }

}
