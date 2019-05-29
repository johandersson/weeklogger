package se.johanandersson.weeklogger;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

public class TestGenerateExcelReport {
    @Test
    public void testGetFileName(){
        ExcelFileGenerator xlsGen = new ExcelFileGenerator("weeklogger-report.xlsx");
        assertTrue(xlsGen instanceof ExcelFileGenerator);
        assertEquals(xlsGen.getFileName(), "weeklogger-report.xlsx");
    }

    @Test
    public void testFileExistsAfterCreation() throws FileNotFoundException {
        ExcelFileGenerator xlsGen = new ExcelFileGenerator("weeklogger-report.xlsx");
        xlsGen.createFile();
        File f = new File(xlsGen.getFileName());
        assertTrue(f.exists());
    }

    @Test
    public void testCreateWorkBookAndSheet() throws IOException {
        ExcelFileGenerator xlsGen = new ExcelFileGenerator("weeklogger-report.xlsx");
        xlsGen.createFile();
        Workbook wb = new HSSFWorkbook();
        wb.write(xlsGen.getFileOutPutStream());
        Sheet sheet = wb.createSheet();
        wb.setSheetName(0,"veckologgaren");
        assertEquals(wb.getSheetName(0), "veckologgaren");
    }


}
