package com.dragon.project.front.controller;

import com.dragon.common.constant.CommonConstant;
import com.dragon.framework.aspectj.lang.annotation.VLog;
import com.dragon.framework.web.controller.BaseController;
import com.dragon.project.blog.blog.domain.Blog;
import com.dragon.project.blog.blog.service.BlogService;
import com.dragon.project.blog.category.service.CategoryService;
import com.dragon.project.blog.tag.domain.Tag;
import com.dragon.project.blog.tag.service.TagService;
import com.dragon.project.front.service.HomeService;
import com.dragon.project.link.service.LinkService;
import com.dragon.project.system.carouselMap.service.CarouselMapService;
import com.dragon.project.system.notice.service.INoticeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2019/6/17 16:27
 * @description： 前台首页Controller
 * @modified By：
 * @version: 1.0.0
 */
@Controller
@RequestMapping({"/","/my_blog"})
public class HomeController extends BaseController {

    @Autowired
    private HomeService homeService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private INoticeService noticeService;

    @Autowired
    private CarouselMapService carouselMapService;

    /**
     * 博客首页
     * @param pageNum
     * @param model
     * @return
     */
    @VLog(title = "首页")
    @RequestMapping("")
    public String index(Integer pageNum, Model model){
        setCommonMessage(model);
        PageHelper.startPage(pageNum == null ? 1 : pageNum, 12, "create_time desc");
        model.addAttribute("blogs", new PageInfo<>(homeService.selectFrontBlogList(new Blog())));
        //最新课程
        model.addAttribute("repositoryList", blogService.selectNewestUpdateRepository());
        //最新源码
        model.addAttribute("sourceCodeList", blogService.selectNewestUpdateSourceCode());
        //放置轮播图
        model.addAttribute("carouselMaps", carouselMapService.selectCarouselMapListFront());
        return "front/index/index";
    }

    private void setCommonMessage(Model model) {
        //获取分类下拉项中的分类
        model.addAttribute("categories", categoryService.selectSupportCategoryList());
        //查询最新更新的源码

        //查询最新更新的课程

        //查询通知
        model.addAttribute("notices", noticeService.selectNoticeListDisplay());
        //查询所有的标签
        model.addAttribute("tags", tagService.selectTagList(new Tag()));
        //查询文章排行
        model.addAttribute("blogRanking", blogService.selectBlogRanking());
        //查询推荐博文
        model.addAttribute("supportBlog", blogService.selectSupportBlog());
        //查询你喜欢的文章
        model.addAttribute("randBlogList", blogService.selectRandBlogList());
        //获取友链信息
        model.addAttribute("links", linkService.selectLinkListFront());
    }

    /**
     * 轮播图点击量
     * @param carouselId
     * @param url
     * @return
     */
    @GetMapping("/f/carouselMap/{carouselId}")
    public String carouselMapI(@PathVariable Integer carouselId, String url) {
        //增加点击量
        carouselMapService.incrementCarouselClickById(carouselId);
        return redirect(url);
    }

    /**
     * 个人博客日记
     * @param pageNum
     * @param model
     * @return
     */
    @VLog(title = "个人博客")
    @RequestMapping("blog")
    public String blog(Integer pageNum, Model model){
        setCommonMessage(model);
        PageHelper.startPage(pageNum == null ? 1 : pageNum, 12, "create_time desc");
        model.addAttribute("blogs", new PageInfo<>(homeService.selectFrontBlogList(new Blog())));
        return "front/blog/blog";
    }

    /**
     * 博客
     * @param blogId
     * @param model
     * @return
     */
    @VLog(title = "博客")
    @GetMapping("/f/article/{blogId}.html")
    public String article(@PathVariable Integer blogId, Model model) {
        setCommonMessage(model);
        Blog blog = blogService.selectBlogWithTextAndTagsAndCategoryByBlogId(blogId);
        //只能访问是已经发表的文章
        if (!CommonConstant.one.equals(blog.getStatus())) {
            return "error/404";
        }
        model.addAttribute("blog", blog);
        model.addAttribute("nextBlog", blogService.selectNextBlogById(blogId));
        model.addAttribute("previousBlog", blogService.selectPreviousBlogById(blogId));
        model.addAttribute("randBlogList", blogService.selectRandBlogList());
        return "front/article/article";
    }

    /**
     * 随笔分类
     * @param categoryId
     * @param pageNum
     * @param model
     * @return
     */
    @VLog(title = "随笔分类")
    @GetMapping({"/f/category/{categoryId}.html"})
    public String category(@PathVariable Integer categoryId, Integer pageNum, Model model) {
        setCommonMessage(model);
        model.addAttribute("category", categoryService.selectCategoryById(categoryId));
        Blog blog = new Blog();
        blog.setCategoryId(categoryId);
        PageHelper.startPage(pageNum == null ? 1 : pageNum, 10, "create_time desc");
        model.addAttribute("blogs", new PageInfo<>(homeService.selectFrontBlogList(blog)));
        return "front/category/category";
    }

    /**
     * 归档
     */
    @VLog(title = "归档")
    @GetMapping("archives")
    public String archives(Model model) {
        setCommonMessage(model);
        model.addAttribute("archives", homeService.selectArchives());
        return "front/archives/archives";
    }

    /**
     * 源码库
     * @param pageNum
     * @param model
     * @return
     */
    @VLog(title = "源码库")
    @RequestMapping("sourcecode")
    public String sourcecode(Integer pageNum, Model model){
        setCommonMessage(model);
        Blog blog = new Blog();
        blog.setModule("3");
        PageHelper.startPage(pageNum == null ? 1 : pageNum, 10, "create_time desc");
        model.addAttribute("blogs", new PageInfo<>(homeService.selectFrontBlogList(blog)));
        return "front/sourcecode/sourcecode";
    }

    /**
     * 知识库
     * @param pageNum
     * @param model
     * @return
     */
    @VLog(title = "知识库")
    @RequestMapping("repository")
    public String repository(Integer pageNum, Model model){
        setCommonMessage(model);
        Blog blog = new Blog();
        blog.setModule("1");
        PageHelper.startPage(pageNum == null ? 1 : pageNum, 10, "create_time desc");
        model.addAttribute("blogs", new PageInfo<>(homeService.selectFrontBlogList(blog)));
        return "front/repository/repository";
    }

    /**
     * 关于我
     * @return
     */
    @VLog(title = "关于我")
    @RequestMapping("about")
    public String about(Model model){
        setCommonMessage(model);
        return "front/about/about";
    }

    /**
     * 标签
     * @param tagId
     * @param pageNum
     * @param model
     * @return
     */
    @VLog(title = "标签")
    @GetMapping("/f/tag/{tagId}.html")
    public String tag(@PathVariable Integer tagId, Integer pageNum, Model model) {
        setCommonMessage(model);
        PageHelper.startPage(pageNum == null ? 1 : pageNum, 10, "b.create_time desc");
        List<Blog> blogs = blogService.selectBlogListByTagId(tagId);
        model.addAttribute("blogs", new PageInfo(blogs));
        model.addAttribute("tag", tagService.selectTagById(tagId));
        return "front/tag/tag";
    }

    /**
     * 友链跳转
     */
    @VLog(title = "友链跳转")
    @GetMapping("/f/linkRedirect")
    public String redirectTo(String ref, Integer id) {
        //增加点击量
        linkService.incrementLinkClickById(id);
        return redirect(ref);
    }
}
