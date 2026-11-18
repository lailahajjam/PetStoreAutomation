
package utilities;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CreateUserDataExcel {

    public static void main(String[] args) {

        // Create the Excel file path
        Path folderPath = Paths.get("testData");
        Path filePath = folderPath.resolve("Userdata.xlsx");

        // User data
        String[][] data = {
                {"UserID", "UserName", "FirstName", "LastName",
                        "Email", "Password", "Phone"},

                {"1010", "testuser1", "John", "Canedy",
                        "a@gmail.com", "test@1", "1234567897"},

                {"1020", "testuser2", "Kim", "Kom",
                        "b@gmail.com", "test@2", "1234567897"},

                {"1030", "testuser3", "Smith", "Steve",
                        "c@gmail.com", "test@3", "1234567897"}
        };

        // Create workbook
        try (Workbook workbook = new XSSFWorkbook()) {

            // Create sheet
            Sheet sheet = workbook.createSheet("Sheet1");

            // Create header style
            CellStyle headerStyle = workbook.createCellStyle();

            headerStyle.setFillForegroundColor(
                    IndexedColors.YELLOW.getIndex()
            );

            headerStyle.setFillPattern(
                    FillPatternType.SOLID_FOREGROUND
            );

            Font font = workbook.createFont();
            font.setBold(true);

            headerStyle.setFont(font);

            // Write data into Excel
            for (int i = 0; i < data.length; i++) {

                Row row = sheet.createRow(i);

                for (int j = 0; j < data[i].length; j++) {

                    Cell cell = row.createCell(j);

                    cell.setCellValue(data[i][j]);

                    // Apply style to header
                    if (i == 0) {
                        cell.setCellStyle(headerStyle);
                    }
                }
            }

            // Adjust column widths automatically
            for (int i = 0; i < data[0].length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Create folder if it doesn't exist
            Files.createDirectories(folderPath);

            // Write workbook to file
            try (FileOutputStream fileOut =
                         new FileOutputStream(filePath.toFile())) {

                workbook.write(fileOut);
            }

            System.out.println(
                    "Excel file generated successfully!"
            );

            System.out.println(
                    "File location: " + filePath.toAbsolutePath()
            );

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
