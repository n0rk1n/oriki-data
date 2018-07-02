package cn.oriki.data.jpa.generate.curd.query;

import cn.oriki.commons.utils.collection.Collections;
import cn.oriki.commons.utils.string.Strings;
import cn.oriki.data.generate.Generate;
import cn.oriki.data.generate.curd.from.From;
import cn.oriki.data.generate.curd.predicate.Predicate;
import cn.oriki.data.generate.curd.query.AbstractQuery;
import cn.oriki.data.generate.exception.GenerateException;
import cn.oriki.data.generate.result.GenerateResult;
import cn.oriki.data.jpa.repository.AbstractJpaRepository;
import cn.oriki.data.utils.reflect.ReflectDatas;
import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

public abstract class AbstractJpaQuery extends AbstractQuery {

    private static final String SELECT_KEY_WORD = "select ";
    private static final String SELECT_ALL_KEY_WORD = " * ";

    //    private static final String SEPARATOR = "_separator_";
    private static final String NO_ALIAS = "@noAlias@";

    private static final String AS = " as ";

    private List<String> selectQuery; // 查询字段 key _separator_ alias

    public AbstractJpaQuery(Predicate predicate, From from) {
        super(predicate, from);
        selectQuery = Lists.newArrayList();
    }

    /*public AbstractJpaQuery(Where where, From from) {
        super(where, from);
        selectQuery = Lists.newArrayList();
    }*/

    // 不推荐使用
    @Override
    @Deprecated
    public void query(String key) {
        addSelect(key, NO_ALIAS);
    }

    @Override
    public void query(String key, String alias) {
        addSelect(key, alias);
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

    @Override
    public GenerateResult generate() throws GenerateException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SELECT_KEY_WORD); // SELECT

        // 获取所有属性
        if (this.selectQuery.size() == 0) {
            stringBuilder.append(SELECT_ALL_KEY_WORD); // select * TODO 不使用别名可能会存在录入数据问题。最好保证 selectQuery 数据 > 0 ;
        } else {
            String join = Collections.join(this.selectQuery, Generate.COMMA);
            stringBuilder.append(join);
        }

        String fromSQL = getFrom().generate().getGenerateResult();
        stringBuilder.append(fromSQL); // from table_name

        GenerateResult whereResult = getPredicate().getWhere().generate();
        String whereSQL = whereResult.getGenerateResult();
        stringBuilder.append(whereSQL); // where ...

        // 添加 sort 排序 sql
        if (Objects.nonNull(getPredicate().getSort()) && 0 != getPredicate().getSort().size()) {
            GenerateResult generateResult = getPredicate().getSort().generate();
            String sortSQL = generateResult.getGenerateResult();
            if (Strings.isNotBlank(sortSQL)) {
                stringBuilder.append(sortSQL);
            }
        }

        GenerateResult generateResult = new GenerateResult();
        {
            generateResult.setGenerateResult(stringBuilder.toString());
            generateResult.setParams(whereResult.getParams());
        }

        return generateResult;
    }

    // 添加查询字段
    private void addSelect(String key, String alias) {
        if (Objects.isNull(selectQuery)) {
            selectQuery = Lists.newArrayList();
        }
        if (Strings.isNotBlank(key) && Strings.isNotBlank(alias))
            selectQuery.add(key + AS + alias);
        if (Strings.isNotBlank(key) && Strings.isBlank(alias)) {
            selectQuery.add(key);
        }
    }

}
