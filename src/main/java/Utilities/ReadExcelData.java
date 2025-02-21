package Utilities;

import Reports.loggerLogs;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class ReadExcelData {

    private static ReadExcelData instance;

    public static ReadExcelData getInstance(){
        if(instance==null){
            instance= new ReadExcelData();
        }
        return instance;
    }
    private ReadExcelData(){

    }
    HSSFWorkbook hssfWorkbook;
    HSSFSheet hssfSheet;
    HSSFCell hssfCell;
    loggerLogs logs = loggerLogs.getInstance();

    public Object[][] readData(String filePath) {
        int rowCount = 0;
        int cellCount = 0;
        FileInputStream inputStream = null;
        Object[][] data = new Object[rowCount][cellCount];
        try {
            File file = new File(filePath);
            inputStream = new FileInputStream(file);
            hssfWorkbook = new HSSFWorkbook(inputStream);

            hssfSheet = hssfWorkbook.getSheetAt(0);

            rowCount = hssfSheet.getLastRowNum();
            cellCount = hssfSheet.getRow(0).getLastCellNum();
            data = new Object[rowCount + 1][cellCount];

            for (int i = 0; i <= rowCount; i++) {
                for (int j = 0; j < cellCount; j++) {
                    hssfCell = hssfSheet.getRow(i).getCell(j);
                    switch (hssfCell.getCellType()) {
                        case STRING:
                            data[i][j] = hssfCell.getStringCellValue();
                            break;
                        case NUMERIC:
                            data[i][j] = hssfCell.getNumericCellValue();
                            break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logs.logger("error", Thread.currentThread().getStackTrace()[2] + " : failed to the data from excel file  " + e.getMessage(), ReadExcelData.class.getSimpleName(), Thread.currentThread().getStackTrace()[2]);
        }
        finally {
            try {
                inputStream.close();
                logs.logger("info", Thread.currentThread().getStackTrace()[2] + " : Data has been fetched successfully from excel file - " + ReadExcelData.class.getSimpleName(), "");
            }
            catch (Exception e){
                e.printStackTrace();
                logs.logger("error",  e.getMessage(), ReadExcelData.class.getSimpleName(), Thread.currentThread().getStackTrace()[2]);
            }
        }
        return data;
    }

    public void convertCSVToExcel(String csvFile) {
        FileOutputStream fileOut = null;
        try {
            ArrayList arList = null;
            ArrayList al = null;

            String thisLine;

            FileInputStream fis = new FileInputStream(new File(csvFile));
            DataInputStream myInput = new DataInputStream(fis);
            int i = 0;
            arList = new ArrayList();
            while ((thisLine = myInput.readLine()) != null) {
                al = new ArrayList();
                for(int num=1;num<=9;num++) {
                    thisLine = thisLine.replaceAll(String.valueOf(num)+",", String.valueOf(num));
                }
                String strar[] = thisLine.split(",");
                for (int j = 0; j < strar.length; j++) {
                    al.add(strar[j]);
                }
                arList.add(al);
                i++;
            }
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("new sheet");
            for (int k = 0; k < arList.size(); k++) {
                ArrayList ardata = (ArrayList) arList.get(k);
                HSSFRow row = sheet.createRow((short) 0 + k);
                for (int p = 0; p < ardata.size(); p++) {
                    HSSFCell cell = row.createCell((short) p);
                    String data = ardata.get(p).toString();

                    if (data.startsWith("=")) {
                        cell.setCellType(CellType.STRING);
                        data = data.replaceAll("\"", "");
                        data = data.replaceAll("=", "");
                        cell.setCellValue(data);
                    } else if (data.startsWith("\"")) {
                        data = data.replaceAll("\"", "");
                        cell.setCellType(CellType.STRING);
                        cell.setCellValue(data);
                    } else {
                        data = data.replaceAll("\"", "");
                        cell.setCellType(CellType.NUMERIC);
                        cell.setCellValue(data);
                    }
                }
            }
            fileOut = new FileOutputStream("data.xls");
            hwb.write(fileOut);

        } catch (Exception e) {
            e.printStackTrace();
            logs.logger("error", Thread.currentThread().getStackTrace()[2] + "failed to  convert the csv to excel"+ e.getMessage(), ReadExcelData.class.getSimpleName(), Thread.currentThread().getStackTrace()[2]);
        }finally {
            try{
                fileOut.close();
                logs.logger("info", Thread.currentThread().getStackTrace()[2] + " : Successfully converted the csv to excel - " + ReadExcelData.class.getSimpleName(), "");
            }catch (Exception e){
                e.printStackTrace();
                logs.logger("error",  e.getMessage(), ReadExcelData.class.getSimpleName(), Thread.currentThread().getStackTrace()[2]);
            }
        }
    }

}
