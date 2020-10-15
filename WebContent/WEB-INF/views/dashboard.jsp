<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>

<title>모아 & More</title>
</head>
<jsp:include page="sidebarTmp.jsp"/>
        <!-- Begin Page Content -->
        <div class="container-fluid">

          <!-- Page Heading -->
          <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h1 class="h3 mb-0 text-gray-800">대시보드</h1>
            </div>
            
           	<!-- 첫번째 줄 -->
            <div class="row">
            
            	<!-- 오늘의 예산 -->
            	<div class="col-xl-5 col-lg-5">
	              <div class="card shadow mb-4">
	                <!-- Card Header - Dropdown -->
	                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
	                  <h6 class="m-0 font-weight-bold text-primary">오늘의 예산</h6>
	                  <div class="dropdown no-arrow">
	                    <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	                      <i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
	                    </a>
	                    <div class="dropdown-menu dropdown-menu-right shadow animated--fade-in" aria-labelledby="dropdownMenuLink">
	                      <a class="dropdown-item" href="/moamore/budget/todayBudget.moa">오늘의 예산</a>
	                    </div>
	                  </div>
	                </div>
	                <!-- Card Body -->
	                <div class="card-body">
	                	<div>
	                	</div>
	                	
	                </div>
	              </div>
	            </div>
	            
	            
            	<!-- 목표 -->
            	<div class="col-xl-5 col-lg-5">
	              <div class="card shadow mb-4">
	                <!-- Card Header - Dropdown -->
	                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
	                  <h6 class="m-0 font-weight-bold text-primary">오늘의 예산</h6>
	                  <div class="dropdown no-arrow">
	                    <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	                      <i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
	                    </a>
	                    <div class="dropdown-menu dropdown-menu-right shadow animated--fade-in" aria-labelledby="dropdownMenuLink">
	                      <a class="dropdown-item" href="/moamore/budget/todayBudget.moa">오늘의 예산</a>
	                    </div>
	                  </div>
	                </div>
	                <!-- Card Body -->
	                <div class="card-body">
	                	<div>
	                		
	                	</div>
	                	
	                </div>
	              </div>
	            </div>
            
            
            

          </div>

        </div>
        <!-- /.container-fluid -->

      <!-- End of Main Content -->

		
<jsp:include page="footerTmp.jsp"/>
