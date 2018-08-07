package com.javalec.app.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.javalec.app.dto.ANDto;

public class ANDao {

	DataSource dataSource;

	public ANDao() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:/comp/env/team02");
//			dataSource = (DataSource) context.lookup("java:/comp/env/CSS");
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

	public ArrayList<ANDto> anAllSelect() {
		ArrayList<ANDto> adtos = new ArrayList<ANDto>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;		
		
		try {
			connection = dataSource.getConnection();
			String query = "select b_num, b_title, b_id, b_content, b_date, b_readcount, b_like, image1 from sangwa order by b_num desc";
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();
			
			while (resultSet.next()) {
				int index = resultSet.getInt("b_num");
				String title = resultSet.getString("b_title");
				String id = resultSet.getString("b_id");
				String content = resultSet.getString("b_content");
				Date date = resultSet.getDate("b_date"); 
				int readCount = resultSet.getInt("b_readcount");
				int like = resultSet.getInt("b_like");
				String img = resultSet.getString("image1");
				
				System.out.println(id);
				System.out.println(title);
				System.out.println(date);
//				String imagePath = resultSet.getString("image1"); 

				ANDto adto = new ANDto();
				adto.setIndex(index);
				adto.setTitle(title);
				adto.setId(id);
				adto.setContent(content);
				adto.setDate(date);
				adto.setReadCount(readCount);
				adto.setLike(like);
				adto.setImage1(img);
				adtos.add(adto);
			}			
			

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {			
				
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}				

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}
		return adtos;
	}
	
