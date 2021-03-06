package cn.oriki.data.generate.curd.cud.save;

import cn.oriki.data.generate.Generate;
import cn.oriki.data.generate.base.from.AbstractFrom;
import cn.oriki.data.generate.base.from.From;
import com.google.common.collect.Maps;
import lombok.Getter;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractSave implements Save, From, Generate {

    /**
     * TODO 批量插入，应换成 List<Serializable> ，设计考虑只用单值
     */
    private Map<String, Serializable> params;
    @Getter
    private AbstractFrom from;

    public AbstractSave(AbstractFrom from) {
        check();
        this.from = from;
    }

    @Override
    public void save(String key, Serializable value) {
        check();
        // 添加
        params.put(key, value);
    }

    @Override
    public void setFromName(String fromName) {
        this.from.setFromName(fromName);
    }

    @Override
    public String getFromName() {
        return this.from.getFromName();
    }

    private void check() {
        // 如果 params 为空，创建并添加
        if (Objects.isNull(params)) {
            params = Maps.newHashMap();
        }
    }

    public Map<String, Serializable> getParams() {
        return Collections.unmodifiableMap(params);
    }

}
