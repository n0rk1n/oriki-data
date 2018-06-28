package cn.oriki.data.repository;

import cn.oriki.data.generate.curd.delete.result.DeleteResult;
import cn.oriki.data.generate.curd.save.result.SaveResult;
import cn.oriki.data.generate.curd.update.result.UpdateResult;
import cn.oriki.data.generate.exception.GenerateException;

import java.io.Serializable;
import java.util.Collection;

public interface CURDRepository<T, ID extends Serializable> extends Repository<T, ID> {

    <S extends T> SaveResult<S, ID> save(S entity) throws GenerateException, IllegalAccessException;

    DeleteResult deleteById(ID id) throws GenerateException;

    DeleteResult delete(T entity) throws GenerateException;

    DeleteResult deleteAll() throws GenerateException;

    <S extends T> UpdateResult update(S entity) throws GenerateException, IllegalAccessException; // 要求方法本身先查（ byId ），不存在执行 save 方法

    T queryById(ID id) throws GenerateException;

    Collection<T> queryByIds(Collection<ID> ids) throws GenerateException;

    Collection<T> queryAll() throws GenerateException;

    boolean exists(ID id) throws GenerateException;

}