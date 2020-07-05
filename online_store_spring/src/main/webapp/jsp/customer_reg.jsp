<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>客戶註冊</title>
<link rel="stylesheet" type="text/css" href="css/public.css">
<style type="text/css">
table {
	border-collapse: collapse;
}

.boder {
	border: 1px solid #5B96D0;
}

.col1 {
	background-color: #A6D2FF;
	text-align: right;
	padding-right: 10px;
	border: 1px solid #5B96D0;
	line-height: 50px;
}

.col2 {
	padding-left: 10px;
	border: 1px solid #5B96D0;
	line-height: 50px;
}

.textfield {
	height: 20px;
	width: 200px;
	border: 1px solid #999999;
	text-align: left;
	font-size: medium;
	line-height: 50px;
}
</style>

<script>
	function verify(myform) {
		var errorMes = "";

		if (myform.userid.value == "") {
			errorMes += "客戶帳號不可為空! \n";
		}
		if (myform.name.value == "") {
			errorMes += "客戶名稱不可為空! \n";
		}
		if (myform.password.value != myform.password2.value) {
			errorMes += "兩次密碼不相同! \n";
		}
		/*
		var pattern = /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
		if (!pattern.test(myform.birthday.value)) {
			errorMes += "出生日期格式有誤! \n";
		}
		 */
		if (errorMes == "") {
			return true;
		} else {
			alert(errorMes);
			return false;
		}

	}
</script>

</head>

<body>
	<div>
		<img src="images/reg.jpg" align="absmiddle" />
	</div>
	<br>
	<hr width="100%" />
	<div class="text3" align="center">請填寫下列信息</div>
	<br>
	<%--顯示controller回傳錯誤訊息--%>
	<ul>
		<c:forEach var="error" items="${errors}">
			<li class="error">${error}</li>
		</c:forEach>
	</ul>
	<form action="/reg" method="post" onsubmit="return verify(this)">
		<table width="60%" border="0" align="center" class="boder">
			<tr>
				<td width="35%" height="27" class="col1">客戶賬號：</td>
				<td class="col2"><input type="text" name="id" />*</td>
			</tr>
			<tr>
				<td height="27" class="col1">客戶姓名：</td>
				<td class="col2"><input type="text" name="name" />*</td>
			</tr>
			<tr>
				<td height="27" class="col1">客戶密碼：</td>
				<td class="col2"><input type="password" name="password" />*</td>
			</tr>
			<tr>
				<td height="27" class="col1">再次輸入密碼：</td>
				<td class="col2"><input type="password" name="confirmPassword" />*</td>
			</tr>
			<!-- 
			<tr>
				<td height="27" class="col1">出生日期：</td>
				<td class="col2"><input type="text" name="birthday" />*
					格式（YYYY-MM-DD）</td>
			</tr>
			 -->
			<tr>
				<td height="27" class="col1">通訊地址：</td>
				<td class="col2"><input type="text" name="address" /></td>
			</tr>
			<tr>
				<td height="27" class="col1">電話號碼：</td>
				<td class="col2"><input type="text" name="phone" /></td>
			</tr>
		</table>
		<br>
		<div align="center">
			<input type="image" src="images/submit_button.jpg" />
		</div>
	</form>
	<%@include file="footer.jsp"%>
</body>
</html>