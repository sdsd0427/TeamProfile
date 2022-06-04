package com.jsp.action.workBoard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.action.Action;
import com.jsp.command.WorkBoardModifyCommand;
import com.jsp.controller.XSSHttpRequestParameterAdapter;
import com.jsp.dto.WorkBoardVO;
import com.jsp.service.WorkBoardService;

public class WorkBoardModifyAction implements Action {

	private WorkBoardService workBoardService;

	public void setWorkBoardService(WorkBoardService workBoardService) {
		this.workBoardService = workBoardService;
	}
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url = "redirect:/workBoard/detail.do?wno="+request.getParameter("wno");
		
		try {
			WorkBoardModifyCommand modifyReq = (WorkBoardModifyCommand)XSSHttpRequestParameterAdapter.execute(request, WorkBoardModifyCommand.class, true);
			
			WorkBoardVO workBoard = modifyReq.toWorkBoardVO();
			workBoard.setContent(request.getParameter("content"));
			
			workBoardService.modify(workBoard);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return url;
	}

}
