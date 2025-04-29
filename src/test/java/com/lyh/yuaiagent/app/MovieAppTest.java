package com.lyh.yuaiagent.app;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyh.yuaiagent.entity.Movie;
import com.lyh.yuaiagent.entity.Movieorder;
import com.lyh.yuaiagent.service.MovieService;
import com.lyh.yuaiagent.service.MovieorderService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@Slf4j
@ActiveProfiles("local")
class MovieAppTest {

    @Resource
    private MovieApp movieApp;

    @Resource
    private MovieService movieService;

    @Resource
    private MovieorderService movieorderService;


    @Test
    public void testChat() {
        String chatId = UUID.randomUUID().toString();
        String userId = "1";
        //获取电影列表
        List<Movie> movieList = movieService.list();
        //获取当前用户订单
        QueryWrapper<Movieorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",userId);
        List<Movieorder> movieorderList = movieorderService.list(queryWrapper);
        MovieApp.MovieList recommendedMovies = movieApp.recommendMovies(chatId, movieList, movieorderList);
        System.out.println(recommendedMovies);
    }

}