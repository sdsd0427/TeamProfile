package com.jsp.dto;

import java.util.Date;
import java.util.List;

public class WorkBoardVO {
	private int wno;
	private String title;
	private String content;
	private Date regdate;
	private int viewcnt;
	private Date updatedate;
	private String writer;
	
	private int replycnt;
	
	private List<WorkFileVO> workFileList;

	public int getWno() {
		return wno;
	}

	public void setWno(int wno) {
		this.wno = wno;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public int getViewcnt() {
		return viewcnt;
	}

	public void setViewcnt(int viewcnt) {
		this.viewcnt = viewcnt;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public int getReplycnt() {
		return replycnt;
	}

	public void setReplycnt(int replycnt) {
		this.replycnt = replycnt;
	}

	public List<WorkFileVO> getWorkFileList() {
		return workFileList;
	}

	public void setWorkFileList(List<WorkFileVO> workFileList) {
		this.workFileList = workFileList;
	}
	
	
	
	
	
}
