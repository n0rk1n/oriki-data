package cn.oriki.data.generate.base.where.enumeration;

public enum ConditionalEnum {

    EQUALS(" = "), GREATER_THAN(" > "), LESS_THAN(" < "), IS(" IS "), IS_NOT(" IS NOT ");

    private String conditional;

    ConditionalEnum(String condition) {
        this.conditional = condition;
    }

    public String getConditional() {
        return conditional;
    }

}
