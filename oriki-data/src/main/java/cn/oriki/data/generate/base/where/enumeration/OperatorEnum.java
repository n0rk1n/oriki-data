package cn.oriki.data.generate.base.where.enumeration;

/**
 * 逻辑运算符枚举
 *
 * @author oriki
 */
public enum OperatorEnum {

    /**
     * 逻辑与
     */
    AND(" AND "),
    /**
     * 逻辑或
     */
    OR(" OR ");

    private String operator;

    OperatorEnum(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

}
