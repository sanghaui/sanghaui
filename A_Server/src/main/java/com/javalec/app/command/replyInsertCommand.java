package com.javalec.app.command;

import org.springframework.ui.Model;

import com.javalec.app.dao.ANDao;

public class replyInsertCommand implements ACommand {

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		String id = (String)model.asMap().get("id");
		String content = (String)model.asMap().get("content");
		int index = Integer.parseInt((String)model.asMap().get("index"));
		
		ANDao dao = new ANDao();
		int state = dao.anReplyInsert(index,id,content);
		
		if(state > 0 ){
			System.out.println("리플삽입 성공");
		}else{
			System.out.println("리플삽입 실패");
		}
	}

}
