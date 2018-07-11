package cn.oriki.datasource.jpa.manager;

import cn.oriki.commons.loader.ConfigLoader;
import cn.oriki.commons.utils.string.Strings;
import cn.oriki.datasource.jpa.container.Container;
import cn.oriki.datasource.jpa.info.DataSourceInfo;
import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class DataSourceManager {

    private static Container container;

    private static final String DEFAULT_KEY_WORD = "default";

    // 默认配置映射键
    private static final String DEFAULT_DRIVER_CLASS_KEY = "oriki." + DEFAULT_KEY_WORD + ".driver-class";
    private static final String DEFAULT_URL_KEY = "oriki." + DEFAULT_KEY_WORD + ".url";
    private static final String DEFAULT_USERNAME_KEY = "oriki." + DEFAULT_KEY_WORD + ".userName";
    private static final String DEFAULT_PASSWORD_KEY = "oriki." + DEFAULT_KEY_WORD + ".password";

    // singleton
    private static DataSourceManager dataSourceManager;

    private DataSourceManager() {
    }

    public static DataSourceManager getInstance() {
        if (Objects.isNull(dataSourceManager)) {
            dataSourceManager = new DataSourceManager();
            init();
        }
        return dataSourceManager;
    }

    /**
     * 获取 source 对应 DataSource ，如果不存在则提供默认数据源
     *
     * @param sourceKey
     * @return
     */
    public DataSource chooseDataSource(String sourceKey) {
        return container.getContains().getOrDefault(sourceKey, container.getContains().get(DEFAULT_KEY_WORD));
    }

    private static void init() {
        ConfigLoader configLoader = new ConfigLoader("oriki-datasource.properties");

        String defaultDriverClass = configLoader.getProperty(DEFAULT_DRIVER_CLASS_KEY);
        String defaultUrl = configLoader.getProperty(DEFAULT_URL_KEY);
        String defaultUsername = configLoader.getProperty(DEFAULT_USERNAME_KEY);
        String defaultPassword = configLoader.getProperty(DEFAULT_PASSWORD_KEY);

        String profile = configLoader.getProperty("oriki.datasource.profile.active");
        if (Strings.isNotBlank(profile)) {
            ConfigLoader configLoader2 = new ConfigLoader("oriki-datasource-" + profile + ".properties");

            Map<String, String> properties = configLoader2.getProperties();
            List<String> sourceKeys = properties.keySet().stream().filter((e) -> e.contains("driver-class")).map((e) ->
                    e.replaceFirst("oriki\\.", "").replace(".driver-class", "")
            ).collect(Collectors.toList());

            // 校验是否有重复项
            checkDistinct(sourceKeys);

            sourceKeys.forEach((sourceKey) -> {
                // 拼接
                String driverClass = "oriki." + sourceKey + ".driver-class";
                String url = "oriki." + sourceKey + ".url";
                String username = "oriki." + sourceKey + ".userName";
                String password = "oriki." + sourceKey + ".password";
                DataSourceInfo dataSourceInfo = createDataSourceInfo(driverClass, url, username, password);
                createDataSource(sourceKey, dataSourceInfo);
            });
        }

        // 初始化默认配置信息
        DataSourceInfo defaultDataSource = createDataSourceInfo(defaultDriverClass, defaultUrl, defaultUsername, defaultPassword);
        createDataSource(DEFAULT_KEY_WORD, defaultDataSource);
    }

    // 创建 DataSource TODO 使用 Druid 数据库（方便统计和效率考虑
    private static void createDataSource(String key, DataSourceInfo dataSourceInfo) {
        checkDataSourceInfo(dataSourceInfo);

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(dataSourceInfo.getDriverClass());
        dataSource.setUrl(dataSourceInfo.getUrl());
        dataSource.setUsername(dataSourceInfo.getUserName());
        dataSource.setPassword(dataSourceInfo.getPassword());

        checkContainer();
        container.setContains(key, dataSource);
    }

    // 创建 DataSourceInfo 配置信息对象
    private static DataSourceInfo createDataSourceInfo(String driverClass, String url, String username, String password) {
        DataSourceInfo dataSourceInfo = new DataSourceInfo();
        dataSourceInfo.setDriverClass(driverClass);
        dataSourceInfo.setUrl(url);
        dataSourceInfo.setUserName(username);
        dataSourceInfo.setPassword(password);
        return dataSourceInfo;
    }

    // 保证容器对象存在
    private static void checkContainer() {
        if (Objects.isNull(container)) {
            container = new Container();
        }
    }

    // 保证 DataSourceInfo 各项参数非空
    private static void checkDataSourceInfo(DataSourceInfo dataSourceInfo) {
        if (Strings.isBlank(dataSourceInfo.getDriverClass())) {
            throw new NullPointerException("we can't create DataSource , please check driverClass config");
        } else if (Strings.isBlank(dataSourceInfo.getUrl())) {
            throw new NullPointerException("we can't create DataSource , please check url config");
        } else if (Strings.isBlank(dataSourceInfo.getUrl())) {
            throw new NullPointerException("we can't create DataSource , please check url config");
        } else if (Strings.isBlank(dataSourceInfo.getUrl())) {
            throw new NullPointerException("we can't create DataSource , please check url config");
        }
    }

    // 保证 sourceKeys 没有重复
    private static void checkDistinct(List<String> list) {
        Set<String> collect = list.stream().collect(Collectors.toSet());
        if (collect.size() != list.size()) {
            throw new RuntimeException("we find the same sourceKey in properties,please check");
        }
    }

}
