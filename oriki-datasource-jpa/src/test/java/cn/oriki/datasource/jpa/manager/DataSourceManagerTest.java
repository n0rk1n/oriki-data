package cn.oriki.datasource.jpa.manager;

import cn.oriki.datasource.jpa.container.imp.SimpleContainerImpl;
import org.junit.Test;

import javax.sql.DataSource;

public class DataSourceManagerTest {

    @Test
    public void chooseDataSource() {
        DataSource dataSource = DataSourceManager.getInstance(new SimpleContainerImpl()).chooseDataSource("csf");
        System.out.println(dataSource);
    }

}