package cn.oriki.data.generate.base.pageable;

public interface Pageable {

    Integer getPageNumber();

    Integer getPageSize();

    void set(Integer pageNumber, Integer pageSize);

}
