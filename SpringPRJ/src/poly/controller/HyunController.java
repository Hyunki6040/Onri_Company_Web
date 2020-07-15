package poly.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonParser;
import com.mysql.fabric.FabricCommunicationException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import poly.service.IHyunService;
import poly.util.CmmUtil;

import java.util.HashMap;

@Controller
public class HyunController {
    @Resource(name = "HyunService")
    private IHyunService brandService;
    /*
	@Resource(name = "AdminService")
	private IAdminService adminService;
	
	@RequestMapping(value = "/hyun/top01")
	public String top01() throws Exception {
		return "/hyun/top01";
	}
	
	@RequestMapping(value = "/hyun/top02")
	public String top02() throws Exception {
		return "/hyun/top02";
	}
	
	@RequestMapping(value = "/hyun/main_img01")
	public String main_img01() throws Exception {
		return "/hyun/main_img01";
	}
	
	@RequestMapping(value = "/hyun/main")
	public String main() throws Exception {
		return "/hyun/main";
	}
	
	@RequestMapping(value = "/hyun/img_button01")
	public String img_button01() throws Exception {
		return "/hyun/img_button01";
	}
	
	@RequestMapping(value = "/hyun/navigation_slide")
	public String navigation_slide() throws Exception {
		return "/hyun/navigation_slide";
	}
	
	@RequestMapping(value = "/hyun/test")
	public String test() throws Exception {
		return "/hyun/test";
	}
	
	@RequestMapping(value = "/hyun/test03")
	public String test03() throws Exception {
		return "/hyun/test03";
	}
	
	@RequestMapping(value = "/error_404")
	public String error_404() throws Exception {
		return "/error_404";
	}
	
	@RequestMapping(value = "/index")
	public String index() throws Exception {
		return "redirect:/TBC/main.do";
	}
	
	@RequestMapping(value = "/admin")
	public String login(HttpServletRequest request, Model model, HttpSession session) throws Exception {
		session.invalidate();
		return "/admin/login";
	}


*/
    @RequestMapping(value = "/brand/kakao_friends")
    public String KakaoFriendsCheck() throws Exception {

        return "/brand/KakaoFriendsCheck";
    }

