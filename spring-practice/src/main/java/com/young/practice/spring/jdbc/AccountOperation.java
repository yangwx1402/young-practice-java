package com.young.practice.spring.jdbc;

import com.young.practice.bean.Account;

import java.util.List;

/**
 * @author yangyong
 */
public interface AccountOperation {
    /**
     *
     * @param username
     * @param age
     * @return
     */
    Long insert(String username,Integer age);

    /**
     * @param id
     * @return
     */
    Account withId(Long id);

    /**
     * @return
     */
    List<Account> list();

    /**
     * @param account
     */
    void update(Account account);

    /**
     * @param id
     */
    void delete(Long id);
}
