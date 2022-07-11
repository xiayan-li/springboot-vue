package com.example.springboot.controller;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import org.springframework.web.bind.annotation.*;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.UserMapper;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")

//负责前后台交互
public class UserController {
    @Resource
    UserMapper userMapper;

    //    从前端post过来， 增
    @PostMapping
    public  Result<?> save (@RequestBody User user){
        if (user.getPassword() == null){
            user.setPassword("420420");
        }
        userMapper.insert(user);
        return Result.success();
    }

    //    前端需要从后端get数据，查
    @GetMapping
    public  Result<?> find (@RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize,
                            @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(User::getNickName, search);
        }
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize),wrapper);
        return Result.success(userPage);
    }

    //改
    @PutMapping
    public  Result<?> update (@RequestBody User user){

        userMapper.updateById(user);
        return Result.success();
    }

    //删
    @DeleteMapping("/{id}")
    public  Result<?> delete (@PathVariable long id){

        userMapper.deleteById(id);
        return Result.success();
    }

    //    从前端post过来， 登录
    @PostMapping("/login")
    public  Result<?> login (@RequestBody User user) {
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()).eq(User::getPassword, user.getPassword()));
        if (res == null) {
            return Result.error("-1", "用户名或密码错误");
        }
        return Result.success(res);
    }

    //    从前端post过来，注册
    @PostMapping("/register")
    public  Result<?> register (@RequestBody User user){
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()));
        if (res != null){
            return Result.error("-1","用户名重复");
        }
        if (user.getPassword() == null){
            user.setPassword("420420");
        }
        userMapper.insert(user);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(userMapper.selectById(id));
    }

}

