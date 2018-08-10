package com.javalec.app.command;

import org.springframework.ui.Model;

import com.javalec.app.dao.ANDao;

public class AUpdateCommand implements ACommand{	
	
	@Override
	public void execute(Model model) {
		
		
		String id = (String)model.asMap().get("id");
		String index = (String)model.asMap().get("index");
		String pw= (String)model.asMap().get("pw");
		String title= (String)model.asMap().get("title");
		String content = (String)model.asMap().get("content");
		System.out.println("커맨드:id"+id+"pw"+pw+"title"+title+"content"+content);
		String imageData= (String)model.asMap().get("imageData");
		String imagePath = (String)model.asMap().get("imagePath");
		String realImgPath = (String)model.asMap().get("realImgPath");
		String beforeImage = (String)model.asMap().get("beforeImage");
		
		System.out.println("이미지데이터:"+imageData);
//		int id = Integer.parseInt((String)model.asMap().get("id"));
//		String name = (String)model.asMap().get("name");
//		String date = (String)model.asMap().get("date");
//		String imageData= (String)model.asMap().get("imageData");
//		String imagePath = (String)model.asMap().get("imagePath");
//		String pImagePath = (String)model.asMap().get("pImagePath");
//		String pDelImagePath = (String)model.asMap().get("pDelImagePath");
//		
		if(imageData.equals("")){
//			String realImgPath = (String)model.asMap().get("realImgPath");
			System.out.println("커맨드:이미지변경안됨");
			ANDao adao = new ANDao();
			adao.anUpdate(index, id, pw, title,content, beforeImage);
		}else{
			System.out.println("커맨드:이미지변경됨");
			ANDao adao = new ANDao();
			adao.anUpdate(index, id, pw, title, content, imageData, imagePath, realImgPath, beforeImage);
		}		
		
			
	}	 

}
