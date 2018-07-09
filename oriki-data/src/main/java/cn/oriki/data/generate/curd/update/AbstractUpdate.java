package cn.oriki.data.generate.curd.update;

import cn.oriki.data.generate.Generate;
import cn.oriki.data.generate.base.from.AbstractFrom;
import cn.oriki.data.generate.base.from.From;
import cn.oriki.data.generate.base.where.AbstractWhere;
import cn.oriki.data.generate.base.where.Where;
import cn.oriki.data.generate.base.where.entity.Criteria;
import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractUpdate implements Update, Where, From, Generate {

    private AbstractWhere where;
    private AbstractFrom from;
    private Map<String, Serializable> setParams;

    public AbstractUpdate(AbstractWhere where, AbstractFrom from) {
        this.where = where;
        this.from = from;
        checkSetParams();
    }

    @Override
    public void update(String key, Serializable value) {
        checkSetParams();
        setParams.put(key, value);
    }

    @Override
    public void from(String fromName) {
        this.from.from(fromName);
    }

    @Override
    public String getFromName() {
        return this.from.getFromName();
    }

    @Override
    public void clear() {
        this.where.clear();
    }

    @Override
    public void andCriteria(Criteria... criterias) {
        this.where.andCriteria(criterias);
    }

    @Override
    public void orCriteria(Criteria... criterias) {
        this.where.orCriteria(criterias);
    }

    private void checkSetParams() {
        if (Objects.isNull(setParams)) {
            setParams = Maps.newHashMap();
        }
    }

    public AbstractWhere getWhere() {
        return where;
    }

    public void setWhere(AbstractWhere where) {
        this.where = where;
    }

    public AbstractFrom getFrom() {
        return from;
    }

    public void setFrom(AbstractFrom from) {
        this.from = from;
    }

    public Map<String, Serializable> getSetParams() {
        return Collections.unmodifiableMap(setParams); // 不可被修改映射
    }

}
