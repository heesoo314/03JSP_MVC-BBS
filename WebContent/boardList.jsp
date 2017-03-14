
<%@page import="mybatis.bbs.vo.CommentVO"%>
<%@page import="mybatis.dao.MemDAO"%>
<%@page import="mybatis.vo.MemVO"%>
<%@page import="mybatis.bbs.vo.BoardVO"%>
<%@page import="mybatis.bbs.vo.PageVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판 보기</title>

<link href="css/header.css" rel="stylesheet">
<link href="css/list.css" rel="stylesheet">

<!-- include libraries(jQuery, bootstrap) -->
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.js"></script> 
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script> 

<script>

	// 글쓰기
	function sendWrite(cp){	
		
		document.fm.cPage.value = cp;
		document.fm.type.value="addBoard";
		document.fm.submit();
		
	}
	
	// 제목을 클릭하면 내용보기
	function viewData(idx){
		document.fm.b_idx.value = idx;
		document.fm.type.value = "boardContent";
		document.fm.submit();
	}
	
	// 로그아웃
	function logout(){
		document.f1.type.value="logout"
		document.f1.submit();
	}
	
</script>

</head>

<body>

<%
	MemVO mvo = (MemVO) session.getAttribute("login_vo");
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
	
		<table summary="게시판 목록" >
			<caption>게시판 목록</caption>
			
			<thead>
				<tr class="title">
					<th class="no">번호</th>
					<th class="subject" >제목</th>
					<th class="file" >첨부파일</th>
					<th class="writer">작성자</th>
					<th class="reg">날짜</th>
				</tr>
			</thead>
			
			<tbody>
			
<%
	
	PageVO pvo = (PageVO) request.getAttribute("page_vo");
	Object obj = request.getAttribute("list");
			
%>
	
		<c:if test="${ list != null }" >	
			<c:forEach items="${ list }" var="bvo" varStatus="idx" >
				
				<c:set var="num" 
					value="${ page_vo.totalRecord - ((page_vo.nowPage-1) * page_vo.recPerPage + idx.index) }"/>
				
				<tr>
					<td>${ num }</td>
					<td style="text-align: left">
						<a href="javascript:viewData('${bvo.b_idx}')">${ bvo.title }						
							<c:if test="${ bvo.c_list != null && bvo.c_size > 0 }">
								&nbsp;&nbsp; ---- (${ bvo.c_size }) 
							</c:if>					 
						</a></td>						
					<td>${ bvo.file_name }</td>					
					<td>${ bvo.writer }</td>			
					<td>${ bvo.w_date }</td>
				</tr>
				
			</c:forEach>			
		</c:if>

		<c:if test=" ${ list == null } " >
				<tr>
					<td colspan="5">등록된 게시물이 없습니다.</td>
				</tr>
		</c:if>

			</tbody>
			
			<tfoot>				
				<tr>
					<td><input type="button" value="돌아가기" 
						onclick="javascript:location.href='Controller?type=index'" /></td>
						
					<td colspan="3" >
						<ol class="paging">						


<%
	// starPage가 1인 경우 이전으로 돌아갈 값이 없다
	if(pvo.getStartPage() < pvo.getPagePerBlock()){

%>												
							<li class="disable">&lt;</li>
							
<%	} else { %>
							<li><a href="Controller?type=boardList&cPage=<%= pvo.getNowPage() - pvo.getPagePerBlock() %>">&lt;</a></li>	
							
<%	} 

	
	// endPage가 totalPage를 초과하는 경우,
	// 예를들어 total : 12, end : 15(무조건 pagePerBlock인 5의 배수)이므로
	// 값이 없는 페이지가 3장이 남는다!
	 	
	if(pvo.getEndPage() > pvo.getTotalPage()) {
		pvo.setEndPage( pvo.getTotalPage() );		
	}
		
	
	for(int i = pvo.getStartPage(); i <= pvo.getEndPage(); i++){
		
		// 총 페이지 수보다 넘는 값이 출력되는 것을 방지
		if(i > pvo.getTotalPage())
			break;
			
		if(i == pvo.getNowPage()){
%>
							<li class="now"><%=i%></li>
							
<% 		} else { %>
							<li><a href="Controller?type=boardList&cPage=<%=i%>"><%=i%></a></li>
							
<%		}
	} // for문의 끝	
		
	
	if(pvo.getEndPage() < pvo.getTotalPage()){
%>			
							<li><a href="Controller?type=boardList&cPage=<%= pvo.getNowPage() + pvo.getPagePerBlock() %>">&gt;</a></li>	
			
<%	} else { %>
							<li class="disable">&gt;</li>
<% 	} %>								
						
						</ol>
					</td>
					
					<td><input type="button" value="글쓰기" onclick="javascript:location.href='Controller?type=addBoard&cPage=<%=pvo.getNowPage()%>'" /></td>
					</tr>
			</tfoot>	
		</table>
	</div>
	
	<form name="fm" method="post" action="Controller" >		
		<input type="hidden" name="type" />
		<input type="hidden" name="cPage" value="<%= pvo.getNowPage() %>" />
		<input type="hidden" name="b_idx" />
	</form>
	
	
</body>
</html>

