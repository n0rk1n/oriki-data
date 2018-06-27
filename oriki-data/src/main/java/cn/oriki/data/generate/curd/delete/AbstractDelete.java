package cn.oriki.data.generate.curd.delete;

import cn.oriki.data.generate.curd.from.From;
import cn.oriki.data.generate.curd.where.Where;

public abstract class AbstractDelete implements Delete {

    private Where where;
    private From from;

    public AbstractDelete(Where where, From from) {
        this.where = where;
        this.from = from;
    }

    @Override
    public void deleteAll() {
        this.where.clear();
    }

    public Where getWhere() {
        return where;
    }

    public From getFrom() {
        return from;
    }

}
