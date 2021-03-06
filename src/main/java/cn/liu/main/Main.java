package cn.liu.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 主程序入口.
 *
 * @author xuping
 */
@EnableCaching
@EnableScheduling
@Configuration
@ComponentScan("cn.liu")
@EnableAutoConfiguration
@EnableTransactionManagement
@MapperScan("cn.liu.webChat.mybatis_dao")
public class Main extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Main.class);
    }

	public static void main(final String[] args) {
		SpringApplication.run(Main.class, args);
	}

}