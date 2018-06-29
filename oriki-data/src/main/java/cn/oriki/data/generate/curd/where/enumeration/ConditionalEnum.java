package cn.oriki.data.generate.curd.where.enumeration;

public enum ConditionalEnum {

    EQUALS(" = "), GREATER_THAN(" > "), LESS_THAN(" < ");

    private String conditional;

    ConditionalEnum(String condition) {
        this.conditional = condition;
    }

    public String getConditional() {
        return conditional;
    }

}
