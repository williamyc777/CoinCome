package com.nyu.coincome.controller;

import com.nyu.coincome.entity.Users;
import com.nyu.coincome.mapper.UsersMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestDBController {

    private final UsersMapper usersMapper;

    public TestDBController(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }

    // http://localhost:8080/api/test-db
    @GetMapping("/api/test-db")
    public List<Users> testDB() {
        return usersMapper.selectList(null);  // select * from Users;
    }
}
