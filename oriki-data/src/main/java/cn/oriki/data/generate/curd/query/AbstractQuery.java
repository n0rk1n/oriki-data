package cn.oriki.data.generate.curd.query;

import cn.oriki.data.generate.curd.from.From;
import cn.oriki.data.generate.curd.predicate.Predicate;

public abstract class AbstractQuery implements Query {

    private Predicate predicate;

    private From from;

    public AbstractQuery(Predicate predicate, From from) {
        this.predicate = predicate;
        this.from = from;
    }

    /*private Where where;*/

    /*public AbstractQuery(Where where, From from) {
        this.where = where;
        this.from = from;
    }

    public Where getWhere() {
        return where;
    }

    public From getFrom() {
        return from;
    }*/

    public Predicate getPredicate() {
        return predicate;
    }

    public void setPredicate(Predicate predicate) {
        this.predicate = predicate;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

}
