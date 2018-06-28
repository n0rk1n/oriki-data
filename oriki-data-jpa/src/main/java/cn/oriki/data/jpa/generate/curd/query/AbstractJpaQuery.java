package cn.oriki.data.jpa.generate.curd.query;

import cn.oriki.commons.utils.collection.Collections;
import cn.oriki.data.generate.Generate;
import cn.oriki.data.generate.curd.from.From;
import cn.oriki.data.generate.curd.query.AbstractQuery;
import cn.oriki.data.generate.curd.where.Where;
import cn.oriki.data.generate.exception.GenerateException;
import cn.oriki.data.generate.result.GenerateResult;
import cn.oriki.data.jpa.repository.AbstractJpaRepository;
import cn.oriki.data.utils.reflect.ReflectDatas;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractJpaQuery extends AbstractQuery {

    private static final String SELECT_KEY_WORD = "select ";
    private static final String SELECT_ALL_KEY_WORD = " * ";

    private static final String SEPARATOR = "_separator_";
    private static final String NO_ALIAS = "@noAlias@";

    private static final String AS = " as ";

    private List<String> selectQuery; // 查询字段 key _separator_ alias

    public AbstractJpaQuery(Where where, From from) {
        super(where, from);
        selectQuery = Lists.newArrayList();
    }

    @Override
    public void query(String key) {
        if (Objects.isNull(selectQuery)) {
            selectQuery = Lists.newArrayList();
        }
        selectQuery.add(key + SEPARATOR + NO_ALIAS); // 添加别名占用词
    }

    @Override
    public void query(String key, String alias) {
        if (Objects.isNull(selectQuery)) {
            selectQuery = Lists.newArrayList();
        }
        selectQuery.add(key + SEPARATOR + alias);
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

            query(columnName, field.getName()); // 列名 as 属性名
        }
    }

    @Override
    public GenerateResult generate() throws GenerateException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SELECT_KEY_WORD); // SELECT

        // 获取所有属性
        if (this.selectQuery.size() == 0) {
            stringBuilder.append(SELECT_ALL_KEY_WORD); // select * TODO 不适用别名可能会存在录入数据问题。最好保证 selectQuery 数据 > 0 ;
        } else {
            ArrayList<String> columns = Lists.newArrayList();
            this.selectQuery.forEach((selectField) -> {
                String[] split = selectField.split(SEPARATOR);
                String column = split[0] + AS + split[1];
                columns.add(column);
            });

            String join = Collections.join(columns, Generate.COMMA);
            stringBuilder.append(join);
        }

        String fromSQL = getFrom().generate().getGenerateResult();
        stringBuilder.append(fromSQL); // from table_name

        GenerateResult whereResult = getWhere().generate();
        String whereSql = whereResult.getGenerateResult();
        stringBuilder.append(whereSql); // where ...
        List<Serializable> params = whereResult.getParams();


        GenerateResult generateResult = new GenerateResult();
        generateResult.setGenerateResult(stringBuilder.toString());
        generateResult.setParams(params);

        return generateResult;
    }

}
