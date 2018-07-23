package cn.oriki.data.generate.base.pageable;

import cn.oriki.data.generate.Generate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Pageable 抽象类
 *
 * @author oriki.wang
 */
@Getter
@Setter
@AllArgsConstructor
public abstract class AbstractPageable implements Pageable, Generate {

    /**
     * 当 pageNumber 或者 pageSize 任意一个为 null 的情况，都不会进行排序操作
     */
    private Integer pageNumber;
    private Integer pageSize;

    @Override
    public void setPageNumberAndPageSize(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

}
