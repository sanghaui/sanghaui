package com.javalec.app.command;

import org.springframework.ui.Model;

import com.javalec.app.dao.ANDao;

public class AUpdateCommand implements ACommand{	
	
	@Override
	public void execute(Model model) {
		int id = Integer.parseInt((String)model.asMap().get("id"));
		String name = (String)model.asMap().get("name");
		String date = (String)model.asMap().get("date");
		String imageData= (String)model.asMap().get("imageData");
		String imagePath = (String)model.asMap().get("imagePath");
		String pImagePath = (String)model.asMap().get("pImagePath");
		String pDelImagePath = (String)model.asMap().get("pDelImagePath");
		
		if(imagePath != pImagePath){
			String realImgPath = (String)model.asMap().get("realImgPath");
			ANDao adao = new ANDao();
			adao.anUpdate(id, name, date, imageData, imagePath, pImagePath, realImgPath, pDelImagePath);
		}else{
			ANDao adao = new ANDao();
			adao.anUpdate(id, name, date, imageData, imagePath, pImagePath);
		}		
		
			
	}	 

}
