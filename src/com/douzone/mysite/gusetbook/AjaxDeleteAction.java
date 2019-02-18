package com.douzone.mysite.gusetbook;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mvc.action.Action;
import com.douzone.mysite.repository.GuestBookDao;

import net.sf.json.JSONObject;

public class AjaxDeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json; charset=utf-8");
		String no = request.getParameter("no");
		String password = request.getParameter("password");

		
		GuestBookDao dao = new GuestBookDao();
		boolean result = dao.delete(no, password);
		//System.out.println(result);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result",result);
		map.put("no", no);
		response.setContentType("application/json; charset=utf-8");
		JSONObject jsonobject = JSONObject.fromObject(map);
		response.getWriter().print(jsonobject.toString());
	}

}
