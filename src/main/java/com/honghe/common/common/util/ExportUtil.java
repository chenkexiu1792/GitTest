package com.honghe.common.common.util;

import com.honghe.common.common.entity.ExcelRow;
import com.honghe.common.common.entity.ExcelTitle;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author youye
 * @Date 2019/4/30 13:45
 * @Description TODO
 **/
public class ExportUtil {

    static Logger logger = LoggerFactory.getLogger(ExportUtil.class);

    /**
     * 生成EXCEL表
     * @param sheetName   sheet的名字
     * @param titleList   表头的header，string[]类型
     * @param dataList   数据，list<string[]>类型
     * @param filePath    保存的路径
     */
    public static boolean exportExcelByDatas (String filePath,String sheetName, List<ExcelTitle> titleList, List<ExcelRow> dataList) {
        boolean result = false;

        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath);

            // Excel的sheet的最大条数65535，现在以为60000为一个sheet最大数据进行存储，如下进行处理
            int sheetSize = 60000;

            // 计算sheet的个数
            int sheetPages = dataList.size() / sheetSize + (dataList.size() % sheetSize > 0 ? 1 : 0);

            // 创建一个webbook，对应一个Excel文件
            HSSFWorkbook wb = new HSSFWorkbook();
            for(int i=0;i<sheetPages;i++){

                // 当前sheet数据的开始索引和结束索引
                int fromIndex = i * sheetSize;
                int toIndex = (dataList.size() > (i + 1) * sheetSize ? fromIndex + sheetSize:  dataList.size());
                // 索引：fromIndex 若是0则截取时包含索引数据，若>0时则截取时不包含索引数据，toIndex 截取时包含索引数据
                List<ExcelRow> sheetDataList = dataList.subList(fromIndex,toIndex);

                // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
                String newSheetName = sheetName + "_" + (i + 1);
                HSSFSheet sheet = wb.createSheet(newSheetName);

                // 1.设置Excel的表格的标题
                setExcelTitleCells(wb,sheet,titleList);

                // 2.填充Excel的表格数据
                setExcelDataCells(wb,sheet,titleList,sheetDataList,out);
            }

