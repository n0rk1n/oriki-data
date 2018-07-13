package cn.oriki.data.generate.base.predicate;

import cn.oriki.data.generate.base.pageable.AbstractPageable;
import cn.oriki.data.generate.base.sort.AbstractSort;
import cn.oriki.data.generate.base.sort.enumeration.Direction;
import cn.oriki.data.generate.base.where.AbstractWhere;
import cn.oriki.data.generate.base.where.entity.Criteria;
import lombok.Getter;
import lombok.Setter;

/**
 * Predicate 抽象类
 * <p>
 * 只做查询条件预存，不需进行 generate
 *
 * @author oriki.wang
 */
public abstract class AbstractPredicate implements Predicate {

    @Getter
    @Setter
    private AbstractWhere where;

    @Getter
    @Setter
    private AbstractSort sort;
    
    @Getter
    @Setter
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
    public void sort(String key, Direction direction) {
        this.sort.sort(key, direction);
    }

    @Override
    public Integer sortSize() {
        return this.sort.sortSize();
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

    @Override
    public void set(Integer pageNumber, Integer pageSize) {
        this.pageable.set(pageNumber, pageSize);
    }

}
