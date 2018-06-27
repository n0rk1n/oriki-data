package cn.oriki.data.jpa.repository;

import cn.oriki.data.repository.AbstractRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.Serializable;

public class AbstractJpaRepository<T, ID extends Serializable> extends AbstractRepository<T, ID> {

    protected JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setJdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcTemplate = jdbcTemplate;
    }

    public AbstractJpaRepository(Class T, Class ID) {
        super(T, ID);
    }

}