            wb.write(out);
            result = true;

        } catch (FileNotFoundException e) {
            logger.error("生成FileOutputStream失败，路径："+filePath,e);
        } catch (IOException e){
            logger.error("关闭输出流失败，e=",e);
        } finally {
            try {
                if (null != out){
                    out.close();
                }
            } catch (Exception e){}
        }

        return result;
    }

    /**
     * 写入Excel表格表头数据
     * @param wb Excel表
     * @param sheet 表单名
     * @param titleList 标题列表
     */
    private static void setExcelTitleCells(HSSFWorkbook wb, HSSFSheet sheet, List<ExcelTitle> titleList){
        HSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);

        //设置字体
        HSSFFont titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short)11);//设置字体大小
        titleFont.setBold(true);      // 设置加粗
        titleStyle.setFont(titleFont);//选择需要用到的字体格式  HSSFFont.BOLDWEIGHT_BOLD

        Font stringFont = new Font(titleFont.getFontName(),Font.PLAIN, 11);
        Graphics graphics = new java.awt.image.BufferedImage(10,10,java.awt.image.BufferedImage.TYPE_INT_ARGB).getGraphics();
        FontMetrics fontMetrics = graphics.getFontMetrics(stringFont);
        //FontMetrics fontMetrics = sun.font.FontDesignMetrics.getMetrics(stringFont);

        // 创建第1行
        HSSFRow row = sheet.createRow(0);
        // 对第1行创建表头列，并设置表头内容
        for(int i=0;i<titleList.size();i++){
            ExcelTitle titleItem = titleList.get(i);
            String title = titleItem.getTitle();

            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(titleStyle);
            cell.setCellValue(title);

            int titleWidth = fontMetrics.stringWidth(title);
            sheet.setColumnWidth(i,titleWidth * 110); // 256->85->110

            titleItem.setColummTextMaxWidth(titleItem.isShowCenter() ? titleWidth : titleWidth * titleItem.getTitleWidthMultiple());
        }
    }

    /**
     * 写入Excel表格数据
     * @param wb Excel表
     * @param sheet 表单名
     * @param titleList 标题列表
     * @param dataList 数据列表
     */
    private static void setExcelDataCells(HSSFWorkbook wb, HSSFSheet sheet,List<ExcelTitle> titleList, List<ExcelRow> dataList,OutputStream out){
        try {
            HSSFCellStyle centerStyle = wb.createCellStyle();
            centerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            centerStyle.setAlignment(HorizontalAlignment.CENTER);

            HSSFCellStyle leftStyle = wb.createCellStyle();
            leftStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            leftStyle.setAlignment(HorizontalAlignment.LEFT);

            //设置字体
            HSSFFont contentFont = wb.createFont();
            contentFont.setFontHeightInPoints((short)10);//设置字体大小
            centerStyle.setFont(contentFont);
            leftStyle.setFont(contentFont);

            Font stringFont = new Font(contentFont.getFontName(),Font.PLAIN, 10);
            Graphics graphics = new java.awt.image.BufferedImage(10,10,java.awt.image.BufferedImage.TYPE_INT_ARGB ).getGraphics();
            FontMetrics fontMetrics = graphics.getFontMetrics(stringFont);
            //FontMetrics fontMetrics = sun.font.FontDesignMetrics.getMetrics(stringFont);

            // 2.写入Excel 表中数据
            for (int i=0; i<dataList.size(); i++) {
                ExcelRow rowItem = dataList.get(i);
                HSSFRow row = sheet.createRow(i + 1);

                // 创建单元格，并设置值
                for (int j=0; j<rowItem.getColumnTextList().size(); j++) {
                    String columnText = rowItem.getColumnTextList().get(j);
                    try{
                        // 列宽
                        ExcelTitle titleItem = titleList.get(j);
                        Integer colummTextMaxWidth = titleItem.getColummTextMaxWidth();
                        int textWidth = fontMetrics.stringWidth(columnText);
                        if (titleItem.isShowCenter() && colummTextMaxWidth.intValue() < textWidth){
                            colummTextMaxWidth = textWidth;
                            titleItem.setColummTextMaxWidth(colummTextMaxWidth);
                        }

                        HSSFCell cell = row.createCell(j);
                        cell.setCellStyle(titleItem.isShowCenter() ? centerStyle : leftStyle);
                        cell.setCellValue(columnText);

                        if (i == dataList.size() - 1){
                            sheet.setColumnWidth(j,colummTextMaxWidth * 110); // 256->85->100
                        }
                    }catch (Exception e){
                        logger.error("写入Excel 单元格数据失败，e=",e);
                    }
                }
            }
        } catch (Exception e){
            logger.error("写入Excel表格数据失败，e=",e);
        }
    }




















    /**
     * 生成EXCEL表
     * @param sheetName   sheet的名字
     * @param title   表头的header，string[]类型
     * @param dataArray   数据，list<string[]>类型
     * @param filePath    保存的路径
     */
    public static void exportExcel (String sheetName, String[] title, List<String[]> dataArray, String filePath) {

        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath);
            HSSFWorkbook wb = new HSSFWorkbook();    // 创建一个webbook，对应一个Excel文件
            HSSFSheet sheet = wb.createSheet(sheetName);     // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet

            wb = setHeadStyle( wb, sheet, title, 500, 10, HSSFFont.BOLDWEIGHT_BOLD);  // 第三步，设置表头样式
            setExportDate(wb, sheetName, dataArray, out);   // 第四步，写入数据
        } catch (FileNotFoundException e) {
            logger.error("生成FileOutputStream失败，路径："+filePath,e);
        } finally {
            try {
                if (null != out){
                    out.close();
                }
            } catch (Exception e){}
        }
    }





    /**
     *
     *  设置样式
     * @param wb    表格
     * @param height    高度
     * @param fontSize    字体大小
     * @param fontWeight    字体粗细
     * @return 1
     */
    private static HSSFWorkbook setHeadStyle( HSSFWorkbook wb, HSSFSheet sheet, String[] title, int height, int fontSize, short fontWeight){
        HSSFCellStyle style = wb.createCellStyle();
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中

        //设置字体
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) fontSize);//设置字体大小
        font.setBoldweight(fontWeight);//粗体粗细
        style.setFont(font);//选择需要用到的字体格式

        HSSFRow row = sheet.createRow((int) 0);   // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        row.setHeight((short) height);  //设置高度
        for(int i=0;i<title.length;i++){
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(title[i]);
        }

        //数值自动列宽
        for (int i = 0; i < title.length; i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i,sheet.getColumnWidth(i)*17/10);
        }

        //创建单元格，并设置值表头 设置表头居中
        return wb;
    }


    /**
     * 写入数据
     * @param wb    HSSFWorkbook
     * @param sheetName
     * @param dataArray
     * @param out
     */
    @SuppressWarnings("unchecked")
    private static void setExportDate( HSSFWorkbook wb, String sheetName, List<String[]> dataArray, OutputStream out) {
        try {
            HSSFSheet sheet = wb.getSheet(sheetName);
            int c = 0;
            for (int i=0;i<dataArray.size();i++) {
                c++;
                HSSFRow row = sheet.createRow((int) c);
                String[] data = dataArray.get(i);
                // 第四步，创建单元格，并设置值
                for (int j = 0; j < data.length; j++) {
                    try{
                        row.createCell(j).setCellValue(data[j]);
                        if (data[j] != null && data[j].length() > 0) {
                            sheet.setColumnWidth(j, data[j].toString().length() * 712);
                        }
                    }catch (Exception e){
                        logger.error("写入excel失败",e);
                        continue;
                    }
                }
            }
            wb.write(out);
        } catch (IOException e) {
            logger.error("生成excel失败",e);
        }
    }

    public static void main(String[] args){
        List<String[]> list = new ArrayList<>();
        ExportUtil exportExcelService = new ExportUtil();
        String[] title = {"会议室","门牌号"};
        String[] data1 = {"会议室1","0501"};
        list.add(data1);
        String[] data2 = {"会议室2","0502"};
        list.add(data2);
        exportExcelService.exportExcel("ceshi",title,list,"e://a.xls");
    }
}
