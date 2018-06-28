package cn.oriki.data.generate.curd.save;

import cn.oriki.data.generate.curd.from.From;
import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractSave implements Save {

    private From from;
    private Map<String, Serializable> params;

    public AbstractSave(From from) {
        this.from = from;
        params = Maps.newHashMap();
    }

    public String getFromName() {
        return this.from.getFromName();
    }

    @Override
    public void save(String key, Serializable value) {
        // 如果 params 为空，创建并添加
        if (Objects.isNull(params)) {
            params = Maps.newLinkedHashMap();
        }
        // 直接添加
        params.put(key, value);
    }

    public From getFrom() {
        return from;
    }

    public Map<String, Serializable> getParams() {
        return params;
    }

}
