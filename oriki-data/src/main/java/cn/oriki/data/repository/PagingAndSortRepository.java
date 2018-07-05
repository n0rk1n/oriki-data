package cn.oriki.data.repository;

import cn.oriki.data.generate.base.predicate.AbstractPredicate;
import cn.oriki.data.generate.exception.GenerateException;

import java.io.Serializable;

public interface PagingAndSortRepository<T, ID extends Serializable> extends CURDRepository<T, ID> {

    Iterable<T> query(AbstractPredicate predicate) throws GenerateException;

    <S extends T> Long count(AbstractPredicate predicate) throws GenerateException;

}
