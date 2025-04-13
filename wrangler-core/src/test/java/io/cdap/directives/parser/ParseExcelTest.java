// /*
//  *  Copyright © 2017-2019 Cask Data, Inc.
//  *
//  *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
//  *  use this file except in compliance with the License. You may obtain a copy of
//  *  the License at
//  *
//  *  http://www.apache.org/licenses/LICENSE-2.0
//  *
//  *  Unless required by applicable law or agreed to in writing, software
//  *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
//  *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
//  *  License for the specific language governing permissions and limitations under
//  *  the License.
//  */

// package io.cdap.directives.parser;

// import io.cdap.wrangler.TestingRig;
// import io.cdap.wrangler.api.Pair;
// import io.cdap.wrangler.api.Row;
// import org.apache.commons.io.IOUtils;
// import org.junit.Assert;
// import org.junit.Test;

// import java.io.InputStream;
// import java.util.ArrayList;
// import java.util.List;

// /**
//  * Tests {@link ParseExcel}
//  */
// public class ParseExcelTest {

//   @Test
//   public void testBasicExcel() throws Exception {
//     try (InputStream stream = ParseAvroFileTest.class.getClassLoader().getResourceAsStream("titanic.xlsx")) {
//       byte[] data = IOUtils.toByteArray(stream);

//       String[] directives = new String[]{
//         "parse-as-excel :body '0'",
//       };

//       List<Row> rows = new ArrayList<>();
//       rows.add(new Row("body", data));

//       List<Row> results = TestingRig.execute(directives, rows);
//       Assert.assertEquals(892, results.size());
//       Assert.assertEquals(0, results.get(0).getValue("fwd"));
//       Assert.assertEquals(891, results.get(0).getValue("bkd"));
//     }
//   }

//   @Test
//   public void testNoSheetName() throws Exception {
//     try (InputStream stream = ParseAvroFileTest.class.getClassLoader().getResourceAsStream("titanic.xlsx")) {
//       byte[] data = IOUtils.toByteArray(stream);

//       String[] directives = new String[]{
//         "parse-as-excel :body 'wrong_error'",
//       };

//       List<Row> rows = new ArrayList<>();
//       rows.add(new Row("body", data));
//       Pair<List<Row>, List<Row>> pipeline = TestingRig.executeWithErrors(directives, rows);
//       Assert.assertEquals(0, pipeline.getFirst().size());
//       Assert.assertEquals(1, pipeline.getSecond().size());
//     }
//   }

//   @Test
//   public void testDateFormatting() throws Exception {
//     try (InputStream stream =
//            ParseAvroFileTest.class.getClassLoader().getResourceAsStream("date-formats-test-sheet.xlsx")) {
//       byte[] data = IOUtils.toByteArray(stream);

//       String[] directives = new String[]{
//         "parse-as-excel :body '0'",
//       };

//       List<Row> rows = new ArrayList<>();
//       rows.add(new Row("body", data));
//       List<Row> results = TestingRig.execute(directives, rows);

//       for (Row result : results) {
//         Assert.assertEquals(result.getValue("A"), result.getValue("B"));
//       }
//     }
//   }
// }




// /*
//  *  Copyright © 2017-2019 Cask Data, Inc.
//  *
//  *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
//  *  use this file except in compliance with the License. You may obtain a copy of
//  *  the License at
//  *
//  *  http://www.apache.org/licenses/LICENSE-2.0
//  *
//  *  Unless required by applicable law or agreed to in writing, software
//  *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
//  *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
//  *  License for the specific language governing permissions and limitations under
//  *  the License.
//  */

// package io.cdap.directives.parser;

// import io.cdap.wrangler.TestingRig;
// import io.cdap.wrangler.api.Pair;
// import io.cdap.wrangler.api.Row;
// import org.apache.commons.io.IOUtils;
// import org.junit.Assert;
// import org.junit.Test;

// import java.io.InputStream;
// import java.util.ArrayList;
// import java.util.List;

// /**
//  * Tests {@link ParseExcel}
//  */
// public class ParseExcelTest {

