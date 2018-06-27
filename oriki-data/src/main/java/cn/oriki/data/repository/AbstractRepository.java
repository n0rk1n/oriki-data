package cn.oriki.data.repository;

import java.io.Serializable;

public abstract class AbstractRepository<T, ID extends Serializable> implements PagingAndSortRepository<T, ID> {
}
