<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>회원정보 수정</title>
</head>

<style>

.imgbox{ text-align:center; padding:5px;}
.filebox{
	text-align:center;
	padding:5px;	
}
.filebox label {
  display: inline-block;
  padding: .5em .75em;
  color: #333;
  font-size: inherit;
  line-height: normal;
  vertical-align: middle;
  background-color: #fdfdfd;
  cursor: pointer;
  border: 1px solid #ebebeb;
  border-bottom-color: #e2e2e2;
  border-radius: .25em;
  text-align:center;
}

.filebox input[type="file"] {  /* 파일 필드 숨기기 */
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip:rect(0,0,0,0);
  border: 0;
}

</style>

<body id="page-top">
<jsp:include page="../sidebar.jsp"/>
<!-- 본문내용 시작 -->
  <div class="container">
    <div class="card o-hidden border-0 shadow-lg my-5">
      <div class="card-body p-0">
        <!-- Nested Row within Card Body -->
        <div class="row">
          <div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
          <div class="col-lg-6">
            <div class="p-5">
              <div class="text-center">
                <h1 class="h4 text-gray-900 mb-4">회원정보 수정</h1>
              </div>
              <!-- 폼 시작 -->
				<form action="/moamore/member/updateMemberPro.moa" class ="user" method="post" name="inputForm" enctype="multipart/form-data">
               
                <div class="form-group">
                  <input type="text" name ="id" class="form-control form-control-user" id="exampleInputEmail" disabled placeholder="${dto.id}">
                </div>
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <input type="password" name ="pw" class="form-control form-control-user" id="pw" placeholder="비밀번호">
                  </div>
                  <div class="col-sm-6">
                    <input type="password" name="pwCh" class="form-control form-control-user" id="pwch" placeholder="비밀번호 확인">
                  </div>
                </div>
                 <div id="pw_alert"></div>
                <div class="form-group">
                  <input type="text" name="nickname" class="form-control form-control-user" id="nickname" value="${dto.nickname}"  placeholder="닉네임">
             	  <div class="check_nickname" id="nick_check"></div>
                </div>
                  <div>
                 	<div class="imgbox">
            		<img class="rounded-circle" src="/moamore/save/${dto.profile_img}" width="300px" height="250px" />
					</div>
					<div class="filebox ">
						  <label for="ex_file">사진변경</label> 
						  <input type="file" id="ex_file"  name="image"> 
					</div>
           		 </div>
               
                <input type="submit" value="수정하기" id="reg_submit" class="btn btn-facebook btn-user btn-block"  />
                
                <hr>
              </form> <!--  폼 끝! -->
              
            </div>
          </div>
        </div>
      </div>
    </div>

  </div>



<jsp:include page="../footer.jsp"/>	

	
<script>
$(document).ready(function () {

	$("#reg_submit").attr("disabled", true);
	
	
	//비밀번호 확인 체크
	$('#pwch').keyup(function(){
	
		var pw = $("#pw").val();
		var pwch = $("#pwch").val();
	
	   if(pw != pwch){
		   $("#pw_alert").text("비밀번호가 일치하지 않습니다.");
			$("#pw_alert").css("color", "red");
			$("#reg_submit").attr("disabled", true);
		    	
	       
	    }else{
	    	$("#pw_alert").text("비밀번호가 일치합니다.");
	   		$("#pw_alert").css("color", "green");
	   		$("#reg_submit").attr("disabled", false);
	    	
	   		if(pw==""){
	    		$("#pw_alert").text("비밀번호를 입력해주세요");
				$("#pw_alert").css("color", "red");
				$("#reg_submit").attr("disabled", true);
	    	}else{
	    		$("#pw_alert").text("비밀번호가 일치합니다.");
	    		$("#pw_alert").css("color", "green");
	    		$("#reg_submit").attr("disabled", false);
	    	}
	    	
	    }
	});  	   
			
	//닉네임 유효성 검사
	$("#nickname").keyup(function() {
	var nickname = $('#nickname').val();
		$.ajax({
			url : 'nicknameCheck.moa?nickname='+ nickname,
			type : 'get',
			success : function(data) {
				
					if (data == 1) { 
						// 1 : 아이디가 중복되는 문구
						if(nickname =='${dto.nickname}'){
							$('#nick_check').text('지금 사용중인 닉네임입니다.');
							$('#nick_check').css('color', 'green');
							$("#reg_submit").attr("disabled", false);
						}else{
							$("#nick_check").text("이미 사용중인 닉네임입니다");
							$("#nick_check").css("color", "red");
							$("#reg_submit").attr("disabled", true);
							
						}
					
					} else {

						if(nickname == ""){				
							$('#nick_check').text('닉네임을 입력해주세요');
							$('#nick_check').css('color', 'red');
							$("#reg_submit").attr("disabled", true);	
							
						}else{
							$('#nick_check').text('사용할 수 있는 닉네임 입니다.');
							$('#nick_check').css('color', 'green');
							$("#reg_submit").attr("disabled", false);
						}
						
					}
				}, error : function() {
						console.log("실패");
				}
			});
	
	});	
		
	
	
	
});


</script>
</html>