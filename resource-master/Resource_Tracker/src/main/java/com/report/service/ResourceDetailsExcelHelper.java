package com.report.service;

import com.report.entity.ResourceDetails;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ResourceDetailsExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = { "spid", "requested_date", "customer_mgr_name", "bill_rate", "overall_status", "internal_external", "resource_name", "skill_set",
            "no_of_years_experience","profile_shared_date", "customer_interview_date", "l1_interview_date",
            "date_of_join", "flex_field_1_rating", "flex_field_2_rating", "flex_field_3_rating", "flex_field_4_rating","customer_name","stream","oppty_name","manager_name"};
    static String SHEET = "Openneeds";

    public static ByteArrayInputStream resourceDetailsToExcel(List<ResourceDetails> resourceDetails) {

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (ResourceDetails resourceDetail:resourceDetails) {
                Row row = sheet.createRow(rowIdx++);

                CellStyle dateStyle = workbook.createCellStyle();
                dateStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("yyyy-MM-dd"));

                row.createCell(0).setCellValue(resourceDetail.getSpid());
                row.createCell(1).setCellValue(String.valueOf(resourceDetail.getRequestedDate()));
                row.createCell(2).setCellValue(resourceDetail.getCustomerMgrName());
                row.createCell(3).setCellValue(resourceDetail.getBillRate());
                row.createCell(4).setCellValue(resourceDetail.getOverallStatus());
                row.createCell(5).setCellValue(resourceDetail.getInternalExternal());
                row.createCell(6).setCellValue(resourceDetail.getResourceName());
                row.createCell(7).setCellValue(resourceDetail.getSkillSet());
                row.createCell(8).setCellValue(resourceDetail.getNoOfYears());
                row.createCell(9).setCellValue(String.valueOf(resourceDetail.getProfileSharedDate()));
                row.createCell(10).setCellValue(String.valueOf(resourceDetail.getCustomerInterviewDate()));
                row.createCell(11).setCellValue(String.valueOf(resourceDetail.getL1InterviewDate()));
                row.createCell(12).setCellValue(String.valueOf(resourceDetail.getDateOfJoin()));
                row.createCell(13).setCellValue(String.valueOf(resourceDetail.getFlexField1Rating()));
                row.createCell(14).setCellValue(String.valueOf(resourceDetail.getFlexField2Rating()));
                row.createCell(15).setCellValue(String.valueOf(resourceDetail.getFlexField3Rating()));
                row.createCell(16).setCellValue(String.valueOf(resourceDetail.getFlexField4Rating()));
                row.createCell(17).setCellValue(resourceDetail.getCustomerName());
                row.createCell(18).setCellValue(resourceDetail.getStream());
                row.createCell(19).setCellValue(resourceDetail.getOpptyName());
                row.createCell(20).setCellValue(resourceDetail.getManagerName());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}
