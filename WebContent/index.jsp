<%@page import="mybatis.vo.MemVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> Index </title>

<link rel="stylesheet" href="css/index.css">

<!-- include libraries(jQuery, bootstrap) -->
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.js"></script> 
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script> 
<script src='https://code.jquery.com/jquery-1.10.0.min.js'></script>
<script src="js/index.js"></script>

<script type="text/javascript">	
	
	// 로그인
	function login(){

		var s_id = document.f0.id.value;
		var s_pwd = document.f0.pwd.value;
		
		if(s_id == ""){
			alert("아이디를 입력하세요");
			document.f0.id.focus();
			return;
		}
		
		if(s_pwd == ""){
			alert("비밀번호를 입력하세요");
			document.f0.pwd.focus();
			return;
		}
		document.f0.type.value = "login";
		document.f0.submit();
	}
	
	
	// 로그아웃
	function logout(){
		document.f1.type.value="logout"
		document.f1.submit();
	}
	
	
	// 게시판
	function goBoard(){
		document.f1.type.value="boardList";
		document.f1.submit();		
	}
	
	
	//회원가입 폼을 보여주는 함수
	function regView(){
		var log_div = document.getElementById("log_form");
		var reg_div = document.getElementById("reg_form");
		log_div.style.display = "none";
		reg_div.style.display = "block";
		
	}
	
	function notView(){
		var log_div = document.getElementById("log_form");
		var reg_div = document.getElementById("reg_form");
		log_div.style.display = "block";
		reg_div.style.display = "none";
	}
	
	
	// 회원가입 폼에 값이 입력되었는지를 판단한 후 회원가입
	function reg(){		
		
		if(document.f2.id.value == ""){				
			alert(document.f2.id.name +"을(를) 입력하세요!");
			document.f2.id.focus();
			return;		
		}
		
		if(document.f2.pwd.value == ""){				
			alert(document.f2.pwd.name +"을(를) 입력하세요!");
			document.f2.pwd.focus();
			return;		
		}
		
		document.f2.action="Controller"
		document.f2.type.value = "reg";
		document.f2.submit();
	}
	
</script>


</head>
<body>
	<div id="wrap">
	
<% 
	// 로그인이 진행되지 않았다면 로그인하는 div를 화면에 보여주고 
	// 로그인이 되었다면 로그인화면을 보여주지 않는다.

	Object obj = session.getAttribute("login_vo");
	
	if(obj == null){
		
%>
		<div id="log_form" class="login-wrap">
			<h2>Login</h2>
			
			<div class="form">
				<form name="f0" action="Controller" method="post">
					<input type="hidden" name="type"  />					
					<input type="text" placeholder="ID or email" name="id" />
					<input type="password" placeholder="Password" name="pwd" />					
				</form>
				<button onclick="login()">LOGIN</button>
				<a href="javascript:regView()"><p>Don't have an account? Register!</p></a>
			</div>
		</div>
		
<%
	} else {
						// 로그인이 되어있는 경우 obj를 vo로 형변환
		MemVO vo = (MemVO) obj;
%>
		<div class="login-wrap">
			<div class="form">
	
			<h2><%=vo.getName() %>(<%=vo.getId() %>)님 환영합니다.</h2>
				
			<button onclick="goBoard()">Go Board</button>
			<button onclick="logout()">LOGOUT</button>
						
			</div>
		</div>
		
		<form name="f1" method="post" action="Controller">
		<input type="hidden" name="type"/>
		</form>
		
	
<%
	}// if-else문 끝
%>

		<!-- 숨어있다가 회원가입 버튼을 누르면 보이기 -->
		<div id="reg_form" class="login-wrap">	
			<h2>Register</h2>	
			
			<div class="form">			
				<form name="f2" action="Controller" method="post">				
					<input type="hidden" name="type" />										
					<input type="text" name="id" placeholder="id"/>
					<input type="password" name="pwd" placeholder="password"/>								
					<input type="text" name="name" placeholder="name"/>
					<input type="text" name="phone" placeholder="phone"/>
					<input type="text" name="email" placeholder="email" />
					<input type="text" name="addr" placeholder="address" />				 		 			
				</form>
				<button onclick="reg()">Register</button>
				<button onclick="notView()">Cancel</button>		
			</div>
			
		</div> 	
			
	</div>	
	
</body>
</html>
    
    