package cn.oriki.data.generate.curd.where.entity;

import cn.oriki.data.generate.curd.where.enumeration.ConditionalEnum;

import java.io.Serializable;

public class Criteria {

    private String key; // 键，可以是列名或键名
    private ConditionalEnum conditional; // 关系符（等于，小于，大于）
    private Serializable value; // 值（TODO 目前只支持单表）

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ConditionalEnum getConditional() {
        return conditional;
    }

    public void setConditional(ConditionalEnum conditional) {
        this.conditional = conditional;
    }

    public Serializable getValue() {
        return value;
    }

    public void setValue(Serializable value) {
        this.value = value;
    }

}
