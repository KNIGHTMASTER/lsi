package com.tripoin.lsi.core.util;

import com.tripoin.lsi.core.data.dto.excel.AgentImportDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created on 9/27/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class ReaderImportAgentAssignment {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReaderImportAgentAssignment.class);

    public List<AgentImportDTO> read(String p_FileName) {
        List<AgentImportDTO> result = new ArrayList<>();
        FileInputStream excelFile = null;
        try {
            excelFile = new FileInputStream(new File(p_FileName));
        } catch (FileNotFoundException e) {
            LOGGER.error("Error Reading File {}", e.toString());
        }
        Workbook workbook = null;
        if (excelFile != null) {
            try {
                workbook = new XSSFWorkbook(excelFile);
            } catch (IOException e) {
                LOGGER.error("Error Reading Workbook {}", e.toString());
            }
            if (workbook != null) {
                if (workbook.getNumberOfSheets() > 0) {
                    Sheet sheet = workbook.getSheetAt(0);
                    Iterator<Row> rowIterator = sheet.rowIterator();
                    /*Skip 1st Line*/
                    rowIterator.next();
                    FormulaEvaluator mainEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
                    while (rowIterator.hasNext()) {
                        AgentImportDTO agentAssignmentDTO = new AgentImportDTO();
                        Row currentRow = rowIterator.next();
                        Iterator<Cell> cellIterator = currentRow.cellIterator();
                        String[] contents = new String[14];
                        int a = 0;
                        while (cellIterator.hasNext()) {
                            Cell currentCell = cellIterator.next();
                            LOGGER.info("EVALUATING CELL {} {}", currentCell.getAddress().getRow(), currentCell.getAddress().getColumn());
                            if (currentCell.getCellType() == CellType.STRING) {
                                contents[a] = (currentCell.getStringCellValue());
                                LOGGER.info("CELL IS STRING");
                            } else if (currentCell.getCellType() == CellType.NUMERIC) {
                                LOGGER.info("CELL IS NUMERIC");
                                contents[a] = (String.format("%.0f", currentCell.getNumericCellValue()));
                            }else if (currentCell.getCellType() == CellType.FORMULA) {
                                LOGGER.info("CELL IS FORMULA");
                                contents[a] = (String.format("%.0f", mainEvaluator.evaluateInCell(currentCell).getNumericCellValue()));
                            }else if (currentCell.getCellType() == CellType.BLANK) {
                                LOGGER.info("CELL IS BLANK");
                                contents[a] = null;
                            }else {
                                LOGGER.info("CELL IS UNDEFINED");
                            }
                            a++;
                        }
                        agentAssignmentDTO.setContents(contents);
                        result.add(agentAssignmentDTO);
                    }
                }else {
                    LOGGER.error("Workbook has no Sheet");
                }
            }else {
                LOGGER.error("Workbook is Null");
            }
        }else {
            LOGGER.error("Excel File not Found");
        }
        return result;
    }
}
