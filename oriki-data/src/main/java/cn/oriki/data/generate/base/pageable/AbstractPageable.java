package cn.oriki.data.generate.base.pageable;

import cn.oriki.data.generate.Generate;

public abstract class AbstractPageable implements Pageable, Generate {

    // 当 pageNumber 或者 pageSize 任意一个为 null 的情况，都不会进行排序操作
    private Integer pageNumber;
    private Integer pageSize;

    public AbstractPageable(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    @Override
    public void set(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    @Override
    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