//   @Test
//   public void testBasicExcel() throws Exception {
//     InputStream stream = ParseExcelTest.class.getClassLoader().getResourceAsStream("titanic.xlsx");
//     if (stream == null) {
//       Assert.fail("Resource titanic.xlsx not found in src/test/resources");
//     }

//     try (InputStream resourceStream = stream) {
//       byte[] data = IOUtils.toByteArray(resourceStream);

//       String[] directives = new String[]{
//           "parse-as-excel :body '0'",
//       };

//       List<Row> rows = new ArrayList<>();
//       rows.add(new Row("body", data));

//       List<Row> results = TestingRig.execute(directives, rows);

//       // Debug: Print result size and first row (if available)
//       System.out.println("Parsed rows: " + results.size());
//       if (!results.isEmpty()) {
//         Row firstRow = results.get(0);
//         StringBuilder columns = new StringBuilder("First row values: ");
//         for (int i = 0; i < firstRow.length(); i++) {
//           String columnName = "Column" + (i + 1);
//           Object value = firstRow.getValue(i);
//           columns.append(columnName).append("=").append(value).append(", ");
//         }
//         System.out.println(columns.toString());
//       }

//       // Titanic dataset: 891 rows expected (excluding header if skipped)
//       Assert.assertEquals(891, results.size());
//       if (!results.isEmpty()) {
//         // Verify standard Titanic columns
//         Assert.assertNotNull("Column 'PassengerId' not found", 
//             results.get(0).getValue("PassengerId"));
//         Assert.assertNotNull("Column 'Survived' not found", 
//             results.get(0).getValue("Survived"));
//       }
//     }
//   }

//   @Test
//   public void testNoSheetName() throws Exception {
//     try (InputStream stream = ParseExcelTest.class.getClassLoader().getResourceAsStream("titanic.xlsx")) {
//       byte[] data = IOUtils.toByteArray(stream);

//       String[] directives = new String[]{
//           "parse-as-excel :body 'wrong_error'",
//       };

//       List<Row> rows = new ArrayList<>();
//       rows.add(new Row("body", data));
//       Pair<List<Row>, List<Row>> pipeline = TestingRig.executeWithErrors(directives, rows);
//       Assert.assertEquals(0, pipeline.getFirst().size());
//       Assert.assertEquals(1, pipeline.getSecond().size());
//     }
//   }

//   @Test
//   public void testDateFormatting() throws Exception {
//     try (InputStream stream =
//            ParseExcelTest.class.getClassLoader().getResourceAsStream("date-formats-test-sheet.xlsx")) {
//       byte[] data = IOUtils.toByteArray(stream);

//       String[] directives = new String[]{
//           "parse-as-excel :body '0'",
//       };

//       List<Row> rows = new ArrayList<>();
//       rows.add(new Row("body", data));
//       List<Row> results = TestingRig.execute(directives, rows);

//       for (Row result : results) {
//         Assert.assertEquals(result.getValue("A"), result.getValue("B"));
//       }
//     }
//   }
// }



// /*
//  *  Copyright © 2017-2019 Cask Data, Inc.
//  *
//  *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
//  *  use this file except in compliance with the License. You may obtain a copy of
//  *  the License at
//  *
//  *  http://www.apache.org/licenses/LICENSE-2.0
//  *
//  *  Unless required by applicable law or agreed to in writing, software
//  *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
//  *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
//  *  License for the specific language governing permissions and limitations under
//  *  the License.
//  */

// package io.cdap.directives.parser;

// import io.cdap.wrangler.TestingRig;
// import io.cdap.wrangler.api.Pair;
// import io.cdap.wrangler.api.Row;
// import org.apache.commons.io.IOUtils;
// import org.junit.Assert;
// import org.junit.Test;

// import java.io.InputStream;
// import java.util.ArrayList;
// import java.util.List;

// /**
//  * Tests {@link ParseExcel}
//  */
// public class ParseExcelTest {

