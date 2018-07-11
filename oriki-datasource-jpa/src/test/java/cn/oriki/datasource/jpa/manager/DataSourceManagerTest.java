package cn.oriki.datasource.jpa.manager;

import org.junit.Test;

import javax.sql.DataSource;

public class DataSourceManagerTest {

    @Test
    public void chooseDataSource() {
        DataSource dataSource = DataSourceManager.getInstance().chooseDataSource("csf");
        System.out.println(dataSource);
    }

}