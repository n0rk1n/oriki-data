package cn.oriki.data.generate.curd.cud.update;

import cn.oriki.data.generate.Generate;
import cn.oriki.data.generate.base.from.AbstractFrom;
import cn.oriki.data.generate.base.predicate.crd.AbstractCRDPredicate;
import cn.oriki.data.generate.curd.cud.AbstractCRD;
import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * 抽象 Update
 *
 * @author oriki.wang
 */

public abstract class AbstractUpdate extends AbstractCRD implements Update, Generate {

    private Map<String, Serializable> setParams;

    public AbstractUpdate(AbstractCRDPredicate predicate, AbstractFrom from) {
        super(predicate, from);
        checkSetParams();
    }

    @Override
    public void update(String key, Serializable value) {
        checkSetParams();
        setParams.put(key, value);
    }

    private void checkSetParams() {
        if (Objects.isNull(setParams)) {
            setParams = Maps.newHashMap();
        }
    }

    public Map<String, Serializable> getSetParams() {
        // 不可被修改映射
        return Collections.unmodifiableMap(setParams);
    }

}
