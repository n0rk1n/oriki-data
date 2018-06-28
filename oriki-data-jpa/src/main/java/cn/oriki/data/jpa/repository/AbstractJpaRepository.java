package cn.oriki.data.jpa.repository;

import cn.oriki.commons.utils.string.StringConverts;
import cn.oriki.commons.utils.string.Strings;
import cn.oriki.data.annotation.Column;
import cn.oriki.data.annotation.Table;
import cn.oriki.data.repository.AbstractRepository;
import cn.oriki.data.utils.reflect.ReflectDatas;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Objects;

public class AbstractJpaRepository<T, ID extends Serializable> extends AbstractRepository<T, ID> {

    protected JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setJdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcTemplate = jdbcTemplate;
    }

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
    protected String getColumnName(Field field) {
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

}
