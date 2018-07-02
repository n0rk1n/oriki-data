package cn.oriki.data.generate.curd.predicate;

import cn.oriki.data.generate.curd.sort.Sort;
import cn.oriki.data.generate.curd.where.Where;

public class AbstractPredicate implements Predicate {

    private Where where;
    private Sort sort;

    public AbstractPredicate(Where where, Sort sort) {
        this.where = where;
        this.sort = sort;
    }

    public Where getWhere() {
        return where;
    }

    public void setWhere(Where where) {
        this.where = where;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

}
