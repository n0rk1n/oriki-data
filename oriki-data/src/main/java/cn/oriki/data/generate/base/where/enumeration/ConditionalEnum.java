package cn.oriki.data.generate.base.where.enumeration;

/**
 * 条件运算符枚举
 *
 * @author oriki
 */
public enum ConditionalEnum {

    /**
     * 等于
     */
    EQUALS(" = "),
    /**
     * 大于
     */
    GREATER_THAN(" > "),
    /**
     * 大于等于
     */
    GREATER_THAN_AND_EQUALS(" >="),
    /**
     * 小于
     */
    LESS_THAN(" < "),
    /**
     * 小于等于
     */
    LESS_THAN_AND_EQUALS(" <= "),
    /**
     * 为空（使用
     */
    IS(" IS "),
    /**
     * 不为空（使用
     */
    IS_NOT(" IS NOT ");

    private String conditional;

    ConditionalEnum(String condition) {
        this.conditional = condition;
    }

    public String getConditional() {
        return conditional;
    }

}
