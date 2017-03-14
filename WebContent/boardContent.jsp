<%@page import="mybatis.dao.MemDAO"%>
<%@page import="mybatis.bbs.vo.CommentVO"%>
<%@page import="java.util.List"%>
<%@page import="mybatis.vo.MemVO"%>
<%@page import="mybatis.dao.BoardDAO"%>
<%@page import="mybatis.bbs.vo.BoardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시물보기</title>

<link href="css/header.css" rel="stylesheet">
<link href="css/write.css" rel="stylesheet">
<link href="css/comment.css" rel="stylesheet"> 

<!-- include libraries(jQuery, bootstrap) -->
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.js"></script> 
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script> 


<script>

	//목록으로 돌아가기
	function goList() {

		//if(cp != null){
		//		document.forms[0].cPage.value = cp;
		document.forms[0].type.value = "boardList";
		//	}		

		document.forms[0].submit();
	}

	// 파일다운로드
	function down(fn) {

		if (fn != null) {
			document.forms[1].fname.value = fn;
			document.forms[1].type.value = "download";
		}

		document.forms[1].submit();
	}

	// 게시물 수정
	function edit(ff) {
		ff.edit_chk.value = "1";
		ff.type.value = "editBoard";
		ff.submit();
	}

	// 게시물 삭제
	function del(ff) {
		if (confirm("정말로 삭제하시겠습니까?")) {
			ff.type.value = "delBoard";
			ff.submit();
		}
	}

	function delWin() {
		alert("삭제실패!");
	}

	// 이전글/다음글 내용보기
	function viewData(idx) {
		document.fm.b_idx.value = idx;
		document.fm.type.value = "boardContent";
		document.fm.submit();
	}

	// 댓글 삽입
	function saveComm(ff) {

		if (ff.content.value.trim().length < 1) {
			alert("댓글을 입력하세요!");
			return;
		}

		ff.type.value = "addComm";
		ff.submit();
	}

	// 댓글 수정
	function editComm(ff) {

	}

	// 댓글 삭제
	function delComm(ff) {
		if (confirm("정말로 삭제하시겠습니까?")) {
			ff.type.value = "delComm";
			ff.submit();
		}
	}


	// 로그아웃
	function logout() {
		document.f1.type.value = "logout"
		document.f1.submit();
	}
</script>

</head>
<body
<% 	
	String del_chk = (String) request.getAttribute("del_chk");
	if( del_chk != null && del_chk.equals("1") ) { 
%>	
		onload = "delWin()"
		
<%	} %>

>

