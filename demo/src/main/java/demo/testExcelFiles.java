package demo;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;

/**
 * Created by johnny.ly on 2016/6/14.
 */
public class testExcelFiles {
    public static void main(String[] args) throws Exception {
        Workbook workbook = null;

        String fileName= "ISO_COUNTRY_CODE.xlsx";
        InputStream fin = testExcelFiles.class.getClassLoader().getResourceAsStream(fileName);
        if(fileName.endsWith("xlsx")){
            workbook = new XSSFWorkbook(fin);
        }else if(fileName.endsWith("xls")){
            workbook = new HSSFWorkbook(fin);
        }else{
            throw new Exception("invalid file name, should be xls or xlsx");
        }
        Sheet sheet = workbook.getSheetAt(0);
        int row = sheet.getLastRowNum();
        System.out.println(row);
    }
}
