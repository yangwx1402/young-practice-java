package com.young.practice.spring.jdbc;

import com.young.practice.Bootstrap;
import com.young.practice.bean.Account;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class JdbcTemplateExample {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Bootstrap.class);
        AccountOperation accountOperation = context.getBean(AccountOperation.class);
        List<Account> accountList = accountOperation.list();
        System.out.println(accountList);
    }
}
