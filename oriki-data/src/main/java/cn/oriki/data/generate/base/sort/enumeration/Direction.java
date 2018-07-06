package cn.oriki.data.generate.base.sort.enumeration;

/**
 * 排序顺序枚举
 *
 * @author oriki
 */
public enum Direction {

    /**
     * 升序
     */
    ASC(" ASC "),
    /**
     * 降序
     */
    DESC(" DESC ");

    private String order;

    Direction(String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }

//    public boolean isAsc() {
//        return this.equals(ASC);
//    }

//    public boolean isDesc() {
//        return this.equals(DESC);
//    }

}
