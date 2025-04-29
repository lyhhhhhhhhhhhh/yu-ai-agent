package com.lyh.yuaiagent.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyh.yuaiagent.app.LoveApp;
import com.lyh.yuaiagent.app.MovieApp;
import com.lyh.yuaiagent.common.BaseResponse;
import com.lyh.yuaiagent.common.ResultUtils;
import com.lyh.yuaiagent.entity.Movie;
import com.lyh.yuaiagent.entity.Movieorder;
import com.lyh.yuaiagent.service.MovieService;
import com.lyh.yuaiagent.service.MovieorderService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/health")
public class HealthController {

    @Resource
    private LoveApp loveApp;
    
    @Resource
    private MovieApp movieApp;
    
    @Resource
    private MovieService movieService;
    
    @Resource
    private MovieorderService movieorderService;

    @GetMapping
    public String healthCheck() {
        return "ok";
    }

    @GetMapping("/getAIResponse/{message}")
    public BaseResponse<String> getAIResponse(@PathVariable String message) {
        String chatId = UUID.randomUUID().toString();
        String aiMessage = loveApp.doChat(message, chatId);
        return ResultUtils.success(aiMessage);
    }
    
    @GetMapping("/getMovieList")
    public BaseResponse<MovieApp.MovieList> getMovieList() {
        String chatId = cn.hutool.core.lang.UUID.randomUUID().toString();
        String userId = "1";
        //获取电影列表
        List<Movie> movieList = movieService.list();
        //获取当前用户订单
        QueryWrapper<Movieorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",userId);
        List<Movieorder> movieorderList = movieorderService.list(queryWrapper);
        MovieApp.MovieList movies = movieApp.recommendMovies(chatId, movieList, movieorderList);
        return ResultUtils.success(movies);
    }

}
