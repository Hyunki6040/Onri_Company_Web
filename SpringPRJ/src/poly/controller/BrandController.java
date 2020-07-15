package poly.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpRequest;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import poly.util.CmmUtil;
import poly.util.jdbcService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Integer.parseInt;
import static poly.util.jdbcService.validating;

@Controller
@RequestMapping(value = "/brand/*")
public class BrandController {
    @RequestMapping(value = "/tempGroupCreate")
    public String kakoGroup() throws Exception {

        return "/brand/tempGroupCreate";
    }

    @RequestMapping(value = "/userUpdate")
    public @ResponseBody HashMap<String, Object> userUpdate(HttpSession session, HttpServletRequest request) throws Exception {
            HashMap<String, Object> hMap = new HashMap<>();
            try {
                int start_num = 0;
                List<List<String>> input_list = (List<List<String>>) session.getAttribute("user_update_list");

                int total_num = Integer.parseInt(CmmUtil.nvl(request.getParameter("size")));
                if(input_list.size() == 0){
                    hMap.put("msg", "end");
                }else {
                    int finish_num = input_list.size();
                    int end_num = start_num + 2; //갯수 조절
                    List<List<String>> output_list = new ArrayList<>();

                    if(end_num > finish_num){
                        end_num = finish_num;
                        hMap.put("msg", "end");
                    }

                    for (int k=start_num; k<end_num; k++){
                        output_list.add(input_list.get(k));
                        input_list.remove(k);
                    }

                    jdbcService.insert(output_list);
                    session.removeAttribute("user_update_list");
                    if(output_list.size() != 0){
                        session.setAttribute("user_update_list", input_list);
                    }

                    hMap.put("msg", "sucess");
                    if(total_num-finish_num>0 && total_num>0){
                        hMap.put("value", (int) (total_num-finish_num)*100/total_num);

                        if((total_num-finish_num)*100/total_num == 100){
                            hMap.put("msg", "end");
                        }
                    }else {
                        hMap.put("value", 0);
                    }
                }

            }catch (Exception e) {
                hMap.put("msg", "fail"+ e);
            }

            return hMap;
        }

