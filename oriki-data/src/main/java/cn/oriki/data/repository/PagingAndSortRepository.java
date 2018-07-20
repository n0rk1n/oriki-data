package cn.oriki.data.repository;

import cn.oriki.data.generate.base.predicate.AbstractPredicate;
import cn.oriki.data.generate.exception.GenerateException;

import java.io.Serializable;

/**
 * Paging & Sort
 *
 * @param <T>  实体类泛型
 * @param <ID> id 泛型
 * @author oriki.wang
 */
public interface PagingAndSortRepository<T, ID extends Serializable> extends CurdRepository<T, ID> {

    Iterable<T> query(AbstractPredicate predicate) throws GenerateException;

    Long count(AbstractPredicate predicate) throws GenerateException;

}
