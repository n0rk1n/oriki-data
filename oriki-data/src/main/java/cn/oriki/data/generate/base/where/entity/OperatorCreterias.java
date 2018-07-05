package cn.oriki.data.generate.base.where.entity;

import cn.oriki.data.generate.base.where.enumeration.OperatorEnum;

import java.util.List;

public class OperatorCreterias {

    private OperatorEnum operator; // 连接符 （and | or）
    private List<Criteria> criterias; // 条件集合

    public OperatorEnum getOperator() {
        return operator;
    }

    public void setOperator(OperatorEnum operator) {
        this.operator = operator;
    }

    public List<Criteria> getCriterias() {
        return criterias;
    }

    public void setCriterias(List<Criteria> criterias) {
        this.criterias = criterias;
    }

}
