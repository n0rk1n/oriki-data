package cn.oriki.data.generate.curd.query;

import cn.oriki.data.generate.base.from.AbstractFrom;
import cn.oriki.data.generate.base.predicate.AbstractPredicate;

public abstract class AbstractQuery implements Query {

    private AbstractPredicate predicate;
    private AbstractFrom from;

    public AbstractQuery(AbstractPredicate predicate, AbstractFrom from) {

        this.predicate = predicate;
        this.from = from;
    }

    public AbstractPredicate getPredicate() {
        return predicate;
    }

    public void setPredicate(AbstractPredicate predicate) {
        this.predicate = predicate;
    }

    public AbstractFrom getFrom() {
        return from;
    }

}
