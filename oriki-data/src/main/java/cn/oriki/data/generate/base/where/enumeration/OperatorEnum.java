package cn.oriki.data.generate.base.where.enumeration;

public enum OperatorEnum {

    AND(" AND "), OR(" OR ");

    private String operator;

    OperatorEnum(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

}
