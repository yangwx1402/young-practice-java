package com.young.practice.spring.jdbc;

import com.young.practice.bean.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

/**
 * @author yangyong
 */
@Service
public class AccountOperationImpl implements AccountOperation {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Long insert(String username, Integer age) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            /**
             * 如果需要返回生成的主键，那么需要设置Statement.RETURN_GENERATED_KEYS
             */
            PreparedStatement preparedStatement = connection.prepareStatement("insert account(username,age) values(?,?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, age);
            return preparedStatement;
        }, keyHolder);
        return (Long) keyHolder.getKeys().get("id");
    }

    @Override
    public Account withId(Long id) {
        return jdbcTemplate.query(con -> {
            PreparedStatement preparedStatement = con.prepareStatement("select id,username,age from account where id=?");
            preparedStatement.setLong(1, id);
            return preparedStatement;
        }, rs -> {
            Account account = new Account();
            account.setUsername(rs.getString("username"));
            account.setId(rs.getLong("id"));
            account.setAge(rs.getInt("age"));
            return account;
        });
    }

    @Override
    public List<Account> list() {
        return jdbcTemplate.query(con -> {
            PreparedStatement preparedStatement = con.prepareStatement("select id,username,age from account");
            return preparedStatement;
        }, (rs, rowNum) -> {
            Account account = new Account();
            account.setUsername(rs.getString("username"));
            account.setId(rs.getLong("id"));
            account.setAge(rs.getInt("age"));
            return account;
        });
    }

    @Override
    public void update(Account account) {
      jdbcTemplate.update("update account set username = ?,age = ? where id =? ", ps -> {
          ps.setString(1,account.getUsername());
          ps.setInt(2,account.getAge());
          ps.setLong(3,account.getId());
      });
    }

    @Override
    public void delete(Long id) {
       jdbcTemplate.update("delete from account where id = ?", ps -> {
            ps.setLong(1,id);
       });
    }
}
