package cn.oriki.data.repository;

import cn.oriki.data.generate.curd.predicate.Predicate;
import cn.oriki.data.generate.exception.GenerateException;

import java.io.Serializable;

public interface PagingAndSortRepository<T, ID extends Serializable> extends CURDRepository<T, ID> {

    Iterable<T> query(Predicate predicate) throws GenerateException;

}
