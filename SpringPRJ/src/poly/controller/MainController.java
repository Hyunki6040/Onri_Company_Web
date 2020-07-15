package poly.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import poly.dto.ApplicantDTO;
import poly.service.IMainService;
import poly.util.UtilFile;

@Controller
public class MainController {

	@Resource(name = "MainService")
	private IMainService mainService;

//	@RequestMapping(value = "/applyProc", method = RequestMethod.POST)
//	public String loginProc(@RequestParam("appli_resume") MultipartFile appli_resume1, @RequestParam("appli_portfolio") MultipartFile appli_portfolio1, MultipartHttpServletRequest request,
//							HttpServletResponse response, Model model, HttpSession session) throws Exception {
//		String appli_depart = request.getParameter("appli_depart");
//		String appli_name = request.getParameter("appli_name");
//		String appli_birth = request.getParameter("appli_birth");
//		String appli_phone = request.getParameter("appli_phone");
//		String appli_mail = request.getParameter("appli_mail");
//
//
//		String resume_file_name = "";
//		String portfolio_file_name = "";
//
//		String path = "";
//		String excel_file_path = "";
//
//		//몆주차인지 구하기
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
//
//		Calendar calendar = Calendar.getInstance();
//		String[] dates = sdf.format(new Date()).split("-");
//		int year1 = Integer.parseInt(dates[0]);
//		int month = Integer.parseInt(dates[1]);
//		int day = Integer.parseInt(dates[2]);
//		calendar.set(year1, month - 1, day);
//		int thisWeek = calendar.get(Calendar.WEEK_OF_MONTH);
//
//		String year = Integer.toString(year1).substring(2);
//
//		//엑셀파일 작성
//		excel_file_path = month + "월/" + month + "월 " + thisWeek + "주차/" + "정규직_지원자_리스트_" + year + "년" + month + "월" + thisWeek + "주차.xlsx";
//		excel_file_path = "/usr/local/upload_files/" + excel_file_path;
//		File Folder = new File("/usr/local/upload_files/" + month + "월/" + month + "월 " + thisWeek + "주차");
//
//		// 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
//		if (!Folder.exists()) {
//			try {
//				Folder.mkdirs(); // 폴더 생성합니다.
//			} catch (Exception e) {
//				e.getStackTrace();
//			}
//		}
//
//		File file = new File(excel_file_path);
//		boolean isExists = file.exists();
//		if (!isExists) {
//			XSSFWorkbook wb = new XSSFWorkbook();
//			//시트생성
//			Sheet sheeta = wb.createSheet("디자인팀");
//			if (sheeta == null) {
//				/* test */
//				System.out.println("디자인팀 is Null!");
//			}
//			Row row0 = sheeta.createRow(0);
//			Cell cell = row0.createCell(0);
//			cell.setCellValue("이름");
//			Cell cell1 = row0.createCell(1);
//			cell1.setCellValue("생일");
//			Cell cell2 = row0.createCell(2);
//			cell2.setCellValue("연락처");
//			Cell cell3 = row0.createCell(3);
//			cell3.setCellValue("메일");
//
//			/*sheet.setColumnWidth(0, 13*256); //첫번째 칼럼의 폭 조절
//			sheet.setColumnWidth(3, 50*256); //네번째 칼럼의 폭 조절
//			sheet.setColumnWidth(5, 21*256); //여섯번째 칼럼의 폭 조절
//
//*/
//			Sheet sheetb = wb.createSheet("브랜드팀");
//			if (sheetb == null) {
//				/* test */
//				System.out.println("브랜드팀 is Null!");
//			}
//			Row row0b = sheetb.createRow(0);
//			Cell cellb = row0b.createCell(0);
//			cellb.setCellValue("이름");
//			Cell cell1b = row0b.createCell(1);
//			cell1b.setCellValue("생일");
//			Cell cell2b = row0b.createCell(2);
//			cell2b.setCellValue("연락처");
//			Cell cell3b = row0b.createCell(3);
//			cell3b.setCellValue("메일");
//
//			Sheet sheetc = wb.createSheet("테크팀");
//			if (sheetc == null) {
//				/* test */
//				System.out.println("테크팀 is Null!");
//			}
//			Row row0c = sheetc.createRow(0);
//			Cell cellc = row0c.createCell(0);
//			cellc.setCellValue("이름");
//			Cell cell1c = row0c.createCell(1);
//			cell1c.setCellValue("생일");
//			Cell cell2c = row0c.createCell(2);
//			cell2c.setCellValue("연락처");
//			Cell cell3c = row0c.createCell(3);
//			cell3c.setCellValue("메일");
//
//			Sheet sheetd = wb.createSheet("CS팀");
//			if (sheetd == null) {
//				/* test */
//				System.out.println("CS팀 is Null!");
//			}
//			Row row0d = sheetd.createRow(0);
//			Cell celld = row0d.createCell(0);
//			celld.setCellValue("이름");
//			Cell cell1d = row0d.createCell(1);
//			cell1d.setCellValue("생일");
//			Cell cell2d = row0d.createCell(2);
//			cell2d.setCellValue("연락처");
//			Cell cell3d = row0d.createCell(3);
//			cell3d.setCellValue("메일");
//
//			Sheet sheete = wb.createSheet("인사팀");
//			if (sheete == null) {
//				/* test */
//				System.out.println("인사팀 is Null!");
//			}
//			Row row0e = sheete.createRow(0);
//			Cell celle = row0e.createCell(0);
//			celle.setCellValue("이름");
//			Cell cell1e = row0e.createCell(1);
//			cell1e.setCellValue("생일");
//			Cell cell2e = row0e.createCell(2);
//			cell2e.setCellValue("연락처");
//			Cell cell3e = row0e.createCell(3);
//			cell3e.setCellValue("메일");
//			try {
//				FileOutputStream fileOut = new FileOutputStream(excel_file_path);
//				wb.write(fileOut);
//				fileOut.close();
//				wb.close(); //HSSFWorkbook. XSSFWorkbook 사용시 사용
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//		}
//
//		//파일 업로드 시작
//		ApplicantDTO aDTO = new ApplicantDTO();
//		String error = "";
//		path = month + "월/" + month + "월 " + thisWeek + "주차/" + month + "월" + thisWeek + "주차_정규직/";
//
//
//		if (!appli_resume1.isEmpty() || !appli_portfolio1.isEmpty()) {
//			// 해당 디렉토리가 없을경우 디렉토리를 생성합니다. //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!프론트 마무리후 폴더명 설정 필요!!!!!!!!!!!!!!!!!!!!!!!!!!!
//			File personal_Folder = new File(path + appli_name + "/");
//			if (!personal_Folder.exists()) {
//				try {
//					personal_Folder.mkdirs(); // 폴더 생성합니다.
//					path += appli_name + "/";
//				} catch (Exception e) {
//					e.getStackTrace();
//				}
//			} else {
//				File personal_Folder1 = new File(path + appli_name + "_" + appli_birth + "/");
//				if (!personal_Folder1.exists()) {
//					try {
//						personal_Folder1.mkdirs(); // 폴더 생성합니다.
//						path += appli_name + "_" + appli_birth + "/";
//					} catch (Exception e) {
//						e.getStackTrace();
//					}
//				} else {
//					File personal_Folder2 = new File(path + appli_name + "_" + appli_birth + "_" + appli_phone + "/");
//					if (!personal_Folder2.exists()) {
//						try {
//							personal_Folder2.mkdirs(); // 폴더 생성합니다.
//							path += appli_name + "_" + appli_birth + "_" + appli_phone + "/";
//						} catch (Exception e) {
//							e.getStackTrace();
//						}
//					} else {
//						error = "error, 폴더명이 중복되어 파일을 전송받지 못했습니다.";
//					}
//				}
//			}
//
//			resume_file_name = appli_depart + "팀_" + appli_name + "_이력서";
//			portfolio_file_name = appli_depart + "팀_" + appli_name + "_포트폴리오";
//		}
//
//		// 파일 업로드 결과값을 path로 받아온다(이미 fileUpload() 메소드에서 해당 경로에 업로드는 끝났음)
//		if (!appli_resume1.isEmpty()) {
//			UtilFile utilFile = new UtilFile();
//			String appli_resume = utilFile.fileUpload(request, appli_resume1, path, resume_file_name, appli_birth);
//			aDTO.setAppli_resume(appli_resume);
//		} else {
//			aDTO.setAppli_resume("");
//		}
//		if (!appli_portfolio1.isEmpty()) {
//			UtilFile utilFile = new UtilFile();
//			String appli_portfolio = utilFile.fileUpload(request, appli_portfolio1, path, portfolio_file_name, appli_birth);
//			aDTO.setAppli_portfolio(appli_portfolio);
//		} else {
//			aDTO.setAppli_portfolio("");
//		}
//
//
//		//엑셀 파일 작성 시작
//		FileOutputStream outStream = null;
//		InputStream fis = null;
//
//		try {
//			fis = new FileInputStream(excel_file_path);
//			XSSFWorkbook workbook = new XSSFWorkbook(fis); // xlsx 파일 Open
//			XSSFSheet sheet = workbook.getSheet(appli_depart + "팀");
//			int rows = sheet.getLastRowNum();
//
//			Row insert_row0 = sheet.createRow(rows + 1);
//			Cell insert_cell = insert_row0.createCell(0);
//			insert_cell.setCellValue(appli_name);
//			Cell insert_cell1 = insert_row0.createCell(1);
//			insert_cell1.setCellValue(appli_birth);
//			Cell insert_cell2 = insert_row0.createCell(2);
//			insert_cell2.setCellValue(appli_phone);
//			Cell insert_cell3 = insert_row0.createCell(3);
//			insert_cell3.setCellValue(appli_mail);
//			Cell insert_cell4 = insert_row0.createCell(4);
//			insert_cell4.setCellValue(error);
//
//			for (int i = 0; i < 4; i++) //autuSizeColumn after setColumnWidth setting!!
//			{
//				sheet.autoSizeColumn(i);
//				sheet.setColumnWidth(i, (sheet.getColumnWidth(i)) + 512); //이건 자동으로 조절 하면 너무 딱딱해 보여서 자동조정한 사이즈에 (short)512를 추가해 주니 한결 보기 나아졌다.
//			}
//
//			outStream = new FileOutputStream(excel_file_path);
//			workbook.write(outStream);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			fis.close();
//			outStream.close();
//		}
//
//
//		aDTO.setAppli_depart(appli_depart + "팀");
//		aDTO.setAppli_birth(appli_birth);
//		aDTO.setAppli_mail(appli_mail);
//		aDTO.setAppli_name(appli_name);
//		aDTO.setAppli_phone(appli_phone);
//
//
//		mainService.insertApplicant(aDTO);
//
//		model.addAttribute("url", "/recruit.do");
//		model.addAttribute("msg", "등록되었습니다! 지원해주셔서 감사합니다");
//		return "/alert";
//	}
//
//	@RequestMapping(value = "/aboutUs")
//	public String aboutUs() throws Exception {
//		return "/aboutUs";
//	}
//
//	@RequestMapping(value = "/blogDetails")
//	public String blogDetails() throws Exception {
//		return "/blogDetails";
//	}
//
//	@RequestMapping(value = "/clients")
//	public String clients() throws Exception {
//		return "/clients";
//	}
//
//	@RequestMapping(value = "/contact")
//	public String contact() throws Exception {
//		return "/contact";
//	}
//
//	@RequestMapping(value = "/index")
//	public String index() throws Exception {
//		return "/index";
//	}
//
//	@RequestMapping(value = "/news")
//	public String news() throws Exception {
//		return "/news";
//	}
//
//	@RequestMapping(value = "/recruit")
//	public String recruit() throws Exception {
//		return "/recruit";
//	}
//
//	@RequestMapping(value = "/roomDetails")
//	public String roomDetails() throws Exception {
//		return "/roomDetails";
//	}
//
//	@RequestMapping(value = "/team")
//	public String team() throws Exception {
//		return "/team";
//	}

}
