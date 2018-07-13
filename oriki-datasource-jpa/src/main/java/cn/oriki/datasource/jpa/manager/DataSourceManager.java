package cn.oriki.datasource.jpa.manager;

import cn.oriki.commons.loader.ConfigLoader;
import cn.oriki.commons.utils.string.Strings;
import cn.oriki.datasource.jpa.container.Container;
import cn.oriki.datasource.jpa.info.DataSourceInfo;
import com.alibaba.druid.pool.DruidDataSource;
import lombok.NonNull;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 数据源管理器，单例。
 * <p>
 * 获取方式使用 getInstance 获取，对外提供 chooseDataSource 方法获取数据源
 *
 * @author oriki.wang
 */
public class DataSourceManager<T extends DataSource> {

    private static Container container;

    /**
     * singleton
     */
    private static DataSourceManager dataSourceManager;

    private static final String DEFAULT_KEY_WORD = "default";
    private static final String PREFIX_KEY_WORD = "oriki.";

    private static final String SUFFIX_DRIVER_CLASS_KEY_WORD = ".driver-class";
    private static final String SUFFIX_URL_KEY_WORD = ".url";
    private static final String SUFFIX_USER_NAME_KEY_WORD = ".userName";
    private static final String SUFFIX_PASSWORD_KEY_WORD = ".password";

    /**
     * 默认配置映射键
     */
    private static final String DEFAULT_DRIVER_CLASS_KEY = "oriki.default.driver-class";
    private static final String DEFAULT_URL_KEY = "oriki.default.url";
    private static final String DEFAULT_USERNAME_KEY = "oriki.default.userName";
    private static final String DEFAULT_PASSWORD_KEY = "oriki.default.password";

    private DataSourceManager() {
    }

    public static DataSourceManager getInstance() {
        if (Objects.isNull(dataSourceManager)) {
            synchronized (DataSourceManager.class) {
                if (Objects.isNull(dataSourceManager)) {
                    dataSourceManager = new DataSourceManager();
                    dataSourceManager.init();
                }
            }
        }
        return dataSourceManager;
    }

    /**
     * 获取 source 对应 DataSource ，如果不存在则提供默认数据源
     *
     * @param sourceKey 数据源对应键
     * @return 数据源
     */
    public DataSource chooseDataSource(String sourceKey) {
        return container.getContains().getOrDefault(sourceKey, container.getContains().get(DEFAULT_KEY_WORD));
    }

    private void init() {
        ConfigLoader configLoader = new ConfigLoader("oriki-datasource.properties");
        String profile = configLoader.getProperty("oriki.datasource.profile.active");

        if (Strings.isNotBlank(profile)) {
            ConfigLoader configLoader2 = new ConfigLoader("oriki-datasource-" + profile + ".properties");

            Map<String, String> properties = configLoader2.getProperties();
            List<String> sourceKeys = properties.keySet().stream().filter((e) -> e.contains(SUFFIX_DRIVER_CLASS_KEY_WORD)).map((e) ->
                    e.replaceFirst("oriki\\.", "").replace(SUFFIX_DRIVER_CLASS_KEY_WORD, "")
            ).collect(Collectors.toList());

            // 校验是否有重复项
            checkDistinct(sourceKeys);

            sourceKeys.forEach((sourceKey) -> {
                // 拼接
                String driverClass = configLoader2.getProperty(PREFIX_KEY_WORD + sourceKey + SUFFIX_DRIVER_CLASS_KEY_WORD);
                String url = configLoader2.getProperty(PREFIX_KEY_WORD + sourceKey + SUFFIX_URL_KEY_WORD);
                String username = configLoader2.getProperty(PREFIX_KEY_WORD + sourceKey + SUFFIX_USER_NAME_KEY_WORD);
                String password = configLoader2.getProperty(PREFIX_KEY_WORD + sourceKey + SUFFIX_PASSWORD_KEY_WORD);
                DataSourceInfo dataSourceInfo = createDataSourceInfo(driverClass, url, username, password);
                createAndSetDataSource(sourceKey, dataSourceInfo);
            });

            configLoader2.clear();
        }

        // 判断是否已有默认配置，如果没有，获取 oriki-datasource.properties 的默认数据源
        if (Objects.isNull(container.getContains().get(DEFAULT_KEY_WORD))) {
            // 初始化默认配置信息
            String defaultDriverClass = configLoader.getProperty(DEFAULT_DRIVER_CLASS_KEY);
            String defaultUrl = configLoader.getProperty(DEFAULT_URL_KEY);
            String defaultUsername = configLoader.getProperty(DEFAULT_USERNAME_KEY);
            String defaultPassword = configLoader.getProperty(DEFAULT_PASSWORD_KEY);

            DataSourceInfo defaultDataSource = createDataSourceInfo(defaultDriverClass, defaultUrl, defaultUsername, defaultPassword);
            createAndSetDataSource(DEFAULT_KEY_WORD, defaultDataSource);
        }
    }

    /**
     * 创建和设置 DataSource TODO 使用 Druid 数据库（方便统计和效率考虑
     *
     * @param key            映射键
     * @param dataSourceInfo 数据源配置信息
     */
    private static void createAndSetDataSource(String key, DataSourceInfo dataSourceInfo) {
        checkDataSourceInfo(dataSourceInfo);
        checkContainer();

        DataSource dataSource = createDataSource(dataSourceInfo);
        container.setContain(key, dataSource);
    }

    private static DataSource createDataSource(@NonNull DataSourceInfo dataSourceInfo) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(dataSourceInfo.getDriverClass());
        dataSource.setUrl(dataSourceInfo.getUrl());
        dataSource.setUsername(dataSourceInfo.getUserName());
        dataSource.setPassword(dataSourceInfo.getPassword());

        return dataSource;
    }

    /**
     * 创建 DataSourceInfo 配置信息对象
     *
     * @param driverClass 驱动类全路径
     * @param url         连接 url 地址
     * @param username    用户名
     * @param password    密码
     * @return 数据源创建信息对象
     */
    private static DataSourceInfo createDataSourceInfo(String driverClass, String url, String username, String password) {
        DataSourceInfo dataSourceInfo = new DataSourceInfo();

        dataSourceInfo.setDriverClass(driverClass);
        dataSourceInfo.setUrl(url);
        dataSourceInfo.setUserName(username);
        dataSourceInfo.setPassword(password);

        return dataSourceInfo;
    }

    /**
     * 校验
     */
    private static void checkContainer() {
        if (Objects.isNull(container)) {
            container = new Container();
        }
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
     * 保证 sourceKeys 没有重复
     *
     * @param list 保证 List 中没有重复值
     */
    private static void checkDistinct(@NonNull List<String> list) {
        HashSet<String> strings = new HashSet<>(list);
        if (strings.size() != list.size()) {
            list.remove(strings);
            throw new RuntimeException("we find the same sourceKey in properties : " + list.toString() + ", please check");
        }
    }

}
