package demo;/*
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

*/
/**
 * Created by johnny.ly on 2015/9/1.
 *//*

public class FilesPackagedTest {
    public static void main(String[] args) throws Exception {
        List<Person> personList = new ArrayList<Person>();
        Person p = new Person();
        p.setId(1234);
        p.setName("peter");
        p.setSex("Male");
        personList.add(p);
        personList.add(p);
        personList.add(p);
        personList.add(p);
        personList.add(p);


        Workbook workbook = null;

        String fileName= "test.xls";
        if(fileName.endsWith("xlsx")){
            workbook = new XSSFWorkbook();
        }else if(fileName.endsWith("xls")){
            workbook = new HSSFWorkbook();
        }else{
            throw new Exception("invalid file name, should be xls or xlsx");
        }

        Sheet sheet = workbook.createSheet("Persons");
        FileOutputStream zfout = new FileOutputStream("file.zip");
        ZipOutputStream gzip = new ZipOutputStream(zfout);
        for(int i=0;i<4;i++) {
            ZipEntry zipEntry = new ZipEntry("test"+i+".xls");
            gzip.putNextEntry(zipEntry);
            Iterator<Person> iterator = personList.iterator();

            int rowIndex = 0;
            while(iterator.hasNext()){
                Person person = iterator.next();
                Row row = sheet.createRow(rowIndex++);
                Cell cell0 = row.createCell(0);
                cell0.setCellValue(person.getName());
                Cell cell1 = row.createCell(1);
                cell1.setCellValue(person.getSex());
                Cell cell2 = row.createCell(2);
                cell2.setCellValue(person.getId());
            }
            workbook.write(gzip);

        }
        gzip.finish();
        gzip.close();
    }
}

class Person{
    private String name;
    private int id;
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
*/
