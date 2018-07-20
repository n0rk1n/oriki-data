package cn.oriki.data.repository;

import cn.oriki.data.generate.base.where.AbstractWhere;
import cn.oriki.data.generate.curd.cud.delete.result.DeleteResult;
import cn.oriki.data.generate.curd.cud.save.result.SaveResult;
import cn.oriki.data.generate.curd.cud.update.result.UpdateResult;
import cn.oriki.data.generate.exception.GenerateException;

import java.io.Serializable;
import java.util.Collection;

public interface CurdRepository<T, ID extends Serializable> extends Repository<T, ID> {

    <S extends T> SaveResult<S, ID> save(S entity) throws GenerateException, IllegalAccessException;

    DeleteResult deleteById(ID id) throws GenerateException;

    DeleteResult delete(T entity) throws GenerateException;

    DeleteResult deleteAll() throws GenerateException;

    /**
     * 要求方法本身先查（ exists ），不存在执行 save 方法
     *
     * @param entity 封装条件的实体
     * @param <S>    实体泛型
     * @return 更新结果对象
     * @throws GenerateException      生成 SQL 异常抛出
     * @throws IllegalAccessException 返回主键存入实体出现异常
     */
    <S extends T> UpdateResult update(S entity) throws GenerateException, IllegalAccessException;

    T queryById(ID id) throws GenerateException;

    Collection<T> queryByIds(Collection<ID> ids) throws GenerateException;

    /**
     * 查询所有
     *
     * @return 查询到的所有对象
     * @throws GenerateException 生成 SQL 失败抛出的异常
     */
    Collection<T> queryAll() throws GenerateException;

    Collection<T> queryAll(AbstractWhere where) throws GenerateException;

    boolean exists(ID id) throws GenerateException;

    <S extends T> Long count(S entity) throws GenerateException;

    Long count(AbstractWhere where) throws GenerateException;

}