package com.java456.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.java456.entity.Config;
import com.java456.service.ConfigService;


/**
 * #初始化加载数据
 * @author Administrator
 *
 */
@Component
public class InitSystem implements ServletContextListener,ApplicationContextAware{
	
	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext=applicationContext;
	}
	
	/**
	 *    #加载数据到application缓存中
	 */
	public void loadData(ServletContext application){
		ConfigService configService=(ConfigService) applicationContext.getBean("configService");
		Config config = configService.findById(1);
		application.setAttribute("config", config);
		
		
	}
	
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		loadData(sce.getServletContext());
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

	
}
