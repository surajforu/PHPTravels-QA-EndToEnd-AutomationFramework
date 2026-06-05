package utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtil {
    
    private ExcelUtil() {}

    public static String getCellData(String filePath, String sheetName, int rowNumber, int columnNumber) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {
            
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Worksheet tab named '" + sheetName + "' was not found in the Excel file.");
            }
            
            Row row = sheet.getRow(rowNumber);
            if (row == null) {
                return ""; 
            }
            
            Cell cell = row.getCell(columnNumber);
            return new DataFormatter().formatCellValue(cell).trim();
            
        } catch (IOException e) {
            throw new RuntimeException("Unable to read excel file at path: " + filePath, e);
        }
    }

    /**
     * Fetches login credentials safely.
     * Automatically handles 1-based indexing offsets from Gherkin feature definitions.
     */
    public static Map<String, String> getLoginData(int rowNumber) {
        String filePath = ConfigReader.get("excelPath");
        Map<String, String> data = new HashMap<>();
        
        
        int targetRow = rowNumber; 

        String username = getCellData(filePath, "Sheet1", targetRow, 0);
        String password = getCellData(filePath, "Sheet1", targetRow, 1);
        
        data.put("username", username);
        data.put("password", password);
        
        return data;
    }
}