    @RequestMapping(value = "brand/kakaoFriendsProc")
    public @ResponseBody HashMap<String, Object> kakaoFriendsProc(HttpServletRequest request, HttpSession session) throws Exception {
        HashMap<String, Object> hMap = new HashMap<>();
//        String friends_list = CmmUtil.nvl(request.getParameter("friends_list"));
//        String result = "";
//
//        String[] list = friends_list.split(",");
//        for(int i=0; i<list.length; i++){
//            HttpResponse<String> response = Unirest.get("https://kapi.kakao.com/v1/api/talk/plusfriends?target_id_type=user_id&target_id=" + list[i])
//                    .header("authorization", "KakaoAK e28da5d9214c471e9654416fd6117d34")
//                    .asString();
//
//            JsonParser kakao_Parser = new JsonParser();
//            JsonObject kakao_jsonObj = (JsonObject) kakao_Parser.parse(response.getBody());
//            JsonArray kakao_ObjArray = (JsonArray) kakao_jsonObj.get("plus_friends");
//
//            boolean check_channel = true;
//
//            for(int j = 0; j < kakao_ObjArray.size(); j++){
//                if(kakao_ObjArray.get(j).getAsJsonObject().get("plus_friend_uuid").getAsString().equals("@todaydeal") && kakao_ObjArray.get(j).getAsJsonObject().get("relation").getAsString().equals("ADDED")){
//
//                }
//            }
//        }
//
        return hMap;
    }

//    @RequestMapping(value = "/groupCreateProc")
//    public String groupCreateProc(HttpServletRequest request, Model model, HttpSession session) throws Exception {
//        List<String> skusList = Arrays.asList("퓨리파잉_샴푸_250ml", "퓨리파잉_샴푸_1000ml", "누누_샴푸", "퓨리파잉_젤", "올인원밀크", "에너자이징_샴푸_250ml", "에너자이징_샴푸_1000ml", "에너자이징_젤", "디톡시파잉_샴푸_250ml", "디톡시파잉_샴푸_1000ml");
//        List<String> selectSkuList = Arrays.asList(request.getParameterValues("sku"));
//        String file_name = "";
//        List<Map<String, Object>> cList;
//
//        String sql = "select o.ORDER_NO, b.BUYER_NO, b.BUYER_CALL, b.BUYER_NAME, b.BUYER_ID, p.PRODUCT_NAME, o.ORDER_PAY_AMT,o .ORDER_PAY_DT, b.BUYER_REG_DT from Onrikorea.buyer b, Onrikorea.order o, Onrikorea.product p\n" +
//                "where (\n" +
//                "    o.BUYER_NO = b.BUYER_NO\n" +
//                "    and\n" +
//                "    b.BUYER_NO in(\n" +
//                "        select BUYER_NO\n" +
//                "        from Onrikorea.order\n" +
//                "        where(\n" +
//                "\t\t\tp.PRODUCT_ID = o.PRODUCT_ID\n" +
//                "            and\n" +
//                "            p.PRODUCT_ID in(\n" +
//                "                select PRODUCT_ID\n" +
//                "                from product\n" +
//                "                where (PRODUCT_SKU = '다비네스') and (PRODUCT_CHANNEL = 'own')\n" +
//                "                )\n" +
//                "        )and (BUYER_NO is not null)\n" +
//                "        )and BUYER_REG_CHANNEL = 'kakao'\n";
//
//
//        for (String skus : skusList){
//            for(String selectSku : selectSkuList){
//                if(skus.equals(selectSku)){
//                    sql += "and REPLACE(ORDER_OPTION,\" \",\"\") like '%" + selectSku.replaceAll("_", "") + "%' ";
//                    if(file_name.length()>0){
//                        file_name += "&";
//                    }
//                    file_name += selectSku;
//                }else{
//                    sql += "and REPLACE(ORDER_OPTION,\" \",\"\") not like '%" + skus.replaceAll("_", "") + "%' ";
//                }
//            }
//        }
//
//
//
//        sql += ")";
//
//        cList = jdbcService.select(sql);
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
//
//        Calendar calendar = Calendar.getInstance();
//        String[] dates = sdf.format(new Date()).split("-");
//
//        int year1 = Integer.parseInt(dates[0]);
//        int month = Integer.parseInt(dates[1]) - 1;
//        int day = Integer.parseInt(dates[2]);
//        calendar.set(year1, month, day);
//
//        String date = sdf.format(calendar.getTime());
//        String fileId = "";
//
//        Unirest.setTimeouts(0, 0);
//        String failed_id = "";
//        try {
//            HttpResponse<String> createTempRespon = Unirest.post("https://kapi.kakao.com/v1/talkchannel/create/target_user_file")
//                    .header("Authorization", "KakaoAK 76b7e9f841c80e27c2de73cd6aad4ff7")
//                    .header("Content-Type", "application/json")
//                    .body("{\"channel_public_id\": \"_RxgHmxb\",\"file_name\": \"(" + date + ")temp_["+ file_name +"]\",\"schema\":{ \"앱유저아이디\":\"string\",  \"이름\":\"string\",  \"생년월일\":\"string\",  \"연령\":\"number\",  \"구매금액\":\"number\",  \"가입일\":\"string\",  \"최근구매일\":\"string\"}\r\n}")
//                    .asString();
//            JsonParser parser = new JsonParser();
//            JsonObject fileObject = (JsonObject) parser.parse(createTempRespon.getBody());
//            try{
////                fileId = fileObject.get("file_id").getAsString();
//                fileId = "15027";
//            }catch (NullPointerException e){
//                String code = fileObject.get("code").getAsString();
//
//                if(code.equals("-817")){
//                    model.addAttribute("msg", "이미 temp 그룹이 생성되어 있습니다.삭제 후 다시시도 해주세요!");
//                    model.addAttribute("url", "/brand/tempGroupCreate.do");
//
//                    return "/alert";
//                }else{
//                    model.addAttribute("msg", "실패했습니다. 테크팀에 문의해주세요.에러코드 : " + fileObject.get("msg").getAsString());
//                    model.addAttribute("url", "/brand/tempGroupCreate.do");
//
//                    return "/alert";
//                }
//            }
//
//
//            //생성
//            JsonObject bodyObject = new JsonObject();
//            bodyObject.add("file_id", parser.parse(fileId));
//            bodyObject.add("channel_public_id", parser.parse("_RxgHmxb"));
//            bodyObject.add("user_type", parser.parse("app"));
//
//            JsonArray userArray = new JsonArray();
//            List<List<String>> input_list = new ArrayList<>();
//
//            try {
//                FileInputStream file = new FileInputStream("/Users/robert/Project/db_server/excel/member_list.xlsx");
//
//                //FileInputStream file = new FileInputStream("/usr/local/HR/webapps/member_list.xlsx");
//                XSSFWorkbook workbook = new XSSFWorkbook(file);
//
//                int rowindex;
//                int columnindex;
//                //시트 수 (첫번째에만 존재하므로 0을 준다)
//                //만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
//                XSSFSheet sheet = workbook.getSheetAt(0);
//                //행의 수
//                int rows = sheet.getPhysicalNumberOfRows();
//
//            for(int i=0; i < cList.size(); i++){
//                String name = "";
//                String birth = "";
//                String regDT = "";
//
//                JsonObject userObject = new JsonObject();
//                userObject.add("id", parser.parse(cList.get(i).get("BUYER_ID").toString().replaceAll("kakao_", "")));
//
//                JsonObject fieldObject = new JsonObject();
//
//                    for (rowindex = 0; rowindex < rows; rowindex++) {
//                        //행을읽는다
//                        XSSFRow row = sheet.getRow(rowindex);
//                        if (row != null) {
//                            List<String> data_list = new ArrayList<>();
//                            for(int j=0; j<12; j++){
//                                data_list.add("");
//                            }
//                            //셀의 수
//                            int cells = row.getPhysicalNumberOfCells();
//                            boolean id_check = false;
//                            for (columnindex = 0; columnindex <= cells; columnindex++) {
//                                //셀값을 읽는다
//                                XSSFCell cell = row.getCell(columnindex);
//                                String value = "";
//                                //셀이 빈값일경우를 위한 널체크
//                                if (cell == null) {
//                                    value = "";
//                                } else {
//                                    if (HSSFDateUtil.isInternalDateFormat(cell.getCellStyle().getDataFormat())) {
//                                        value = sdf.format(cell.getDateCellValue());
//                                    }
//                                    // 기타
//                                    else {
//                                        //타입별로 내용 읽기
//                                        switch (cell.getCellType()) {
//                                            case XSSFCell.CELL_TYPE_FORMULA:
//                                                value = cell.getCellFormula();
//                                                break;
//                                            case XSSFCell.CELL_TYPE_NUMERIC:
//                                                //숫자 포맷 문자로 변환
//                                                cell.setCellType(XSSFCell.CELL_TYPE_STRING);
//                                                value = cell.getStringCellValue();
//                                                break;
//                                            case XSSFCell.CELL_TYPE_STRING:
//                                                value = cell.getStringCellValue() + "";
//                                                break;
//                                            case XSSFCell.CELL_TYPE_BLANK:
//                                                value = cell.getBooleanCellValue() + "";
//                                                break;
//                                            case XSSFCell.CELL_TYPE_ERROR:
//                                                value = cell.getErrorCellValue() + "";
//                                                break;
//                                        }
//                                    }
//
//                                }
//                                if(columnindex == 0){
//                                    value = value.replaceAll(" ", "").replaceAll("false", "").replaceAll(",", "");
//                                    if(validating(value).trim().equals(cList.get(i).get("BUYER_ID").toString().trim())){
//                                        id_check = true;
//                                    }else{
//                                        id_check = false;
//                                    }
//                                }
//
//                                if(id_check == true){
//                                    if(columnindex == 0) {
//                                        data_list.set(0, "%own%");//자사몰//input_buyer_channel
//                                        value = value.replaceAll(" ", "").replaceAll("false", "").replaceAll(",", "");
//                                        if (value.contains("kakao")) {
//                                            data_list.set(1, "kakao");//input_buyer_channel
//                                        } else {
//                                            data_list.set(1, "null");
//                                        }
//                                        data_list.set(2, validating(value)); //input_buyer_id
//                                    }else if (columnindex == 1) {
//                                        value = value.replaceAll(" ", "").replaceAll("false", "");
//                                        data_list.set(3, validating(value));//input_buyer_name
//                                        name = validating(value);
//                                    } else if (columnindex == 2) {
//                                        value = value.replaceAll(" ", "").replaceAll("false", "");
//                                        if (value.equals("남자")) {
//                                            data_list.set(4, "1");//input_buyer_sex
//                                        } else {
//                                            data_list.set(4, "0");
//                                        }
//                                    } else if (columnindex == 3) {
//                                        value = value.replaceAll(" ", "").replaceAll("false", "");
//                                        data_list.set(5, validating(value));//input_buyer_call
//                                    } else if (columnindex == 4) {
//                                        value = value.replaceAll(" ", "").replaceAll("false", "");
//                                        data_list.set(6, validating(value));//input_buyer_mail
//                                    } else if (columnindex == 5) {
//                                        value = value.replaceAll(" ", "").replaceAll("false", "").replaceAll("'", "");
//                                        data_list.set(7, validating(value));//input_buyer_address
//                                    } else if (columnindex == 6) {
//                                        value = value.replaceAll(" ", "").replaceAll("false", "");
//                                        if (value.equals("n")) {
//                                            data_list.set(8, "0");//input_buyer_opt_in
//                                        } else if (value.equals("y")) {
//                                            data_list.set(8, "1");
//                                        }
//                                    } else if (columnindex == 7) {
//                                        value = value.replaceAll(" ", "").replaceAll("false", "");
//                                        data_list.set(9, validating(value));//input_buyer_birth
//                                        birth = validating(value);
//                                    } else if (columnindex == 8) {
//                                        value = value.replaceAll(" ", "").replaceAll("false", "");
//                                        data_list.set(10, validating(value));//input_buyer_reg_dt
//                                        regDT = validating(value);
//                                    } else if (columnindex == 9) {
//                                        value = value.replaceAll(" ", "").replaceAll("false", "");
//                                        data_list.set(11, validating(value));//input_buyer_reg_dt
//                                    }
//
//                                    if(columnindex == cells){
//                                        rowindex = rows;
//                                        id_check = false;
//                                        data_list.add("call update_own_buyer(?,?,?,?,?,?,?,?,?,?,?,?)");
//                                        input_list.add(data_list);
//                                    }
//
//                                }else{
//                                    columnindex = cells + 1;
//                                }
//
//                            }
//                            if(rowindex == rows - 1 && id_check == false){
//                                failed_id += cList.get(i).get("BUYER_ID").toString() + " // ";
//                            }
//                        }
//                    }
//
//
//
//                int age = 0;
//                fieldObject.add("이름",parser.parse(name.replaceAll(":","").replaceAll(",","")));
//                if(birth != null && birth.length() > 4){
//                    age = (year1 - parseInt(birth.substring(0, 4))) + 1;
//                    fieldObject.add("생년월일",parser.parse(birth));
//                }
//
//                fieldObject.add("연령",parser.parse(age + ""));
//                fieldObject.add("구매금액",parser.parse(cList.get(i).get("ORDER_PAY_AMT").toString()));
//                fieldObject.add("가입일",parser.parse(regDT));
//                fieldObject.add("최근구매일",parser.parse(cList.get(i).get("ORDER_PAY_DT").toString()));
//
//                userObject.add("field", fieldObject);
//                userArray.add(userObject);
//            }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                model.addAttribute("msg", "실패했습니다. 테크팀에 문의해주세요" + e);
//                model.addAttribute("url", "/brand/tempGroupCreate.do");
//                return "/alert";
//            }
//            //jdbcService.insert(input_list);
//            List<List<String>> uList = (List<List<String>>) session.getAttribute("user_update_list");
//            if(uList != null){
//                uList.addAll(input_list);
//            }else{
//                session.setAttribute("user_update_list", input_list);
//            }
//
//
//            bodyObject.add("users", userArray);
//
//            Unirest.setTimeouts(0, 0);
//            HttpResponse<String> response = Unirest.post("https://kapi.kakao.com/v1/talkchannel/update/target_users")
//                    .header("Authorization", "KakaoAK 76b7e9f841c80e27c2de73cd6aad4ff7")
//                    .header("Content-Type", "application/json")
//                    .body(bodyObject.toString())
//                    .asString();
//        }catch (Exception e){
//            e.printStackTrace();
//            model.addAttribute("msg", "실패했습니다. 테크팀에 문의해주세요" + e);
//            model.addAttribute("url", "/brand/tempGroupCreate.do");
//            return "/alert";
//        }
//
//        if(failed_id.length()>0){
//            model.addAttribute("msg", "실패한 아이디가 있습니다. 테크팀에 문의해주세요" );
//            model.addAttribute("url", "/brand/tempGroupCreate.do?failed_id=" + failed_id);
//            return "/alert";
//        }
//
//        model.addAttribute("url", "/brand/tempGroupCreate.do");
//        return "/alert2";
//    }

}
