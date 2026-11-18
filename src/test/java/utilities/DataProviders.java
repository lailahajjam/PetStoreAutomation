
package utilities;

import java.io.IOException;
import java.nio.file.Paths;

import org.testng.annotations.DataProvider;

public class DataProviders {

    // DataProvider 1: Get all data from Excel
    @DataProvider(name = "Data")
    public static String[][] getAllData() throws IOException {

        // Excel file location
        String path = Paths.get(
                System.getProperty("user.dir"),
                "testData",
                "Userdata.xlsx"
        ).toString();

        // Create XLUtility object
        XLUtility xl = new XLUtility(path);

        // Get row and column count
        int rownum = xl.getRowCount("Sheet1");
        int colcount = xl.getCellCount("Sheet1", 0);

        // Create two-dimensional array
        String apidata[][] = new String[rownum][colcount];

        // Read data from Excel
        for (int i = 1; i <= rownum; i++) {

            for (int j = 0; j < colcount; j++) {

                apidata[i - 1][j] =
                        xl.getCellData("Sheet1", i, j);
            }
        }

        return apidata;
    }


    // DataProvider 2: Get usernames only
    @DataProvider(name = "UserNames")
    public static String[][] getUserNames() throws IOException {

        String path = Paths.get(
                System.getProperty("user.dir"),
                "testData",
                "Userdata.xlsx"
        ).toString();

        XLUtility xl = new XLUtility(path);

        int rownum = xl.getRowCount("Sheet1");

        String usernames[][] = new String[rownum][1];

        for (int i = 1; i <= rownum; i++) {

            usernames[i - 1][0] =
                    xl.getCellData("Sheet1", i, 1);
        }

        return usernames;
    }
}
