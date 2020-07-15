<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="poly.util.CmmUtil" %><%--
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
    <link rel="stylesheet" href="../dist/loading-bar.css">
    <link rel="stylesheet" href="https://kit-free.fontawesome.com/releases/latest/css/free.min.css" media="all">
    <script src="../dist/loading-bar.js"></script>
    <%
        String failed_id = CmmUtil.nvl(request.getParameter("failed_id"));
        List<List<String>> uList = (List<List<String>>) session.getAttribute("user_update_list");
        if(session.getAttribute("user_update_list") != null){
    %>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script type="text/javascript">

        $( document ).ready(function() {
             userUpdate();
        });

    </script>
    <%
        }
    %>
</head>
<body>
<div>
    <ul class="tabWrap">
        <li><button id="dvBtn" class="tabBtn" type="button">다비네스</button></li>
        <li><button id="bwtBtn" class="tabBtn" type="button">바이위시트렌드</button></li>
    </ul>
    <ul class="tabCont">
        <li id="dvSkuWrap" class="on">
            <form id="dvSkuForm" class="container" action="/brand/groupCreateProc.do">
                <h4>포함시킬 상품 선택</h4>
                <ul class="ks-cboxtags">
                    <li><input type="checkbox" id="cbOne" name="sku" value="에너자이징_샴푸_250ml"><label for="cbOne">에너자이징 샴푸 250ml</label></li>
                    <li><input type="checkbox" id="cbTwo" name="sku" value="에너자이징_젤"><label for="cbTwo">에너자이징 젤</label></li>
                    <li><input type="checkbox" id="cbSeven" name="sku" value="퓨리파잉_젤"><label for="cbSeven">퓨리파잉 젤</label></li>
                    <li><input type="checkbox" id="cbThree" name="sku" value="퓨리파잉_샴푸_250ml"><label for="cbThree">퓨리파잉 샴푸 250ml</label></li>
                    <li><input type="checkbox" id="cbFour" name="sku" value="디톡시파잉_샴푸_250ml"><label for="cbFour">디톡시파잉 샴푸 250ml</label></li>
                    <li><input type="checkbox" id="cbFive" name="sku" value="누누_샴푸"><label for="cbFive">누누 샴푸</label></li>
                    <li><input type="checkbox" id="cbSix" name="sku" value="올인원밀크"><label for="cbSix">다비네스 올인원밀크</label></li>
                </ul>
                <h4>제외시킬 상품 선택</h4>
                <ul class="ks-cboxtags">
                    <li><input type="checkbox" id="nbOne"  name="nonSku" value="에너자이징_샴푸_250ml"><label for="nbOne">에너자이징 샴푸 250ml</label></li>
                    <li><input type="checkbox" id="nbTwo" name="nonSku" value="에너자이징_젤"><label for="nbTwo">에너자이징 젤</label></li>
                    <li><input type="checkbox" id="nbSeven" name="nonSku" value="퓨리파잉_젤"><label for="nbSeven">퓨리파잉 젤</label></li>
                    <li><input type="checkbox" id="nbThree" name="nonSku" value="퓨리파잉_샴푸_250ml"><label for="nbThree">퓨리파잉 샴푸 250ml</label></li>
                    <li><input type="checkbox" id="nbFour" name="nonSku" value="디톡시파잉_샴푸_250ml"><label for="nbFour">디톡시파잉 샴푸 250m</label></li>
                    <li><input type="checkbox" id="nbFive" name="nonSku" value="누누_샴푸"><label for="nbFive">누누 샴푸</label></li>
                    <li><input type="checkbox" id="nbSix" name="nonSku" value="올인원밀크"><label for="nbSix">다비네스 올인원밀크</label></li>
                </ul>
                <input type="hidden" value="다비네스" name="brandName">
            </form>
        </li>
        <li id="bwtSkuWrap">
            <form id="bwtSkuForm" class="container" action="/brand/groupCreateProc.do">
                <h4>포함시킬 상품 선택</h4>
                <ul class="ks-cboxtags">
                    <li><input type="checkbox" id="bwtOne"  name="sku" value="비타민C_21.5%_세럼_30ml"><label for="bwtOne">비타민C 21.5% 세럼 30ml</label></li>
                    <li><input type="checkbox" id="bwtTwo" name="sku" value="비타민C_15%_세럼_30ml"><label for="bwtTwo">비타민C 15% 세럼 30ml</label></li>
                    <li><input type="checkbox" id="bwtThree" name="sku" value="비타민E_크림_50ml"><label for="bwtThree">비타민E 크림 50ml</label></li>
                    <li><input type="checkbox" id="bwtFour" name="sku" value="프로폴리스_앰플_30ml"><label for="bwtFour">프로폴리스 앰플 30ml</label></li>
                    <li><input type="checkbox" id="bwtFive" name="sku" value="비타민_시트_마스크_5매"><label for="bwtFive">비타민 시트 마스크 5매</label></li>
                </ul>
                <h4>제외시킬 상품 선택</h4>
                <ul class="ks-cboxtags">
                    <li><input type="checkbox" id="nbwtOne"  name="nonSku" value="비타민C_21.5%_세럼_30ml"><label for="nbwtOne">비타민C 21.5% 세럼 30ml</label></li>
                    <li><input type="checkbox" id="nbwtTwo" name="nonSku" value="비타민C_15%_세럼_30ml"><label for="nbwtTwo">비타민C 15% 세럼 30ml</label></li>
                    <li><input type="checkbox" id="nbwtThree" name="nonSku" value="비타민E_크림_50ml"><label for="nbwtThree">비타민E 크림 50ml</label></li>
                    <li><input type="checkbox" id="nbwtFour" name="nonSku" value="프로폴리스_앰플_30ml"><label for="nbwtFour">프로폴리스 앰플 30ml</label></li>
                    <li><input type="checkbox" id="nbwtFive" name="nonSku" value="비타민_시트_마스크_5매"><label for="nbwtFive">비타민 시트 마스크 5매</label></li>
                </ul>
                <input type="hidden" value="바이위시트렌드" name="brandName">
            </form>
        </li>
        <button id="submit_button" class="submitBtn" onclick="sumbitClick();">그룹생성하기</button>
    </ul>

    <div>
        <h4 id="wailt_message" style="display: none">창을 닫지말고 잠시 기다려주세요!</h4>
        <%
            if(failed_id.length()>0){
        %>
        <h4>회원 리스트 업데이트가 필요합니다. 테크팀에 문의 해주세요! <br/>카카오 id : <%=failed_id%></h4>
        <%
            }
        %>
        <script type="text/javascript">
            let ajax = true;
            let update;

            function sumbitClick(){
                var skus = document.getElementsByName("sku");
                var checked = 0;
                for(i=0; i<skus.length; i++){
                    if(skus[i].checked === true){
                        checked += 1;
                    }
                }
                var nonskus = document.getElementsByName("nonSku");
                for(i=0; i<nonskus.length; i++){
                    if(nonskus[i].checked === true){
                        checked += 1;
                    }
                }

                if(checked === 0){
                    alert("선택된 SKU가 없습니다!");
                }else {
                    document.getElementById("submit_button").style.display = 'none';
                    document.getElementById("wailt_message").style.display = 'block';
                    ajax = false;
                    if(document.getElementById("dvSkuWrap").classList.contains("on")){
                        document.getElementById("dvSkuForm").submit();
                    }else{document.getElementById("bwtSkuForm").submit();}
                }
            }
        </script>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        $(document).ready(function() {
            $('.tabWrap li button').click(function() {
                $('.tabCont > li.on').removeClass("on");
            });
            $('#dvBtn').click(function() {
                $('#dvSkuWrap').addClass("on");
            });
            $('#bwtBtn').click(function() {
                $('#bwtSkuWrap').addClass("on");
            });
        });
    </script>
    <%if(session.getAttribute("user_update_list") != null){%>
        <div class="container ldBarCont">
            <h4 id="message">푸시메세지 그룹이 생성되었습니다!<br/>DB업데이트를 위해 아래의 퍼센트가 다 차기전까지 이 페이지를 닫지 마세요!</h4>
            <div id="percent" class="mybar ldBar label-center" data-preset="circle" data-value="0"></div>
            <script type="text/javascript">
                let bar = new ldBar(".ldBar", {
                    "stroke": '#09f',
                    "stroke-width": 10,
                    "preset": "fan",
                    "value": 0,
                });

                function userUpdate(){
                    if(ajax === true) {
                        update = $.ajax({
                            url: "/brand/userUpdate.do",
                            method: 'get',
                            data: {
                                "size": <%=uList.size()%>
                            },
                            success: function (data) {
                                console.table(data);
                                if (data.msg === "sucess") {
                                    bar.set(data.value + 0, true)
                                    if (ajax === true) {
                                        userUpdate();
                                    }else {
                                        update.abort();
                                    }

                                } else if (data.msg === "end") {
                                    document.getElementById("message").innerHTML = 'DB에 업데이트가 완료되었습니다.\n감사합니다!';
                                    bar.set(100, true)
                                }
                            },
                            error: function (request,status,error){
                                if (ajax === true) {
                                    if(request.status === 0){
                                        update.abort();
                                    }else {
                                        alert("오류발생! \n code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
                                    }
                                }else {
                                    update.abort();
                                }
                            }

                        });
                    }
                }
            </script>
        </div>
    <%}%>
</div>
<script>

</script>
</body>
</html>
