package project.honey.comm;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Component
public class ExcelMaker {

    public void makeExcel(String sheetName, List<String> titles,
                              List<List<String>> excelData, List<String> excelType,
                              String fileName, HttpServletResponse response) throws IOException {

        //엑셀 생성
        Workbook wb = new SXSSFWorkbook();
        Sheet sheet = wb.createSheet(sheetName);

        //엑셀 스타일 지정
        CellStyle bodyStyle = wb.createCellStyle();
        CellStyle headStyle = wb.createCellStyle();

        bodyStyle.setAlignment(HorizontalAlignment.CENTER);

        headStyle.setAlignment(HorizontalAlignment.CENTER);
        ((XSSFCellStyle)headStyle).setFillForegroundColor(new XSSFColor(new java.awt.Color(244, 244, 244),null));
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        sheet.setColumnWidth(0, 1000);

        //초기값 설정
        Row row = null;
        Cell cell = null;
        int rowNum = 0;

        // 제목 삽입
        row = sheet.createRow(rowNum++);
        for(int i = 0; i < titles.size(); i++){
            cell = row.createCell(i);
            cell.setCellValue(titles.get(i));
            cell.setCellStyle(headStyle);
        }

        // 메인 데이터 삽입
        for(int i = 0; i < excelData.size(); i++) {
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue(i + 1);
            cell.setCellStyle(bodyStyle);
            List<String> data = excelData.get(i);
            for (int j = 0; j < data.size(); j++) {
                cell = row.createCell(j + 1);
                if(!data.get(j).equals("") && excelType.get(j) == "int") cell.setCellValue(Integer.parseInt(data.get(j)));
                else {
                    cell.setCellValue(data.get(j));
                    cell.setCellStyle(bodyStyle);
                }
            }
        }

        // 컨텐츠 타입과 파일명 지정
        response.setContentType("ms-vnd/excel");
        String name = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-Disposition", "attachment;filename=" + name + ".xlsx");

        OutputStream fileOut = response.getOutputStream();

        // Excel File Output
        wb.write(fileOut);
        fileOut.close();

        response.getOutputStream().flush();
        response.getOutputStream().close();

        wb.close();
    }
}
