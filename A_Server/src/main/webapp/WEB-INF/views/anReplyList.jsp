<%@page import="com.javalec.app.dto.ANDto"%>
<%@page import="org.springframework.ui.Model"%>
<%@page import="java.sql.*, java.sql.Date, javax.sql.*, javax.naming.*, 
					java.util.*, java.io.PrintWriter" %>

<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%	//XML형식으로 변환
 	try{
 		out.println("<lists>"); 
		
		for(ANDto dto  : (ArrayList<ANDto>)request.getAttribute("anReplyList")){
			out.println("<list>");
			out.println("<r_index>" + dto.getIndex() + "</r_index>");
			out.println("<r_id>" + dto.getId() + "</r_id>");
			out.println("<r_content>" + dto.getContent() + "</r_content>");
			out.println("<r_date>" + dto.getDate() + "</r_date>");
			out.println("</list>");
		}
 		out.println("</lists>");
 	}catch(Exception e) {
 		System.out.println("select list failed" + e.getMessage());
 	}finally{
 		System.out.println("전송완료");
 	}
		
%>

