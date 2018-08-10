package com.javalec.app.command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.javalec.app.dao.ANDao;
import com.javalec.app.dto.ANDto;


public class AnAllReListCommand implements ACommand{

	@Override
	public void execute(Model model) {		
		int parent = Integer.parseInt((String)model.asMap().get("parent"));
		System.out.println("리플컨트롤러:"+parent);
		ANDao adao = new ANDao();
		ArrayList<ANDto> adtos = adao.anAllRelist(parent);
		model.addAttribute("anReplyList", adtos); 
		
	}
	
}
