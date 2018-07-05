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

    public AbstractFrom getFrom() {
        return from;
    }
    
}