	public ArrayList<ANDto> anNoticeList() {		
		System.out.println("공지사항 DAO");
		ArrayList<ANDto> adtos = new ArrayList<ANDto>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		PrintWriter outputStream = null;	
		int resultSetCnt = 0;

		try {
			connection = dataSource.getConnection();
			String query = "select b_num, b_date, b_content, b_title, b_readcount from noticeboard order by b_num desc";
			
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();
			
			while (resultSet.next()) {
				int index = resultSet.getInt("b_num");
				String content = resultSet.getString("b_content");
				String title = resultSet.getString("b_title");
				Date date = resultSet.getDate("b_date"); 
				int readcount = resultSet.getInt("b_readcount");
//				System.out.println("DAO안입니다");
				System.out.println(resultSet.getInt("b_num"));
				System.out.println(content);
				System.out.println(title);
				System.out.println(date);
				System.out.println(readcount);
//				String imagePath = resultSet.getString("image1"); 

				ANDto adto = new ANDto();
				adto.setIndex(index);
				adto.setContent(content);;
				adto.setTitle(title);
				adto.setDate(date);
				adto.setReadCount(readcount);
				adtos.add(adto);
								
			}		
			

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				// 占쏙옙占싹쏙옙트占쏙옙 占쌥깍옙
				if(outputStream != null){
					outputStream.close();
				}
				
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}				

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}		
		return adtos;
	}
	public int BoardInsert(String id, String pw, String title, String content, String imageData, String imagePath, String realImgPath) {
		System.out.println("DAO 삽입 들어옴");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
				
		int state = -1;

		try {
			// 이미지 저장 처리		
			imageDecorder(imageData, realImgPath);
			
			// DB접속
			connection = dataSource.getConnection();
//			String query = "insert into sangwa(id, pw, content, title, sdate) " + "values(" + id + ",'" + pw + "',"
//					+ "to_date('" + date + "','rr/mm/dd') , '" + imagePath + "' )";

			String query = "insert into sangwa(b_num,b_id, b_pw, b_content, b_title, b_date, image1,b_readcount,b_like) values(b_num_seq.nextVal,?,?,?,?,sysdate,?,0,0)";
			prepareStatement = connection.prepareStatement(query);
			prepareStatement.setString(1, id);
			prepareStatement.setString(2, pw);
			prepareStatement.setString(3, content);
			prepareStatement.setString(4, title);
			prepareStatement.setString(5, imagePath);
			state = prepareStatement.executeUpdate();
			
			 
			if (state > 0) {
				System.out.println(state + "글쓰기 성공");				
			} else {
				System.out.println(state + "글쓰기 실패");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			} 

		}

		return state;

	}
	
	public void imageDecorder(String base64Image, String pathFile){
				//이미지 디코딩
		FileOutputStream imageOutFile = null;
		
		try{
			//서버 저장경로(pathfile)에 아웃풋 설정
			imageOutFile = new FileOutputStream(pathFile);
			base64Image = base64Image.replaceAll("\\s+", "");	//반드시 추가 "\\s+", ""
			byte[] imageByteArray = Base64.getDecoder().decode(base64Image);
			imageOutFile.write(imageByteArray);			
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally {
			if(imageOutFile != null){
				try {
					imageOutFile.close();
				} catch (IOException e) {					
					System.out.println(e.getMessage());
				}
			}
		}
	}
	
	
	//이미지가 변경됬을 경우
	public int anUpdate(String index,String id,String pw,String title,String content,String imageData,String imagePath,String realImgPath,String beforeImage) {
		System.out.println("DAO:이미지 변경 됨");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		int state = -1;

		try {
			imageDecorder(imageData, realImgPath);
			
			//이전파일 삭제
			File delfile = new File(beforeImage);
            if(delfile.exists()) {
                System.out.println("Sub1Add:pDelImagePath " + delfile.exists());
                delfile.delete();
            }			
			connection = dataSource.getConnection();
			String query = "update sangwa set b_id=?, b_pw=?, b_title=?, b_content=?, image1=? where b_num =?";
			System.out.println("id"+id+"pw"+pw+"title"+title+"content"+content);
			prepareStatement = connection.prepareStatement(query);
			prepareStatement.setString(1, id);
			prepareStatement.setString(2, pw);
			prepareStatement.setString(3, title);
			prepareStatement.setString(4, content);
			prepareStatement.setString(5, imagePath);
			prepareStatement.setString(6, index);
			state = prepareStatement.executeUpdate();

			if (state > 0) {
				System.out.println("성공");
				
			} else {
				System.out.println("실패");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return state;

	}
	//이미지 변경안됬을 경우
public int anUpdate(String index,String id,String pw,String title,String content,String beforeImage) {
		System.out.println("이미지 변경 안됨");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		System.out.println("beforeImage:"+beforeImage);
		int state = -1;

		try {			
			connection = dataSource.getConnection();
			String query = "update sangwa set b_id=?, b_pw=?, b_title=?, b_content=?,image1=? where b_num =?";
			
			prepareStatement = connection.prepareStatement(query);
			prepareStatement.setString(1, id);
			prepareStatement.setString(2, pw);
			prepareStatement.setString(3, title);
			prepareStatement.setString(4, content);
			prepareStatement.setString(5, beforeImage);
			prepareStatement.setString(6, index);
			state = prepareStatement.executeUpdate();

			if (state > 0) {
				System.out.println("성공");
				
			} else {
				System.out.println("실패");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return state;

	}
	
	public int anDelete(int index) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		int state = -1;

		try {
			connection = dataSource.getConnection();
			String query = "delete from sangwa where b_num="+index;
			
			System.out.println(index);

			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();

			if (state > 0) {
				System.out.println("삭제 완료");				
			} else {
				System.out.println("삭제 실패");
			}
//			File delfile = new File(pDelImagePath);
//            if(delfile.exists()) {
//                System.out.println("Sub1Add:pDelImagePath " + delfile.exists());
//                delfile.delete();
//            }	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return state;
	}

	public int anReplyInsert(int index, String id, String content) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		int state = 0;
		try {
			connection = dataSource.getConnection();
			String query = "insert into replyboard (r_index, r_id, r_content, r_date, r_parent) values (seq_reply.nextVal,?,?,sysdate,?)";
			
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, content);
			preparedStatement.setInt(3, index);
			state = preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return state;
	}

	public ArrayList<ANDto> anAllRelist() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<ANDto> anAllRelist(int parent) {
		System.out.println("리플 DAO");
		ArrayList<ANDto> adtos = new ArrayList<ANDto>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		PrintWriter outputStream = null;	
		int resultSetCnt = 0;

		try {
			connection = dataSource.getConnection();
			String query = "select r_index, r_id, r_content, r_date from replyboard where r_parent=? order by r_index desc";
			
			prepareStatement = connection.prepareStatement(query);
			prepareStatement.setInt(1, parent);
			resultSet = prepareStatement.executeQuery();
			
			while (resultSet.next()) {
				int index = resultSet.getInt("r_index");
				String id = resultSet.getString("r_id");
				String content = resultSet.getString("r_content");
				Date date = resultSet.getDate("r_date"); 
				System.out.println("리플DAO안입니다");
				System.out.println(resultSet.getInt("r_index"));
				System.out.println(content);
				System.out.println(date);

				ANDto adto = new ANDto();
				adto.setIndex(index);
				adto.setId(id);
				adto.setContent(content);;
				adto.setDate(date);
				adtos.add(adto);
			}		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				// 占쏙옙占싹쏙옙트占쏙옙 占쌥깍옙
				if(outputStream != null){
					outputStream.close();
				}
				
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}				

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}		
		return adtos;
	}	
}
