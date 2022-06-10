package com.jsp.action.pds;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.action.Action;
import com.jsp.controller.FileUploadResolver;
import com.jsp.controller.GetUploadPath;
import com.jsp.controller.XSSHttpRequestParameterAdapter;
import com.jsp.controller.XSSMultipartHttpServletRequestParser;
import com.jsp.dto.PFileVO;
import com.jsp.dto.PdsVO;
import com.jsp.exception.NotMultipartFormDataException;
import com.jsp.service.PdsService;

public class PdsRegistAction implements Action{

	private PdsService pdsService;
	
	public void setPdsService(PdsService pdsService) {
		this.pdsService = pdsService;
	}

	private final int MEMORY_THRESHOLD = 1024 * 1024 * 3; //3MB
	private final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private final int MAX_REQUEST_SIZE = 1024 * 1024 * 200; //200MB
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url = "/pds/regist_success";
		
		XSSMultipartHttpServletRequestParser multi = null;
		List<PFileVO> pfileList = null;
		
		try {
			multi = new XSSMultipartHttpServletRequestParser(request, MEMORY_THRESHOLD, MAX_FILE_SIZE, MAX_REQUEST_SIZE);
			
			String uploadPath = GetUploadPath.getUploadPath("pds.upload");
			
			List<File> fileList = FileUploadResolver.fileUpload(multi.getFileItems("uploadFile"), uploadPath);
			
			//List<File> -> List<AttachVO>
			if(fileList != null) {
				pfileList = new ArrayList<PFileVO>();
				for(File file : fileList) {
					PFileVO pfile = new PFileVO();
					pfile.setFilename(file.getName());;
					pfile.setUploadpath(uploadPath);
					pfile.setFiletype(file.getName().substring(file.getName().lastIndexOf(".") + 1));
					
					pfileList.add(pfile);
				}
			}
		}catch(NotMultipartFormDataException e) {
			e.printStackTrace();
			response.sendError(response.SC_BAD_REQUEST);
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		PdsVO pds = new PdsVO();
		pds.setTitle(multi.getXSSParameter("title"));
		pds.setContent(multi.getParameter("content"));
		pds.setWriter(multi.getXSSParameter("writer"));
		pds.setImportant(Integer.parseInt(multi.getParameter("important")));
		pds.setPfileList(pfileList);
		
		try {
			pdsService.regist(pds);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		
		return url;
	}

	
}
