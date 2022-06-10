package com.jsp.action.pds;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import com.jsp.action.Action;
import com.jsp.controller.FileUploadResolver;
import com.jsp.controller.GetUploadPath;
import com.jsp.controller.XSSMultipartHttpServletRequestParser;
import com.jsp.dto.PFileVO;
import com.jsp.dto.PdsVO;
import com.jsp.service.PdsService;

public class PdsModifyAction implements Action{

	private PdsService pdsService;
	
	public void setPdsService(PdsService pdsService) {
		this.pdsService = pdsService;
	}

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url = "/pds/modify_success";
		
		
		
		try {
			//파일 삭제, 저장
			PdsVO pds = null;
			pds = modifyAttaches(request, response);
			
			//DB 수정
			pdsService.modify(pds);
			
			request.setAttribute("pds", pds);
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		
		return url;
	}

	private final int MEMORY_THRESHOLD = 1024 * 1024 * 3; //3MB
	private final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private final int MAX_REQUEST_SIZE = 1024 * 1024 * 200; //200MB
	
	private PdsVO modifyAttaches(HttpServletRequest request, HttpServletResponse response) throws Exception{
		PdsVO pds = null;
		
		XSSMultipartHttpServletRequestParser multi = new XSSMultipartHttpServletRequestParser(request, MEMORY_THRESHOLD, MAX_FILE_SIZE, MAX_REQUEST_SIZE);
		
		//파일 삭제 및 DB삭제
		String[] deleteFiles = multi.getParameterValues("deleteFile");
		if(deleteFiles != null && deleteFiles.length > 0) {
			for(String anoStr : deleteFiles) {
				int p_ano = Integer.parseInt(anoStr);
				
				PFileVO pfile = pdsService.getPFileByAno(p_ano);
				String filePath = pfile.getUploadpath() + File.separator + pfile.getFilename();
				File targetFile = new File(filePath);
				
				if(targetFile.exists()) {
					targetFile.delete();
				}
				
				pdsService.removePFileByAno(p_ano);
			}
		}
		
		//파일 저장
		FileItem[] fileItems = multi.getFileItems("uploadFile");
		List<PFileVO> pfileList = null;
		if(fileItems != null && fileItems.length > 0) {
			String uploadPath = GetUploadPath.getUploadPath("pds.upload");
			List<File> fileList = FileUploadResolver.fileUpload(fileItems, uploadPath);

			pfileList = new ArrayList<PFileVO>();
			if(fileList.size() > 0) for(File file : fileList) {
				PFileVO pfile = new PFileVO();
				pfile.setFilename(file.getName());
				pfile.setFiletype(file.getName().substring(file.getName().lastIndexOf(".") + 1));
				pfile.setUploadpath(uploadPath);
				
				pfileList.add(pfile);
			}
		}
		
		pds = new PdsVO();
		pds.setPno(Integer.parseInt(multi.getParameter("pno")));
		pds.setTitle(multi.getXSSParameter("title"));
		pds.setContent(multi.getParameter("content"));
		pds.setWriter(multi.getParameter("writer"));
		pds.setPfileList(pfileList);
		
		return pds;
	}

}
