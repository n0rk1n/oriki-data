package cn.oriki.data.generate.curd.predicate;

import cn.oriki.data.generate.curd.pageable.Pageable;
import cn.oriki.data.generate.curd.sort.Sort;
import cn.oriki.data.generate.curd.where.Where;

public class AbstractPredicate implements Predicate {

    private Where where;
    private Sort sort;
    private Pageable pageable;

    public AbstractPredicate(Where where, Sort sort, Pageable pageable) {
        this.where = where;
        this.sort = sort;
        this.pageable = pageable;
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

    @Override
    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

}
