package com.douzone.mysite.gusetbook;

import com.douzone.mvc.action.AbstractActionFactory;
import com.douzone.mvc.action.Action;
import com.douzone.mysite.action.user.JoinFormAction;

public class GuestBookActionFactory extends AbstractActionFactory{

	@Override
	public Action getAction(String actionName) {
			Action action =null;
			
			if(actionName==null)
				 action = new GuestBookAction();
			else if(actionName.equals("insert"))
			{
				 action=new GusetBookInsertAction();
				
			}
			else if(actionName.equals("deleteform"))
			{
				action = new GuestBookDeleteFormAction();
			}
			else if(actionName.equals("delete"))
			{
				action = new GuestBookDeleteAction();
			}else if(actionName.equals("ajax"))
			{
				action = new AjaxAction();
			}
			else if(actionName.equals("ajax-list"))
			{
				action = new AjaxListAction();
			}else if(actionName.equals("ajax-insert"))
			{
				action = new AjaxInsertAction();
			}else if(actionName.equals("ajax-delete"))
			{
				action = new AjaxDeleteAction();
			}
			
		return action;
	}

}
