package cn.oriki.data.generate.curd.query;

import cn.oriki.data.generate.curd.from.From;
import cn.oriki.data.generate.curd.where.Where;

public abstract class AbstractQuery implements Query {

    private Where where;

    private From from;

    public AbstractQuery(Where where, From from) {
        this.where = where;
        this.from = from;
    }

    public Where getWhere() {
        return where;
    }

    public From getFrom() {
        return from;
    }

}
