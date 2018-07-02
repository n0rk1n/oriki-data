package cn.oriki.data.jpa.generate.curd.pageable;

import cn.oriki.data.generate.curd.pageable.AbstractPageable;

public abstract class AbstractJpaPageable extends AbstractPageable {

    public AbstractJpaPageable(Integer pageNumber, Integer pageSize) {
        super(pageNumber, pageSize);
    }

}
