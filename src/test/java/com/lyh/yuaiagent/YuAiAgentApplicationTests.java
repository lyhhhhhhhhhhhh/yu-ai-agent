package com.lyh.yuaiagent;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyh.yuaiagent.entity.Movie;
import com.lyh.yuaiagent.entity.Movieorder;
import com.lyh.yuaiagent.service.MovieService;
import com.lyh.yuaiagent.service.MovieorderService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("local")
class YuAiAgentApplicationTests {

    @Test
    void contextLoads() {
    }

    @Resource
    private MovieorderService movieorderService;

    @Resource
    private MovieService movieService;

    @Test
    void MovieOrderTest() {
        QueryWrapper<Movieorder> queryWrapper = new QueryWrapper();
        queryWrapper.eq("userId", 1);
        List<Movieorder> list = movieorderService.list(queryWrapper);
        list.forEach(movieorder -> {
            System.out.println(movieorder);
        });
    }

    @Test
    void MovieTest() {
        List<Movie> list = movieService.list();
        for (Movie movie : list) {
            System.out.println(movie);
        }
    }


}
