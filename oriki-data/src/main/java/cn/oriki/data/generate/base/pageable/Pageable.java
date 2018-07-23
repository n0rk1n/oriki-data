package cn.oriki.data.generate.base.pageable;

/**
 * 定义分页相关
 *
 * @author oriki.wang
 */
public interface Pageable {

    /**
     * 获取分页页码（当前第几页）
     *
     * @return 当前第几页
     */
    Integer getPageNumber();

    /**
     * 获取每页条数
     *
     * @return 每页条数
     */
    Integer getPageSize();

    /**
     * 设置当前页码和每页条数
     *
     * @param pageNumber 当前页码
     * @param pageSize   每页条数
     */
    void setPageNumberAndPageSize(Integer pageNumber, Integer pageSize);

}
