package com.javalec.app.command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.javalec.app.dao.ANDao;
import com.javalec.app.dto.ANDto;


public class ASelectCommand2 implements ACommand{

	@Override
	public void execute(Model model) {		
		ANDao adao = new ANDao();
		ArrayList<ANDto> adtos = adao.anNoticeList("admin");	
		
		model.addAttribute("anNoticeList", adtos); 
	}
	
}