//   @Test
//   public void testBasicExcel() throws Exception {
//     InputStream stream = ParseExcelTest.class.getClassLoader().getResourceAsStream("titanic.xlsx");
//     if (stream == null) {
//       Assert.fail("Resource titanic.xlsx not found in src/test/resources");
//     }

//     try (InputStream resourceStream = stream) {
//       byte[] data = IOUtils.toByteArray(resourceStream);

//       // Try sheet index 0 and common sheet name
//       String[] sheetAttempts = new String[]{"0", "Sheet1", "Titanic"};
//       List<Row> results = null;
//       String usedSheet = null;

//       for (String sheet : sheetAttempts) {
//         String[] directives = new String[]{
//             "parse-as-excel :body '" + sheet + "'",
//         };

//         List<Row> rows = new ArrayList<>();
//         rows.add(new Row("body", data));

//         try {
//           System.out.println("Attempting to parse sheet: " + sheet);
//           results = TestingRig.execute(directives, rows);
//           usedSheet = sheet;
//           System.out.println("Parsed rows with sheet '" + sheet + "': " + results.size());
//           if (results.size() > 0) {
//             break; // Stop if we get rows
//           }
//         } catch (Exception e) {
//           System.out.println("Failed to parse sheet '" + sheet + "': " + e.getMessage());
//         }
//       }

//       if (results == null || results.isEmpty()) {
//         Assert.fail("Failed to parse titanic.xlsx with sheets: " + String.join(", ", sheetAttempts));
//       }

//       // Debug: Print first row's values
//       Row firstRow = results.get(0);
//       StringBuilder columns = new StringBuilder("First row values (sheet " + usedSheet + "): ");
//       for (int i = 0; i < firstRow.length(); i++) {
//         String columnName = "Column" + (i + 1);
//         Object value = firstRow.getValue(i);
//         columns.append(columnName).append("=").append(value).append(", ");
//       }
//       System.out.println(columns.toString());

//       // Titanic dataset: 891 rows expected (data only)
//       Assert.assertEquals(891, results.size());
//       if (!results.isEmpty()) {
//         // Verify standard Titanic columns
//         Assert.assertNotNull("Column 'PassengerId' not found", 
//             results.get(0).getValue("PassengerId"));
//         Assert.assertNotNull("Column 'Survived' not found", 
//             results.get(0).getValue("Survived"));
//       }
//     }
//   }

//   @Test
//   public void testNoSheetName() throws Exception {
//     try (InputStream stream = ParseExcelTest.class.getClassLoader().getResourceAsStream("titanic.xlsx")) {
//       byte[] data = IOUtils.toByteArray(stream);

//       String[] directives = new String[]{
//           "parse-as-excel :body 'wrong_error'",
//       };

//       List<Row> rows = new ArrayList<>();
//       rows.add(new Row("body", data));
//       Pair<List<Row>, List<Row>> pipeline = TestingRig.executeWithErrors(directives, rows);
//       Assert.assertEquals(0, pipeline.getFirst().size());
//       Assert.assertEquals(1, pipeline.getSecond().size());
//     }
//   }

//   @Test
//   public void testDateFormatting() throws Exception {
//     try (InputStream stream =
//            ParseExcelTest.class.getClassLoader().getResourceAsStream("date-formats-test-sheet.xlsx")) {
//       byte[] data = IOUtils.toByteArray(stream);

//       String[] directives = new String[]{
//           "parse-as-excel :body '0'",
//       };

//       List<Row> rows = new ArrayList<>();
//       rows.add(new Row("body", data));
//       List<Row> results = TestingRig.execute(directives, rows);

//       for (Row result : results) {
//         Assert.assertEquals(result.getValue("A"), result.getValue("B"));
//       }
//     }
//   }
// }





/*
 *  Copyright © 2017-2019 Cask Data, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 */

package io.cdap.directives.parser;

import io.cdap.wrangler.TestingRig;
import io.cdap.wrangler.api.Pair;
import io.cdap.wrangler.api.Row;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests {@link ParseExcel}
 */
public class ParseExcelTest {

