package poly.util;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

public class jdbcService {
    public static Connection connect() {
        //jdbc connection

        // Connection 객체를 자동완성으로 import할 때는 com.mysql.connection이 아닌
        // java 표준인 java.sql.Connection 클래스를 import해야 한다.
        Connection conn = null;

        try {
            // 1. 드라이버 로딩
            // 드라이버 인터페이스를 구현한 클래스를 로딩
            // mysql, oracle 등 각 벤더사 마다 클래스 이름이 다르다.
            // mysql은 "com.mysql.jdbc.Driver"이며, 이는 외우는 것이 아니라 구글링하면 된다.
            // 참고로 이전에 연동했던 jar 파일을 보면 com.mysql.jdbc 패키지에 Driver 라는 클래스가 있다.
            Class.forName("com.mysql.jdbc.Driver");

            // 2. 연결하기
            // 드라이버 매니저에게 Connection 객체를 달라고 요청한다.
            // Connection을 얻기 위해 필요한 url 역시, 벤더사마다 다르다.
            // mysql은 "jdbc:mysql://localhost/사용할db이름" 이다.
            String url = "jdbc:mysql://59.10.213.40:3306/Onrikorea?characterEncoding=utf8&amp;useSSL=false&amp;autoReconnection=true";

            // @param  getConnection(url, userName, password);
            // @return Connection
            conn = DriverManager.getConnection(url, "onridb", "dbonrikorea00!");
        } catch (Exception e) {
            System.out.println("error!, jdbc connection fail : " + e);
        }

        return conn;
    }

    public static List<Map<String, Object>>  select(String sql) {
        //jdbc connection

        // Connection 객체를 자동완성으로 import할 때는 com.mysql.connection이 아닌
        // java 표준인 java.sql.Connection 클래스를 import해야 한다.
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs;
        List<Map<String, Object>> list = new ArrayList<>();

        try {
            //jdbc 연결
            conn = connect();

            // 3. 쿼리 수행을 위한 Statement 객체 생성
            stmt = conn.createStatement();

            rs = stmt.executeQuery(sql);

            // ResultSet 의 MetaData를 가져온다.
            ResultSetMetaData metaData;
            metaData = rs.getMetaData();

            // ResultSet 의 Column의 갯수를 가져온다.
            int sizeOfColumn = metaData.getColumnCount();


            Map<String, Object> map;
            String column;
            // rs의 내용을 돌려준다.
            while (rs.next()) {
                // 내부에서 map을 초기화
                map = new HashMap<>();
                // Column의 갯수만큼 회전
                for (int indexOfcolumn = 0; indexOfcolumn < sizeOfColumn; indexOfcolumn++) {
                    column = metaData.getColumnName(indexOfcolumn + 1);
                    // map에 값을 입력 map.put(columnName, columnName으로 getString)

                    map.put(column, rs.getString(column));
                }
                // list에 저장
                list.add(map);
            }

        } catch (SQLException e) {
            System.out.println("error! " + "sql_error " + e);
            e.printStackTrace();
        } catch (NullPointerException e2) {
            System.out.println("서버가 연결되어있지 않습니다.");
            System.out.println("error! //fail to connect server " + e2);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
                if (stmt != null && !stmt.isClosed()) {
                    stmt.close();
                }

            } catch (SQLException e) {
                System.out.println("error! SQL_FAIL " + e);
                e.printStackTrace();
            }
        }

