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
		
		for(ANDto dto  : (ArrayList<ANDto>)request.getAttribute("AnAllList")){
			out.println("<list>");
			out.println("<b_index>" + dto.getIndex() + "</b_index>");
			out.println("<b_title>" + dto.getTitle() + "</b_title>");
			out.println("<b_id>" + dto.getId() + "</b_id>");
			out.println("<b_content>" + dto.getContent() + "</b_content>");
			out.println("<b_date>" + dto.getDate() + "</b_date>");
			out.println("<b_readcount>" + dto.getReadCount() + "</b_readcount>");
			out.println("<b_like>" + dto.getLike() + "</b_like>");
			out.println("<imgRes>" + dto.getImage1() + "</imgRes>");
			out.println("</list>");
		}
 		out.println("</lists>");
 	}catch(Exception e) {
 		System.out.println("select list failed" + e.getMessage());
 	}	
		
%>

