<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<title>취업정보방 상세보기</title>

<body>
	<div>
	 <!-- Content Header (Page header) -->
    <section class="content-header">
    	<div class="container-fluid">
    		<div class="row mb-2">
    			<div class="col-sm-6">
	      			<h1>취업정보방</h1>
	      		</div>	      		
	    	
	       		
	       		<div class="col-sm-6">
			      <ol class="breadcrumb float-sm-right">
			        <li class="breadcrumb-item"><a href="list.do"><i class="fa fa-dashboard"></i>취업정보방</a></li>
			        <li class="breadcrumb-item active">상세보기</li>		        
			      </ol>
		      	</div>
	     	</div>	     	
      	</div>
    </section>


  <!-- Main content -->
    <section class="content container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="card card-outline card-primary">
					<div class="card-header">
						<h3 class="card-title">상세보기</h3>
						<div class ="card-tools">
							<button type="button" id="modifyBtn" class="btn btn-warning" onclick="modify_go(${workBoard.wno});">Modify</button>						
					    	<button type="button" id="removeBtn" class="btn btn-danger" onclick="remove_go(${workBoard.wno});">REMOVE</button>
					    	<button type="button" id="listBtn" class="btn btn-primary" onclick="CloseWindow();">CLOSE </button>
				    	</div>
					</div>
					<div class="card-body">
						<div class="form-group col-sm-12">
							<label for="title">제 목</label>
							<input type="text" class="form-control" id="title" 
								value="${workBoard.title }" readonly />							
						</div>	
						<div class="col-sm-12 row">
							<div class="form-group col-sm-4" >
								<label for="writer">작성자</label>
								<input type="text" class="form-control" id="writer" 
									 value="${workBoard.writer }" readonly />
							</div>		
							
							<div class="form-group col-sm-4" >
								<label for="regDate">작성일</label>
								<input type="text" class="form-control" id="regDate" 
									value="<fmt:formatDate value="${workBoard.regDate }" pattern="yyyy-MM-dd" />" readonly />
							</div>	
							<div class="form-group col-sm-4" >
								<label for="viewcnt">조회수</label>
								<input type="text" class="form-control" id="viewcnt" value="${workBoard.viewcnt }"
									 readonly />
							</div>	
						</div>	
						<div class="form-group col-sm-12">
							<label for="content">내 용</label>
							<div >${workBoard.content }</div>	
						</div>
						<div class="form-group col-sm-12">
							<div class="card card-outline card-success">
								<div class="card-header">
									<h3>첨부파일</h3>
								</div>
											
								<div class="card-footer">
									<div class="row">
										<c:forEach items="${workBoard.workFileList }" var="workFile">
											<div class="col-md-4 col-sm-4 col-xs-12"  style="cursor:pointer;" onclick="location.href='getFile.do?w_ano=${workFile.w_ano}';">
												<div class="info-box">	
												 	<span class="info-box-icon bg-yellow">
														<i class="fa fa-copy"></i>
													</span>
													<div class="info-box-content">
														<span class ="info-box-text">
															<fmt:formatDate value="${workBoard.regDate }" pattern="yyyy-MM-dd" />
														</span>
														<span class ="info-box-number">${workFile.fileName }</span>
													</div>
												</div>
											 </div>	
										</c:forEach>
									</div>
								</div>				
							</div>
						</div>
												
					</div>
					<div class="card-footer">
<%-- 						<button type="button" id="modifyBtn" class="btn btn-warning" onclick="submit_go('modifyForm.do','${workBoard.wno}');">Modify</button>						 --%>
<%-- 				    	<button type="button" id="removeBtn" class="btn btn-danger" onclick="submit_go('remove.do','${workBoard.wno}');">REMOVE</button> --%>
<!-- 				    	<button type="button" id="listBtn" class="btn btn-primary" onclick="CloseWindow();">CLOSE </button> -->
					</div>									
				</div><!-- end card -->				
			</div><!-- end col-md-12 -->
		</div><!-- end row  -->		
		
    </section>
    <!-- Reply content -->
    <section class="content container-fluid">
    	<!-- reply component start --> 
		<div class="row">
			<div class="col-md-12">
				<div class="card card-info">					
					<div class="card-body">
						<!-- The time line -->
						<div class="timeline">
							<!-- timeline time label -->
							<div class="time-label" id="repliesDiv">
								<span class="bg-green">Replies List </span>							
							</div>
							
							
						</div>
						<div class='text-center'>
							<ul id="pagination" class="pagination justify-content-center m-0" >
								
							</ul>
						</div>
					</div>
					<div class="card-footer">
						<label for="newReplyText">Reply Text</label>
						<input class="form-control" type="text"	placeholder="REPLY TEXT" id="newReplyText">
						<br/>
						<button type="button" class="btn btn-primary" id="replyAddBtn" onclick="replyRegist_go();">ADD REPLY</button>
					</div>				
				</div>			
				
			</div><!-- end col-md-12 -->
		</div><!-- end row -->
    </section>
    </div>

<!-- Modal -->
<div id="modifyModal" class="modal modal-default fade" role="dialog">
  <div class="modal-dialog">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title" style="display:none;"></h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>        
      </div>
      <div class="modal-body" data-rno>
        <p><input type="text" id="replytext" class="form-control"></p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-info" id="replyModBtn" onclick="replyModify_go();">Modify</button>
        <button type="button" class="btn btn-danger" id="replyDelBtn" onclick="replyRemove_go();">DELETE</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>



<form role="form">
	<input type="hidden" name="wno" value="${workBoard.wno }" />
</form>
    
<script>
function modify_go(wno){
	location.href = "modifyForm.do?wno=" + wno;
}
function remove_go(wno) {
	location.href = "remove.do?wno=" + wno;
}
</script>  
<%@ include file="./reply_js.jsp" %>   
 </body>