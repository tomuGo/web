package com.zhongkouwei.web.prc;

import com.zhongkouwei.blog.client.BlogClient;
import com.zhongkouwei.blog.common.dto.BlogDTO;
import com.zhongkouwei.blog.common.model.Blog;
import com.zhongkouwei.blog.common.model.Floor;
import com.zhongkouwei.blog.common.model.PageInfo;
import com.zhongkouwei.user.common.model.ResultSub;
import com.zhongkouwei.user.common.model.UserInfo;
import com.zhongkouwei.web.component.CurrentUserComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BlogRpc {

    @Autowired
    BlogClient blogClient;

    public String addBlog(BlogDTO blogDTO) {
        UserInfo currentUser=CurrentUserComponent.getCurrentUser();
        blogDTO.setAuthor(currentUser.getUserId());
        blogDTO.setAuthorName(currentUser.getUsername());
        ResultSub<String> res = blogClient.addBlog(blogDTO);
        RpcUtil.resultHandler(res);
        return res.getData();
    }

    public PageInfo<Blog> getBlogs(Integer pageNumber, Integer pageSize, Integer userId,
                                   Byte blogType, Byte boutique, String blogName) {
        ResultSub<PageInfo<Blog>> blogs=blogClient.getBlogs(pageNumber,pageSize,null,null,userId,blogType,boutique,blogName);
        RpcUtil.resultHandler(blogs);
        return blogs.getData();
    }

    public Integer addFloor(String blogId, Floor floor){
        UserInfo currentUser=CurrentUserComponent.getCurrentUser();
        floor.setUserId(currentUser.getUserId());
        floor.setAuthorName(currentUser.getUsername());
        ResultSub<Integer> floorId=blogClient.addSection(blogId,floor);
        RpcUtil.resultHandler(floorId);
        return floorId.getData();
    }

    public BlogDTO getBlog(String blogId){
        ResultSub<BlogDTO> blogDTO=blogClient.getBlogContent(blogId);
        RpcUtil.resultHandler(blogDTO);
        return blogDTO.getData();
    }


}
