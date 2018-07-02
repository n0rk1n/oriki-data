package cn.oriki.data.generate.curd.pageable;

import cn.oriki.data.generate.Generate;

public interface Pageable extends Generate {

    Integer getPageNumber();

    Integer getPageSize();

    void addPageParam(Integer pageNumber, Integer pageSize);

}
