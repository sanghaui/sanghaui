package com.javalec.app.command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.javalec.app.dao.ANDao;
import com.javalec.app.dto.ANDto;


public class ASelectCommand2 implements ACommand{

	@Override
	public void execute(Model model) {		
		ANDao adao = new ANDao();
		System.out.println("공지사항 커맨드");
		ArrayList<ANDto> adtos = adao.anNoticeList();	
		
		model.addAttribute("AnNoticeList", adtos); 
	}
	
}
