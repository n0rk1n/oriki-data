package cn.oriki.data.jpa.repository;

import cn.oriki.commons.utils.string.StringConverts;
import cn.oriki.commons.utils.string.Strings;
import cn.oriki.data.annotation.Column;
import cn.oriki.data.annotation.Table;
import cn.oriki.data.generate.curd.delete.result.DeleteResult;
import cn.oriki.data.generate.curd.save.result.SaveResult;
import cn.oriki.data.generate.exception.GenerateException;
import cn.oriki.data.generate.result.GenerateResult;
import cn.oriki.data.jpa.generate.curd.delete.AbstractJpaDelete;
import cn.oriki.data.jpa.generate.curd.query.AbstractJpaQuery;
import cn.oriki.data.jpa.generate.curd.save.AbstractJpaSave;
import cn.oriki.data.jpa.generate.curd.update.AbstractJpaUpdate;
import cn.oriki.data.repository.AbstractRepository;
import cn.oriki.data.utils.reflect.ReflectDatas;
import com.google.common.collect.Lists;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

public abstract class AbstractJpaRepository<T, ID extends Serializable> extends AbstractRepository<T, ID> {

    protected JdbcTemplate jdbcTemplate;

    public AbstractJpaRepository(Class T, Class ID) {
        super(T, ID);
    }

    /**
     * 获取表名
     * <p>
     * 如果实体类没有设置 @Table 注解，按照标准类名转换为标准表名（ CustomerInformation -> t_customer_infomation）
     * <p>
     * 如果有 @Table 注解，直接使用注解的 name 属性作为表名
     *
     * @return 表名称
     */
    protected String getTableName() {
        String tableName;
        Table tableAnnotation = (Table) ReflectDatas.getAnnotation(entityClass, Table.class);
        if (Objects.nonNull(tableAnnotation)) {
            tableName = tableAnnotation.name();
        } else {
            String javaClassName = ReflectDatas.getClassName(entityClass); // 截取类名
            tableName = StringConverts.toSQLTableName(javaClassName);
        }

        if (Strings.isBlank(tableName)) {
            throw new IllegalArgumentException(" we can't get tableName , maybe @Table Annotation's name set null or \"\" ;");
        }

        return tableName;
    }

    /**
     * 获取列名
     * 默认获取 @Column 注解的名称
     * 如果没有，自动转换标准列名（ userName -> user_name ）
     *
     * @return 数据库列名称
     */
    public static String getColumnName(Field field) {
        String columnName;
        Column annotation = (Column) ReflectDatas.getAnnotation(field, Column.class);
        if (Objects.nonNull(annotation)) {
            columnName = annotation.name();
        } else {
            columnName = StringConverts.toSQLColumnName(field.getName());
        }

        if (Strings.isBlank(columnName)) {
            throw new IllegalArgumentException(" we can't get columnName , maybe @Column Annotation's name set null or \"\" ;");
        }

        return columnName;
    }

    /**
     * 获取被 @PrimaryKey 标识的数据库列名称
     * <p>
     * 首先获取被 @PrimaryKey 标识的列，获取 Field
     * <p>
     * 获取其上 @Column 注解，获取 name 值；
     * 如果不存在，转换标准列名
     *
     * @return 主键对应数据库列名
     */
    protected String getPrimaryKeyColumnName() {
        Field field = getPrimaryKeyField();
        return this.getColumnName(field);
    }

    /**
     * 执行 delete 方法操作
     *
     * @return delete 操作结果
     */
    protected DeleteResult executeDelete(AbstractJpaDelete delete) throws GenerateException {
        GenerateResult generateResult = delete.generate();
        String sql = generateResult.getGenerateResult();
        List<Serializable> params = generateResult.getParams();

        int i = jdbcTemplate.update(sql, params.toArray());

        // 封装结果
        DeleteResult deleteResult = new DeleteResult();
        deleteResult.setNumber(i);

        return deleteResult;
    }

    /**
     * 执行 update 方法操作
     *
     * @param update
     * @return
     */
    protected int executeUpdate(AbstractJpaUpdate update) throws GenerateException {
        GenerateResult generateResult = update.generate();

        String sql = generateResult.getGenerateResult();
        List<Serializable> params = generateResult.getParams();

        return this.jdbcTemplate.update(sql, params.toArray());
    }

    /**
     * 执行 save 方法操作
     *
     * @param save
     * @return
     */
    protected <S extends T> SaveResult<S, ID> executeSave(AbstractJpaSave save, S entity) throws GenerateException, IllegalAccessException {
        GenerateResult generateResult = save.generate();
        String sql = generateResult.getGenerateResult();
        List<Serializable> params = generateResult.getParams();

        int i = jdbcTemplate.update(sql, params.toArray());

        // 获取 id 并存入实体中
        ID id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", idClass);
        super.putIdToEntity(entity, id);

        // 获取结果
        SaveResult<S, ID> saveResult = new SaveResult<>();
        saveResult.setEntitys(Lists.newArrayList(entity));
        saveResult.setNumber(i);

        return saveResult;
    }

    /**
     * 执行 Query 查询单条数据的时候使用的操作
     *
     * @param query
     * @return
     * @throws GenerateException
     */
    protected T queryOne(AbstractJpaQuery query) throws GenerateException {
        GenerateResult generateResult = query.generate();
        String sql = generateResult.getGenerateResult();
        List<Serializable> params = generateResult.getParams();

        return this.jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<T>(entityClass), params.toArray());
    }

    /**
     * 执行 Query 查询某个类型值的查询
     *
     * @param query
     * @param <S>
     * @return
     * @throws GenerateException
     */
    protected <S> S queryValue(AbstractJpaQuery query, Class<S> clazz) throws GenerateException {
        GenerateResult generateResult = query.generate();
        String sql = generateResult.getGenerateResult();
        List<Serializable> params = generateResult.getParams();

        return this.jdbcTemplate.queryForObject(sql, params.toArray(), clazz);
    }

    /**
     * 执行 Query 查询数据列表的时候使用的操作
     *
     * @param query
     * @return
     */
    protected List<T> queryList(AbstractJpaQuery query) throws GenerateException {
        GenerateResult generateResult = query.generate();
        List<Serializable> params = generateResult.getParams();
        String sql = generateResult.getGenerateResult();

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(entityClass), params.toArray());
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setJdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcTemplate = jdbcTemplate;
    }

}
