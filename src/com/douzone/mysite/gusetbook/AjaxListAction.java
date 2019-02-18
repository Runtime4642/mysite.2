package com.douzone.mysite.gusetbook;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mvc.action.Action;
import com.douzone.mysite.repository.GuestBookDao;
import com.douzone.mysite.vo.GuestBookVo;

import net.sf.json.JSONObject;

public class AjaxListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String spage = request.getParameter("p");
		if(spage.equals(""))
		{
			spage="1";
		}
		
		//isNumeric [a-zA-Z0-9]*    *-> 길이 제한없이  d-> 숫자문자
		if(spage.matches("\\d*")==false)
			spage="1"; 
		
		int page=Integer.parseInt(spage);
		
		GuestBookDao dao = new GuestBookDao();
		List<GuestBookVo> list = dao.getList(page);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result","success");
		map.put("data", list);
		
		//날씨 받아오기 헤더 고치다가 일단 멈춤.
		//response.setHeader("Access-Control-Allow-Origin", "*");
		//response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setContentType("application/json; charset=utf-8");
		JSONObject jsonobject = JSONObject.fromObject(map);
		response.getWriter().print(jsonobject.toString());
		
	}

}
