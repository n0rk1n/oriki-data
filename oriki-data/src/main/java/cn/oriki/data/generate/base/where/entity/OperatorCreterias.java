package cn.oriki.data.generate.base.where.entity;

import cn.oriki.data.generate.base.where.enumeration.OperatorEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OperatorCreterias {

    /**
     * 连接符 （and | or）
     */
    private OperatorEnum operator;
    /**
     * 条件集合
     */
    private List<Criteria> criterias;

}
