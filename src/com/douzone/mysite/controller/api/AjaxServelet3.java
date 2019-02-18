package com.douzone.mysite.controller.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.vo.UserVo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class AjaxServelet3
 */
@WebServlet("/ajax3")
public class AjaxServelet3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxServelet3() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
			List<UserVo> list = new ArrayList<>();
			
			UserVo vo1 = new UserVo();
			vo1.setNo(10);
			vo1.setName("마이콜");
			vo1.setEmail("mana129@nave.com");
			vo1.setGender("male");
			
			list.add(vo1);
			
			
			UserVo vo2 = new UserVo();
			vo2.setNo(10);
			vo2.setName("둘리");
			vo2.setEmail("mana129@nave.com");
			vo2.setGender("male");
			
			list.add(vo2);
			
			
			JSONArray jsonarray = JSONArray.fromObject(list);
			String jsonString = jsonarray.toString();
			
			response.setContentType("aplication/json; charset=utf-8");
			PrintWriter pw = response.getWriter();
			pw.println(jsonString);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
