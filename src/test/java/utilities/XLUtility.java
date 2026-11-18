
package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLUtility {

    public String path;

    // Constructor
    public XLUtility(String path) {
        this.path = path;
    }

    // Get number of rows
    public int getRowCount(String sheetName) throws IOException {

        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new IllegalArgumentException(
                        "Sheet not found: " + sheetName
                );
            }

            return sheet.getLastRowNum();
        }
    }

    // Get number of cells in a row
    public int getCellCount(String sheetName, int rownum)
            throws IOException {

        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

            Sheet sheet = workbook.getSheet(sheetName);
            Row row = sheet.getRow(rownum);

            if (row == null) {
                return 0;
            }

            return row.getLastCellNum();
        }
    }

    // Get cell data
    public String getCellData(String sheetName, int rownum,
                              int colnum) throws IOException {

        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

            Sheet sheet = workbook.getSheet(sheetName);
            Row row = sheet.getRow(rownum);

            if (row == null || row.getCell(colnum) == null) {
                return "";
            }

            DataFormatter formatter = new DataFormatter();

            return formatter.formatCellValue(
                    row.getCell(colnum)
            );
        }
    }

    // Set cell data
    public void setCellData(String sheetName, int rownum,
                            int colnum, String data)
            throws IOException {

        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

            Sheet sheet = workbook.getSheet(sheetName);

            Row row = sheet.getRow(rownum);

            if (row == null) {
                row = sheet.createRow(rownum);
            }

            Cell cell = row.getCell(colnum);

            if (cell == null) {
                cell = row.createCell(colnum);
            }

            cell.setCellValue(data);

            try (FileOutputStream fo = new FileOutputStream(path)) {
                workbook.write(fo);
            }
        }
    }

    // Apply green color to a cell
    public void fillGreenColor(String sheetName, int rownum,
                               int colnum) throws IOException {

        fillColor(sheetName, rownum, colnum,
                IndexedColors.GREEN.getIndex());
    }

    // Apply red color to a cell
    public void fillRedColor(String sheetName, int rownum,
                             int colnum) throws IOException {

        fillColor(sheetName, rownum, colnum,
                IndexedColors.RED.getIndex());
    }

    // Common method to apply cell color
    private void fillColor(String sheetName, int rownum,
                           int colnum, short color)
            throws IOException {

        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

            Sheet sheet = workbook.getSheet(sheetName);

            Row row = sheet.getRow(rownum);

            if (row == null) {
                row = sheet.createRow(rownum);
            }

            Cell cell = row.getCell(colnum);

            if (cell == null) {
                cell = row.createCell(colnum);
            }

            CellStyle style = workbook.createCellStyle();

            style.cloneStyleFrom(cell.getCellStyle());

            style.setFillForegroundColor(color);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            cell.setCellStyle(style);

            try (FileOutputStream fo = new FileOutputStream(path)) {
                workbook.write(fo);
            }
        }
    }
}
