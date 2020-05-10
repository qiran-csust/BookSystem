package com.java456.controller.houtai;

import com.java456.dao.MessageDao;
import com.java456.dao.MessageTypeDao;
import com.java456.dao.MoviesDao;
import com.java456.dao.MoviesTypeDao;
import com.java456.entity.Food;
import com.java456.entity.FoodType;
import com.java456.entity.Message;
import com.java456.entity.MessageType;
import com.java456.entity.Movies;
import com.java456.entity.MoviesType;
import com.java456.service.MoviesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/houtai/movies")
public class HouTai_Movies_Controller {
    @Resource
    private MoviesDao moviesDao;
    @Resource
    private MoviesService moviesService;
    @Resource
    private MoviesTypeDao moviesTypeDao;
    @Resource
    private MessageDao messageDao;
	@Resource
	private MessageTypeDao messageTypeDao;
    /**
     * /houtai/movies/manage
     */
    @RequestMapping("/manage")
    public ModelAndView manage() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "电影管理");
        
        List<Message> messages = messageDao.findAllMovies();
        moviesDao.deleteAll();
        List<Movies> data = new ArrayList<>();
        for (Message ss:messages)
        {
        	Movies movies =new Movies();
        	movies.setId(ss.getId());
        	movies.setAddrString(ss.getAddrString());
        	movies.setCreateDateTime(new Date());
        	movies.setImageUrl(ss.getImageUrl());
        	movies.setSource(ss.getSource());
        	movies.setPrice(ss.getPrice());
        	movies.setUrlString(ss.getUrlString());
        	movies.setOrderNo(ss.getOrderNo());
        	if(data.size() == 300) {
        		moviesDao.saveAll(data);
                data.clear();
            }
        	data.add(movies);
        }
        if(!data.isEmpty()) {
        	moviesDao.saveAll(data);
        }
        
        
        
        
        
        
        
        mav.setViewName("/admin/page/movies/movies_manage");
        return mav;
    }

    /**
     * /houtai/movies/add
     * @return
     * @throws Exception
     */
    @RequestMapping("/add")
    public ModelAndView add() throws Exception {
        ModelAndView mav = new ModelAndView();

        Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
        Page<MoviesType> list = moviesTypeDao.findAll(pageable);
        List<MoviesType> moviesTypeList = list.getContent(); //拿到list集合
        mav.addObject("moviesTypeList", moviesTypeList);


        mav.addObject("btn_text", "添加");
        mav.addObject("save_url", "/admin/movies/add");
        mav.setViewName("/admin/page/movies/add_update");
        return mav;
    }


    /**
     * /houtai/movies/edit?id=1
     * @return
     * @throws Exception
     */
    @RequestMapping("/edit")
    public ModelAndView edit(@RequestParam(value = "id", required = false) Integer id) throws Exception {
        ModelAndView mav = new ModelAndView();

        Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
        Page<MoviesType> list = moviesTypeDao.findAll(pageable);
        List<MoviesType> moviesTypeList = list.getContent();//拿到list集合
        mav.addObject("moviesTypeList", moviesTypeList);


        Movies movies = moviesDao.findId(id);
        mav.addObject("movies", movies);
        mav.addObject("btn_text", "修改");
        mav.addObject("save_url", "/admin/movies/update?id=" + id);
        mav.setViewName("/admin/page/movies/add_update");
        return mav;
    }
    /**
	 * /houtai/food/sort
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sort")
	public ModelAndView sort() throws Exception {
		ModelAndView mav = new ModelAndView();
		List<Movies> userList =moviesDao.MoviesOrderByPrice();
		Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
		Page<MoviesType> list = moviesTypeDao.findAll(pageable);
		List<MoviesType> MoviesTypeList = list.getContent();//拿到list集合
		mav.addObject("TypeList", MoviesTypeList);
		mav.addObject("userList", userList);
		mav.addObject("title", "排序");
		mav.setViewName("/admin/Sort");
		return mav;

	}
	/**
	 * /houtai/food/select
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/select")
	public ModelAndView select(Model model,HttpServletResponse  res,HttpServletRequest req,HttpSession session) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		String source=req.getParameter("source");	//获取html页面搜索框的值
		Integer message_type_id=1;
		List<Message> userList =messageDao.selectMessages(message_type_id, source);
		Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
		Page<MessageType> list = messageTypeDao.findAll(pageable);
		List<MessageType> MessageTypeList = list.getContent();//拿到list集合
		model.addAttribute("type","movies");
		mav.addObject("MessageTypeList", MessageTypeList);
		mav.addObject("userList", userList);
		mav.addObject("title", "选择查询");
		mav.setViewName("/admin/select");
		return mav;

	}
    
    
    
}
