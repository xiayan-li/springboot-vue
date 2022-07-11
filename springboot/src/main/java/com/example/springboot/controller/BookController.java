package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Book;
import com.example.springboot.mapper.BookMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/book")

//负责前后台交互
public class BookController {
    @Resource
    BookMapper bookMapper;

    //    从前端post过来， 增
    @PostMapping
    public  Result<?> save (@RequestBody Book Book){

        bookMapper.insert(Book);
        return Result.success();
    }

    //    前端需要从后端get数据，查
    @GetMapping
    public  Result<?> find (@RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize,
                            @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<Book> wrapper = Wrappers.<Book>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(Book::getName, search);
        }
        Page<Book> BookPage = bookMapper.selectPage(new Page<>(pageNum, pageSize),wrapper);
        return Result.success(BookPage);
    }

    //改
    @PutMapping
    public  Result<?> update (@RequestBody Book Book){

        bookMapper.updateById(Book);
        return Result.success();
    }

    //删
    @DeleteMapping("/{id}")
    public  Result<?> delete (@PathVariable long id){

        bookMapper.deleteById(id);
        return Result.success();
    }

    @PostMapping("/deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Integer> ids) {
        bookMapper.deleteBatchIds(ids);
        return Result.success();
    }



}
