package cn.oriki.data.generate.base.pageable;

import cn.oriki.data.generate.Generate;

public abstract class AbstractPageable implements Pageable, Generate {

    private Integer pageNumber;
    private Integer pageSize;

    @Override
    public Integer getPageNumber() {
        return pageNumber;
    }

    @Override
    public Integer getPageSize() {
        return pageSize;
    }

    public AbstractPageable(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

}
