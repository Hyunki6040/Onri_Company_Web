<%--
  Created by IntelliJ idEA.
  User: Jinha
  Date: 6/22/2020
  Time: 4:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="../css/tempGroupCreate.css" type="text/css" />
    <link rel="stylesheet" href="../css/reset.css" type="text/css" />
    <script src="https://kit.fontawesome.com/a076d05399.js"></script>
</head>
<body>
<form class="container" action="/jinha/groupCreateProc.do">
    <ul class="ks-cboxtags">
        <li><input type="checkbox" id="cbOne"  name="sku" value="에너자이징_샴푸_250ml"><label for="cbOne">에너자이징 샴푸 250ml</label></li>
        <li><input type="checkbox" id="cbTwo" name="sku" value="에너자이징_젤"><label for="cbTwo">에너자이징 젤</label></li>
        <li><input type="checkbox" id="cbSeven" name="sku" value="퓨리파잉_젤"><label for="cbSeven">퓨리파잉 젤</label></li>
        <li><input type="checkbox" id="cbThree" name="sku" value="퓨리파잉_샴푸_250ml"><label for="cbThree">퓨리파잉 샴푸 250ml</label></li>
        <li><input type="checkbox" id="cbFour" name="sku" value="디톡시파잉_샴푸_250ml"><label for="cbFour">디톡시파잉 샴푸 250ml</label></li>
        <li><input type="checkbox" id="cbFive" name="sku" value="누누_샴푸"><label for="cbFive">누누 샴푸</label></li>
        <li><input type="checkbox" id="cbSix" name="sku" value="올인원밀크"><label for="cbSix">다비네스 올인원밀크</label></li>
    </ul>
    <input type="submit" value="그룹생성하기" class="submitBtn">
</form>
</body>
</html>