    @RequestMapping(value = "/groupCreateProc")
    public String groupCreateProc(HttpServletRequest request, Model model, HttpSession session) throws Exception {
//        List<String> skusList = Arrays.asList("퓨리파잉_샴푸_250ml", "퓨리파잉_샴푸_1000ml", "누누_샴푸", "퓨리파잉_젤", "올인원밀크", "에너자이징_샴푸_250ml", "에너자이징_샴푸_1000ml", "에너자이징_젤", "디톡시파잉_샴푸_250ml", "디톡시파잉_샴푸_1000ml");
        List<String> selectSkuList = null;
        if(request.getParameterValues("sku") != null){
            selectSkuList = Arrays.asList(request.getParameterValues("sku"));
        }
        List<String> unselectSkuList = null;
        if(request.getParameterValues("nonSku") != null){
            unselectSkuList = Arrays.asList(request.getParameterValues("nonSku"));
        }

        String brandName = CmmUtil.nvl(request.getParameter("brandName"));

        System.out.println(selectSkuList);

        String file_name = "";
        List<Map<String, Object>> cList;

        String sql = "select  BUYER_NO, BUYER_CALL, BUYER_NAME, BUYER_BIRTH, BUYER_ID, PRODUCT_NAME, sum(ORDER_PAY_AMT) as ORDER_PAY_AMT, ORDER_PAY_DT, BUYER_REG_DT, group_concat(ORDER_OPTION) as ORDER_OPTION from (" +
                "select o.ORDER_NO, b.BUYER_NO, b.BUYER_CALL, b.BUYER_NAME, b.BUYER_BIRTH, b.BUYER_ID, p.PRODUCT_NAME, o.ORDER_PAY_AMT, o.ORDER_PAY_DT, b.BUYER_REG_DT, o.ORDER_OPTION from Onrikorea.buyer b, Onrikorea.order o, Onrikorea.product p\n" +
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
                "                where (PRODUCT_SKU = '" + brandName + "') and (PRODUCT_CHANNEL = 'own')\n" +
                "                )\n" +
                "        )and (BUYER_NO is not null)\n" +
                "        )and BUYER_REG_CHANNEL = 'kakao'\n";


        if(selectSkuList != null){
            for(String selectSku : selectSkuList){
                sql += "and REPLACE(ORDER_OPTION,\" \",\"\") like '%" + selectSku.replaceAll("_", "") + "%' ";
                if(file_name.length()>0){
                    file_name += " & ";
                }
                file_name += selectSku;
            }
        }

        if(unselectSkuList != null){
            for(String unselectSku : unselectSkuList){
                sql += "and REPLACE(ORDER_OPTION,\" \",\"\") not like '%" + unselectSku.replaceAll("_", "") + "%' ";
                if(file_name.length()>0){
                    file_name += " & !";
                }
                file_name += unselectSku;
            }
        }

        sql += ")\n" +
                "order by o.ORDER_PAY_DT asc, b.BUYER_REG_DT desc\n" +
                ")a\n" +
                "group by BUYER_NO;";

        cList = jdbcService.select(sql);

        System.out.println(sql);

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
        String failed_id = "";
        try {
            HttpResponse<String> createTempRespon = Unirest.post("https://kapi.kakao.com/v1/talkchannel/create/target_user_file")
                    .header("Authorization", "KakaoAK 76b7e9f841c80e27c2de73cd6aad4ff7")
                    .header("Content-Type", "application/json")
                    .body("{\"channel_public_id\": \"_RxgHmxb\",\"file_name\": \"(" + date + ")temp4_["+ file_name +"]\",\"schema\":{ \"앱유저아이디\":\"string\",  \"이름\":\"string\",  \"생년월일\":\"string\",  \"연령\":\"number\",  \"구매금액\":\"number\",  \"가입일\":\"string\",  \"최근구매일\":\"string\"}\r\n}")
                    .asString();
            JsonParser parser = new JsonParser();
            JsonObject fileObject = (JsonObject) parser.parse(createTempRespon.getBody());
            try{
//                fileId = fileObject.get("file_id").getAsString();
                fileId = "15369";
            }catch (NullPointerException e){
                String code = fileObject.get("code").getAsString();

                if(code.equals("-817")){
                    model.addAttribute("msg", "이미 temp 그룹이 생성되어 있습니다.삭제 후 다시시도 해주세요!");
                    model.addAttribute("url", "/brand/tempGroupCreate.do");

                    return "/alert";
                }else{
                    model.addAttribute("msg", "실패했습니다. 테크팀에 문의해주세요.에러코드 : " + fileObject.get("msg").getAsString());
                    model.addAttribute("url", "/brand/tempGroupCreate.do");

                    return "/alert";
                }
            }


            //생성
            JsonObject bodyObject = new JsonObject();
            bodyObject.add("file_id", parser.parse(fileId));
            bodyObject.add("channel_public_id", parser.parse("_RxgHmxb"));
            bodyObject.add("user_type", parser.parse("app"));

            JsonArray userArray = new JsonArray();
            List<List<String>> input_list = new ArrayList<>();

            try {
                FileInputStream file = new FileInputStream("/Users/robert/Project/db_server/excel/member_list_kakao.xlsx");
//                FileInputStream file = new FileInputStream("/usr/local/HR/webapps/member_list_kakao.xlsx");
                XSSFWorkbook workbook = new XSSFWorkbook(file);

                int rowindex;
                int columnindex;
                //시트 수 (첫번째에만 존재하므로 0을 준다)
                //만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
                XSSFSheet sheet = workbook.getSheetAt(0);
                //행의 수
                int rows = sheet.getPhysicalNumberOfRows();


                for(int i=0; i < cList.size(); i++) {
                    String name = "";
                    String birth = "";
                    String regDT = "";
                    boolean excel_check = false;

                    JsonObject fieldObject = new JsonObject();
                    JsonObject userObject = new JsonObject();

                    if (cList.get(i).get("BUYER_ID") != null && cList.get(i).get("BUYER_ID").toString().contains("kakao_")) {
                        try {
                            String kakao_id = cList.get(i).get("BUYER_ID").toString().replaceAll("kakao_", "");


                            userObject.add("id", parser.parse(kakao_id));

                            HttpResponse<String> info_response = Unirest.get("https://kapi.kakao.com/v2/user/me?target_id_type=user_id&target_id=" + kakao_id)
                                    .header("authorization", "KakaoAK e28da5d9214c471e9654416fd6117d34")
                                    .header("cache-control", "no-cache")
                                    .header("postman-token", "b154dacf-cabc-b594-6173-990dcb3a581d")
                                    .asString();

                            JsonParser infoParser = new JsonParser();
                            JsonObject infoObject = (JsonObject) infoParser.parse(info_response.getBody());
                            JsonObject detailObject = infoObject.get("kakao_account").getAsJsonObject();

                            List<String> data_list = new ArrayList<>();
                            for (int j = 0; j < 12; j++) {
                                data_list.add("");
                            }

                            data_list.set(0, "%own%");//자사몰//input_buyer_channel
                            data_list.set(1, "kakao");//input_buyer_channel
                            data_list.set(2, "kakao_" + validating(infoObject.get("id").getAsString())); //input_buyer_id
                            name = validating(infoObject.get("properties").getAsJsonObject().get("nickname").getAsString());
                            data_list.set(3, name);//input_buyer_name
                            if (detailObject.get("gender").getAsString().equals("남자")) {
                                data_list.set(4, "1");//input_buyer_sex
                            }else {
                                data_list.set(4, "0");
                            }
                            if (detailObject.get("phone_number") != null) {
                                data_list.set(5, validating(detailObject.get("phone_number").getAsString()).trim().replaceAll("\\+82", "0"));//input_buyer_call
                            }else {
                                data_list.set(5, "null");
                            }

                            if (detailObject.get("email") != null) {
                                data_list.set(6, validating(detailObject.get("email").getAsString()));//input_buyer_mail
                            }else {
                                data_list.set(6, "null");
                            }
                            data_list.set(7, validating("null"));//input_buyer_address
                            data_list.set(8, "3");//input_buyer_opt_in
                            birth = validating(detailObject.get("birthyear").getAsString()) + "-" + validating(detailObject.get("birthday").getAsString()).substring(0, 1) + "-" + validating(detailObject.get("birthday").getAsString()).substring(2, 3);
                            data_list.set(9, validating(birth));//input_buyer_birth

                            regDT = validating(infoObject.get("connected_at").getAsString().substring(0, 10));
                            data_list.set(10, regDT);//input_buyer_reg_dt
                            data_list.set(11, regDT);//input_buyer_reg_dt

                            data_list.add("call update_own_buyer(?,?,?,?,?,?,?,?,?,?,?,?)");
                            input_list.add(data_list);

                            //                failed_id += cList.get(i).get("BUYER_ID").toString() + " // ";
                        }catch (NullPointerException e){
                            excel_check = true;
                        }
                    }else {
                        excel_check = true;
                    }

                    if(excel_check == true) {
                        for (rowindex = 0; rowindex < rows; rowindex++) {
                            //행을읽는다
                            XSSFRow row = sheet.getRow(rowindex);
                            if (row != null) {
                                List<String> data_list = new ArrayList<>();
                                for (int j = 0; j < 12; j++) {
                                    data_list.add("");
                                }
                                //셀의 수
                                int cells = row.getPhysicalNumberOfCells();
                                boolean id_check;
                                XSSFCell call_cell = row.getCell(3);
                                String call_value = jdbcService.XSSFCell_value(call_cell);

                                call_value = call_value.replaceAll(" ", "").replaceAll("false", "").replaceAll(",", "");
                                if (validating(call_value).trim().equals(cList.get(i).get("BUYER_CALL").toString().trim())) {
                                    id_check = true;
                                } else {
                                    id_check = false;
                                }

                                for (columnindex = 0; columnindex <= cells; columnindex++) {
                                    //셀값을 읽는다
                                    XSSFCell cell = row.getCell(columnindex);
                                    String value = jdbcService.XSSFCell_value(cell);

                                    if (id_check == true) {
                                        if (columnindex == 0) {
                                            data_list.set(0, "%own%");//자사몰//input_buyer_channel
                                            value = value.replaceAll(" ", "").replaceAll("false", "").replaceAll(",", "");
                                            if (value.contains("kakao")) {
                                                data_list.set(1, "kakao");//input_buyer_channel
                                            } else {
                                                data_list.set(1, "null");
                                            }
                                            data_list.set(2, value); //input_buyer_id
                                            userObject.add("id", parser.parse(value.trim().replaceAll("kakao_","")));

                                        } else if (columnindex == 1) {
                                            value = value.replaceAll(" ", "").replaceAll("false", "");
                                            data_list.set(3, validating(value));//input_buyer_name
                                            name = validating(value);
                                        } else if (columnindex == 2) {
                                            value = value.replaceAll(" ", "").replaceAll("false", "");
                                            if (value.equals("남자")) {
                                                data_list.set(4, "1");//input_buyer_sex
                                            } else {
                                                data_list.set(4, "0");
                                            }
                                        } else if (columnindex == 3) {
                                            value = value.replaceAll(" ", "").replaceAll("false", "");
                                            data_list.set(5, validating(value));//input_buyer_call
                                        } else if (columnindex == 4) {
                                            value = value.replaceAll(" ", "").replaceAll("false", "");
                                            data_list.set(6, validating(value));//input_buyer_mail
                                        } else if (columnindex == 5) {
                                            value = value.replaceAll(" ", "").replaceAll("false", "").replaceAll("'", "");
                                            data_list.set(7, validating(value));//input_buyer_address
                                        } else if (columnindex == 6) {
                                            value = value.replaceAll(" ", "").replaceAll("false", "");
                                            if (value.equals("n")) {
                                                data_list.set(8, "0");//input_buyer_opt_in
                                            } else if (value.equals("y")) {
                                                data_list.set(8, "1");
                                            }
                                        } else if (columnindex == 7) {
                                            value = value.replaceAll(" ", "").replaceAll("false", "");
                                            data_list.set(9, validating(value));//input_buyer_birth
                                            birth = validating(value);
                                        } else if (columnindex == 8) {
                                            value = value.replaceAll(" ", "").replaceAll("false", "");
                                            data_list.set(10, validating(value));//input_buyer_reg_dt
                                            regDT = validating(value);
                                        } else if (columnindex == 9) {
                                            value = value.replaceAll(" ", "").replaceAll("false", "");
                                            data_list.set(11, validating(value));//input_buyer_reg_dt
                                        }

                                        if (columnindex == cells) {
                                            rowindex = rows;
                                            id_check = false;
                                            data_list.add("call update_own_buyer(?,?,?,?,?,?,?,?,?,?,?,?)");
                                            input_list.add(data_list);
                                        }

                                    } else {
                                        columnindex = cells + 1;
                                    }

                                }
                                if (rowindex == rows - 1 && id_check == false) {
                                    if(cList.get(i).get("BUYER_ID").toString().contains("kakao_")){
                                        userObject.add("id", parser.parse(cList.get(i).get("BUYER_ID").toString().trim().replaceAll("kakao_","")));
                                        name = cList.get(i).get("BUYER_NAME").toString();
                                        birth = cList.get(i).get("BUYER_BIRTH").toString();
                                        regDT = cList.get(i).get("BUYER_REG_DT").toString();
                                    }else {
                                        failed_id += cList.get(i).get("BUYER_ID").toString() + " // ";
                                    }
                                }
                            }
                        }

                    }
                    int age = 0;

                    if(name == null || name.replaceAll("null","none").equals("")){
                        name = "none";
                    }
                    if(regDT == null || regDT.replaceAll("null","none").equals("")){
                        regDT = "none";
                    }

                    fieldObject.add("이름", parser.parse("\"" + name.trim().replaceAll(":", "").replaceAll(",", "").replaceAll("null","none") + "\""));
                    if (birth != null && birth.length() > 4) {
                        age = (year1 - parseInt(birth.substring(0, 4))) + 1;
                        fieldObject.add("생년월일", parser.parse(birth));
                    }
                    fieldObject.add("연령", parser.parse(age + ""));
                    fieldObject.add("구매금액", parser.parse(cList.get(i).get("ORDER_PAY_AMT").toString()));
                    fieldObject.add("가입일", parser.parse(regDT.replaceAll("null","none")));
                    fieldObject.add("최근구매일", parser.parse(cList.get(i).get("ORDER_PAY_DT").toString()));

                    userObject.add("field", fieldObject);
                    userArray.add(userObject);
                }
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("msg", "실패했습니다. 테크팀에 문의해주세요" + e);
                model.addAttribute("url", "/brand/tempGroupCreate.do");
                return "/alert";
            }
            //jdbcService.insert(input_list);
            List<List<String>> uList = (List<List<String>>) session.getAttribute("user_update_list");
            if(uList != null){
                uList.addAll(input_list);
            }else{
                session.setAttribute("user_update_list", input_list);
            }


            bodyObject.add("users", userArray);

            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.post("https://kapi.kakao.com/v1/talkchannel/update/target_users")
                    .header("Authorization", "KakaoAK 76b7e9f841c80e27c2de73cd6aad4ff7")
                    .header("Content-Type", "application/json")
                    .body(bodyObject.toString())
                    .asString();

            System.out.println(bodyObject.toString());
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("msg", "실패했습니다. 테크팀에 문의해주세요" + e);
            model.addAttribute("url", "/brand/tempGroupCreate.do");
            return "/alert";
        }

        if(failed_id.length()>0){
            model.addAttribute("msg", "실패한 아이디가 있습니다. 테크팀에 문의해주세요" );
            model.addAttribute("url", "/brand/tempGroupCreate.do?failed_id=" + failed_id);
            return "/alert";
        }

        model.addAttribute("url", "/brand/tempGroupCreate.do");
        return "/alert2";
    }
}
