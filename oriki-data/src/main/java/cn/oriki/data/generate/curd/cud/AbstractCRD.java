package cn.oriki.data.generate.curd.cud;

import cn.oriki.data.generate.base.from.AbstractFrom;
import cn.oriki.data.generate.base.from.From;
import cn.oriki.data.generate.base.predicate.crd.AbstractCRDPredicate;
import cn.oriki.data.generate.base.predicate.crd.CRDPredicate;
import cn.oriki.data.generate.base.where.AbstractWhere;
import cn.oriki.data.generate.base.where.entity.Criteria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class AbstractCRD implements CRDPredicate, From {

    private AbstractCRDPredicate predicate;
    private AbstractFrom from;

    @Override
    public AbstractWhere getWhere() {
        return predicate.getWhere();
    }

    @Override
    public void clear() {
        this.predicate.clear();
    }

    @Override
    public void andCriteria(Criteria... criterias) {
        this.predicate.andCriteria(criterias);
    }

    @Override
    public void orCriteria(Criteria... criterias) {
        this.predicate.orCriteria(criterias);
    }

    @Override
    public void setFromName(String fromName) {
        this.from.setFromName(fromName);
    }

    @Override
    public String getFromName() {
        return this.from.getFromName();
    }

}
