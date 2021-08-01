package com.young.practice.testcase.spring.jdbc;

import com.young.practice.bean.Account;
import com.young.practice.spring.jdbc.AccountOperation;
import com.young.practice.testcase.spring.BaseSpringTestcase;
import org.junit.Test;

import java.util.List;

public class SpringBootTestCase extends BaseSpringTestcase {

   private AccountOperation accountOperation = super.getBean(AccountOperation.class);

    @Test
    public void testList(){
        List<Account> accountList = accountOperation.list();
        System.out.println(accountList);
    }

    @Test
    public void testInsert(){
        accountOperation.insert("yangyong",10);
    }

    @Test
    public void testUpate(){
        Account account = new Account();
        account.setId(1L);
        account.setUsername("yangzhi");
        account.setAge(32);
        accountOperation.update(account);
    }

    @Test
    public void testDelete(){
        accountOperation.delete(1L);
    }
}
