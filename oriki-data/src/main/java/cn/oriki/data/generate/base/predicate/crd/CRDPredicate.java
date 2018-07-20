package cn.oriki.data.generate.base.predicate.crd;

import cn.oriki.data.generate.base.where.Where;
import cn.oriki.data.generate.base.where.entity.Criteria;

public interface CRDPredicate extends Where {

    Where getWhere();

    @Override
    default void clear() {
        getWhere().clear();
    }

    @Override
    default void andCriteria(Criteria... criterias) {
        getWhere().andCriteria(criterias);
    }

    @Override
    default void orCriteria(Criteria... criterias) {
        getWhere().orCriteria(criterias);
    }

}
