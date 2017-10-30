package com.kaishengit.dao;


import com.kaishengit.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(User user) {

        String sql = "insert into user (name,address) values (? , ?)";
        jdbcTemplate.update(sql,user.getName(),user.getAddress());
    }
    public User findById(int id) {

        String sql = "select * from user where id = ?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(User.class),id);
    }
    public List<User> findAll() {

        String sql = "select * from user";
        return jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setAddress(resultSet.getString("address"));
                return user;
            }
        });
    }
    public int count() {

        String sql = "select count(*) FROM user";

        return jdbcTemplate.queryForObject(sql,new SingleColumnRowMapper<Long>()).intValue();
    }
}
