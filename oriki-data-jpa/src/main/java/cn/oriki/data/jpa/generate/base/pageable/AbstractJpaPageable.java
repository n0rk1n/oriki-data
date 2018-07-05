package cn.oriki.data.jpa.generate.base.pageable;

import cn.oriki.data.generate.base.pageable.AbstractPageable;

public abstract class AbstractJpaPageable extends AbstractPageable {

    public AbstractJpaPageable(Integer pageNumber, Integer pageSize) {
        super(pageNumber, pageSize);
    }

}