  @Test
  public void testBasicExcel() throws Exception {
    // Load the Excel file
    InputStream stream = ParseExcelTest.class.getClassLoader().getResourceAsStream("titanic.xlsx");
    if (stream == null) {
      Assert.fail("Resource titanic.xlsx not found in src/test/resources");
    }

    try (InputStream resourceStream = stream) {
      byte[] data = IOUtils.toByteArray(resourceStream);

      // Dynamically detect available sheets
      List<String> sheetNames = getSheetNames(data);
      if (sheetNames.isEmpty()) {
        Assert.fail("No sheets found in titanic.xlsx");
      }

      List<Row> results = null;
      String usedSheet = null;

      // Try parsing each sheet until successful
      for (String sheet : sheetNames) {
        String[] directives = new String[]{
            "parse-as-excel :body '" + sheet + "' true" // Added 'true' for first-row-as-header
        };

        List<Row> rows = new ArrayList<>();
        rows.add(new Row("body", data));

        try {
          System.out.println("Attempting to parse sheet: " + sheet);
          results = TestingRig.execute(directives, rows);
          usedSheet = sheet;
          System.out.println("Parsed rows with sheet '" + sheet + "': " + results.size());
          if (results != null && !results.isEmpty()) {
            break; // Stop if we get rows
          }
        } catch (Exception e) {
          System.out.println("Failed to parse sheet '" + sheet + "': " + e.getMessage());
        }
      }

      // Validate results
      if (results == null || results.isEmpty()) {
        Assert.fail("Failed to parse titanic.xlsx with sheets: " + String.join(", ", sheetNames));
      }

      // Debug: Print first row's values
      Row firstRow = results.get(0);
      StringBuilder columns = new StringBuilder("First row values (sheet " + usedSheet + "): ");
      for (int i = 0; i < firstRow.length(); i++) {
        String columnName = firstRow.getColumn(i);
        Object value = firstRow.getValue(i);
        columns.append(columnName).append("=").append(value).append(", ");
      }
      System.out.println(columns.toString());

      // Titanic dataset: 891 rows expected (data only)
      Assert.assertEquals("Expected 891 rows in Titanic dataset", 891, results.size());
      if (!results.isEmpty()) {
        // Verify standard Titanic columns
        Assert.assertNotNull("Column 'PassengerId' not found", 
            firstRow.getValue("PassengerId"));
        Assert.assertNotNull("Column 'Survived' not found", 
            firstRow.getValue("Survived"));
      }
    }
  }

  @Test
  public void testNoSheetName() throws Exception {
    try (InputStream stream = ParseExcelTest.class.getClassLoader().getResourceAsStream("titanic.xlsx")) {
      byte[] data = IOUtils.toByteArray(stream);

      String[] directives = new String[]{
          "parse-as-excel :body 'wrong_error'"
      };

      List<Row> rows = new ArrayList<>();
      rows.add(new Row("body", data));
      Pair<List<Row>, List<Row>> pipeline = TestingRig.executeWithErrors(directives, rows);
      Assert.assertEquals(0, pipeline.getFirst().size());
      Assert.assertEquals(1, pipeline.getSecond().size());
    }
  }

  @Test
  public void testDateFormatting() throws Exception {
    try (InputStream stream =
           ParseExcelTest.class.getClassLoader().getResourceAsStream("date-formats-test-sheet.xlsx")) {
      byte[] data = IOUtils.toByteArray(stream);

      String[] directives = new String[]{
          "parse-as-excel :body '0'"
      };

      List<Row> rows = new ArrayList<>();
      rows.add(new Row("body", data));
      List<Row> results = TestingRig.execute(directives, rows);

      for (Row result : results) {
        Assert.assertEquals(result.getValue("A"), result.getValue("B"));
      }
    }
  }

  /**
   * Helper method to get all sheet names from an Excel file.
   */
  private List<String> getSheetNames(byte[] data) throws Exception {
    List<String> sheetNames = new ArrayList<>();
    try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(data))) {
      for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
        Sheet sheet = workbook.getSheetAt(i);
        sheetNames.add(sheet.getSheetName());
      }
    }
    return sheetNames;
  }
}
