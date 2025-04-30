package com.lyh.yuaiagent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.yuaiagent.app.MovieApp;
import com.lyh.yuaiagent.entity.Movie;
import com.lyh.yuaiagent.entity.Movieorder;
import com.lyh.yuaiagent.mapper.MovieorderMapper;
import com.lyh.yuaiagent.service.MovieService;
import com.lyh.yuaiagent.mapper.MovieMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author liyuhang
 * @description 针对表【movie(电影)】的数据库操作Service实现
 * @createDate 2025-04-28 20:01:04
 */
@Service
@Slf4j
public class MovieServiceImpl extends ServiceImpl<MovieMapper, Movie>
        implements MovieService {

    @Resource
    private MovieorderMapper movieorderMapper;

    @Resource
    private MovieMapper movieMapper;

    @Resource
    private MovieApp movieApp;

    @Override
    public List<Movie> getRecommendMovieList(String userId) {
        // 1.根据用户id获取用户历史订单记录
        QueryWrapper<Movieorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        List<Movieorder> movieorders = movieorderMapper.selectList(queryWrapper);

        // 2.根据历史订单记录推荐电影
        String chatId = UUID.randomUUID().toString();
        MovieApp.MovieList movies = movieApp.recommendMoviesWithRag(chatId, movieorders);

        // 创建一个Map保存电影ID到推荐理由的映射
        Map<Long, String> idToReasonMap = new HashMap<>();

        //获取所有推荐电影Id并保存推荐理由
        ArrayList<Long> recommendMovieIdList = new ArrayList<>();
        movies.reCommandList().forEach(
                movie -> {
                    recommendMovieIdList.add(movie.getId());
                    idToReasonMap.put(movie.getId(), movie.getRecommendReason());
                });

        if (recommendMovieIdList.isEmpty()) {
            return new ArrayList<>();
        }

        // 3.返回推荐电影列表
        List<Movie> allMovies = movieMapper.selectList(null);
        List<Movie> recommendMovies = allMovies.stream()
                .filter(movie -> recommendMovieIdList.contains(movie.getId()))
                .peek(movie -> {
                    // 这里我们假设Movie类中有一个setMoviesynopsis方法，可以用来临时存储推荐理由
                    // 如果Movie没有其他合适的字段，建议修改Movie类添加recommendReason字段
                    String reason = idToReasonMap.get(movie.getId());
                    if (reason != null) {
                        movie.setMoviesynopsis(reason); // 使用电影简介字段临时存储推荐理由
                    }
                })
                .toList();

        return recommendMovies;
    }
}




