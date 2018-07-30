package com.javalec.app.command;

import org.springframework.ui.Model;

import com.javalec.app.dao.ANDao;


public class ADeleteCommand implements ACommand{	
	
	@Override
	public void execute(Model model) {
		int index = Integer.parseInt((String)model.asMap().get("index"));
//		String pDelImagePath  = (String)model.asMap().get("pDelImagePath");
				
		ANDao adao = new ANDao();
		adao.anDelete(index);		
			
	}	 

}
