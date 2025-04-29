package com.lyh.yuaiagent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.yuaiagent.entity.Movie;
import com.lyh.yuaiagent.service.MovieService;
import com.lyh.yuaiagent.mapper.MovieMapper;
import org.springframework.stereotype.Service;

/**
* @author liyuhang
* @description 针对表【movie(电影)】的数据库操作Service实现
* @createDate 2025-04-28 20:01:04
*/
@Service
public class MovieServiceImpl extends ServiceImpl<MovieMapper, Movie>
    implements MovieService{

}




