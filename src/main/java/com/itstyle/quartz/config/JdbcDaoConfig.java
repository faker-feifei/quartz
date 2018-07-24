package com.itstyle.quartz.config;

import com.dexcoder.dal.spring.JdbcDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**.
 * @author 百果园
 * @date 2018端午节
 */

@Configuration
public class JdbcDaoConfig {
    /*  */
    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcTemplate jdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

    @Bean
    public JdbcDaoImpl jdbcDaoImpl(){
        JdbcDaoImpl jdbcDaoImpl = new JdbcDaoImpl();
        jdbcDaoImpl.setJdbcTemplate(jdbcTemplate());
        return jdbcDaoImpl;
    }

}
