<%--
  Created by IntelliJ IDEA.
  User: robert
  Date: 2020/06/19
  Time: 1:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>카카오플러스친구 명단 확인</title>

</head>
<body>
        <h5>오늘의 딜에 카카오 플러스친구 추가여부를 확인할 아이디들을 입력해주세요</h5>
        <h5>각각의 아이디는 ,(콤마)로 구분해주세</h5>

        <textarea id="friends_list" placeholder="카카오아이디를 입력해주세요">

        </textarea>
        <button onclick="checkList()">확인</button>
        <h4 id="result"></h4>

        <script type="text/javascript">
                function checkList(){
                        var list = document.getElementById("friends_list").value().toString().replace(" ","");

                        $.ajax({
                                url: "/brand/kakaoFriendsProc.do",
                                method: 'get',
                                data: {
                                        "friends_list": friends_list
                                },
                                success: function (data) {
                                        console.table(data);

                                        if (data.msg == "sucess") {

                                        } else {
                                                document.getElementById('stuNo').value = "";
                                        }

                                },
                                error: function (error) {
                                        document.getElementById('stuNo').value = "";
                                        document.getElementById('audio').src = "../audio/Error.wav";

                                }

                        });

                }
        </script>
</body>
</html>
