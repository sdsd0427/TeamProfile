package com.jsp.action.pds;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.action.Action;
import com.jsp.dto.PFileVO;
import com.jsp.dto.PdsVO;
import com.jsp.service.PdsService;
import com.jsp.controller.MakeFileName;

public class PdsModifyFormAction implements Action {

	public PdsService pdsService;

	public void setPdsService(PdsService pdsService) {
		this.pdsService = pdsService;

	}

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String url = "/pds/modify";

		try {
			int pno = Integer.parseInt(request.getParameter("pno"));

			PdsVO pds = pdsService.getPds(pno);			
		
			List<PFileVO> renamedAttachList=
					MakeFileName.parseFileNameFromAttaches(pds.getPfileList(), "\\$\\$");
			pds.setPfileList(renamedAttachList);;
			
			request.setAttribute("pds", pds);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(response.SC_INTERNAL_SERVER_ERROR);
			url = null;
		}
		return url;
	}

}
