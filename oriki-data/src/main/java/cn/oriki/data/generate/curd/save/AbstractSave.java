package cn.oriki.data.generate.curd.save;

import cn.oriki.data.generate.Generate;
import cn.oriki.data.generate.base.from.AbstractFrom;
import cn.oriki.data.generate.base.from.From;
import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractSave implements Save, From, Generate {

    private AbstractFrom from;
    private Map<String, Serializable> params; // TODO 批量插入，应换成 List<Serializable> ，设计考虑只用单值

    public AbstractSave(AbstractFrom from) {
        this.from = from;
        check();
    }

    @Override
    public void save(String key, Serializable value) {
        check();
        params.put(key, value); // 添加
    }

    @Override
    public void from(String fromName) {
        this.from.from(fromName);
    }

    @Override
    public String getFromName() {
        return this.from.getFromName();
    }

    private void check() {
        if (Objects.isNull(params)) { // 如果 params 为空，创建并添加
            params = Maps.newHashMap();
        }
    }

    public AbstractFrom getFrom() {
        return from;
    }

    public Map<String, Serializable> getParams() {
        return Collections.unmodifiableMap(params);
    }

}
