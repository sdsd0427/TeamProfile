package com.jsp.action.workBoard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.action.Action;
import com.jsp.service.WorkBoardService;

public class WorkBoardRemoveAction implements Action {

	private WorkBoardService workBoardService;

	public void setWorkBoardService(WorkBoardService workBoardService) {
		this.workBoardService = workBoardService;
	}

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url ="/workBoard/remove_success";
		
		try {
			int wno = Integer.parseInt(request.getParameter("wno"));
			
			workBoardService.remove(wno);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return url;
	}

}
