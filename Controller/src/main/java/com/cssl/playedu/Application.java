package com.cssl.playedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author : Tang
 * @create 2023/8/28 9:36
 */
@SpringBootApplication
//  扫描Servlet组件 [Servlet、Filter、Listener]
@ServletComponentScan
// 开启事务支持
@EnableTransactionManagement
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
