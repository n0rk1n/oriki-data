package cn.oriki.data.jpa.generate.curd.query;

import cn.oriki.commons.utils.string.Strings;
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

public abstract class AbstractJpaQuery extends AbstractQuery {

    protected static final String SELECT_KEY_WORD = "select ";
    protected static final String SELECT_ALL_KEY_WORD = " * ";
    protected static final String COUNT_1_KEY_WORD = " COUNT(1) ";

    //    private static final String SEPARATOR = "_separator_";
    private static final String NO_ALIAS = "@noAlias@";

    private static final String AS = " as ";

    private List<String> selectQuery; // 查询字段 key _separator_ alias

    public AbstractJpaQuery(AbstractPredicate predicate, AbstractFrom from) {
        super(predicate, from);
        selectQuery = Lists.newArrayList();
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
        addSelect(COUNT_1_KEY_WORD, " COUNT ");
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
            query(columnName, field.getName()); // 列名 as 属性名，方便后续封装
        }
    }

    // 添加查询字段
    private void addSelect(String key, String alias) {
        selectQuery.remove(COUNT_1_KEY_WORD + AS + " COUNT "); // 任何情况移除 count(1) as count

        if (Objects.isNull(selectQuery)) {
            selectQuery = Lists.newArrayList();
        }
        if (Strings.isNotBlank(key) && Strings.isNotBlank(alias)) {
            selectQuery.add(key + AS + alias);
        }
        if (Strings.isNotBlank(key) && Strings.isBlank(alias)) {
            selectQuery.add(key);
        }
    }

    protected void setSortImpl(StringBuilder stringBuilder) throws GenerateException {
        if (Objects.nonNull(getPredicate().getSort()) && 0 != getPredicate().getSort().size()) {
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

}
