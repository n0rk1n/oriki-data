package cn.oriki.data.generate.curd.save.result;

import java.io.Serializable;
import java.util.Collection;

public class SaveResult<T, ID extends Serializable> {

    private Integer number; // 影响行数

    private Collection<T> entities; // 默认为空

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Collection<T> getEntities() {
        return entities;
    }

    public void setEntities(Collection<T> entities) {
        this.entities = entities;
    }
}
