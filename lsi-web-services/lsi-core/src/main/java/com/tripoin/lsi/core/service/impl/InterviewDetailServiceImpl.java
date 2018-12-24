package com.tripoin.lsi.core.service.impl;

import com.tripoin.lsi.core.LsiCoreConstant;
import com.tripoin.lsi.core.dao.IInterviewDetailDAO;
import com.tripoin.lsi.core.data.dto.request.*;
import com.tripoin.lsi.core.data.model.InterviewDetail;
import com.tripoin.lsi.core.data.model.InterviewHeader;
import com.tripoin.lsi.core.data.model.InterviewImage;
import com.tripoin.lsi.core.data.model.InterviewUserAnswer;
import com.tripoin.lsi.core.service.*;
import com.tripoin.lsi.core.util.PaginationUtil;
import com.wissensalt.scaffolding.exception.DAOException;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.AScaffoldingService;
import com.wissensalt.shared.constant.EResponseCode;
import com.wissensalt.shared.dto.response.base.ResponseData;
import com.wissensalt.util.common.time.FormatDateConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class InterviewDetailServiceImpl extends AScaffoldingService<InterviewDetail, RequestInterviewDetailDTO> implements IInterviewDetailService {

    @Autowired
    private IInterviewDetailDAO interviewDetailDAO;

    @Autowired
    private IInterviewHeaderService interviewHeaderService;

    @Autowired
    private IInterviewImageService interviewImageService;

    @Autowired
    private IInterviewUserAnswerService interviewUserAnswerService;

    @Autowired
    private IEventCandidateService eventCandidateService;

    private static final Logger LOGGER = LoggerFactory.getLogger(InterviewDetailServiceImpl.class);

    @PostConstruct
    @Override
    public void initService() {
        scaffoldingDAO = interviewDetailDAO;
    }

    @Override
    public InterviewDetail generateEntityForCommonTrx(RequestInterviewDetailDTO p_RequestInterviewDetailDTO) throws ServiceException {
        InterviewDetail interviewDetail = new InterviewDetail();
        interviewDetail = matchBaseFields(interviewDetail, p_RequestInterviewDetailDTO);
        interviewDetail.setLatitude(p_RequestInterviewDetailDTO.getLatitude());
        interviewDetail.setLongitude(p_RequestInterviewDetailDTO.getLongitude());
        interviewDetail.setInterviewStatus(p_RequestInterviewDetailDTO.getInterviewStatus());
        interviewDetail.setRespondentName(p_RequestInterviewDetailDTO.getRespondentName());
        interviewDetail.setRespondentAge(p_RequestInterviewDetailDTO.getRespondentAge());
        interviewDetail.setRespondentReligion(p_RequestInterviewDetailDTO.getRespondentReligion());
        interviewDetail.setInterviewScore(p_RequestInterviewDetailDTO.getInterviewScore());
        try {
            interviewDetail.setInterviewStartTime(FormatDateConstant.DEFAULT.parse(p_RequestInterviewDetailDTO.getInterviewStartDate()));
            interviewDetail.setInterviewEndTime(FormatDateConstant.DEFAULT.parse(p_RequestInterviewDetailDTO.getInterviewEndDate()));
        } catch (ParseException e) {
            LOGGER.error("Error Parsing Date {} ", e.toString());
        }
        if (p_RequestInterviewDetailDTO.getInterviewHeaderId() != null) {
            InterviewHeader interviewHeader = interviewHeaderService.findById(p_RequestInterviewDetailDTO.getInterviewHeaderId());
            interviewDetail.setInterviewHeader(interviewHeader);
        }
        interviewDetail.setValidityStatus(p_RequestInterviewDetailDTO.getValidityStatus());
        interviewDetail.setEventCandidateBefore(p_RequestInterviewDetailDTO.getEventCandidateBefore());
        interviewDetail.setEventCandidateAfter(p_RequestInterviewDetailDTO.getEventCandidateAfter());
        return interviewDetail;
    }

    @Override
    public Page<InterviewDetail> findByInterviewHeaderIdAndValidityStatus(List<Long> interviewHeaders, String p_ValidityStatus, Pageable pageable) throws ServiceException {
        try {
            return interviewDetailDAO.findByInterviewHeader_idInAndValidityStatus(interviewHeaders, p_ValidityStatus, pageable);
        } catch (DAOException e) {
            LOGGER.error("Error Searching Interview Detail Pageable by Header Id and Validity Status {}", e.toString());
            return null;
        }
    }

    @Override
    public List<InterviewDetail> findByInterviewHeaderIdAndValidityStatus(Long p_InterviewHeaderId, String p_ValidityStatus) throws ServiceException {
        try {
            return interviewDetailDAO.findByInterviewHeader_idAndValidityStatusOrderByCreatedOnDesc(p_InterviewHeaderId, p_ValidityStatus);
        } catch (DAOException e) {
            LOGGER.error("Error Searching Interview Detail by Header Id and Validity Status {}", e.toString());
            return null;
        }
    }

    @Override
    public List<InterviewDetail> findByInterviewHeaderIdAndValidityStatusNotValid(Long p_InterviewHeaderId) throws ServiceException {
        try {
            return interviewDetailDAO.findByInterviewHeader_idAndNotEqualValidityStatus(p_InterviewHeaderId, LsiCoreConstant.ValidityStatus.NOT_VALID);
        } catch (DAOException e) {
            LOGGER.error("Error Searching Interview Detail by Header Id and Validity Status {}", e.toString());
            return null;
        }
    }


    @Override
    public Page<InterviewDetail> findByInterviewHeaderIdAndValidityStatusNotValid(List<Long> interviewHeaders, Pageable p_PageRequest) throws ServiceException {
        try {
            return interviewDetailDAO.findByInterviewHeader_idInAndValidityStatusNot(interviewHeaders, LsiCoreConstant.ValidityStatus.NOT_VALID, p_PageRequest);
        } catch (DAOException e) {
            LOGGER.error("Error Searching Page Interview Detail by Header Id and Validity Status {}", e.toString());
            return null;
        }
    }

    @Override
    public Page<InterviewDetail> findByInterviewHeaderId(Long p_InterviewHeaderId, Pageable pageable) throws ServiceException {
        try {
            return interviewDetailDAO.findByInterviewHeader_id(p_InterviewHeaderId, pageable);
        } catch (DAOException e) {
            LOGGER.error("Error Searching Interview Detail by Header Id {}", e.toString());
            return null;
        }
    }

    @Override
    public List<InterviewDetail> findByInterviewHeaderId(Long p_InterviewHeaderId) throws ServiceException {
        try {
            return interviewDetailDAO.findByInterviewHeader_id(p_InterviewHeaderId);
        } catch (DAOException e) {
            LOGGER.error("Error Searching Interview Detail by Header Id {}", e.toString());
            return null;
        }
    }

    @Override
    public Integer countInterviewDetailByInterviewHeaderId(Long p_InterviewHeaderId) throws ServiceException {
        try {
            return interviewDetailDAO.countInterviewDetailByInterviewHeaderId(p_InterviewHeaderId);
        } catch (DAOException e) {
            LOGGER.error("Error Count Interview Detail By Header Id {}", e.toString());
            return null;
        }
    }

    @Override
    public Integer countInterviewDetailByInterviewHeaderIdAndInterviewStatus(Long p_InterviewHeaderId, String p_InterviewStatus) throws ServiceException {
        try {
            return interviewDetailDAO.countInterviewDetailByInterviewHeaderIdAndInterviewStatus(p_InterviewHeaderId, p_InterviewStatus);
        } catch (DAOException e) {
            LOGGER.error("Error Count Interview Detail By Interview Header And Interview Status {}", e.toString());
            return null;
        }
    }

    @Override
    public Integer countRespondentAmountByInterviewHeaderId(Long p_InterviewHeaderId) throws ServiceException {
        try {
            return interviewDetailDAO.countRespondentAmountByInterviewHeaderId(p_InterviewHeaderId);
        } catch (DAOException e) {
            LOGGER.error("countRespondentAmountByInterviewHeaderId {}", e.toString());
            return null;
        }
    }

    @Override
    public Integer countTotalPointByInterviewHeaderId(Long p_InterviewHeaderId) throws ServiceException {
        try {
            return interviewDetailDAO.countTotalPointByInterviewHeaderId(p_InterviewHeaderId);
        } catch (DAOException e) {
            LOGGER.error("Error Count Total Point By Header Id {}", e.toString());
            return null;
        }
    }

    @Override
    public Integer countDataByPointGreaterThanZeroByInterviewHeaderId(Long p_InterviewHeaderId) throws ServiceException {
        try {
            return interviewDetailDAO.countDataByPointGreaterThanZeroByInterviewHeaderId(p_InterviewHeaderId);
        } catch (DAOException e) {
            LOGGER.error("Error Count Total Point By Header Id {}", e.toString());
            return null;
        }
    }

    @Override
    public Integer countInterviewDetailByInterviewHeaderIdAndValidityStatus(Long p_InterviewHeaderId, String p_ValidityStatus) throws ServiceException {
        try {
            return interviewDetailDAO.countInterviewDetailByInterviewHeaderIdAndValidityStatus(p_ValidityStatus, p_InterviewHeaderId);
        } catch (DAOException e) {
            LOGGER.error("Error Count Interview Detail By Interview Header Id and Validity Status {}", e.toString());
            return null;
        }
    }

    @Override
    public Integer countInterviewDetailByInterviewHeaderIdAndScore(Long p_InterviewHeaderId, Integer p_Score) throws ServiceException {
        try {
            return interviewDetailDAO.countInterviewDetailByInterviewHeaderIdAndInterviewScore(p_Score, p_InterviewHeaderId);
        } catch (DAOException e) {
            LOGGER.error("Error Count Interview Detail By Interview Header Id and Score {}", e.toString());
            return null;
        }
    }

    @Override
    public InterviewDetail findByRespondentName(String p_RespondentName) throws ServiceException {
        try {
            return interviewDetailDAO.findByRespondentName(p_RespondentName);
        } catch (DAOException e) {
            LOGGER.error("Error Find By Respondent Name {}", e.toString());
            return null;
        }
    }

    @Override
    public ResponseData insertInterviewDetailResult(RequestInsertInterviewDetailResultDTO p_RequestInsertInterviewDetailResultDTO) throws ServiceException {
        ResponseData responseData = new ResponseData(EResponseCode.RC_SUCCESS.getResponseCode(), EResponseCode.RC_SUCCESS.getResponseMsg());
        if (p_RequestInsertInterviewDetailResultDTO.getInterviewHeaderId() != null) {
            InterviewHeader interviewHeader = interviewHeaderService.findById(p_RequestInsertInterviewDetailResultDTO.getInterviewHeaderId());
            List<InterviewDetail> temp =new ArrayList<>();
            if (interviewHeader != null) {
                try {
                    temp = interviewDetailDAO.findByMobileUniqueCode(p_RequestInsertInterviewDetailResultDTO.getMobileUniqueCode());
                } catch (DAOException e) {
                    LOGGER.error("Error find Interview Detail with Mobile Unique Code {}", e.toString());
                    responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
                }
                if (temp.size() > 0) {
                    LOGGER.info("Interview Detail With Mobile Unique Code {} has already exist", p_RequestInsertInterviewDetailResultDTO.getMobileUniqueCode());
//                    responseData = new ResponseData(EMobileResponseCode.RC_DUPLICATE.getResponseCode(), EMobileResponseCode.RC_DUPLICATE.getResponseMsg());
                    if (p_RequestInsertInterviewDetailResultDTO.getMobileUniqueCode() == null) {
                        LOGGER.info("Insert Interview Result with Unique Code NULL");
                        responseData = insertNewResult(p_RequestInsertInterviewDetailResultDTO, interviewHeader, responseData);
                    }else {
//                        responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
                        responseData = insertNewResult(p_RequestInsertInterviewDetailResultDTO, interviewHeader, responseData);
                    }
                } else {
                    responseData = insertNewResult(p_RequestInsertInterviewDetailResultDTO, interviewHeader, responseData);
                }
            }else {
                responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), "Interview Header Not Found");
                LOGGER.error("Interview Header Not Found");
            }
        }else {
            responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), "Interview Header Can Not Be Null");
            LOGGER.error("Interview Header Can Not Be Null");
        }
        return responseData;
    }

    private ResponseData insertNewResult(RequestInsertInterviewDetailResultDTO p_RequestInsertInterviewDetailResultDTO, InterviewHeader interviewHeader, ResponseData responseData) throws ServiceException{
        LOGGER.info("Insert New Interview Detail {}", p_RequestInsertInterviewDetailResultDTO.getMobileUniqueCode());
        InterviewDetail interviewDetail = new InterviewDetail();
        interviewDetail.setMobileUniqueCode(p_RequestInsertInterviewDetailResultDTO.getMobileUniqueCode());
        interviewDetail.setInterviewHeader(interviewHeader);
        if (p_RequestInsertInterviewDetailResultDTO.getRespondentName() != null && p_RequestInsertInterviewDetailResultDTO.getRespondentPhoneNumber() != null && p_RequestInsertInterviewDetailResultDTO.getRespondentAddress() != null) {
            interviewDetail.setRespondentName(p_RequestInsertInterviewDetailResultDTO.getRespondentName());
            interviewDetail.setRespondentPhoneNumber(p_RequestInsertInterviewDetailResultDTO.getRespondentPhoneNumber());
            interviewDetail.setRespondentAddress(p_RequestInsertInterviewDetailResultDTO.getRespondentAddress());

            if (p_RequestInsertInterviewDetailResultDTO.getEventCandidateBefore() != null) {
                Long eventCandidateBefore = eventCandidateService.findByInterviewEventIdAndCandidateOrder(interviewHeader.getInterviewEvent().getId(), p_RequestInsertInterviewDetailResultDTO.getEventCandidateBefore());
                interviewDetail.setEventCandidateBefore(eventCandidateBefore);
            }
            if (p_RequestInsertInterviewDetailResultDTO.getEventCandidateAfter() != null) {
                Long eventCandidateAfter = eventCandidateService.findByInterviewEventIdAndCandidateOrder(interviewHeader.getInterviewEvent().getId(), p_RequestInsertInterviewDetailResultDTO.getEventCandidateAfter());
                interviewDetail.setEventCandidateAfter(eventCandidateAfter);
            }
            interviewDetail.setInterviewScore(p_RequestInsertInterviewDetailResultDTO.getInterviewScore());
            if (p_RequestInsertInterviewDetailResultDTO.getInterviewScore() != null) {
                interviewDetail.setValidityStatus(getValidityStatus(p_RequestInsertInterviewDetailResultDTO.getInterviewScore()));
            }
            if (p_RequestInsertInterviewDetailResultDTO.getLatitude() != null) {
                interviewDetail.setLatitude(p_RequestInsertInterviewDetailResultDTO.getLatitude());
            }
            if (p_RequestInsertInterviewDetailResultDTO.getLongitude() != null) {
                interviewDetail.setLongitude(p_RequestInsertInterviewDetailResultDTO.getLongitude());
            }
            SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S");
            if (p_RequestInsertInterviewDetailResultDTO.getInterviewStartTime() != null) {
                try {
                    interviewDetail.setInterviewStartTime(formatDate.parse(p_RequestInsertInterviewDetailResultDTO.getInterviewStartTime()));
                } catch (ParseException e) {
//                            responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), "Error Parsing Start Time");
                    LOGGER.error("Error Parsing Start Time {}", e.toString());
                }
            }
            if (p_RequestInsertInterviewDetailResultDTO.getInterviewEndTime() != null) {
                try {
                    interviewDetail.setInterviewEndTime(formatDate.parse(p_RequestInsertInterviewDetailResultDTO.getInterviewEndTime()));
                } catch (ParseException e) {
//                            responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), "Error Parsing End Time");
                    LOGGER.error("Error Parsing End Time {}", e.toString());
                }
            }
            interviewDetail.setRemarks(p_RequestInsertInterviewDetailResultDTO.getRemarks());
            insert(interviewDetail);
            Set<InterviewImage> interviewImages = new HashSet<>();
            if (p_RequestInsertInterviewDetailResultDTO.getEvidenceImagePath().size() > 0) {
                for (ContentInsertEvidenceImage evidenceImage :p_RequestInsertInterviewDetailResultDTO.getEvidenceImagePath() ) {
                    InterviewImage interviewImage = new InterviewImage();
                    interviewImage.setInterviewDetail(interviewDetail);
                    interviewImage.setName(evidenceImage.getFilePath());
                    interviewImage.setFilePath(evidenceImage.getFilePath());
                    interviewImage.setFileSize(evidenceImage.getFileSize());
                    interviewImage.setFileType(evidenceImage.getFileType());
                    interviewImage.setLatitude(evidenceImage.getLatitude());
                    interviewImage.setLongitude(evidenceImage.getLongitude());
                    interviewImageService.insert(interviewImage);
                    interviewImages.add(interviewImage);
                }
                interviewDetail.setInterviewStatus(LsiCoreConstant.InterviewStatus.COMPLETED);
            }else {
                interviewDetail.setInterviewStatus(LsiCoreConstant.InterviewStatus.NOT_COMPLETED);
            }
            interviewDetail.setInterviewImages(interviewImages);
            for (ContentInsertUserAnswerDTO userAnswerDTO : p_RequestInsertInterviewDetailResultDTO.getUserAnswers()) {
                InterviewUserAnswer interviewUserAnswer = new InterviewUserAnswer();
                interviewUserAnswer.setInterviewQuestionId(userAnswerDTO.getQuestionId());
                interviewUserAnswer.setQuestionAnswerId(userAnswerDTO.getAnswerId());
                interviewUserAnswer.setOtherAnswer(userAnswerDTO.getOtherAnswer());
                interviewUserAnswer.setInterviewDetail(interviewDetail);
                interviewUserAnswerService.insert(interviewUserAnswer);
            }
            update(interviewDetail);
        }else {
            responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), "Mandatory Fields (Name, Phone Number, Address) Not Exist");
        }
        return responseData;
    }
    private String getValidityStatus(Integer p_InterviewScore) {
        if (p_InterviewScore >= 0 && p_InterviewScore <= 25) {
            return LsiCoreConstant.ValidityStatus.NOT_VALID;
        }else if (p_InterviewScore > 25 && p_InterviewScore <= 50) {
            return LsiCoreConstant.ValidityStatus.LESS_VALID;
        }else if (p_InterviewScore > 50 && p_InterviewScore <= 75) {
            return LsiCoreConstant.ValidityStatus.VALID_ENOUGH;
        }else if (p_InterviewScore > 75) {
            return LsiCoreConstant.ValidityStatus.VALID;
        }else {
            return LsiCoreConstant.ValidityStatus.NOT_VALID;
        }
    }

    @Override
    public List<Object[]> findInterviewHeaderByAreaAndEventAndBranch(RequestFilterInterviewHeader p_RequestFilterInterviewHeader, PageRequest p_PageRequest) throws ServiceException {
        List<Object[]> result = new ArrayList<>();
        if (p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
            if (p_RequestFilterInterviewHeader.getBranchId() != null) {
                LOGGER.info("Filter Interview Header with Branch {}", p_RequestFilterInterviewHeader.getBranchId());
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                    try {
                        LOGGER.info("Find By Province Id");
                        result = interviewDetailDAO.findByProvinceIdAndBranchIdAndInterviewEvent_Id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId(), LsiCoreConstant.InterviewStatus.COMPLETED, 25);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province And City Id");
                        result = interviewDetailDAO.findByProvinceIdAndCityIdAndBranchIdAndInterviewEvent_Id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId(), LsiCoreConstant.InterviewStatus.COMPLETED, 25);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City and District Id");
                        result = interviewDetailDAO.findByProvinceIdAndCityIdAndDistrictIdAndBranchIdAndInterviewEvent_Id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId(), LsiCoreConstant.InterviewStatus.COMPLETED, 25);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City And District {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() != null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City District and Village Id");
                        result = interviewDetailDAO.findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndBranchIdAndInterviewEvent_Id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getVillageId(), p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId(), LsiCoreConstant.InterviewStatus.COMPLETED, 25);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() == null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find All Event Only");
                        result = interviewDetailDAO.findByBranchIdAndInterviewEvent_Id(p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId(), LsiCoreConstant.InterviewStatus.COMPLETED, 25);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Event Only {}", e.toString());
                    }
                }
            }else {
                LOGGER.info("Filter Interview Header without Branch");
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                    try {
                        LOGGER.info("Find By Province Id");
                        result = interviewDetailDAO.findByProvinceIdAndInterviewEvent_Id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getInterviewEventId(), LsiCoreConstant.InterviewStatus.COMPLETED, 25);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province And City Id");
                        result = interviewDetailDAO.findByProvinceIdAndCityIdAndInterviewEvent_Id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getInterviewEventId(), LsiCoreConstant.InterviewStatus.COMPLETED, 25);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City and District Id");
                        result = interviewDetailDAO.findByProvinceIdAndCityIdAndDistrictIdAndInterviewEvent_Id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getInterviewEventId(), LsiCoreConstant.InterviewStatus.COMPLETED, 25);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City And District {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() != null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City District and Village Id");
                        result = interviewDetailDAO.findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndInterviewEvent_Id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getVillageId(), p_RequestFilterInterviewHeader.getInterviewEventId(), LsiCoreConstant.InterviewStatus.COMPLETED, 25);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() == null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find All - By Event Only");
                        result = interviewDetailDAO.findByInterviewEvent_Id(p_RequestFilterInterviewHeader.getInterviewEventId(), LsiCoreConstant.InterviewStatus.COMPLETED, 25);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Event Only {}", e.toString());
                    }
                }
            }
        }else {
            LOGGER.error("Interview Event Id can not be NULL");
        }
        return result;
    }

    @Override
    public Page<Object[]> findInterviewHeaderByAreaAndEventAndBranchPaged(RequestFilterInterviewHeader p_RequestFilterInterviewHeader, PageRequest p_PageRequest) throws ServiceException {
        List<Object[]> result = new ArrayList<>();
        if (p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
            if (p_RequestFilterInterviewHeader.getBranchId() != null) {
                LOGGER.info("Filter Interview Header with Branch {}", p_RequestFilterInterviewHeader.getBranchId());
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                    try {
                        LOGGER.info("Find By Province Id");
                        result = interviewDetailDAO.findByProvinceIdAndBranchIdAndInterviewEvent_Id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId(), LsiCoreConstant.InterviewStatus.COMPLETED, 25);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province And City Id");
                        result = interviewDetailDAO.findByProvinceIdAndCityIdAndBranchIdAndInterviewEvent_Id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId(), LsiCoreConstant.InterviewStatus.COMPLETED, 25);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City and District Id");
                        result = interviewDetailDAO.findByProvinceIdAndCityIdAndDistrictIdAndBranchIdAndInterviewEvent_Id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId(), LsiCoreConstant.InterviewStatus.COMPLETED, 25);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City And District {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() != null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City District and Village Id");
                        result = interviewDetailDAO.findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndBranchIdAndInterviewEvent_Id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getVillageId(), p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId(), LsiCoreConstant.InterviewStatus.COMPLETED, 25);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() == null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find All Event Only");
                        result = interviewDetailDAO.findByBranchIdAndInterviewEvent_Id(p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId(), LsiCoreConstant.InterviewStatus.COMPLETED, 25);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Event Only {}", e.toString());
                    }
                }
            }else {
                LOGGER.info("Filter Interview Header without Branch");
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                    try {
                        LOGGER.info("Find By Province Id");
                        result = interviewDetailDAO.findByProvinceIdAndInterviewEvent_Id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getInterviewEventId(), LsiCoreConstant.InterviewStatus.COMPLETED, 25);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province And City Id");
                        result = interviewDetailDAO.findByProvinceIdAndCityIdAndInterviewEvent_Id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getInterviewEventId(), LsiCoreConstant.InterviewStatus.COMPLETED, 25);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City and District Id");
                        result = interviewDetailDAO.findByProvinceIdAndCityIdAndDistrictIdAndInterviewEvent_Id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getInterviewEventId(), LsiCoreConstant.InterviewStatus.COMPLETED, 25);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City And District {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() != null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City District and Village Id");
                        result = interviewDetailDAO.findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndInterviewEvent_Id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getVillageId(), p_RequestFilterInterviewHeader.getInterviewEventId(), LsiCoreConstant.InterviewStatus.COMPLETED, 25);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() == null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find All - By Event Only");
                        result = interviewDetailDAO.findByInterviewEvent_Id(p_RequestFilterInterviewHeader.getInterviewEventId(), LsiCoreConstant.InterviewStatus.COMPLETED, 25);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Event Only {}", e.toString());
                    }
                }
            }
        }else {
            LOGGER.error("Interview Event Id can not be NULL");
        }
        return new PaginationUtil<Object[]>().makePagination(result, p_PageRequest);
    }
}
