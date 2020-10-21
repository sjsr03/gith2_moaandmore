<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>회원정보 수정</title>
<

  <!-- Custom fonts for this template-->
  <link href="/moamore/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="/moamore/css/sb-admin-2.min.css" rel="stylesheet">
</head>
<style>
.filebox {display:inline-block; margin-right: 10px;}

.filebox label {
  display: inline-block;
  padding: .5em .75em;
  color: #999;
  font-size: inherit;
  line-height: normal;
  vertical-align: middle;
  background-color: #fdfdfd;
  cursor: pointer;
  border: 1px solid #ebebeb;
  border-bottom-color: #e2e2e2;
  border-radius: .25em;
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

.filebox.bs3-primary label {
  color: #fff;
  background-color: #337ab7;
	border-color: #2e6da4;
}
</style>

<body class="bg-gradient-primary">
<jsp:include page="../sidebar.jsp"/>
 <div class="container">

    <div class="card o-hidden border-0 shadow-lg my-5">
      <div class="card-body p-0">
        <!-- Nested Row within Card Body -->
        <div class="row">
          <div class="col-lg-7">
            <div class="p-5">
              <div class="text-center">
                <h1 class="h4 text-gray-900 mb-4">회원정보 수정</h1>
              </div>
              <!-- 폼 시작 -->
				<form action="/moamore/member/signupPro.moa" class ="user" method="post" name="inputForm" enctype="multipart/form-data">
               
                <div class="form-group">
                  <input type="email" name ="id" class="form-control form-control-user" id="exampleInputEmail" disabled placeholder="${dto.id}">
                </div>
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <input type="password" name ="pw" class="form-control form-control-user" id="exampleInputPassword" placeholder="비밀번호">
                  </div>
                  <div class="col-sm-6">
                    <input type="password" name="pwCh" class="form-control form-control-user" id="exampleRepeatPassword" placeholder="비밀번호 확인">
                  </div>
                </div>
                <div class="form-group">
                  <input type="text" name="nickname" class="form-control form-control-user" id="exampleInputEmail" placeholder="닉네임">
             
                </div>
                 <div class="form-group">
                <button class="btn btn-google btn-user btn-block" value="닉네임 중복 확인" onclick="confirmNickName(this.form)" >닉네임 중복확인</button>
                </div>
                <div class="form-group row">
                  
                 </div>
                
                <input type="submit" value="수정하기" class="btn btn-facebook btn-user btn-block"  />
                
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
              
            </div>
          </div>
          <div class='col-xl-4 col-lg-5'>
            	<img class="rounded-circle" src="/moamore/save/${dto.profile_img}" />
					<div class="filebox bs3-primary">
						  <label for="ex_file2">업로드</label> 
						  <input type="file" id="ex_file2" name="image"> 
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




<jsp:include page="../footer.jsp"/>	
</body>
	<form action="/moamore/member/updateMemberPro.moa" method="post" name="updateMember" enctype="multipart/form-data">
		<table>
			<tr>
				<td>아이디*</td>
				<td>${dto.id}</td> 
			</tr>
			<tr>
				<td>비밀번호*</td>
				<td><input type="password" name="pw" value="${dto.pw}"/></td>
			</tr>
			<tr>
				<td>비밀번호 확인*</td>
				<td><input type="password" name="pwCh" /></td>
			</tr>
			<tr>
				<td>닉네임*</td>
				<td>
					<input type="text" name="nickname" value="${dto.nickname}" />
					<input type="button" value="닉네임 중복 확인" onclick="confirmId(this.form)" />
				</td> 
			</tr>
		
			<tr>
				<td>사진*</td>
				<td>
					<img src="/moamore/save/${dto.profile_img}" />
					<input type="file" name="image">
					<input type="hidden" name="eximage" value="${dto.profile_img}" />
				</td> 
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="수정"/>
					<input type="button" value="취소" onclick="window.location='/moamore/member/main.moa'"/>
				</td>
			</tr>
		</table>
	</form>

</html>