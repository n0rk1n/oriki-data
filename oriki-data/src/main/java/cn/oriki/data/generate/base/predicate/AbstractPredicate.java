package cn.oriki.data.generate.base.predicate;

import cn.oriki.data.generate.base.pageable.AbstractPageable;
import cn.oriki.data.generate.base.pageable.Pageable;
import cn.oriki.data.generate.base.predicate.crd.AbstractCRDPredicate;
import cn.oriki.data.generate.base.sort.AbstractSort;
import cn.oriki.data.generate.base.sort.Sort;
import cn.oriki.data.generate.base.sort.enumeration.Direction;
import cn.oriki.data.generate.base.where.AbstractWhere;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractPredicate extends AbstractCRDPredicate implements Predicate, Sort, Pageable {

    private AbstractSort sort;
    private AbstractPageable pageable;

    public AbstractPredicate(AbstractWhere where, AbstractSort sort, AbstractPageable pageable) {
        super(where);
        this.sort = sort;
        this.pageable = pageable;
    }

    /**
     * 获取分页页码（当前第几页）
     *
     * @return 当前第几页
     */
    @Override
    public Integer getPageNumber() {
        return this.pageable.getPageNumber();
    }

    /**
     * 获取每页条数
     *
     * @return 每页条数
     */
    @Override
    public Integer getPageSize() {
        return this.pageable.getPageSize();
    }

    /**
     * 设置当前页码和每页条数
     *
     * @param pageNumber 当前页码
     * @param pageSize   每页条数
     */
    @Override
    public void set(Integer pageNumber, Integer pageSize) {
        this.pageable.set(pageNumber, pageSize);
    }

    /**
     * 排序列和排序方向
     *
     * @param key       键
     * @param direction 排序方向
     */
    @Override
    public void sort(String key, Direction direction) {
        this.sort.sort(key, direction);
    }

    /**
     * 排序的数量
     * <p>
     * 用于判断是否有排序条件，传 size （比起 boolean ） 更通用一些
     *
     * @return 参与排序列的数量
     */
    @Override
    public Integer sortSize() {
        return this.sort.sortSize();
    }

}
