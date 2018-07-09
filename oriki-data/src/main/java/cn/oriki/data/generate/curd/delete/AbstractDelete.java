package cn.oriki.data.generate.curd.delete;

import cn.oriki.data.generate.Generate;
import cn.oriki.data.generate.base.from.AbstractFrom;
import cn.oriki.data.generate.base.from.From;
import cn.oriki.data.generate.base.where.AbstractWhere;
import cn.oriki.data.generate.base.where.Where;
import cn.oriki.data.generate.base.where.entity.Criteria;

public abstract class AbstractDelete implements Delete, Where, From, Generate {

    private AbstractWhere where;
    private AbstractFrom from;

    public AbstractDelete(AbstractWhere where, AbstractFrom from) {
        this.where = where;
        this.from = from;
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

    @Override
    public void from(String fromName) {
        this.from.from(fromName);
    }

    @Override
    public String getFromName() {
        return this.from.getFromName();
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

}
