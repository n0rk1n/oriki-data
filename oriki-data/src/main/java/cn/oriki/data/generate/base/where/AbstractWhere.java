package cn.oriki.data.generate.base.where;

import cn.oriki.data.generate.Generate;
import cn.oriki.data.generate.base.where.entity.Criteria;
import cn.oriki.data.generate.base.where.entity.OperatorCreterias;
import cn.oriki.data.generate.base.where.enumeration.OperatorEnum;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractWhere implements Where, Generate {

    /**
     * 条件列表
     */
    private LinkedList<OperatorCreterias> operatorCreterias;

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
            OperatorCreterias operatorCreterias = createOperatorCreteria(OperatorEnum.AND, criterias);
            // and 条件添加到结尾
            this.operatorCreterias.addLast(operatorCreterias);
        }
    }

    @Override
    public void orCriteria(Criteria... criterias) {
        if (criterias.length > 0) {
            OperatorCreterias operatorCreterias = createOperatorCreteria(OperatorEnum.OR, criterias);
            // or 条件添加到开头
            this.operatorCreterias.addFirst(operatorCreterias);
        }
    }

    /**
     * 添加条件，关系符使用 operator
     *
     * @param operator  关系符
     * @param criterias 条件
     * @return 关系符+条件
     */
    private OperatorCreterias createOperatorCreteria(OperatorEnum operator, Criteria... criterias) {
        if (Objects.isNull(criterias) || criterias.length == 0) {
            throw new RuntimeException("criteria must not null");
        }

        OperatorCreterias operatorCreteria = new OperatorCreterias();
        operatorCreteria.setOperator(operator);
        operatorCreteria.setCriterias(Lists.newArrayList(criterias));

        return operatorCreteria;
    }

    public List<OperatorCreterias> getOperatorCreterias() {
        return Collections.unmodifiableList(operatorCreterias);
    }

}
