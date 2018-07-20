package cn.oriki.datasource.jpa.init;

import cn.oriki.commons.utils.string.Strings;
import com.alibaba.druid.pool.DruidDataSource;
import cn.oriki.datasource.jpa.init.info.DataSourceInfo;
import lombok.NonNull;

import javax.sql.DataSource;

/**
 * 数据源创建器
 *
 * @author oriki.wang
 */
public class DataSourceInitializer {

    /**
     * 创建 DataSourceInfo 配置信息对象
     *
     * @param driverClass 驱动类全路径
     * @param url         连接 url 地址
     * @param username    用户名
     * @param password    密码
     * @return 数据源创建信息对象
     */
    public static DataSourceInfo createDataSourceInfo(String driverClass, String url, String username, String password) {
        DataSourceInfo dataSourceInfo = new DataSourceInfo();

        dataSourceInfo.setDriverClass(driverClass);
        dataSourceInfo.setUrl(url);
        dataSourceInfo.setUserName(username);
        dataSourceInfo.setPassword(password);

        return dataSourceInfo;
    }

    /**
     * 保证 DataSourceInfo 各项参数非空
     *
     * @param dataSourceInfo 数据库创建信息类
     */
    private static void checkDataSourceInfo(DataSourceInfo dataSourceInfo) {
        if (Strings.isBlank(dataSourceInfo.getDriverClass())) {
            throw new NullPointerException("we can't create DataSource , please check driverClass config");
        }
        if (Strings.isBlank(dataSourceInfo.getUrl())) {
            throw new NullPointerException("we can't create DataSource , please check url config");
        }
        if (Strings.isBlank(dataSourceInfo.getUserName())) {
            throw new NullPointerException("we can't create DataSource , please check username config");
        }
        if (Strings.isBlank(dataSourceInfo.getPassword())) {
            throw new NullPointerException("we can't create DataSource , please check password config");
        }
    }

    /**
     * 创建数据源
     *
     * @param dataSourceInfo
     * @return
     */
    public static DataSource createDataSource(@NonNull DataSourceInfo dataSourceInfo) {
        checkDataSourceInfo(dataSourceInfo);
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(dataSourceInfo.getDriverClass());
        dataSource.setUrl(dataSourceInfo.getUrl());
        dataSource.setUsername(dataSourceInfo.getUserName());
        dataSource.setPassword(dataSourceInfo.getPassword());

        return dataSource;
    }

}
