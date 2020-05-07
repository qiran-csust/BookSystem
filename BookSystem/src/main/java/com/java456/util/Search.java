package com.java456.util;
import java.awt.AWTException;
import java.awt.List;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.core.PrettyPrinter;
import com.huaban.analysis.jieba.JiebaSegmenter;

public class Search implements Spelling{ //几个功能 1.使用近似匹配
private String contents;
private String UseApproximateSearch;

//构造方法
public Search(){
	this("美食","UseApproximateSearch");
	System.out.println("当前信息为"+contents+"当前采用"+UseApproximateSearch);
}
public Search(String contents,String UseApproximateSearch){
		this.contents = contents;
		this.UseApproximateSearch = UseApproximateSearch;//如果确定了分词工具 两个功能 在胡乱输入的情况下 进行分词切割后的重连 第二个 进行相似度匹配
	}
public void WordSplit(String information){
	        JiebaSegmenter segmenter = new JiebaSegmenter();
	        /*单词*/
	        System.out.println(segmenter.sentenceProcess(information));
	        ArrayList<String> s = new ArrayList();
	        s=(ArrayList<String>) segmenter.sentenceProcess(information);
	        System.out.println(s);
	        for (Object o : s ) {
	        	System.out.println(o);
	        	System.out.println(o.toString().length());//通过移动的字段
	        	//System.out.println(o.toString().substring(0,1));
	        	//System.out.println(o.toString().substring(1,2));	        	  
	        	//ApproximateSearch(o.toString().substring(0,1));
	        	//ApproximateSearch(o.toString().substring(1,2));//找到两个中都有的来推荐 
			}
	        /*多词*/
	        /*String[] sentences =
	                new String[] {"这是一个伸手不见五指的黑夜。我叫孙悟空，我爱北京，我爱Python和C++。", "我不喜欢日本和服。", "雷猴回归人间。",
	                        "工信处女干事每月经过下属科室都要亲口交代24口交换机等技术性器件的安装工作", "结果婚的和尚未结过婚的"};
	        for (String sentence : sentences) {
	            System.out.println(segmenter.process(sentence, JiebaSegmenter.SegMode.INDEX).toString());
	        }*/

	//使用结巴分词工具进行分词
	
}
public void ApproximateSearch(Object information){
	//使用大量的数据库来进行近似搜索
	//加载数据库
	//更新数据库
	
	System.out.println(information+"近似搜索功能已经被启动");//几种问题
	//第一步 一个分词都放进去 看是否存在
	//如果都有 推荐那个词 这里就可以保证一些事因为顺序错误的
	//如果只有一部分 换词 重复上一步骤(这里要考虑实用性的问题)
	findTheKey("C:\\Users\\qcj\\Documents\\workspace-sts-3.9.10.RELEASE\\BookSystem\\src\\main\\java\\com\\java456\\dict.txt",information);
	//这里先做整体 
	//然后单个分离
	//之后寻找两个之间的同类
	
}
public void setContents(String contents){
	this.contents = contents;
}
public String getContents(){
	return contents;
}
public String getUseApproximateSearch() {
	return UseApproximateSearch;
}
public void setUseApproximateSearch(String useApproximateSearch) {
	this.UseApproximateSearch = useApproximateSearch;
}
public  void findTheKey(String filePath,Object information) {
	try {
		boolean findkey = false;
		FileReader f = new FileReader(filePath);
		BufferedReader buf = new BufferedReader(f);
		String strLine; 
		ArrayList<String> keyList1 = new ArrayList<String>();
		ArrayList<String> keyList2 = new ArrayList<String>();
		while((strLine = buf.readLine() ) != null){
			if (strLine.indexOf(information.toString())!=-1) {//等于-1表示这个字符串中没有这个字符
				System.out.println("找到该字段"); 
				System.out.println(strLine); 
				keyList1.add(strLine);
				findkey = true;
			}
		}
		/*for (Object o :keyList1 ) {
			System.out.println(o); 
		}*/
		if (findkey==false) {
			System.out.println("没有找到对应的字段信息"); 
			int getStrLen=information.toString().length();//通过移动的字段
			for(int i=0;i<getStrLen;i++) {
				ApproximateSearch(information.toString().substring(i,i+1));
				
			}//可以给与单子的访问次数来给与反馈 这里需要一个数据库 
			//ApproximateSearch(information.toString().substring(0,1));
        	//ApproximateSearch(information.toString().substring(1,2));
		}
		f.close();
		buf.close();
		}
		catch(IOException e){
			System.out.println("没有找到该文件,");
		} 
}
}
