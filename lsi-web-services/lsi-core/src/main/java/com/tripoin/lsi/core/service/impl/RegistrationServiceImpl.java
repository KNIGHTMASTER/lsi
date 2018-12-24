package com.tripoin.lsi.core.service.impl;

import com.tripoin.lsi.core.LsiCoreConstant;
import com.tripoin.lsi.core.LsiCoreConstant.ImportAgentIndex;
import com.tripoin.lsi.core.client.impl.UserClientImpl;
import com.tripoin.lsi.core.data.dto.excel.AgentImportDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseUpload;
import com.tripoin.lsi.core.data.model.ImportData;
import com.tripoin.lsi.core.data.model.InterviewEvent;
import com.tripoin.lsi.core.data.model.InterviewHeader;
import com.tripoin.lsi.core.service.IImportDataService;
import com.tripoin.lsi.core.service.IInterviewEventService;
import com.tripoin.lsi.core.service.IInterviewHeaderService;
import com.tripoin.lsi.core.service.IRegistrationService;
import com.tripoin.lsi.core.util.ReaderImportAgentAssignment;
import com.tripoin.lsi.core.util.UploadUtil;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.shared.constant.CommonConstant;
import com.wissensalt.shared.constant.EResponseCode;
import com.wissensalt.shared.dto.request.RequestFindByCodeDTO;
import com.wissensalt.shared.dto.request.RequestInsertUserAgentDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;
import com.wissensalt.shared.dto.response.security.ResponseUserDTO;
import com.wissensalt.util.common.time.FormatDateConstant;
import com.wissensalt.util.common.time.TimeCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * Created on 9/11/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class RegistrationServiceImpl implements IRegistrationService {

    @Autowired
    private UserClientImpl userClient;

    @Autowired
    private IImportDataService importDataService;

    @Value("${file.upload.dir}")
    private String uploadDir;

    @Autowired
    private IInterviewEventService interviewEventService;

    @Autowired
    private IInterviewHeaderService interviewHeaderService;

    @Autowired
    private ReaderImportAgentAssignment readerImportAgentAssignment;

    @Autowired
    private UploadUtil uploadUtil;

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationServiceImpl.class);

    @Override
    public ResponseData registerBulkUser(String p_Bearer, MultipartFile p_MultiPartFile) throws ServiceException {
        ResponseData responseData = new ResponseData(EResponseCode.RC_SUCCESS.getResponseCode(), EResponseCode.RC_SUCCESS.getResponseMsg());
        /*Runnable myrunnable = new Runnable() {
            public void run() {
                asyncImportUser(p_Bearer, p_MultiPartFile);
            }
        };
        new Thread(myrunnable).start();*/
        asyncImportUser(p_Bearer, p_MultiPartFile);

        return responseData;
    }

    @Async
    private void asyncImportUser(String p_Bearer, MultipartFile p_MultiPartFile) {
        ImportData importData = new ImportData();
        importData.setUploadStartDate(new Date());
        try {
            importDataService.insert(importData);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        try {
            ResponseUpload responseUpload = uploadUtil.doUpload(p_MultiPartFile, uploadDir);
            if (responseUpload != null) {
                importData.setUploadEndDate(new Date());
                importData.setUploadStatus(CommonConstant.GeneralValue.ONE);
                importData.setFilePath(responseUpload.getTargetLocation().toString());
                importData.setFileType(p_MultiPartFile.getContentType());
                importData.setFileSize(p_MultiPartFile.getSize());

                importData.setImportStartDate(new Date());
                ResponseData importToDbResult = importDataUserToDB(p_Bearer, responseUpload.getTargetLocation().toString());
                if (importToDbResult.getResponseCode().equals(EResponseCode.RC_SUCCESS.getResponseCode())) {
                    importData.setImportEndDate(new Date());
                    importData.setImportStatus(CommonConstant.GeneralValue.ONE);
                }else {
                    importData.setImportStatus(CommonConstant.GeneralValue.ZERO);
                }
            }else {
//                responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), "Failed Upload File");
                importData.setUploadStatus(CommonConstant.GeneralValue.ZERO);
            }
        } catch (Exception e) {
//            responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), "Failed Upload File");
            importData.setUploadStatus(CommonConstant.GeneralValue.ZERO);
            LOGGER.error("Error Upload File {}", e.toString());
        }
        try {
            importDataService.update(importData, importData.getId());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int registerBulkArea(MultipartFile p_MultiPartFile) throws ServiceException {
        int result = 1;/*
        ImportData importData = new ImportData();
        importData.setUploadStartDate(new Date());
        importDataService.insert(importData);
        try {
            ResponseUpload responseUpload = uploadUtil.doUpload(p_MultiPartFile, uploadDir);
            if (responseUpload != null) {
                importData.setUploadEndDate(new Date());
                importData.setUploadStatus(CommonConstant.GeneralValue.ONE);
                importData.setFilePath(responseUpload.getTargetLocation().toString());
                importData.setFileType(p_MultiPartFile.getContentType());
                importData.setFileSize(p_MultiPartFile.getSize());

                importData.setImportStartDate(new Date());
                int importToDbResult = importDataAreaToDB(responseUpload.getTargetLocation().toString());
                if (importToDbResult == CommonConstant.GeneralValue.ONE) {
                    importData.setImportEndDate(new Date());
                    importData.setImportStatus(CommonConstant.GeneralValue.ONE);
                }else {
                    importData.setImportStatus(CommonConstant.GeneralValue.ZERO);
                }
            }else {
                result = 0;
                importData.setUploadStatus(CommonConstant.GeneralValue.ZERO);
            }
        } catch (Exception e) {
            result = 0;
            importData.setUploadStatus(CommonConstant.GeneralValue.ZERO);
            LOGGER.error("Error Upload File {}", e.toString());
        }
        importDataService.update(importData, importData.getId());*/
        return result;
    }

    @Async
    private ResponseData importDataUserToDB(String p_Bearer, String p_FilePath) {
        ResponseData responseData = new ResponseData(EResponseCode.RC_SUCCESS.getResponseCode(), EResponseCode.RC_SUCCESS.getResponseMsg());
        List<AgentImportDTO> agentImportDTOs = readerImportAgentAssignment.read(p_FilePath);

        Date currentDate = new Date();
        int a=1;
        for (AgentImportDTO agentImportDTO : agentImportDTOs) {
            LOGGER.info("Processing Row {}", a);
            a++;
            if (agentImportDTO.getContents()[ImportAgentIndex.USER_NAME] != null) {
                LOGGER.info("Processing {}", agentImportDTO.getContents()[ImportAgentIndex.USER_NAME]);

                InterviewEvent interviewEvent = null;
                if (agentImportDTO.getContents()[ImportAgentIndex.EVENT_ID] != null) {
                    try {
                        interviewEvent = interviewEventService.findById(Long.valueOf(agentImportDTO.getContents()[ImportAgentIndex.EVENT_ID]));
                    } catch (ServiceException e) {
                        LOGGER.error("Error Searching Event {}", e.toString());
                        responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), "Event is Not Found");
                    }
                }else {
                    LOGGER.error("Event From Worksheet Is Null");
                }
                RequestFindByCodeDTO requestFindByCodeDTO = new RequestFindByCodeDTO();
                requestFindByCodeDTO.setCode(agentImportDTO.getContents()[ImportAgentIndex.USER_NAME]);
                ResponseUserDTO responseUserDTO = userClient.findAgentByUserName(p_Bearer, requestFindByCodeDTO);
                if (responseUserDTO == null) {
                    LOGGER.info("Agent Not Found - Process Insert New Agent");
                    RequestInsertUserAgentDTO requestInsertUserAgentDTO = new RequestInsertUserAgentDTO();
                    requestInsertUserAgentDTO.setUserName(agentImportDTO.getContents()[ImportAgentIndex.USER_NAME]);
                    requestInsertUserAgentDTO.setPassword(agentImportDTO.getContents()[ImportAgentIndex.USER_NAME]);
                    requestInsertUserAgentDTO.setExpiredDate(FormatDateConstant.DEFAULT.format(TimeCalculator.addNDaysToDate(LsiCoreConstant.DEFAULT_AGENT_EXPIRED_DAY, currentDate)));
                    if (agentImportDTO.getContents()[ImportAgentIndex.COMPANY_ID] != null) {
                        requestInsertUserAgentDTO.setBranchId(Long.valueOf(agentImportDTO.getContents()[ImportAgentIndex.COMPANY_ID]));
                        responseUserDTO = userClient.insertUserAgent(p_Bearer, requestInsertUserAgentDTO);
                        LOGGER.info("Finished Processing Insert User Agent");
                    }else {
                        LOGGER.error("Company Id Can Not Be Null");
                    }
                }
                if (interviewEvent != null) {
                    if (responseUserDTO != null) {
                        if (responseUserDTO.getId() != null) {
                            LOGGER.info("Agent Found - Process Assign Agent");
                            InterviewHeader interviewHeader = null;
                            try {
                                interviewHeader = interviewHeaderService.findByEventIdAndUserIdAndVillageId(interviewEvent.getId(), responseUserDTO.getId(), Long.valueOf(agentImportDTO.getContents()[ImportAgentIndex.VILLAGE_ID]));
                            } catch (ServiceException e) {
                                LOGGER.error("Error Find Header By Event User And Village {}", e.toString());
                            }
                            if (interviewHeader == null) {
                                LOGGER.info("Initiate new assignment");
                                interviewHeader = new InterviewHeader();
                                interviewHeader.setUserId(responseUserDTO.getId());
                                interviewHeader.setBranchId(Long.valueOf(agentImportDTO.getContents()[ImportAgentIndex.COMPANY_ID]));
                                interviewHeader.setProvinceId(Long.valueOf(agentImportDTO.getContents()[ImportAgentIndex.PROVINCE_ID]));
                                interviewHeader.setCityId(Long.valueOf(agentImportDTO.getContents()[ImportAgentIndex.CITY_ID]));
                                interviewHeader.setDistrictId(Long.valueOf(agentImportDTO.getContents()[ImportAgentIndex.DISTRICT_ID]));
                                interviewHeader.setVillageId(Long.valueOf(agentImportDTO.getContents()[ImportAgentIndex.VILLAGE_ID]));
                                interviewHeader.setNumberOfRespondent(Integer.valueOf(agentImportDTO.getContents()[ImportAgentIndex.RESPONDENT_AMOUNT]));
                                interviewHeader.setInterviewEvent(interviewEvent);
                                try {
                                    interviewHeaderService.update(interviewHeader);
                                    LOGGER.info("Finished Processing Assignment");
                                } catch (ServiceException e) {
                                    LOGGER.error("Error Assign Agent");
                                    responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), "Error Assign Agent");
                                }
                            }else {
                                /*responseData = new ResponseData(
                                        EResponseCode.RC_FAILURE.getResponseCode(),
                                        "Can not assign User "+responseUserDTO.getId()+"-"+agentImportDTO.getContents()[ImportAgentIndex.VILLAGE_ID]+"-"+interviewEvent.getId()
                                );*/
                                LOGGER.error("Update assign User {} To {} in Event {}", responseUserDTO.getId(), agentImportDTO.getContents()[ImportAgentIndex.VILLAGE_ID], interviewEvent.getId());
                                interviewHeader.setUserId(responseUserDTO.getId());
                                interviewHeader.setBranchId(Long.valueOf(agentImportDTO.getContents()[ImportAgentIndex.COMPANY_ID]));
                                interviewHeader.setProvinceId(Long.valueOf(agentImportDTO.getContents()[ImportAgentIndex.PROVINCE_ID]));
                                interviewHeader.setCityId(Long.valueOf(agentImportDTO.getContents()[ImportAgentIndex.CITY_ID]));
                                interviewHeader.setDistrictId(Long.valueOf(agentImportDTO.getContents()[ImportAgentIndex.DISTRICT_ID]));
                                interviewHeader.setVillageId(Long.valueOf(agentImportDTO.getContents()[ImportAgentIndex.VILLAGE_ID]));
                                interviewHeader.setNumberOfRespondent(Integer.valueOf(agentImportDTO.getContents()[ImportAgentIndex.RESPONDENT_AMOUNT]));
                                interviewHeader.setInterviewEvent(interviewEvent);
                                try {
                                    interviewHeaderService.update(interviewHeader);
                                    LOGGER.info("Finished Processing Assignment");
                                } catch (ServiceException e) {
                                    LOGGER.error("Error Assign Agent");
                                    responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), "Error Assign Agent");
                                }
                            }
                        }else {
                            responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), "Failed Insert User Agent");
                            LOGGER.error("Failed Insert User Agent || User Agent is Not Found");
                        }
                    }else {
                        LOGGER.error("Failed To Insert User");
                    }
                }else {
                    responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), "Event is Not Found, Process Assignment Failed");
                    LOGGER.error("Event is Not Found, Process Assignment Failed");
                }
            } else {
                LOGGER.error("User Name Can Not Be Null");
            }
        }
        return responseData;
    }
}
