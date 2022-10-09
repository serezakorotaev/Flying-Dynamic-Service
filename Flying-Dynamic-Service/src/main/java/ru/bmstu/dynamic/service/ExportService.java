package ru.bmstu.dynamic.service;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.bmstu.dynamic.model.DynamicParameters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExportService {

    private final DynamicService dynamicService;

    public ExportService(DynamicService dynamicService) {
        this.dynamicService = dynamicService;
    }


    public Resource export() throws IOException {
        List<DynamicParameters> dynamicParameters = new ArrayList<>();
        for (double i = -2000; i <= 80000; i += 500) {
        dynamicParameters.add(dynamicService.getParameterByHigh(i));
        }
        final XSSFWorkbook workbook = new XSSFWorkbook();
        fillDocument(dynamicParameters, workbook);
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        workbook.close();
        return new ByteArrayResource(byteArrayOutputStream.toByteArray());
    }

    private void fillDocument(List<DynamicParameters> dynamicParametersList , XSSFWorkbook workbook) {
        final XSSFSheet sheet = workbook.createSheet("Параметры высот");
        fillHeaders(sheet);
        int rowInExcel = 1;
        for (final DynamicParameters dynamicParameters : dynamicParametersList) {
            final XSSFRow row = sheet.createRow(rowInExcel);
            fillRow(dynamicParameters, row);
            rowInExcel ++;
        }
    }

    private void fillRow(DynamicParameters dynamicParameters , XSSFRow row) {
        row.createCell(0, CellType.STRING).setCellValue(dynamicParameters.getHigh());
        row.createCell(1, CellType.STRING).setCellValue(dynamicParameters.getGeopotHigh());
        row.createCell(2, CellType.STRING).setCellValue(dynamicParameters.getDensity());
        row.createCell(3, CellType.STRING).setCellValue(dynamicParameters.getPressure());
        row.createCell(4, CellType.STRING).setCellValue(dynamicParameters.getTemperature());
        row.createCell(5, CellType.STRING).setCellValue(dynamicParameters.getAccelerOfGravity());
        row.createCell(6, CellType.STRING).setCellValue(dynamicParameters.getVelocityOfSound());
    }

    private void fillHeaders(XSSFSheet sheet) {
        final XSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0, CellType.STRING).setCellValue("высота");
        headerRow.createCell(1, CellType.STRING).setCellValue("Геоп.высота");
        headerRow.createCell(2, CellType.STRING).setCellValue("Плотность");
        headerRow.createCell(3, CellType.STRING).setCellValue("Давление");
        headerRow.createCell(4, CellType.STRING).setCellValue("Температура");
        headerRow.createCell(5, CellType.STRING).setCellValue("ускорение св.падения");
        headerRow.createCell(6, CellType.STRING).setCellValue("Скорость звука");
    }

}
