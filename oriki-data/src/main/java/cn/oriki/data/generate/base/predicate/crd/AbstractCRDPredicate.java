package cn.oriki.data.generate.base.predicate.crd;

import cn.oriki.data.generate.base.where.AbstractWhere;
import cn.oriki.data.generate.base.where.entity.Criteria;
import lombok.AllArgsConstructor;
import lombok.Setter;

/**
 * 抽象增删改条件
 *
 * @author oriki.wang
 */
@Setter
@AllArgsConstructor
public abstract class AbstractCRDPredicate implements CRDPredicate {

    private AbstractWhere where;

    @Override
    public AbstractWhere getWhere() {
        return where;
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

}
