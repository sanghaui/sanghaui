package com.javalec.app.command;

import org.springframework.ui.Model;

import com.javalec.app.dao.ANDao;

public class BoardInsertCommand implements ACommand{	
	
	@Override
	public void execute(Model model) {
		System.out.println("커맨드 넘어옴");
		String id = (String)model.asMap().get("id");
		String pw= (String)model.asMap().get("pw");
		String title= (String)model.asMap().get("title");
		String content = (String)model.asMap().get("content");
		System.out.println("id"+id+"pw"+pw+"title"+title+"content"+content);
//		String date = (String)model.asMap().get("date");	
		String imageData= (String)model.asMap().get("imageData");
		String imagePath = (String)model.asMap().get("imagePath");
		String realImgPath = (String)model.asMap().get("realImgPath");
		ANDao adao = new ANDao();
		adao.BoardInsert(id, pw, title, content,imageData,imagePath,realImgPath);

		//ArrayList<ANDto> adtos = adao.anInsert(id, name, date);
		//model.addAttribute("anInsert", adtos);		
	}	 

}
