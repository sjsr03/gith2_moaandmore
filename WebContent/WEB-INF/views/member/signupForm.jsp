<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="description" content="">
	<meta name="author" content="">
<title>회원가입</title>

<!-- Custom fonts for this template-->
  <link href="/moamore/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="/moamore/css/sb-admin-2.min.css" rel="stylesheet">

</head>
<body class="bg-gradient-primary">
  <div class="container">
    <div class="card o-hidden border-0 shadow-lg my-5">
      <div class="card-body p-0">
        <!-- Nested Row within Card Body -->
        <div class="row">
          <div class="col-lg-5 d-none d-lg-block bg-login-image"></div>
          <div class="col-lg-7">
            <div class="p-5">
              <div class="text-center">
                <h1 class="h4 text-gray-900 mb-4">Create an Account!</h1>
              </div>
              <!-- 폼 시작 -->
				<form action="/moamore/member/signupPro.moa" class ="user" method="post" name="inputForm" enctype="multipart/form-data">
                <div class="form-group">
                  <input type="text" name ="id" class="form-control form-control-user input" id="user_id" placeholder="아이디">
                  <div class="check_font" id="id_check"></div>
                </div>
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <input type="password" name ="pw" class="form-control form-control-user input" id="pw" placeholder="비밀번호">
                  </div>
                  <div class="col-sm-6">
                    <input type="password" name="pwCh" class="form-control form-control-user input" id="pwch" placeholder="비밀번호 확인">
                  </div>
                </div>
                <div id="pw_alert"></div>
                <div class="form-group">
                  <input type="text" name="nickname" class="form-control form-control-user input" id="nickname" placeholder="닉네임">
             	  <div class="check_nickname" id="nick_check"></div>
                </div>
                 <div class="form-group">
                </div>
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <input type="password"  class="form-control form-control-user" id="exampleInputPassword" placeholder="사진">
                  </div>
                  <div class="col-sm-6">
                    <input type="file" name="image"/>
                  </div>
                 </div>
                
                <input type="submit" value="가입하기" id="reg_submit" class="btn btn-facebook btn-user btn-block"  />
                
                <hr>
                <!-- 
                <a href="index.html" class="btn btn-google btn-user btn-block">
                  <i class="fab fa-google fa-fw"></i> 빨강색
                </a>
                <a href="index.html" class="btn btn-facebook btn-user btn-block">
                  <i class="fab fa-facebook-f fa-fw"></i> 찐파랑색
                </a>
                 -->
              </form> <!--  폼 끝! -->
              <hr>
              <div class="text-center">
                <a class="small" href="forgot-password.html">Forgot Password?</a>
              </div>
              <div class="text-center">
                <a class="small" href="/moamore/member/loginForm.moa">Already have an account? Login!</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

  </div>

  <!-- Bootstrap core JavaScript-->
  <script src="/moamore/vendor/jquery/jquery.min.js"></script>
  <script src="/moamore/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="/moamore/vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="/moamore/js/sb-admin-2.min.js"></script>
</body>
<script>
//아이디 중복검사

$(document).ready(function () {
	
	//처음에 회원가입 클릭 안되게 
	//$("#reg_submit").attr("disabled", true);
	var idsuccess = 0;
	var pwsuccess = 0;
	var nicksuccess = 0;
	
$("#user_id").keyup(function() {
	var idJ = /[0-9a-zA-Z]*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
	var user_id = $('#user_id').val();
	console.log(user_id);
	$.ajax({
		url : 'idCheck.moa?userId='+ user_id,
		type : 'get',
		success : function(data) {
			
			if (data == 1) { 
					// 1 : 아이디가 중복되는 문구
					$("#id_check").text("이미 사용중인 아이디입니다");
					$("#id_check").css("color", "red");
					$("#reg_submit").attr("disabled", true);
				} else {
					
					
					if(idJ.test(user_id)){
						// 0 : 아이디 길이 / 문자열 검사
						$("#id_check").text("사용할 수 있는 아이디 입니다.");
						$('#id_check').css('color', 'green');
						idsuccess = 1;
						
					} else if(user_id == ""){
						
						$('#id_check').text('아이디를 입력해주세요');
						$('#id_check').css('color', 'red');
						$("#reg_submit").attr("disabled", true);				
						
					} else {
						
						$('#id_check').text("이메일 형식이 아닙니다.");
						$('#id_check').css('color', 'red');
						$("#reg_submit").attr("disabled", true);
					}
			}
		}, error : function() {
			console.log("실패");
		}
	});
});

	//닉네임 유효성 검사
	$("#nickname").keyup(function() {
		console.log("keyup");
	var nickname = $('#nickname').val();
	$.ajax({
		url : 'nicknameCheck.moa?nickname='+ nickname,
		type : 'get',
		success : function(data) {
			console.log(data);
				if (data == 1) { 
					// 1 : 아이디가 중복되는 문구
					$("#nick_check").text("이미 사용중인 닉네임입니다");
					$("#nick_check").css("color", "red");
					$("#reg_submit").attr("disabled", true);
				} else {
					nicksuccess = 1;
					if(nickname == ""){				
						$('#nick_check').text('닉네임을 입력해주세요');
						$('#nick_check').css('color', 'red');
						$("#reg_submit").attr("disabled", true);	
						
					}else{
						$('#nick_check').text('사용할 수 있는 닉네임 입니다.');
						$('#nick_check').css('color', 'green');
						nicksuccess = 1;
					}
				}
		}, error : function() {
					console.log("실패");
			}
		});
	});
	

 //비밀번호 확인
	$('#pwch').keyup(function(){
	
		var pw = $("#pw").val();
		var pwch = $("#pwch").val();
	
	   if(pw != pwch){
		   $("#pw_alert").text("비밀번호가 일치하지 않습니다.");
			$("#pw_alert").css("color", "red");
			$("#reg_submit").attr("disabled", false);
		    	
	    }else{
	    	$("#pw_alert").text("비밀번호가 일치합니다.");
    		$("#pw_alert").css("color", "green");
    		pwsuccess = 1;
	    	if(pw==""){
	    		$("#pw_alert").text("비밀번호를 입력해주세요");
				$("#pw_alert").css("color", "red");
				pwsuccess = 0;
	    	}else{
	    		$("#pw_alert").text("비밀번호가 일치합니다.");
	    		$("#pw_alert").css("color", "green");
	    		pwsuccess = 1;
	    	}
	    	
	    }
	});  	   
	
    //회원가입 가능
	$(".input").keyup(function(){
		if(idsuccess==1 && pwsuccess==1 && nicksuccess==1){
			$("#reg_submit").attr("disabled", false);
			
		}
		
	});



});





</script>

</html>