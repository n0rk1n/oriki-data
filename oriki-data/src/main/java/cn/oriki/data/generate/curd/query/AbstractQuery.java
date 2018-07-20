package cn.oriki.data.generate.curd.query;

import cn.oriki.data.generate.Generate;
import cn.oriki.data.generate.base.from.AbstractFrom;
import cn.oriki.data.generate.base.from.From;
import cn.oriki.data.generate.base.pageable.Pageable;
import cn.oriki.data.generate.base.predicate.AbstractPredicate;
import cn.oriki.data.generate.base.predicate.Predicate;
import cn.oriki.data.generate.base.sort.Sort;
import cn.oriki.data.generate.base.where.Where;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 抽象的查询类
 *
 * @author oriki.wang
 */
@Getter
@Setter
@AllArgsConstructor
public abstract class AbstractQuery implements Query, Predicate, From, Generate {

    private AbstractPredicate predicate;

    private AbstractFrom from;

    @Override
    public Where getWhere() {
        return this.predicate.getWhere();
    }

    @Override
    public Sort getSort() {
        return this.predicate.getSort();
    }

    @Override
    public Pageable getPageable() {
        return this.predicate.getPageable();
    }

    @Override
    public void from(String fromName) {
        this.from.from(fromName);
    }

    @Override
    public String getFromName() {
        return this.from.getFromName();
    }

}
