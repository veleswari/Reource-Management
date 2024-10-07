package com.report.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import com.report.entity.OpenNeeds;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class OpenneedsExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = { "spid", "project", "role", "resourcename", "status", "experience", "initial_status",
            "profile_shared_to_dh_date", "dh_interview_date", "no_of_days_ageing_for_dh_interview",
            "dh_feedback_date", "dh_lead_name_for_interview", "ageing_for_interview_feedback",
            "latest_status", "role_closed_date", "remarks", "hv_interview_rating", "interview_slots","custom_id" };
    static String SHEET = "Openneeds";

    public static ByteArrayInputStream openneedsToExcel(List<OpenNeeds> openNeeds) {

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (OpenNeeds openneed : openNeeds) {
                Row row = sheet.createRow(rowIdx++);

                CellStyle dateStyle = workbook.createCellStyle();
                dateStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("yyyy-MM-dd"));

                row.createCell(0).setCellValue(openneed.getSPID());
                row.createCell(1).setCellValue(openneed.getProject());
                row.createCell(2).setCellValue(openneed.getRole());
                row.createCell(3).setCellValue(openneed.getResourceName());
                row.createCell(4).setCellValue(openneed.getStatus());
                row.createCell(5).setCellValue(openneed.getExperience());
                row.createCell(6).setCellValue(openneed.getInitialStatus());
                row.createCell(7).setCellValue(openneed.getProfileSharedToDHDate());

                Cell dhinterviewcell = row.createCell(8);
                dhinterviewcell.setCellStyle(dateStyle);
                dhinterviewcell.setCellValue(openneed.getDHInterviewDate());

                row.createCell(9).setCellValue(openneed.getNoOfDaysAgeingForDHInterview());

                Cell dhfeedbackcell = row.createCell(10);
                dhfeedbackcell.setCellStyle(dateStyle);
                dhfeedbackcell.setCellValue(openneed.getDHFeedbackDate());

                row.createCell(11).setCellValue(openneed.getDHLeadNameForInterview());
                row.createCell(12).setCellValue(openneed.getAgeingForInterviewFeedback());
                row.createCell(13).setCellValue(openneed.getLatestStatus());

                Cell roleclosedcell = row.createCell(14);
                roleclosedcell.setCellStyle(dateStyle);
                roleclosedcell.setCellValue(openneed.getRoleClosedDate());

                row.createCell(15).setCellValue(openneed.getRemarks());
                row.createCell(16).setCellValue(openneed.getHVInterviewRating());
                row.createCell(17).setCellValue(openneed.getInterviewSlots());
                row.createCell(18).setCellValue(openneed.getCustomId());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}
