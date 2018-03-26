package com.summer.control;

import com.summer.base.bean.Tools;
import com.summer.mybatis.DBTools;
import com.summer.mybatis.entity.User;
import com.summer.mybatis.entity.Video;
import com.summer.mybatis.mapper.VideoMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by SWSD on 2018-03-26.
 */

@Controller
@RequestMapping("/video")
public class VideoControl {

    @RequestMapping(value = "/getAllVideos",method = RequestMethod.GET)
    public void aaa(HttpServletRequest req, HttpServletResponse res){
        Tools.init(req,res);

        SqlSession sqlSession = DBTools.getSession();
        VideoMapper videoMapper = sqlSession.getMapper(VideoMapper.class);

        List<User> users = videoMapper.selectAllUser();
        sqlSession.commit();
        Tools.printOut(res,users);
    }
}
