package cn.oriki.data.repository;

import java.io.Serializable;

public interface PagingAndSortRepository<T, ID extends Serializable> extends CURDRepository<T, ID> {
}
