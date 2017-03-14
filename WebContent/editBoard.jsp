<%@page import="mybatis.bbs.vo.BoardVO"%>
<%@page import="mybatis.dao.BoardDAO"%>
<%@page import="mybatis.vo.MemVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글수정</title>

<link href="css/header.css" rel="stylesheet">
<link href="css/write.css" rel="stylesheet">

<!-- include libraries(jQuery, bootstrap) -->
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.js"></script> 
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script> 

<!-- include summernote css/js-->
<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.2/summernote.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.2/summernote.js"></script>
<script type="text/javascript" src="summernote/dist/summernote-ko-KR.min.js"></script>

<script type="text/javascript" >

	// 에디터 이미지 업로드
	$(function() {
		$('#content').summernote({
			height : 300,
			minHeight : 200,
			maxHeight : 350,
			focus : true,
			lang : 'ko-KR',
			
			// 이벤트발생
			callbacks : {
				onImageUpload : function(files, editor, welEditable) {

					// alert("^^");
					// console.log('image upload:', files);
					sendFile(files[0], editor, welEditable);
				}
			}
		});

		$('#content').summernote('lineHeight', 0.5);
	});

	
	function sendFile(file, editor, welEditable) {
		
		// 폼태그처럼 '파라미터'를 전달
		data = new FormData();

		// data 파라미터에 속성 추가
		data.append("upload_file", file);
		
		// MultipartRequest로 file을 전달하기 때문에 그냥 request인 파라미터는 전달X
		// 따라서 Get방식을 사용하여 Controller에 파라미터를 전달한다
		//data.append("type", "uploadImg");

		// json표기법
		$.ajax({
			url : "Controller?type=uploadImg", 	// jsp 호출, image 저장
			data : data,			// 파라미터
			cache : false,
			contentType : false,
			processData : false,
			type : 'POST',
			success : function(data) {
				
				// alert(data);
				// alert(data.url);
				
				// var image = $('<img>').attr('src', '' + data); // 에디터에 img 태그로 저장을 하기 위함
				// $('#content').summernote("insertNode", image[0]); // summernote 에디터에 img 태그를 보여줌
				// editor.insertImage(welEditable, data);
				$('#content').summernote("insertImage", data.url);

			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log(textStatus + " " + errorThrown);
			}
		});
	}
	
	
</script>

<script type="text/javascript">

	function sendData() {

		if (document.forms[0].title.value == "") {
			alert("제목을 입력하세요");
			document.forms[0].title.focus();
			return;//수행 중단			
		}
		
		if (document.forms[0].content.value == "") {
			alert("내용을 입력하세요");
			document.forms[0].content.focus();
			return;//수행 중단			
		}

		//document.forms[0].edit_chk = "0";
		document.forms[0].submit();
	}

	function goList() {
		document.fm.submit();
	}
	

	// 로그아웃
	function logout(){
		document.f1.type.value="logout"
		document.f1.submit();
	}
	
</script>
</head>

<body

<%
	// 원글의 [수정] 버튼을 눌렀다면 edit_chk=1 이 넘어온다
	String edit_chk = (String)request.getAttribute("edit_chk");
	
	BoardVO bvo = null;
	if(edit_chk != null){
		bvo = (BoardVO)request.getAttribute("bvo");
		
	} else
		response.sendRedirect("Controller?type=boardList");
%>

>

<%	
	String cPage = (String)request.getAttribute("cPage");

	Object obj = session.getAttribute("login_vo");
	if(obj!=null){
		
		MemVO mvo = (MemVO)obj;		

%>

	<div id="header">
		<a href="Controller?type=index"><h2>INCREPAS BOARD</h2></a>
		<div id="user">
			<span><%= mvo.getName() %>님 환영합니다 ^_^</span>
			<input type="button" value="logout" onclick="logout()"/>
		</div>
	</div>

	<div id="bbs">
		<form action="Controller?type=editBoard" method="post" encType="multipart/form-data">
		
			<table summary="게시물 수정">
				<caption>게시물 수정</caption>
				<tbody>
				
					<tr>
						<th>제목:</th>
						<td><input type="text" name="title" size="45" value="<%= bvo.getTitle() %>"/></td>
						
					</tr>
					<tr>
						<th>이름:</th>
						<td><input type="text" size="12" 
							value="<%= mvo.getName() %>(<%= mvo.getId() %>)" disabled="disabled" />
							<input type="hidden" name="writer" value="<%= mvo.getId() %>" /></td>
					</tr>
					<tr>
						<th>내용:</th>
						<td><textarea name="content" id="content" cols="50" rows="8"><%= bvo.getContent() %></textarea></td>
					</tr>
					<tr>
						<th>첨부파일:</th>
						<td><input type="file" name="file" />
<%
			//type="file"인 곳에는 value를 지정할 수 없기 때문에 별도로 출력해야 한다.
			if(bvo.getFile_name() != null &&bvo.getFile_name().trim().length()>0){
				// 파일첨부된 경우
%>
							(<%=bvo.getFile_name() %>)
<%			}	%>					
						
						</td>
					</tr>
					<tr>
						<td colspan="2">						
							<input type="button" value="저장" onclick="sendData()" /> 
							<input type="reset" value="다시" /> 
							<input type="button" value="목록" onclick="goList()"/>
							
							<input type="hidden" name="cPage" value="<%= cPage %>" />
							<input type="hidden" name="b_idx" value="<%= bvo.getB_idx() %>" /></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<form name="fm" method="post" action="Controller" >
		<input type="hidden" name="type" value="boardList" />
		<input type="hidden" name="cPage" value="<%= cPage %>"  />
	</form>
	
</body>
</html>
<%
	}
%>