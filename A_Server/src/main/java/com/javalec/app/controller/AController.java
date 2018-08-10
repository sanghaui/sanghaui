package com.javalec.app.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.javalec.app.command.ACommand;
import com.javalec.app.command.ADeleteCommand;
import com.javalec.app.command.ASelectCommand2;
import com.javalec.app.command.AUpdateCommand;
import com.javalec.app.command.AnAllListCommand;
import com.javalec.app.command.AnAllReListCommand;
import com.javalec.app.command.BoardInsertCommand;
import com.javalec.app.command.replyInsertCommand;




@Controller
public class AController {
	
	ACommand command;	
	
	@RequestMapping(value="/anAllList", method = {RequestMethod.GET, RequestMethod.POST}  )
	public String anSelect(HttpServletRequest req, Model model){
		System.out.println("anAllList");
		
		command = new AnAllListCommand();
		command.execute(model);
		
		return "AnAllList";
	}
	
	@RequestMapping(value="/anReplyList", method = {RequestMethod.GET, RequestMethod.POST}  )
	public String anReplyList(HttpServletRequest req, Model model){
		System.out.println("anReplyList");
		
		model.addAttribute("parent",req.getParameter("parent"));
		System.out.println(req.getParameter("parent"));
		command = new AnAllReListCommand();
		command.execute(model);
		
		return "anReplyList";
	}
	
	@RequestMapping(value="/anReply", method = {RequestMethod.GET, RequestMethod.POST}  )
	public String anBigFile(MultipartFile file, HttpServletRequest req, Model model){
		System.out.println("anReg()");
		
		//file.
		
		return null;
	}
			
	@RequestMapping(value="/anNoticeList", method = {RequestMethod.GET, RequestMethod.POST}  )
	public String anSelect2(HttpServletRequest req, Model model){
		System.out.println("anNoticeList()");		
		
//		// Json 占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙占�
//		String jsonTextPath = req.getSession().getServletContext().getRealPath("/resources/images/upload/");
//		model.addAttribute("jsonTextPath", jsonTextPath);
//		
		command = new ASelectCommand2();
		command.execute(model);		
		System.out.println("공지사항 처리됨");
		return "AnNoticeList";
	}
	
	@RequestMapping(value="/BoardInsert", method = {RequestMethod.GET, RequestMethod.POST})
	public void anInsert(HttpServletRequest req, Model model){
		System.out.println("BoardInsert()");
		//모듈객체는 requese에 객체를 담아서 보낼때 필요함
        try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		
		model.addAttribute("id", req.getParameter("id"));
		model.addAttribute("pw", req.getParameter("pw"));
		model.addAttribute("title", req.getParameter("title"));	
		model.addAttribute("content", req.getParameter("content"));	
//		model.addAttribute("content", req.getAttribute("content"));
		
		model.addAttribute("imagePath", req.getParameter("imagePath"));
		model.addAttribute("imageData", req.getParameter("imageData"));
		
		
		System.out.println(req.getParameter("id"));
		System.out.println(req.getParameter("pw"));
		System.out.println(req.getParameter("content"));
		System.out.println(req.getParameter("title"));
		
		String dbImgPath = req.getParameter("imagePath");
		
		System.out.println(dbImgPath + ":" + req.getParameter("imagePath"));
		
		if(dbImgPath !=null && dbImgPath != ""){
			String fileName = dbImgPath.split("/")[dbImgPath.split("/").length -1];
			String realImgPath = req.getSession().getServletContext()
					.getRealPath("/resources/images/upload/" + fileName);
			System.out.println( dbImgPath + " : " + fileName + " : " + realImgPath);
			model.addAttribute("realImgPath", realImgPath);
		}else{
			String fileName = "FileFail.jpg";
			String realImgPath = req.getSession().getServletContext()
					.getRealPath("/resources/images/upload/" + fileName);
			System.out.println( dbImgPath + " : " + fileName + " : " + realImgPath);
			model.addAttribute("realImgPath", realImgPath);
		}
		
		command = new BoardInsertCommand();
		command.execute(model);
		
	}	
	
	
	@RequestMapping(value="/BoardUpdate", method = {RequestMethod.GET, RequestMethod.POST})
	public void anUpdate(HttpServletRequest req, Model model){
		System.out.println("anUpdate()");	
		
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		model.addAttribute("index", req.getParameter("index"));
		model.addAttribute("id", req.getParameter("id"));
		model.addAttribute("pw", req.getParameter("pw"));
		model.addAttribute("title", req.getParameter("title"));
		model.addAttribute("content", req.getParameter("content"));	
		model.addAttribute("imagePath", req.getParameter("imagePath"));
//		model.addAttribute("pImagePath", req.getParameter("pImagePath"));
		model.addAttribute("imageData", req.getParameter("imageData"));
		model.addAttribute("beforeImage", req.getParameter("beforeImage"));
		System.out.println("이전이미지:"+req.getParameter("beforeImage"));
		
		String dbImgPath = req.getParameter("imagePath");
		System.out.println("이미지데이터:"+req.getParameter("imageData"));
//		String pImgPath = req.getParameter("pImagePath");
		
		//이미지 변경 체크
		if( req.getParameter("imageData").equals("")){
			System.out.println("컨트롤러:이미지 변경 안됨");
		}else{
			System.out.println("컨트롤러:이미지 변경 됨");
			String fileName = dbImgPath.split("/")[dbImgPath.split("/").length -1];
			String realImgPath = req.getSession().getServletContext()
					.getRealPath("/resources/images/upload/" + fileName);
			System.out.println( dbImgPath + " : " + fileName + " : " + realImgPath);
			model.addAttribute("realImgPath", realImgPath);
		}
		
//		System.out.println(dbImgPath + ":" + req.getParameter("imagePath"));		
		
		command = new AUpdateCommand();
		command.execute(model);		
		
	}
	
	
	@RequestMapping(value="/BoardDelete", method = {RequestMethod.GET, RequestMethod.POST})
	public void anDelete(HttpServletRequest req, Model model){
		System.out.println("BoardDelete()"+req.getParameter("index"));		
		model.addAttribute("index", req.getParameter("index"));
		
//		String pFileName = req.getParameter("imagePath").split("/")[req.getParameter("imagePath").split("/").length -1];
//		String delImgPath = req.getSession().getServletContext()
//				.getRealPath("/resources/images/upload/" + pFileName);
//		model.addAttribute("pDelImagePath", delImgPath);
//		
		command = new ADeleteCommand();
		command.execute(model);	
		
	}
	
	@RequestMapping(value="/replyInsert", method = {RequestMethod.GET, RequestMethod.POST})
	public void replyInsert(HttpServletRequest req, Model model){
		System.out.println("replyInsert()");
		//모듈객체는 requese에 객체를 담아서 보낼때 필요함
        try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		
		model.addAttribute("id", req.getParameter("id"));
		model.addAttribute("content", req.getParameter("content"));	
		model.addAttribute("index", req.getParameter("index"));	
		System.out.println(req.getParameter("id"));
		System.out.println(req.getParameter("content"));
		System.out.println(req.getParameter("index"));
		
		command = new replyInsertCommand();
		command.execute(model);
		
	}	

}
