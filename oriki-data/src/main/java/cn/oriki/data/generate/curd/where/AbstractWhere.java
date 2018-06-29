package cn.oriki.data.generate.curd.where;

import cn.oriki.data.generate.curd.where.entity.Criteria;
import cn.oriki.data.generate.curd.where.entity.OperatorCreterias;
import cn.oriki.data.generate.curd.where.enumeration.OperatorEnum;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractWhere implements Where {

    private LinkedList<OperatorCreterias> operatorCreterias; // 条件列表

    public AbstractWhere() {
        operatorCreterias = Lists.newLinkedList();
    }

    @Override
    public void clear() {
        if (Objects.isNull(operatorCreterias)) {
            this.operatorCreterias = Lists.newLinkedList();
        } else if (this.operatorCreterias.size() > 0) {
            this.operatorCreterias.clear();
        }
    }

    @Override
    public void andCriteria(Criteria... criterias) {
        if (criterias.length > 0) {
            OperatorCreterias creterias = createOperatorCreteria(OperatorEnum.AND, criterias);
            this.operatorCreterias.addLast(creterias); // and 条件添加到结尾
        }
    }

    @Override
    public void orCriteria(Criteria... criterias) {
        if (criterias.length > 0) {
            OperatorCreterias creterias = createOperatorCreteria(OperatorEnum.OR, criterias);
            this.operatorCreterias.addFirst(creterias); // or 条件添加到开头
        }
    }

    // 添加条件，关系符使用 operator
    private OperatorCreterias createOperatorCreteria(OperatorEnum operator, Criteria... keyConditionalValues) {
        OperatorCreterias operatorCreteria = new OperatorCreterias();
        {
            operatorCreteria.setOperator(operator);
            operatorCreteria.setCriterias(Lists.newArrayList(keyConditionalValues));
        }
        return operatorCreteria;
    }

    public List<OperatorCreterias> getOperatorCreterias() {
        return Collections.unmodifiableList(operatorCreterias);
    }

}
