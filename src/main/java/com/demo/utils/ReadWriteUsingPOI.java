package com.demo.utils;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import com.demo.model.Attributes;
import com.demo.model.Product;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class ReadWriteUsingPOI {
    /**
     * This method will read from the excel
     * @param fileName
     * @throws Exception
     */
    public List<Product> readExcel(String fileName) throws Exception {

        InputStream ExcelFileToRead = new FileInputStream(fileName);
        XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
        Set<Integer> elements = new HashSet<Integer>();
        Map<Integer,Product> hMap = new HashMap<Integer,Product>();
        List<Product> alProducts = new ArrayList<Product>();


        //
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;
        XSSFCell cell;

        Iterator rows = sheet.rowIterator();
        int rowCount = 0;

        //loop through rows
        while (rows.hasNext())
        {
            Map<String,String> al = new HashMap<String,String>();

            Product product = new Product();
            product.setAttributes(al);
            row = (XSSFRow) rows.next();
            if(rowCount==0){
                rowCount++;
                continue;
            }
            Iterator cells = row.cellIterator();
            int col =0;
            while (cells.hasNext()) {
                cell = (XSSFCell) cells.next();
                if(col<8){
//                  product.setAttributes(al);
                    createProduct(product,cell,cell.getColumnIndex());
                }
                col++;
            }//while
            alProducts.add(product);
            rowCount++;

        }
        return alProducts;
    }

    private void createProduct(Product product, XSSFCell cell, int col) throws Exception{
        switch (col){

            case 0:
                product.setPlace(cell.getStringCellValue());
                break;
            case 1:
                product.setType(cell.getStringCellValue());
                break;
            case 2:
                product.setCategory(cell.getStringCellValue());
                break;
            case 3:
                product.setCode(cell.getStringCellValue());
                break;
            case 4:
                product.getAttributes().put("Brand",cell.getStringCellValue());
//                product.setBrand(cell.getStringCellValue());
                break;
            case 5:
                if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
                {
                    product.getAttributes().put("Price",String.valueOf(cell.getNumericCellValue()));

                }
                // product.setPrice(cell.getNumericCellValue());
                break;
            case 6:
                product.getAttributes().put("Size",cell.getStringCellValue());
                //  product.setSize(cell.getStringCellValue());
                break;
            case 7:
                product.getAttributes().put("Color",cell.getStringCellValue());
//                product.setColor(cell.getStringCellValue());
                break;

        }


    }

}

