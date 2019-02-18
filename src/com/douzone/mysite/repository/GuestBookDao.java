package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.GuestBookVo;

public class GuestBookDao extends Dao{
	
	public GuestBookVo get(long no) {
		
		Connection conn = null;
		PreparedStatement pstmt=null;
		ResultSet rs =null;
		 GuestBookVo vo = new GuestBookVo();
		try {
			 conn = getConnection();
			 
			 String sql = "select no , name  , message,reg_date from guestbook where no=?";
			 pstmt = conn.prepareStatement(sql);
			
			 pstmt.setLong(1, no);
			 rs = pstmt.executeQuery();
			 while(rs.next())
			 {
				 String name = rs.getString(2);
				 String message = rs.getString(3);
				 String date = rs.getString(4);
				 vo.setNo(no);
				 vo.setName(name);
				 vo.setDate(date);
				 vo.setMessage(message);
			 }
			 return vo;
			 
		} catch (SQLException e) {
			System.out.println("error:"+e);
		} 
		finally 
		{
				try {
					if(conn !=null)
					conn.close();
					if(rs !=null)
						rs.close();
					if(pstmt != null)
						pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
		}
		
		return null;
	}
	
	public List<GuestBookVo> getList()
	{
		
			List<GuestBookVo> list = new ArrayList<GuestBookVo>();
		
		
		Connection conn = null;
		PreparedStatement pstmt=null;
		ResultSet rs =null;
		try {
			 conn = getConnection();
			 
			 String sql = "select no , name , password , message,reg_date from guestbook order by no desc";
			 pstmt = conn.prepareStatement(sql);
			 rs = pstmt.executeQuery();
			 while(rs.next())
			 {
				 long no = rs.getLong(1);
				 String name = rs.getString(2);
				 String password = rs.getString(3);
				 String message = rs.getString(4);
				 String date = rs.getString(5);
				 
				 GuestBookVo vo = new GuestBookVo();
				 vo.setNo(no);
				 vo.setName(name);
				 vo.setPassword(password);
				 vo.setDate(date);
				 vo.setMessage(message);
				 list.add(vo);
			 }
			 return list;
			 
		} catch (SQLException e) {
			System.out.println("error:"+e);
		} 
		finally 
		{
				try {
					if(conn !=null)
					conn.close();
					if(rs !=null)
						rs.close();
					if(pstmt != null)
						pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
		}
		
		
		return list;
		
		
		
		
	}
	
	public List<GuestBookVo> getList(int page)
	{
		
			List<GuestBookVo> list = new ArrayList<GuestBookVo>();
		
		
		Connection conn = null;
		PreparedStatement pstmt=null;
		ResultSet rs =null;
		try {
			
			int sqlPage = page-1;
			sqlPage *= 5;
			
			
			 conn = getConnection();
			 
			 //password는 쿼리에 안쓰는게 좋음 .. 메모리에 올라가면 보안관련 문제가 있기때문
			 String sql = "select no , name , password , message,reg_date from guestbook order by no desc limit ?,5";
			 pstmt = conn.prepareStatement(sql);
			 
			 //바인딩
			 pstmt.setInt(1, sqlPage);
			 rs = pstmt.executeQuery();
			 while(rs.next())
			 {
				 long no = rs.getLong(1);
				 String name = rs.getString(2);
				 String password = rs.getString(3);
				 String message = rs.getString(4);
				 String date = rs.getString(5);
				 
				 GuestBookVo vo = new GuestBookVo();
				 vo.setNo(no);
				 vo.setName(name);
				 vo.setPassword(password);
				 vo.setDate(date);
				 vo.setMessage(message);
				 list.add(vo);
			 }
			 return list;
			 
		} catch (SQLException e) {
			System.out.println("error:"+e);
		} 
		finally 
		{
				try {
					if(conn !=null)
					conn.close();
					if(rs !=null)
						rs.close();
					if(pstmt != null)
						pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
		}
		
		
		return list;
		
		
		
		
	}
	
	
	
	
	public int insert(GuestBookVo vo)
	{
		int result=-1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			conn=getConnection();
			
			String sql = "insert into guestbook values(null,?,?,?,current_timestamp())";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getMessage());

			
			int count = pstmt.executeUpdate();
			if( count !=1)
			{
				System.out.println("insert query error");
				return -1;
			}
			//방금들어간 db data 의 no값 받아오기 
			//insert 한번더하기
			sql = "select max(no) from guestbook";
			pstmt = conn.prepareStatement(sql);
			rs =pstmt.executeQuery();
			
			while(rs.next()) {
				result = rs.getInt(1);
			}
			return result;
		}catch (SQLException e) {
			System.out.println("error:"+e);
		} 
		finally 
		{
				try {
					if(conn !=null)
					conn.close();
					if(pstmt != null)
						pstmt.close();
					if(rs != null)
						rs.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
			
		}
		
		
		
		return result;
	}

	public boolean delete(String no,String password)
	{
	boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn=getConnection();
			
			String sql = "delete from guestbook where no=? and password=?";	
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1,(long)Integer.parseInt(no));
			pstmt.setString(2, password);	
			int count = pstmt.executeUpdate();
			result = count ==1;
			
		}catch (SQLException e) {
			System.out.println("error:"+e);
		} 
		finally 
		{
				try {
					if(conn !=null)
					conn.close();
					if(pstmt != null)
						pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
		}
		
		
		
		return result;
	}

	}


