package cn.oriki.data.jpa.generate.curd.query;

import cn.oriki.commons.utils.string.Strings;
import cn.oriki.data.generate.Generate;
import cn.oriki.data.generate.base.from.AbstractFrom;
import cn.oriki.data.generate.base.predicate.AbstractPredicate;
import cn.oriki.data.generate.curd.query.AbstractQuery;
import cn.oriki.data.generate.exception.GenerateException;
import cn.oriki.data.generate.result.GenerateResult;
import cn.oriki.data.jpa.repository.AbstractJpaRepository;
import cn.oriki.data.utils.reflect.ReflectDatas;
import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 抽象 JPA 的查询接口
 *
 * @author oriki.wang
 */
public abstract class AbstractJpaQuery extends AbstractQuery {

    protected static final String SELECT_KEY_WORD = "select ";
    protected static final String SELECT_ALL_KEY_WORD = " * ";
    protected static final String COUNT_1_KEY_WORD = " COUNT(1) ";
    protected static final String COUNT_ALIAS = " COUNT ";

    private static final String NO_ALIAS = "@noAlias@";

    private static final String AS = " as ";

    /**
     * 查询字段 key _separator_ alias
     */
    private List<String> selectQuery;

    public AbstractJpaQuery(AbstractPredicate predicate, AbstractFrom from) {
        super(predicate, from);
        check();
    }

    @Override
    public void query(String key) { // 不推荐使用
        addSelect(key, NO_ALIAS);
    }

    @Override
    public void query(String key, String alias) {
        addSelect(key, alias);
    }

    @Override
    public void count() {
        if (Objects.nonNull(this.selectQuery)) {
            selectQuery.clear();
        } else {
            selectQuery = Lists.newArrayList();
        }
        addSelect(COUNT_1_KEY_WORD, COUNT_ALIAS);
    }

    /**
     * 查询所有字段
     *
     * @param entityClass 实体类 Class
     * @param <T>         泛型
     */
    public <T> void queryAll(Class<T> entityClass) {
        List<Field> fields = ReflectDatas.getFields(entityClass);
        for (Field field : fields) {
            String columnName = AbstractJpaRepository.getColumnName(field);
            // 列名 as 属性名，方便后续封装
            query(columnName, field.getName());
        }
    }

    /**
     * 添加查询字段
     *
     * @param key   键
     * @param alias 别名
     */
    private void addSelect(String key, String alias) {
        // 任何情况移除 count(1) as count
        selectQuery.remove(COUNT_1_KEY_WORD + AS + COUNT_ALIAS);

        check();
        if (Strings.isNotBlank(key) && Strings.isNotBlank(alias)) {
            selectQuery.add(key + AS + alias);
        }
        if (Strings.isNotBlank(key) && Strings.isBlank(alias)) {
            selectQuery.add(key);
        }
    }

    protected void setSelectImpl(StringBuilder stringBuilder) throws GenerateException {
        // SELECT
        stringBuilder.append(SELECT_KEY_WORD);
        // 获取所有属性
        if (getSelectQuery().size() == 0) {
            // select * TODO 不使用别名可能会存在录入数据问题。最好保证 selectQuery 数据 > 0 ;
            stringBuilder.append(SELECT_ALL_KEY_WORD);
        } else {
            String join = cn.oriki.commons.utils.collection.Collections.join(getSelectQuery(), Generate.COMMA);
            stringBuilder.append(join);
        }
    }

    protected void setFromImpl(StringBuilder stringBuilder) throws GenerateException {
        // from table_name
        stringBuilder.append(getFrom().generate().getGenerateResult());
    }

    protected void setSortImpl(StringBuilder stringBuilder) throws GenerateException {
        if (Objects.nonNull(getPredicate().getSort()) && 0 != getPredicate().getSort().sortSize()) {
            GenerateResult sortResult = getPredicate().getSort().generate();
            String sortSQL = sortResult.getGenerateResult();
            if (Strings.isNotBlank(sortSQL)) {
                stringBuilder.append(sortSQL);
            }
        }
    }

    public List<String> getSelectQuery() {
        return Collections.unmodifiableList(selectQuery);
    }

    private void check() {
        if (Objects.isNull(selectQuery)) {
            selectQuery = Lists.newArrayList();
        }
    }

}
