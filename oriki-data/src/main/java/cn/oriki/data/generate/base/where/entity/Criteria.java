package cn.oriki.data.generate.base.where.entity;

import cn.oriki.data.generate.base.where.enumeration.ConditionalEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Criteria {

    /**
     * 键，可以是列名或键名
     */
    private String key;
    /**
     * 关系符（等于，小于，大于）
     */
    private ConditionalEnum conditional;
    /**
     * 值（TODO 目前只支持单值，所以 Where 接口中 in 方法进行 or 样式转换）
     */
    private Serializable value;

}
