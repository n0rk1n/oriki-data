package cn.oriki.data.generate.curd.pageable;

public abstract class AbstractPageable implements Pageable {

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

    @Override
    public void addPageParam(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public AbstractPageable(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

}