        return list;

    }

    /*public static void update(String sql, List<String> parm) {
        //sql은 쿼리문, parm은 ?에 들어갈 파라미터들
        //jdbc connection

        // Connection 객체를 자동완성으로 import할 때는 com.mysql.connection이 아닌
        // java 표준인 java.sql.Connection 클래스를 import해야 한다.
        Connection conn = null;
        CallableStatement cstmt = null;
        String log = "";
        try {
            //jdbc 연결
            conn = connect();

            cstmt = conn.prepareCall(sql);
            for (int j = 0; j < parm.size(); j++){
                if(!JSONUtils.isJSONValid(parm.get(j).toString())){
                    cstmt.setString(j+1,parm.get(j).replaceAll("\"", "").replaceAll("'", ""));
                }
                log += " " + parm.get(j);
            }

            //insert 실행
            cstmt.execute();

        } catch (SQLException e) {
            System.out.println("error! //first : " + parm.get(0) + "sql_error " + e + "\n" + sql + "\n" + log);
        } catch (NullPointerException e2){
            System.out.println("서버가 연결되어있지 않습니다.");
            System.out.println("error! //fail to connect server " + e2);
        }finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
                if( cstmt != null && !cstmt.isClosed()){
                    cstmt.close();
                }
            } catch (SQLException e) {
                System.out.println("error! SQL_FAIL //first : " + parm.get(0) + e);
                e.printStackTrace();
            }
        }
    }*/

    public static void insert(String sql, List<String> parm) {
        //sql은 쿼리문, parm은 ?에 들어갈 파라미터들
        //jdbc connection

        // Connection 객체를 자동완성으로 import할 때는 com.mysql.connection이 아닌
        // java 표준인 java.sql.Connection 클래스를 import해야 한다.
        Connection conn = null;
        PreparedStatement pstmt = null;
        String quyery = null;
        try {
            //jdbc 연결
            conn = connect();

            //pstmt = conn.prepareStatement(sql);
            pstmt = new LoggableStatement(conn, sql);

            if (parm.size() > 0) {
                for (int i = 0; i < parm.size(); i++) {
                    pstmt.setString(i + 1, parm.get(i).replaceAll("\"", "").replaceAll("'", "").replaceAll(" ", " "));
                }

                System.out.println(sql);
                System.out.println(parm);

                int count = pstmt.executeUpdate();
                quyery = ((LoggableStatement) pstmt).getQueryString();
                System.out.println("\t sQuery ? " + quyery + "\n");
                if (count == 0) {
                    System.out.println("error! fail to insert //first : " + parm.get(0) + "\n sQuery ? " + quyery + "\n");
                }
            }

        } catch (SQLException e) {
            System.out.println("\t sQuery ? " + quyery + "\n");
            System.out.println("error! " + "sql_error " + e + "\n" + sql + "\n sQuery ? " + quyery + "\n");
        } catch (NullPointerException e2) {
            System.out.println("서버가 연결되어있지 않습니다.");
            System.out.println("error! //fail to connect server " + e2 + "\n sQuery ? " + quyery + "\n");
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
                if (pstmt != null && !pstmt.isClosed()) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.out.println("error! SQL_FAIL " + e + "\n sQuery ? " + quyery + "\n");
                e.printStackTrace();
            }
        }
    }

    public static void insert(List<List<String>> parm) {
        //sql은 쿼리문, parm은 ?에 들어갈 파라미터들
        //jdbc connection

        // Connection 객체를 자동완성으로 import할 때는 com.mysql.connection이 아닌
        // java 표준인 java.sql.Connection 클래스를 import해야 한다.
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "";
        String quyery = null;
        int j = 0;
        try {
            //jdbc 연결
            conn = connect();
            for (j = 0; j < parm.size(); j++) {
                sql = parm.get(j).get(parm.get(j).size() - 1);
                //pstmt = conn.prepareStatement(sql);
                pstmt = new LoggableStatement(conn, sql);

                if (parm.get(j).size() > 0) {
                    for (int i = 0; i < parm.get(j).size() - 1; i++) {
                        pstmt.setString(i + 1, validating(parm.get(j).get(i)).replaceAll("\"", "").replaceAll("'", "").replaceAll(" ", " "));
                        pstmt.setString(i + 1, parm.get(j).get(i).replaceAll("\"", "").replaceAll("'", "").replaceAll(" ", " "));
                    }

                    int count = pstmt.executeUpdate();
                    quyery = ((LoggableStatement) pstmt).getQueryString();
                    System.out.println("\t sQuery ? " + quyery + "\n");
                    if (count == 0) {
                        System.out.println("error! fail to insert //first : " + parm.get(j).get(0) + "\n sQuery ? " + quyery + "\n");
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("\t sQuery ? " + quyery + "\n");
            System.out.println("error! " + "sql_error " + e + "\n" + sql + "\n sQuery ? " + quyery + "\n");
            int k = 0;
            for (Iterator<List<String>> it = parm.iterator(); it.hasNext(); ) {
                List<String> value = it.next();
                if (k < j) {
                    it.remove();
                }
                k++;
            }
            insert(parm);
        } catch (NullPointerException e2) {
            System.out.println("서버가 연결되어있지 않습니다.");
            System.out.println("error! //fail to connect server " + e2 + "\n sQuery ? " + quyery + "\n");

        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
                if (pstmt != null && !pstmt.isClosed()) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.out.println("error! SQL_FAIL " + e + "\n sQuery ? " + quyery + "\n");
                e.printStackTrace();
            }
        }
    }

//    public static void update(String sql, List<String> parm) {
//        //sql은 쿼리문, parm은 ?에 들어갈 파라미터들
//        //jdbc connection
//
//        // Connection 객체를 자동완성으로 import할 때는 com.mysql.connection이 아닌
//        // java 표준인 java.sql.Connection 클래스를 import해야 한다.
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        String quyery = null;
//        try {
//            //jdbc 연결
//            conn = connect();
//
//            //pstmt = conn.prepareStatement(sql);
//            pstmt = new LoggableStatement(conn, sql);
//
//            if (parm.size() > 0) {
//                for (int i = 0; i < parm.size(); i++) {
//                    if (JSONUtils.isJSONValid(parm.get(i))) {
//                        pstmt.setString(i + 1, parm.get(i).replaceAll("'", "").replaceAll(" ", " "));
//
//                    } else {
//                        pstmt.setString(i + 1, parm.get(i).replaceAll("\"", "").replaceAll("'", "").replaceAll(" ", " "));
//
//                    }
//                }
//
//                int count = pstmt.executeUpdate();
//                quyery = ((LoggableStatement) pstmt).getQueryString();
//                System.out.println("\t sQuery ? " + quyery + "\n");
//                if (count == 0) {
//                    System.out.println("error! fail to insert //first : " + parm.get(0) + "\n" + "\t sQuery ? " + quyery + "\n");
//                }
//            }
//
//        } catch (SQLException e) {
//            System.out.println("\t sQuery ? " + quyery + "\n");
//            System.out.println("error! " + "sql_error " + e + "\n" + sql + "\n sQuery ? " + quyery + "\n");
//        } catch (NullPointerException e2) {
//            System.out.println("서버가 연결되어있지 않습니다.");
//            System.out.println("error! //fail to connect server " + e2 + "\n sQuery ? " + quyery + "\n");
//        } finally {
//            try {
//                if (conn != null && !conn.isClosed()) {
//                    conn.close();
//                }
//                if (pstmt != null && !pstmt.isClosed()) {
//                    pstmt.close();
//                }
//            } catch (SQLException e) {
//                System.out.println("error! SQL_FAIL " + e + "\n sQuery ? " + quyery + "\n");
//                e.printStackTrace();
//            }
//        }
//
//    }

    public static String oderToJSON(String order_name, String order_call, String order_address) {
        return "{order_name:\"" + order_name + "\",order_call:\"" + order_call + "\",order_address:\"" + order_address + "\"}";
    }

    // tag값의 정보를 가져오는 메소드
    public static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = nlList.item(0);
        if (nValue == null)
            return null;
        return nValue.getNodeValue();
    }

    public static String getSKU(String prdNm) {
        if (prdNm.contains("점프업") || prdNm.contains("하이정")) {
            prdNm = "점프업하이정";
        } else if (prdNm.contains("호박") && prdNm.contains("마스크")) {
            prdNm = "호박마스크팩";
        } else if (prdNm.contains("호박")) {
            prdNm = "호박앰플";
        } else if (prdNm.contains("테일러") || prdNm.contains("푸룬") || prdNm.contains("젤리")) {
            prdNm = "테일러푸룬젤리";
        } else if (prdNm.contains("팍팍") || prdNm.contains("콜라겐")) {
            prdNm = "팍팍콜라겐";
        } else if (prdNm.contains("아리얼") || prdNm.contains("리무버")) {
            prdNm = "아리얼패드";
        } else if (prdNm.contains("디어달리아") || prdNm.contains("쿠션") || prdNm.contains("블루밍")) {
            prdNm = "디어달리아";
        } else if (prdNm.contains("바이위시트렌")) {
            prdNm = "바이위시트렌드";
        } else if (prdNm.contains("다비네스")) {
            prdNm = "다비네스";
        } else if (prdNm.contains("아비토젠")) {
            prdNm = "아비토젠";
        } else if (prdNm.contains("테라브레스") || prdNm.contains("태라") || prdNm.contains("브래스")) {
            prdNm = "테라브레스";
        } else if (prdNm.contains("보마르셰")) {
            prdNm = "보마르셰";
        } else {
            prdNm = "Ohers";
        }

        return prdNm;
    }

    //시간 설정 함수				(시작 날짜,채널명 입력, 끝날 날짜)
    public static List<String> Days(String date, String type, int count, String endDate) throws Exception {
        List<String> list = new ArrayList<>();
        SimpleDateFormat dayFormat;
        //각 채널에 따른 형태 변환
        if (type.equals("11st")) {
            dayFormat = new SimpleDateFormat("yyyyMMddHHmm", Locale.KOREA);
        } else if (type.equals("coupang")) {
            dayFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        } else if(type.equals("weamap")) {
        	dayFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        } else {
            dayFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREA);
        }

        Date day = dayFormat.parse(date); //입력날짜
        Date day2;    //오늘 날짜 (-10분)

        Calendar cal = new GregorianCalendar();
        cal.setTime(day);
        day2 = dayFormat.parse(endDate);


        //날짜 비교 값
        int result;
        //오늘 날짜 보다 작으면 1 리턴
        result = day2.compareTo(day);
        while (result == 1) {
            list.add(dayFormat.format(day));

            cal.add(Calendar.DATE, count);

            day = (cal.getTime());
            //오늘 날짜 보다 크면 -1, 같으면 0
            result = day2.compareTo(day);
            if (result <= 0) {
                list.add(dayFormat.format(day2));
            }
        }
        return list;
    }

    //시간 설정 함수 			(날짜입력, 채널 타입)
    public static List<String> Days(String date, String type, int count) {
        List<String> list = new ArrayList<>();
        SimpleDateFormat dayFormat;
        //각 채널에 따른 형태 변환
        if (type.equals("11st")) {
            dayFormat = new SimpleDateFormat("yyyyMMddHHmm", Locale.KOREA);
        } else if (type.equals("coupang") || type.equals("own")) {
            dayFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        } else if (type.equals("wemap")) {
            dayFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        } else {
            dayFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREA);
        }

        Date day;    //입력 날짜
        Date day2 = new Date();    //오늘 날짜 (-10분)
        Calendar cal = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();

        cal2.setTime(day2);
        cal2.add(Calendar.MINUTE, -10);
        day2 = cal2.getTime();
        //날짜 비교 값
        int result;
        try {
            day = dayFormat.parse(date);
            //오늘 날짜 보다 작으면 1 리턴
            result = day2.compareTo(day);
            while (result > 0) {
                list.add(dayFormat.format(day));
                cal.setTime(day);
                cal.add(Calendar.DATE, count);

                day = (cal.getTime());
                //오늘 날짜 보다 크면 0
                result = day2.compareTo(day);
                if (result < 0) {
                    list.add(dayFormat.format(day2));
                }
            }

            if (type.equals("wemap")) {
                for (int i = 0; i < list.size(); i++) {
                    Date date1 = dayFormat.parse(list.get(i));

                    Calendar cal1 = Calendar.getInstance();
                    cal1.setTime(date1);
                    cal1.add(Calendar.SECOND, -i);

                    list.set(i, dayFormat.format(cal1.getTime()));
                }
            }

            return list;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String validOne(String main, String compare) {
        main = validating(main);
        compare = validating(compare);

        if (main.equals("null") && !compare.equals("null")) {
            main = compare;
            return main;
        } else if (compare.equals("null") && !main.equals("null")) {
            compare = main;
            return compare;
        }

        return main;
    }

    public static String validating(String compare) {
        if (compare == null) {
            compare = "null";
        } else {
            compare = compare.replaceAll(" ", "").replaceAll("'", "").replaceAll("\"", "").replaceAll("010-0000-0000", "").replaceAll("02--", "").replaceAll("010-000-0000", "").replaceAll("000-000-0000", "").replaceAll("000-0000-0000", "");
            if (compare.equals("NULL") || compare.equals("") || compare.length() < 1 || compare.contains("050") || compare.equals("Null") || compare.equals("false") || compare.contains("null") || compare.contains("010-****")) {
                compare = "null";
            }
        }
        return compare;
    }

    public static String XSSFCell_value(XSSFCell cell){
        String value = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);

        //셀이 빈값일경우를 위한 널체크
        if (cell == null) {
            value = "";
        } else {
            if (HSSFDateUtil.isInternalDateFormat(cell.getCellStyle().getDataFormat())) {
                value = sdf.format(cell.getDateCellValue());
            }
            // 기타
            else {
                //타입별로 내용 읽기
                switch (cell.getCellType()) {
                    case XSSFCell.CELL_TYPE_FORMULA:
                        value = cell.getCellFormula();
                        break;
                    case XSSFCell.CELL_TYPE_NUMERIC:
                        //숫자 포맷 문자로 변환
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        value = cell.getStringCellValue();
                        break;
                    case XSSFCell.CELL_TYPE_STRING:
                        value = cell.getStringCellValue() + "";
                        break;
                    case XSSFCell.CELL_TYPE_BLANK:
                        value = cell.getBooleanCellValue() + "";
                        break;
                    case XSSFCell.CELL_TYPE_ERROR:
                        value = cell.getErrorCellValue() + "";
                        break;
                }
            }

        }
        return value;
    }
}

