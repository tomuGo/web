package com.zhongkouwei.web.controller;

import com.zhongkouwei.blog.common.dto.BlogDTO;
import com.zhongkouwei.blog.common.enums.BlogType;
import com.zhongkouwei.blog.common.model.Blog;
import com.zhongkouwei.blog.common.model.Floor;
import com.zhongkouwei.blog.common.model.PageInfo;
import com.zhongkouwei.user.common.model.ResultSub;
import com.zhongkouwei.web.aop.TokenHandler;
import com.zhongkouwei.web.prc.BlogRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

@RestController
public class BlogController {

    @Autowired
    BlogRpc blogRpc;

    @TokenHandler
    @RequestMapping(value = "blogs", method = RequestMethod.POST)
    public ResultSub<String> addBlog(BlogDTO blogDTO) {
        String blogId = blogRpc.addBlog(blogDTO);
        return new ResultSub<>(blogId);
    }

    @RequestMapping(value = "blogs", method = RequestMethod.GET)
    public ResultSub<PageInfo<Blog>> getBlogs(@RequestParam("pageNumber") Integer pageNumber,
                                              @RequestParam("pageSize") Integer pageSize,
                                              @RequestParam(value = "userId", required = false) Integer userId,
                                              @RequestParam(value = "blogType", required = false) Byte blogType,
                                              @RequestParam(value = "blogName", required = false) String blogName) {
        Byte boutique = null;
        if (BlogType.GOOD.getBlogType().equals(blogType)) {
            boutique = 1;
        }
        PageInfo<Blog> blogs = blogRpc.getBlogs(pageNumber, pageSize, userId, blogType, boutique, blogName);
        return new ResultSub<>(blogs);
    }

    @RequestMapping(value = "blogs/{id}", method = RequestMethod.GET)
    public ResultSub<BlogDTO> getBlog(@PathVariable("id") String blogId) {
        BlogDTO blogDTO = blogRpc.getBlog(blogId);
        return new ResultSub<>(blogDTO);
    }

    @TokenHandler
    @RequestMapping(value = "blogs/{id}/addFloor", method = RequestMethod.POST)
    public ResultSub<Integer> addFloor(@PathVariable("id") String blogId,
                                       @RequestBody Floor floor) {
        Integer floorId = blogRpc.addFloor(blogId, floor);
        return new ResultSub<>(floorId);
    }
}
