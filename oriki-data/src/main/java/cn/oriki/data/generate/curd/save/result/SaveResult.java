package cn.oriki.data.generate.curd.save.result;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
public class SaveResult<T, ID extends Serializable> {

    /**
     * 影响行数
     */
    private Integer number;

    /**
     * 默认为空
     */
    private Collection<T> entities;

}