<%
	MemVO mvo = (MemVO) session.getAttribute("login_vo");

	String cPage = (String)request.getAttribute("cPage");
	
	Object obj = request.getAttribute("bvo");
	Object obj_w = request.getAttribute("writer_vo");

	if(obj != null && obj_w != null) {
		
		BoardVO bvo = (BoardVO)obj;
		MemVO mvo2 = (MemVO)obj_w;			
		
%>

	<div id="header">
		<a href="Controller?type=index"><h2>INCREPAS BOARD</h2></a>
		<div id="user">
			<span><%= mvo.getName() %>님 환영합니다 ^_^</span>
			<input type="button" value="logout" onclick="logout()"/>
		</div>
	</div>

	<form name="f1" method="post" action="Controller">				
			<input type="hidden" name="type" />
	</form>

	<div id="bbs">
	<form method="post" action="Controller">
	
		<table summary="게시물보기">
			<caption>게시물보기</caption>
			<tbody>
						
				<tr>
					<th>제목</th>
					<td><%= bvo.getTitle() %></td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td>
<%					if(bvo.getFile_name() != null && bvo.getFile_name().trim().length() > 0){
						// 파일이 있는 경우
%>
						<a href="javascript:down('<%=bvo.getFile_name() %>')"><%=bvo.getFile_name() %></a>
						(<%=bvo.getOri_name() %>)
						
<%					}	%>

					
					</td>
				</tr>
				<tr>
					<th>이름</th>
					<td>	
					<%= mvo2.getName() %>(<%= bvo.getWriter() %>)</td>
				</tr>
				<tr>
					<!-- <th>내용:</th> -->
					<td colspan="2"><%= bvo.getContent() %></td>
				</tr>
				
				<tr>
					<td colspan="2">

<%
			if(mvo.getId().equals(bvo.getWriter())){
%>
					<input type="hidden" name="edit_chk" />
					<input type="button" value="수정" onclick="edit(this.form)" />
					<input type="button" value="삭제" onclick="del(this.form)" />				
<%
			}
%>						
					<input type="button" value="목록" onclick="goList()"/>
					</td>
				</tr>
			</tbody>			
		</table>
		
		<input type="hidden" name="type" />
		<input type="hidden" name="fname"/>
		<input type="hidden" name="cPage" value="<%= cPage %>" />
		<input type="hidden" name="b_idx" value="<%= bvo.getB_idx() %>"/>
	</form>
	
	<br/><br/>

<%
	Object obj2 = request.getAttribute("bvo2");
	Object obj3 = request.getAttribute("bvo3");
%>
	
	<div id="bbs2">
		<table>
			<tbody>
				<tr>
					<th>이전글</th>
					<td class="title">
<%	if(obj2 != null){
		BoardVO bvo2 = (BoardVO) obj2;
%>
						<a href="javascript:viewData('<%= bvo2.getB_idx() %>')"><%= bvo2.getTitle() %></a>
						
						
<%	} else {	%>
						이전글이 없습니다.
<%	}	%>					
					</td>
				</tr>
				<tr>
					<th>다음글</th>
					<td class="title">
<%	if(obj3 != null){
		BoardVO bvo3 = (BoardVO) obj3;
%>					
						<a href="javascript:viewData('<%= bvo3.getB_idx() %>')"><%= bvo3.getTitle() %></a>
<%	} else {	%>
						다음글이 없습니다.
<%	} %>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<br/><br/>
	
	<div id="com">	
		
		<form class="com_form" method="post" action="Controller">
		
		<div class="half left cf">
			<input type="text" value="<%= mvo.getId() %>"/>
			<input type="hidden" name="writer" value="<%= mvo.getId() %>" />
			<input type="text" placeholder="Email">    
		</div>
		<div class="half right cf">
			<textarea class="ta1" name="content" type="text" placeholder="Leave a comment..."></textarea>
		</div>
		
		<div style="margin-bottom:30px;">
			<input type="hidden" name="type" />
			<input type="hidden" name="cPage" value="<%= cPage %>" />			
			<input type="hidden" name="b_idx" value="<%= bvo.getB_idx() %>" />
			<input type="hidden" name="ip" value="<%= request.getRemoteAddr() %>"/>	
  
			<input type="button" value="Save" id="save_btn" onclick="saveComm(this.form)">
		</div>
		</form>
			
	
		<br/>				
<%
		/* List<CommentVO> c_list = bvo.getC_list();*/
		Object obj4 = request.getAttribute("c_list");
		
		if(obj4 != null){			
			CommentVO[] c_list = (CommentVO[]) obj4;

			for(CommentVO cvo : c_list){
%>				 
		<div style="height:100px;">
			
		<form class="com_form" method="post" action="Controller">
			
			<div class="half left cf">
				<input type="text" value="<%= cvo.getWriter() %>" disabled="disabled"/>
				<input type="text" value="<%= cvo.getW_date().substring(0,16) %>" disabled="disabled" />
			</div>
			<div class="half right2 cf">
				<textarea disabled="disabled"><%= cvo.getContent() %></textarea>
			</div>
<%
				if(mvo.getId().equals(cvo.getWriter())){
%>
			<div class="half right3 cf">
				<input type="button" value="Edit" onclick="editComm(this.form)"/>
				<input type="button" value="Delete" onclick="delComm(this.form)" />
			</div>
			
			<input type="hidden" name="type" />
			<input type="hidden" name="cPage" value="<%= cPage %>" />
			<input type="hidden" name="b_idx" value="<%= cvo.getB_idx() %>" />
			<input type="hidden" name="c_idx" value="<%= cvo.getC_idx() %>" />
<%
			}
%>
		</form>
		</div> 	
					 
<%		}//for문의 끝 
	}
%>	

		
	
	</div>
	</div>
	
	<form name="fm" method="post" action="Controller" >		
		<input type="hidden" name="type" />
		<input type="hidden" name="cPage" value="<%= cPage %>" />
		<input type="hidden" name="b_idx" />
	</form>
	
</body>
</html>

<%		
	}
%>
