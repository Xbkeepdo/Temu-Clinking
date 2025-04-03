package com.ch.clinking.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.clinking.entity.Merchandise;
import com.ch.clinking.entity.ProductionInfo;
import com.ch.clinking.mapper.MerMapper;
import com.ch.clinking.service.MerService;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import javax.annotation.Resource;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service("merService")
public class MerServiceImpl extends ServiceImpl<MerMapper, Merchandise> implements MerService {

    @Resource
    private MerMapper merMapper;

    public Merchandise selectBySkcId(String skcId) {
        return merMapper.selectBySkcId(skcId);
    }

    public List<String> selectAllSkcId(String shopId) {
        return merMapper.selectAllSkcId(shopId);
    }

    public int existsBySkcId(String skcId) {
        return merMapper.existsBySkcId(skcId);
    }


    @Override
    public String getProductInfoPDF(Merchandise merchandise, ProductionInfo productionInfo){
//        String templatePath = "D:\\mb.xlsx"; // 模板路径
        String templatePath = "/root/Clinking/Xlsx/ProductInfo/mb.xlsx"; // 模板路径

        String outputPath = "/root/Clinking/Xlsx/ProductInfo/ProductInfo_"+merchandise.getSkcId()+".xlsx"; // 输出路径
//        String outputPath = "D:\\ProductInfo_"+merchandise.getSkcId()+".xlsx"; // 输出路径
//        String outputPath = "src/main/resources/ProductInfo_"+merchandise.getSkcId()+".xlsx"; // 输出路径

        try (FileInputStream fis = new FileInputStream(templatePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            // 获取工作表
            Sheet sheet = workbook.getSheetAt(0); // 假设使用第一个工作表

            // 替换占位符
            Row row2 = sheet.getRow(1); // 第二行
            row2.getCell(1).setCellValue(merchandise.getProductNumber());
            row2.getCell(3).setCellValue(merchandise.getShopType());
            if (merchandise.getWeight() != null) {
                row2.getCell(5).setCellValue(merchandise.getWeight());
            }

            if (productionInfo.getFabricSampleWeight() != null) {
                row2.getCell(7).setCellValue(productionInfo.getFabricSampleWeight());
            }

            Row row3 = sheet.getRow(2);
            row3.getCell(1).setCellValue(productionInfo.getMaterial());

            insertImgToExcel(workbook,sheet,merchandise.getFirstImage(),0,3,3,17);

            if (!productionInfo.getDetailImg().equals("default.png")) {

                insertImgToExcel(workbook,sheet,productionInfo.getDetailImg(),4,3,7,17);

            }

            if (merchandise.getSizeInfo() != null) {
                // 尺码
                String[][] tableData = parseTableData(merchandise.getSizeInfo());

                System.out.println(productionInfo.getClothInfo());

                System.out.println(Files.exists(Paths.get(templatePath)));


                // 添加表头
                if (tableData.length > 0) {
                    Row row10 = sheet.getRow(19); // 第二行
                    for (int i = 0; i < tableData[0].length; i++) {
                        row10.getCell(i).setCellValue(tableData[0][i]);
                    }
                }

                // 添加数据行
                for (int i = 1; i < tableData.length; i++) {
                    Row rowSize = sheet.getRow(19+i); // 第二行
                    for (int j = 0; j < tableData[i].length; j++) {
                        rowSize.getCell(j).setCellValue(tableData[i][j]);
                    }
                }
            }

            if (productionInfo.getClothInfo() != null) {
                // 布料
                // 按照换行符 \n 将字符串拆分为每个 item
                String[] items = productionInfo.getClothInfo().split("\n");

                for (int i = 0; i < items.length; i++) {
                    // 按照 \t 分隔 item 的各个部分
                    String[] itemParts = items[i].split("\t");
                    if (itemParts.length == 1) {
                        break;
                    }

                    if (i == 0) {
                        Row rowClothText = sheet.getRow(27);
                        Row rowClothIndo = sheet.getRow(28);
                        rowClothText.getCell(0).setCellValue("名称");
                        rowClothText.getCell(1).setCellValue("颜色/色号");
                        rowClothText.getCell(2).setCellValue("单位/规格");
                        rowClothText.getCell(3).setCellValue("供应商");
                        rowClothIndo.getCell(0).setCellValue(itemParts[0]);
                        rowClothIndo.getCell(1).setCellValue(itemParts[1]);
                        rowClothIndo.getCell(2).setCellValue(itemParts[2]);
                        rowClothIndo.getCell(3).setCellValue(itemParts[3]);
                    }

                    if (i == 1) {
                        Row rowClothText = sheet.getRow(27);
                        Row rowClothIndo = sheet.getRow(28);
                        rowClothText.getCell(4).setCellValue("名称");
                        rowClothText.getCell(5).setCellValue("颜色/色号");
                        rowClothText.getCell(6).setCellValue("单位/规格");
                        rowClothText.getCell(7).setCellValue("供应商");
                        rowClothIndo.getCell(4).setCellValue(itemParts[0]);
                        rowClothIndo.getCell(5).setCellValue(itemParts[1]);
                        rowClothIndo.getCell(6).setCellValue(itemParts[2]);
                        rowClothIndo.getCell(7).setCellValue(itemParts[3]);
                    }

                    if (i == 2) {
                        Row rowClothText = sheet.getRow(29);
                        Row rowClothIndo = sheet.getRow(30);
                        rowClothText.getCell(0).setCellValue("名称");
                        rowClothText.getCell(1).setCellValue("颜色/色号");
                        rowClothText.getCell(2).setCellValue("单位/规格");
                        rowClothText.getCell(3).setCellValue("供应商");
                        rowClothIndo.getCell(0).setCellValue(itemParts[0]);
                        rowClothIndo.getCell(1).setCellValue(itemParts[1]);
                        rowClothIndo.getCell(2).setCellValue(itemParts[2]);
                        rowClothIndo.getCell(3).setCellValue(itemParts[3]);
                    }

                    if (i == 3) {
                        Row rowClothText = sheet.getRow(29);
                        Row rowClothIndo = sheet.getRow(30);
                        rowClothText.getCell(4).setCellValue("名称");
                        rowClothText.getCell(5).setCellValue("颜色/色号");
                        rowClothText.getCell(6).setCellValue("单位/规格");
                        rowClothText.getCell(7).setCellValue("供应商");
                        rowClothIndo.getCell(4).setCellValue(itemParts[0]);
                        rowClothIndo.getCell(5).setCellValue(itemParts[1]);
                        rowClothIndo.getCell(6).setCellValue(itemParts[2]);
                        rowClothIndo.getCell(7).setCellValue(itemParts[3]);
                    }

                }
            }

            if (productionInfo.getFuLiaoInfo() != null) {
                // 辅料
                // 按照换行符 \n 将字符串拆分为每个 item
                String[] fuLiaoItems = productionInfo.getFuLiaoInfo().split("\n");

                Row rowFuLiaoText = sheet.getRow(32);
                Row rowFuLiaoIndo = sheet.getRow(33);

                // 遍历每个 item 并添加到容器中
                for (int i = 0; i < fuLiaoItems.length; i++) {
                    // 按照 \t 分隔 item 的各个部分
                    String[] itemParts = fuLiaoItems[i].split("\t");

                    if (itemParts.length == 1) {
                        break;
                    }

                    rowFuLiaoText.getCell(i*2).setCellValue("名称");
                    rowFuLiaoText.getCell(i*2+1).setCellValue("规格");
                    rowFuLiaoIndo.getCell(i*2).setCellValue(itemParts[0]);
                    rowFuLiaoIndo.getCell(i*2+1).setCellValue(itemParts[1]);

                }

                if (productionInfo.getTechnologicalRequirements() != null) {
                    Row rowGongYi = sheet.getRow(35);
                    rowGongYi.getCell(0).setCellValue(productionInfo.getTechnologicalRequirements());
                }

                if (productionInfo.getRemark() != null) {
                    Row rowReMark = sheet.getRow(37);
                    rowReMark.getCell(0).setCellValue(productionInfo.getRemark());
                }
            }





            // 保存更新后的工作簿到输出路径
            try (FileOutputStream fos = new FileOutputStream(outputPath)) {
                workbook.write(fos);
            }

            return "Excel file saved successfully at " + outputPath;

        } catch (IOException e) {
            e.printStackTrace();
            return "Error occurred while saving the Excel file.";
        }
    }

    private void insertImgToExcel(Workbook workbook, Sheet sheet, String firstImage, int col1, int row1, int col2, int row2) throws IOException {

        String IMAGE_DIRECTORY = "/root/Clinking/Image/Mer/";

        // 读取图片文件
//        InputStream imageInputStream = new FileInputStream("/root/Clinking/Image/Mer/"+firstImage);
        InputStream imageInputStream = new FileInputStream(IMAGE_DIRECTORY + firstImage);
        byte[] imageBytes = IOUtils.toByteArray(imageInputStream);

        // 将图片添加到工作簿中
        int pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
        imageInputStream.close();

        // 创建一个 Drawing 对象
        Drawing<?> drawing = sheet.createDrawingPatriarch();

        // 创建一个锚点，设置图片插入的位置
        CreationHelper helper = workbook.getCreationHelper();
        ClientAnchor anchor = helper.createClientAnchor();

//        // 图片要插入的单元格区域
//        col1 = 0;  // 左上角起始列
//        row1 = 3;  // 左上角起始行
//        col2 = 3;  // 右下角结束列
//        row2 = 17;  // 右下角结束行

        // 设置锚点的起始和结束位置
        anchor.setCol1(col1);
        anchor.setRow1(row1);
        anchor.setCol2(col2);
        anchor.setRow2(row2);

        // 插入图片
        Picture pict = drawing.createPicture(anchor, pictureIdx);

        // 自动调整图片大小以适应单元格
//        pict.resize();

        // 获取单元格区域的宽度和高度来计算图片居中位置
//        float cellWidth = 0;
//        for (int i = col1; i < col2; i++) {
//            cellWidth += sheet.getColumnWidthInPixels(i); // 获取总列宽
//        }
        float cellHeight = (float) (28 * 16 * 4.167);
//        for (int i = row1; i < row2; i++) {
//            cellHeight += sheet.getRow(i).getHeightInPoints() / 72 * 96; // 获取总行高 (in pixels)
//            System.out.println(cellHeight+";"+sheet.getRow(i).getHeightInPoints()+";"+sheet.getRow(i).getHeightInPoints() / 72 * 96);
//        }

        // 获取图片的实际宽高
        double imageWidth = pict.getImageDimension().getWidth();
        double imageHeight = pict.getImageDimension().getHeight();

        // 计算缩放比例，使图片能适应单元格区域
        double scale = cellHeight / imageHeight;
        System.out.println(scale+";"+cellHeight+";"+imageHeight);

        // 设置图片大小
        pict.resize(scale);

    }


    private String[][] parseTableData(String tableData) {
        String[] rows = tableData.split("\n");
        String[][] parsedData = new String[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            parsedData[i] = rows[i].split("\t");
        }
        return transposeArray(parsedData);
    }

    // 反转二维数组的方法
    public static String[][] transposeArray(String[][] array) {
        int rows = array.length;
        int cols = array[0].length;
        String[][] transposed = new String[cols][rows]; // 创建反转后的数组

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = array[i][j]; // 反转
            }
        }
        return transposed;
    }

//    @Override
//    public List<Merchandise> list(Map<String, Object> map) {
//        return merMapper.list(map);
//    }
//
//    @Override
//    public Long getTotal(Map<String, Object> map) {
//        return merMapper.getTotal(map);
//    }
//
//    @Override
//    public void add(Merchandise merchandise) {
//        merMapper.add(merchandise);
//    }
//
//    @Override
//    public void update(Merchandise merchandise) {
//        merMapper.update(merchandise);
//    }
//
//    @Override
//    public Merchandise findById(Integer id) {
//        return merMapper.findById(id);
//    }
}
