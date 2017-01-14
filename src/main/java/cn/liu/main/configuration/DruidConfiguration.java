package cn.liu.main.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DruidConfiguration {
    @Bean
    public ServletRegistrationBean druidServlet() {
        return new ServletRegistrationBean(new StatViewServlet(), "/db/*");
    }

    @Bean
    /**
     * 数据源配置 .
     *
     * @param driver
     * @param url
     * @param username
     * @param password
     * @return
     */
    public DataSource druidDataSource(@Value("${spring.datasource.driver-class-name}") final String driver,
                                      @Value("${spring.datasource.url}") final String url,
                                      @Value("${spring.datasource.username}") final String username,
                                      @Value("${spring.datasource.password}") final String password) {
        final DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driver);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setValidationQuery("SELECT 1 ");
        druidDataSource.setValidationQueryTimeout(5);
        druidDataSource.setQueryTimeout(20);
        try {
            // 配置监控统计拦截的filters //log
            druidDataSource.setFilters("stat, wall");
        } catch (final SQLException exception) {
            exception.printStackTrace();
        }
        return druidDataSource;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        final FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}