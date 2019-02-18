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

public class AjaxInsertAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String name = request.getParameter("name");
		String message =request.getParameter("message");
		String password = request.getParameter("password");
		
		GuestBookVo vo = new GuestBookVo();
		vo.setName(name);
		vo.setMessage(message);
		vo.setPassword(password);
		
		GuestBookDao dao = new GuestBookDao();
		long no = dao.insert(vo);
		if(no==-1)
		{
			System.out.println("insert error");
			return;
		}
		GuestBookVo newVo = dao.get(no);
		//System.out.println(newVo.toString());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result","success");
		map.put("data", newVo);
		
		response.setContentType("application/json; charset=utf-8");
		JSONObject jsonobject = JSONObject.fromObject(map);
		response.getWriter().print(jsonobject.toString());
		
		
	}

}
