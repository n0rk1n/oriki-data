package cn.oriki.data.generate.curd.query;

import cn.oriki.data.generate.Generate;
import cn.oriki.data.generate.base.from.AbstractFrom;
import cn.oriki.data.generate.base.pageable.Pageable;
import cn.oriki.data.generate.base.predicate.AbstractPredicate;
import cn.oriki.data.generate.base.predicate.Predicate;
import cn.oriki.data.generate.base.sort.Sort;
import cn.oriki.data.generate.base.sort.enumeration.Direction;
import cn.oriki.data.generate.base.where.Where;
import cn.oriki.data.generate.base.where.entity.Criteria;

public abstract class AbstractQuery implements Query, Predicate, Generate {

    private AbstractPredicate predicate;
    private AbstractFrom from;

    public AbstractQuery(AbstractPredicate predicate, AbstractFrom from) {
        this.predicate = predicate;
        this.from = from;
    }

    @Override
    public Where getWhere() {
        return this.predicate.getWhere();
    }

    @Override
    public Sort getSort() {
        return this.predicate.getSort();
    }

    @Override
    public Pageable getPageable() {
        return this.predicate.getPageable();
    }

    @Override
    public void clear() {
        this.predicate.getWhere().clear();
    }

    @Override
    public void andCriteria(Criteria... criterias) {
        this.predicate.getWhere().andCriteria(criterias);
    }

    @Override
    public void orCriteria(Criteria... criterias) {
        this.predicate.getWhere().orCriteria(criterias);
    }

    @Override
    public Integer getPageNumber() {
        return this.predicate.getPageable().getPageNumber();
    }

    @Override
    public Integer getPageSize() {
        return this.predicate.getPageable().getPageSize();
    }

    @Override
    public void sort(String key, Direction direction) {
        this.predicate.getSort().sort(key, direction);
    }

    @Override
    public Integer sortSize() {
        return this.predicate.getSort().sortSize();
    }

    @Override
    public void set(Integer pageNumber, Integer pageSize) {
        this.predicate.set(pageNumber, pageSize);
    }

    // setter & getter
    public AbstractPredicate getPredicate() {
        return predicate;
    }

    public void setPredicate(AbstractPredicate predicate) {
        this.predicate = predicate;
    }

    public AbstractFrom getFrom() {
        return from;
    }

    public void setFrom(AbstractFrom from) {
        this.from = from;
    }

}
