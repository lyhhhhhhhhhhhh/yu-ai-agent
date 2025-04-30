package com.lyh.yuaiagent.service;

import com.lyh.yuaiagent.entity.Movie;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author liyuhang
* @description 针对表【movie(电影)】的数据库操作Service
* @createDate 2025-04-28 20:01:04
*/
public interface MovieService extends IService<Movie> {

    List<Movie> getRecommendMovieList(String userId);
}
