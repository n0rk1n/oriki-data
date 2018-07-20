package cn.oriki.data.generate.curd.cud.delete;

import cn.oriki.data.generate.Generate;
import cn.oriki.data.generate.base.from.AbstractFrom;
import cn.oriki.data.generate.base.predicate.crd.AbstractCRDPredicate;
import cn.oriki.data.generate.curd.cud.AbstractCRD;

/**
 * @author oriki.wang
 */
public abstract class AbstractDelete extends AbstractCRD implements Delete, Generate {

    public AbstractDelete(AbstractCRDPredicate predicate, AbstractFrom from) {
        super(predicate, from);
    }

}
