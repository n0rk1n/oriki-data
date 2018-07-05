package cn.oriki.data.generate.base.sort.enumeration;

public enum Direction {

    ASC(" ASC "), DESC(" DESC ");

    private String order;

    Direction(String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }

    public boolean isAsc() {
        return this.equals(ASC);
    }

    public boolean isDesc() {
        return this.equals(DESC);
    }

}
