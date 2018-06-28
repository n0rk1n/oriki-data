package cn.oriki.data.generate.curd.save.result;

import java.io.Serializable;
import java.util.Collection;

public class SaveResult<T, ID extends Serializable> {

    private Integer number; // 影响行数

    private Collection<T> entitys; // 默认为空

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Collection<T> getEntitys() {
        return entitys;
    }

    public void setEntitys(Collection<T> entitys) {
        this.entitys = entitys;
    }

}
