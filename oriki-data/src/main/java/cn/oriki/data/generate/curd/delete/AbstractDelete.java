package cn.oriki.data.generate.curd.delete;

import cn.oriki.data.generate.base.from.AbstractFrom;
import cn.oriki.data.generate.base.where.AbstractWhere;

public abstract class AbstractDelete implements Delete {

    private AbstractWhere where;
    private AbstractFrom from;

    public AbstractDelete(AbstractWhere where, AbstractFrom from) {
        this.where = where;
        this.from = from;
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
