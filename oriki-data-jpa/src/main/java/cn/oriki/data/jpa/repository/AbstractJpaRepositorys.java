package cn.oriki.data.jpa.repository;

import cn.oriki.commons.utils.reflect.entity.FieldTypeNameValue;
import cn.oriki.commons.utils.string.StringConverts;
import cn.oriki.commons.utils.string.Strings;
import cn.oriki.data.annotation.Column;
import cn.oriki.data.annotation.Table;
import cn.oriki.data.generate.base.where.entity.Criteria;
import cn.oriki.data.generate.base.where.enumeration.ConditionalEnum;
import cn.oriki.data.generate.curd.delete.result.DeleteResult;
import cn.oriki.data.generate.curd.save.result.SaveResult;
import cn.oriki.data.generate.exception.GenerateException;
import cn.oriki.data.generate.result.GenerateResult;
import cn.oriki.data.jpa.generate.curd.delete.JpaDeleteImpl;
import cn.oriki.data.jpa.generate.curd.query.AbstractJpaQuery;
import cn.oriki.data.jpa.generate.curd.save.JpaSaveImpl;
import cn.oriki.data.jpa.generate.curd.update.JpaUpdateImpl;
import cn.oriki.data.repository.AbstractRepository;
import cn.oriki.data.utils.reflect.ReflectDatas;
import com.google.common.collect.Lists;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;

public abstract class AbstractJpaRepositorys<T, ID extends Serializable> extends AbstractRepository<T, ID> {

    private JdbcTemplate jdbcTemplate;

    public AbstractJpaRepositorys(Class<T> entityClass, Class<ID> idClass) {
        super(entityClass, idClass);
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
            tableName = StringConverts.toSQLTableName(javaClassName, "t_");
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
        return getColumnName(field);
    }

    /**
     * 执行 delete 方法操作
     *
     * @return delete 操作结果
     */
    protected DeleteResult executeDelete(JpaDeleteImpl delete) throws GenerateException {
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
     * @param update Update 子类
     * @return 影响行数
     */
    protected int executeUpdate(JpaUpdateImpl update) throws GenerateException {
        GenerateResult generateResult = update.generate();

        String sql = generateResult.getGenerateResult();
        List<Serializable> params = generateResult.getParams();

        return this.jdbcTemplate.update(sql, params.toArray());
    }

    /**
     * 执行 save 方法操作
     *
     * @param save Save 子类
     * @return SaveResult
     */
    protected <S extends T> SaveResult<S, ID> executeSave(JpaSaveImpl save, S entity) throws GenerateException, IllegalAccessException {
        GenerateResult generateResult = save.generate();
        String sql = generateResult.getGenerateResult();
        List<Serializable> params = generateResult.getParams();

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int number = jdbcTemplate.update((connection) -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    for (int i = 1; i <= params.size(); i++) {
                        try {
                            preparedStatement.setObject(i, params.get(i - 1));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    return preparedStatement;
                }
                , keyHolder);

        try {
            // 获取 id 并存入实体中
            String idString = keyHolder.getKey().toString(); // id 的 String 表示
            ID id = ReflectDatas.parseId(idClass, idString);
            super.putIdToEntity(entity, id);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 获取结果
        SaveResult<S, ID> saveResult = new SaveResult<>();
        saveResult.setEntities(Lists.newArrayList(entity));
        saveResult.setNumber(number);

        return saveResult;
    }

    /**
     * 执行 Query 查询单条数据的时候使用的操作
     *
     * @param query Query 子类
     * @return 查询的结果对象
     * @throws GenerateException 生成 SQL 异常抛出
     */
    protected T queryOne(AbstractJpaQuery query) throws GenerateException {
        GenerateResult generateResult = query.generate();
        String sql = generateResult.getGenerateResult();
        List<Serializable> params = generateResult.getParams();
        try {
            return this.jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(entityClass), params.toArray());
        } catch (EmptyResultDataAccessException e) { // 未找到数据会抛出的异常
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 执行 Query 查询某个类型值的查询
     *
     * @param query Query 子类
     * @param <E>   范型
     * @return 查询特定范型的结果
     * @throws GenerateException 生成 SQL 异常抛出
     */
    protected <E> E queryValue(AbstractJpaQuery query, Class<E> clazz) throws GenerateException {
        GenerateResult generateResult = query.generate();
        String sql = generateResult.getGenerateResult();
        List<Serializable> params = generateResult.getParams();

        try {
            return this.jdbcTemplate.queryForObject(sql, params.toArray(), clazz);
        } catch (EmptyResultDataAccessException e) { // 未找到数据会抛出的异常（只有 queryForObject 查询数量不为 1 的时候会抛出的异常）
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 执行 Query 查询数据列表的时候使用的操作
     *
     * @param query Query 子类
     * @return 查询结果集合
     * @throws GenerateException 生成 SQL 异常抛出
     */
    protected List<T> queryList(AbstractJpaQuery query) throws GenerateException {
        GenerateResult generateResult = query.generate();
        List<Serializable> params = generateResult.getParams();
        String sql = generateResult.getGenerateResult();

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(entityClass), params.toArray());
    }

    public <S extends T> List<Criteria> createCriteriasByEntity(S entity) {
        List<FieldTypeNameValue> fieldTypeNameValues = ReflectDatas.getFieldTypeNameValuesWithValuesIsNotNull(entity);
        List<Criteria> criterias = Lists.newArrayList();

        for (FieldTypeNameValue fieldTypeNameValue : fieldTypeNameValues) {
            Criteria criteria = new Criteria();
            criteria.setKey(fieldTypeNameValue.getName());
            criteria.setConditional(ConditionalEnum.EQUALS);
            criteria.setValue((Serializable) fieldTypeNameValue.getValue());
            criterias.add(criteria);
        }
        return criterias;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

}
