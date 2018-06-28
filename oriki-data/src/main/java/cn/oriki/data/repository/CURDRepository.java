package cn.oriki.data.repository;

import cn.oriki.data.generate.curd.delete.result.DeleteResult;
import cn.oriki.data.generate.curd.save.result.SaveResult;
import cn.oriki.data.generate.exception.GenerateException;

import java.io.Serializable;

public interface CURDRepository<T, ID extends Serializable> extends Repository<T, ID> {

    <S extends T> SaveResult<S, ID> save(S entity) throws GenerateException, IllegalAccessException;

    DeleteResult deleteById(ID id) throws GenerateException;

    DeleteResult delete(T entity) throws GenerateException;

    DeleteResult deleteAll() throws GenerateException;

}