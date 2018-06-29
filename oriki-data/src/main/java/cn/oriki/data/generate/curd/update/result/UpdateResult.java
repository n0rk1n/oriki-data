package cn.oriki.data.generate.curd.update.result;

public class UpdateResult {

    private Integer number;
    private boolean isUpdate = true; // 是否为 update 操作，如果是 save 方法，则设置为 false

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void isUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

}
