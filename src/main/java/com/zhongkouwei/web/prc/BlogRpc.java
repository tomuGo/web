package com.zhongkouwei.web.prc;

import com.zhongkouwei.blog.client.BlogClient;
import com.zhongkouwei.blog.common.dto.BlogDTO;
import com.zhongkouwei.blog.common.model.Blog;
import com.zhongkouwei.blog.common.model.Floor;
import com.zhongkouwei.blog.common.model.PageInfo;
import com.zhongkouwei.user.common.model.ResultSub;
import com.zhongkouwei.web.aop.RpcHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BlogRpc {

    @Autowired
    BlogClient blogClient;

    @RpcHandler
    public String addBlog(BlogDTO blogDTO) {
        ResultSub<String> res = blogClient.addBlog(blogDTO);
        RpcUtil.resultHandler(res);
        return res.getData();
    }

    @RpcHandler
    public PageInfo<Blog> getBlogs(Integer pageNumber, Integer pageSize, Integer userId,
                                   Byte blogType, Byte boutique, String blogName) {
        ResultSub<PageInfo<Blog>> blogs=blogClient.getBlogs(pageNumber,pageSize,null,null,userId,blogType,boutique,blogName);
        System.out.println(blogs.toString());
        RpcUtil.resultHandler(blogs);
        return blogs.getData();
    }

    @RpcHandler
    public Integer addFloor(String blogId, Floor floor){
        ResultSub<Integer> floorId=blogClient.addSection(blogId,floor);
        RpcUtil.resultHandler(floorId);
        return floorId.getData();
    }

    @RpcHandler
    public BlogDTO getBlog(String blogId){
        ResultSub<BlogDTO> blogDTO=blogClient.getBlogContent(blogId);
        RpcUtil.resultHandler(blogDTO);
        return blogDTO.getData();
    }


}
