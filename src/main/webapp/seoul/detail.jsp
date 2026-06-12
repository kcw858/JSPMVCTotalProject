<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 
	브라우저 : = URL을 이용해서 요청한다
			= 브라우저 자체에서 처리 : JavaScript (유지:Jquery, 최신 개발: MSA:react/Vue)
			
				|
			Controller: delivery (요청 => 전송)
						서빙
				|
			Model => @RequestMapping을 찾아 메소드 호출	
				|
			request를 받아서
				|
			JSP로 전송
			
			=> DAO / Model / JSP --> 웹 개발자
			   -----------	 ---
			   | Back		 | View
			
			mapper.xml ---> DBA
			DAO / VO / Model --> 웹 개발자
			JSP / Jquery --> 프론트 개발자
--%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
		<style type="text/css">
		.container{
			margin-top: 50px;
		}
		.row{
			margin: 0px auto;
			width: 800px;
		}
		</style>
	</head>
	<body>
		<div class="container">
			<div class="row">
				<table class="table">
					<tr>
						<td colspan="2" class="text-center">
							<img src="${vo.poster}" style="width: 750px;height: 300px"
							onerror="this.src='no.jpg'">
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<h3>${vo.title}</h3>
						</td>
					</tr>
					<tr>
						<td width="10%">주소</td>
						<td width="90%">${vo.address}</td>
					</tr>
					<tr>
						<td colspan="2">${vo.msg}</td>
					</tr>
					<tr>
						<td colspan="2" class="text-right">
							<a href="list.do?tno=${tno}" class="btn btn-sm btn-primary">목록</a>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>