package cn.liu.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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

public class Main extends WebMvcConfigurerAdapter {
	public static void main(final String[] args) {
		SpringApplication.run(Main.class, args);
	}

}