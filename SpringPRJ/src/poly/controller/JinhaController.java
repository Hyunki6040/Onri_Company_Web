package poly.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import poly.util.jdbcService;

import javax.lang.model.util.SimpleElementVisitor7;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Integer.parseInt;

@Controller
@RequestMapping(value = "/jinha/*")
public class JinhaController {
    @RequestMapping(value = "/tempGroupCreate")
    public String tempGroupCreate() throws Exception {

        return "/jinha/tempGroupCreate";
    }

    @RequestMapping(value = "/groupCreateProc")
    public String groupCreateProc(HttpServletRequest request, Model model) throws Exception {
        List<String> skusList = Arrays.asList("퓨리파잉_샴푸_250ml", "퓨리파잉_샴푸_1000ml", "누누_샴푸", "퓨리파잉_젤", "올인원밀크", "에너자이징_샴푸_250ml", "에너자이징_샴푸_1000ml", "에너자이징_젤", "디톡시파잉_샴푸_250ml", "디톡시파잉_샴푸_1000ml");
        List<String> selectSkuList = Arrays.asList(request.getParameterValues("sku"));
        List<Map<String, Object>> cList;

        String sql = "select o.ORDER_NO, b.BUYER_NO, b.BUYER_CALL, b.BUYER_NAME, b.BUYER_BIRTH, b.BUYER_ID, p.PRODUCT_NAME, o.ORDER_PAY_AMT,o .ORDER_PAY_DT, b.BUYER_REG_DT from Onrikorea.buyer b, Onrikorea.order o, Onrikorea.product p\n" +
                "where (\n" +
                "    o.BUYER_NO = b.BUYER_NO\n" +
                "    and\n" +
                "    b.BUYER_NO in(\n" +
                "        select BUYER_NO\n" +
                "        from Onrikorea.order\n" +
                "        where(\n" +
                "\t\t\tp.PRODUCT_ID = o.PRODUCT_ID\n" +
                "            and\n" +
                "            p.PRODUCT_ID in(\n" +
                "                select PRODUCT_ID\n" +
                "                from product\n" +
                "                where (PRODUCT_SKU = '다비네스') and (PRODUCT_CHANNEL = 'own')\n" +
                "                )\n" +
                "        )and (BUYER_NO is not null)\n" +
                "        )and BUYER_REG_CHANNEL = 'kakao'\n";


        for (String skus : skusList){
            for(String selectSku : selectSkuList){
                if(skus.equals(selectSku)){
                    sql += "and REPLACE(ORDER_OPTION,\" \",\"\") like '%" + selectSku.replaceAll("_", "") + "%' ";
                }else{
                    sql += "and REPLACE(ORDER_OPTION,\" \",\"\") not like '%" + skus.replaceAll("_", "") + "%' ";

                }
            }
        }



        sql += ")";

        System.out.println(sql);

        cList = jdbcService.select(sql);

        System.out.println(cList);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);

        Calendar calendar = Calendar.getInstance();
        String[] dates = sdf.format(new Date()).split("-");

        int year1 = Integer.parseInt(dates[0]);
        int month = Integer.parseInt(dates[1]) - 1;
        int day = Integer.parseInt(dates[2]);
        calendar.set(year1, month, day);

        String date = sdf.format(calendar.getTime());
        String fileId = "";

        Unirest.setTimeouts(0, 0);
        try {
            HttpResponse<String> createTempRespon = Unirest.post("https://kapi.kakao.com/v1/talkchannel/create/target_user_file")
                    .header("Authorization", "KakaoAK 76b7e9f841c80e27c2de73cd6aad4ff7")
                    .header("Content-Type", "application/json")
                    .body("{\"channel_public_id\": \"_RxgHmxb\",\"file_name\": \"" + date + "temp\",\"schema\":{ \"전화번호\":\"string\",  \"이름\":\"string\",  \"생년월일\":\"string\",  \"연령\":\"number\",  \"구매금액\":\"number\",  \"가입일\":\"string\",  \"최근구매일\":\"string\"}\r\n}")
                    .asString();
            JsonParser parser = new JsonParser();
            JsonObject fileObject = (JsonObject) parser.parse(createTempRespon.getBody());
            //fileId = fileObject.get("file_id").getAsString();

            JsonObject bodyObject = new JsonObject();
            bodyObject.add("file_id", parser.parse("15027"));
            bodyObject.add("channel_public_id", parser.parse("_RxgHmxb"));
            bodyObject.add("user_type", parser.parse("phone"));



            JsonArray userArray = new JsonArray();

            System.out.println(cList.size());

            for(int i=0; i < cList.size(); i++){
                JsonObject userObject = new JsonObject();
                userObject.add("id",parser.parse(cList.get(i).get("BUYER_CALL").toString()));

                String birthDay = (String) cList.get(i).get("BUYER_BIRTH");

               System.out.println(birthDay);
                int age = 0;
                if(birthDay != null){
                    age = (year1 - parseInt(birthDay.substring(0, 4))) + 1;
                }
                System.out.println(age);

                JsonObject fieldObject = new JsonObject();
                fieldObject.add("이름",parser.parse(cList.get(i).get("BUYER_NAME").toString()));
                fieldObject.add("생년월일",parser.parse(cList.get(i).get(birthDay).toString()));
                fieldObject.add("연령",parser.parse(cList.get(i).get("BUYER_BIRTH").toString()));
                fieldObject.add("구매금액",parser.parse(cList.get(i).get("ORDER_PAY_AMT").toString()));
                fieldObject.add("가입일",parser.parse(cList.get(i).get("BUYER_REG_DT").toString()));
                fieldObject.add("최근구매일",parser.parse(cList.get(i).get("ORDER_PAY_DT").toString()));

                userObject.add("field", fieldObject);
                userArray.add(userObject);
            }

            bodyObject.add("users", userArray);

            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.post("https://kapi.kakao.com/v1/talkchannel/update/target_users")
                    .header("Authorization", "KakaoAK 76b7e9f841c80e27c2de73cd6aad4ff7")
                    .header("Content-Type", "application/json")
                    .body(bodyObject.toString())
                    .asString();
            System.out.println(response.getBody());
        } catch (UnirestException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }



        model.addAttribute("url", "/jinha/tempGroupCreate.do");

        return "/alert2";


    }
}