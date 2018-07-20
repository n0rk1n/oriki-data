package cn.oriki.data.generate.curd.cud.update.result;

import lombok.Getter;
import lombok.Setter;

public class UpdateResult {

    @Getter
    @Setter
    private Integer number;
    /**
     * 是否为 update 操作，如果是 save 方法，则设置为 false
     */
    private boolean isUpdate = true;

    public boolean isUpdate() {
        return isUpdate;
    }

    public void isUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

}
