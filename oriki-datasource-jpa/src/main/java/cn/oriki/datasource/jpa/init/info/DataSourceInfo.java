package cn.oriki.datasource.jpa.init.info;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据源创建信息
 *
 * @author oriki.wang
 */
@Getter
@Setter
public class DataSourceInfo {

    private String driverClass;
    private String url;
    private String userName;
    private String password;

}
