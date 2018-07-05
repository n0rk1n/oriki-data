package cn.oriki.data.generate.base.predicate;

import cn.oriki.data.generate.base.pageable.AbstractPageable;
import cn.oriki.data.generate.base.pageable.Pageable;
import cn.oriki.data.generate.base.sort.AbstractSort;
import cn.oriki.data.generate.base.sort.Sort;
import cn.oriki.data.generate.base.sort.enumeration.Direction;
import cn.oriki.data.generate.base.where.AbstractWhere;
import cn.oriki.data.generate.base.where.Where;
import cn.oriki.data.generate.base.where.entity.Criteria;

public abstract class AbstractPredicate implements Predicate, Where, Sort, Pageable {

    private AbstractWhere where;
    private AbstractSort sort;
    private AbstractPageable pageable;

    public AbstractPredicate(AbstractWhere where, AbstractSort sort, AbstractPageable pageable) {
        this.where = where;
        this.sort = sort;
        this.pageable = pageable;
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public Integer getPageNumber() {
        return this.pageable.getPageNumber();
    }

    @Override
    public Integer getPageSize() {
        return this.pageable.getPageSize();
    }

    @Override
    public void addSort(String key, Direction direction) {
        this.sort.addSort(key, direction);
    }

    @Override
    public Integer size() {
        return this.sort.size();
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

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public AbstractWhere getWhere() {
        return where;
    }

    public void setWhere(AbstractWhere where) {
        this.where = where;
    }

    @Override
    public AbstractSort getSort() {
        return sort;
    }

    public void setSort(AbstractSort sort) {
        this.sort = sort;
    }

    @Override
    public AbstractPageable getPageable() {
        return pageable;
    }

    public void setPageable(AbstractPageable pageable) {
        this.pageable = pageable;
    }

}
