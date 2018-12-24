package com.tripoin.lsi.thirdparty.service.impl;

import com.tripoin.lsi.thirdparty.LsiThirdPartyConstant;
import com.tripoin.lsi.thirdparty.client.impl.ProvinceClientImpl;
import com.tripoin.lsi.thirdparty.client.impl.UserClientImpl;
import com.tripoin.lsi.thirdparty.client.impl.VillageClientImpl;
import com.tripoin.lsi.thirdparty.dao.IInterviewHeaderDAO;
import com.tripoin.lsi.thirdparty.data.dto.request.*;
import com.tripoin.lsi.thirdparty.data.dto.response.*;
import com.tripoin.lsi.thirdparty.data.mapper.*;
import com.tripoin.lsi.thirdparty.data.model.*;
import com.tripoin.lsi.thirdparty.formula.FormulaInterviewCompletion;
import com.tripoin.lsi.thirdparty.formula.FormulaInterviewValidity;
import com.tripoin.lsi.thirdparty.service.*;
import com.tripoin.lsi.thirdparty.util.PaginationUtil;
import com.tripoin.lsi.thirdparty.util.PrincipalFinder;
import com.wissensalt.scaffolding.constant.ScaffoldingSecurityConstant.RoleCode;
import com.wissensalt.scaffolding.exception.DAOException;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.AScaffoldingService;
import com.wissensalt.shared.constant.EResponseCode;
import com.wissensalt.shared.dto.request.RequestFindByIdDTO;
import com.wissensalt.shared.dto.request.RequestFindByNameDTO;
import com.wissensalt.shared.dto.response.base.GenericSingleDATAResponseDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;
import com.wissensalt.shared.dto.response.base.ResponsePhoneNumberEmail;
import com.wissensalt.shared.dto.response.master.ResponseProvinceDTO;
import com.wissensalt.shared.dto.response.master.ResponseVillageDTO;
import com.wissensalt.shared.dto.response.security.ResponseRoleDTO;
import com.wissensalt.shared.dto.response.security.ResponseUserDTO;
import com.wissensalt.util.common.time.FormatDateConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class InterviewHeaderServiceImpl extends AScaffoldingService<InterviewHeader, RequestInterviewHeaderDTO> implements IInterviewHeaderService {

    @Autowired
    private IInterviewHeaderDAO interviewHeaderDAO;

    @Autowired
    private IInterviewDetailService interviewDetailService;

    @Autowired
    private IInterviewComponentService interviewComponentService;

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionAnswerMapper questionAnswerMapper;

    @Autowired
    private InterviewComponentMapper interviewComponentMapper;

    @Autowired
    private InterviewImageMapper interviewImageMapper;

    @Autowired
    private InterviewUserAnswerMapper interviewUserAnswerMapper;

    @Autowired
    private IInterviewUserAnswerService interviewUserAnswerService;

    @Autowired
    private VillageClientImpl villageClient;

    @Autowired
    private UserClientImpl userClient;

    @Autowired
    private PrincipalFinder principalFinder;

    @Autowired
    private FormulaInterviewCompletion formulaInterviewCompletion;

    @Autowired
    private FormulaInterviewValidity formulaInterviewValidity;

    @Autowired
    private IInterviewEventService interviewEventService;

    @Autowired
    private ProvinceClientImpl provinceClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(InterviewHeaderServiceImpl.class);

    @PostConstruct
    @Override
    public void initService() {
        scaffoldingDAO = interviewHeaderDAO;
    }

    @Override
    public InterviewHeader generateEntityForCommonTrx(RequestInterviewHeaderDTO p_RequestInterviewHeaderDTO) throws ServiceException {
        InterviewHeader interviewHeader = new InterviewHeader();
        interviewHeader = matchBaseFields(interviewHeader, p_RequestInterviewHeaderDTO);
        if (p_RequestInterviewHeaderDTO.getProvinceId() != null) {
            interviewHeader.setProvinceId(p_RequestInterviewHeaderDTO.getProvinceId());
        }
        if (p_RequestInterviewHeaderDTO.getCityId() != null) {
            interviewHeader.setCityId(p_RequestInterviewHeaderDTO.getCityId());
        }
        if (p_RequestInterviewHeaderDTO.getDistrictId() != null) {
            interviewHeader.setDistrictId(p_RequestInterviewHeaderDTO.getDistrictId());
        }
        if (p_RequestInterviewHeaderDTO.getVillageId() != null) {
            interviewHeader.setVillageId(p_RequestInterviewHeaderDTO.getVillageId());
        }
        if (p_RequestInterviewHeaderDTO.getNumberOfRespondent() != null) {
            interviewHeader.setNumberOfRespondent(p_RequestInterviewHeaderDTO.getNumberOfRespondent());
        }
        if (p_RequestInterviewHeaderDTO.getBranchId() != null) {
            interviewHeader.setBranchId(p_RequestInterviewHeaderDTO.getBranchId());
        }
        if (p_RequestInterviewHeaderDTO.getInterviewEventId() != null) {
            InterviewEvent interviewEvent = interviewEventService.findById(p_RequestInterviewHeaderDTO.getInterviewEventId());
            if (interviewEvent != null) {
                interviewHeader.setInterviewEvent(interviewEvent);
            }
        }
        return interviewHeader;
    }

    @Override
    public Page<ContentInterviewProgressDTO> findInterviewProgressByStatus(String p_Bearer, String p_Principal, RequestInterviewProgressByStatusDTO p_RequestInterviewProgressByStatusDTO, PageRequest p_PageRequest) throws ServiceException {
        List<ContentInterviewProgressDTO> result = new ArrayList<>();
        ResponseRoleDTO role = principalFinder.findRoleByUserName(p_Bearer, p_Principal);
        Page<InterviewDetail> interviewDetails = null;
        if (role != null) {
            RequestFilterInterviewHeader filterHeader = new RequestFilterInterviewHeader();
            filterHeader.setInterviewEventId(p_RequestInterviewProgressByStatusDTO.getInterviewEventId());
            filterHeader.setProvinceId(p_RequestInterviewProgressByStatusDTO.getProvinceId());
            filterHeader.setCityId(p_RequestInterviewProgressByStatusDTO.getCityId());
            filterHeader.setDistrictId(p_RequestInterviewProgressByStatusDTO.getDistrictId());
            filterHeader.setVillageId(p_RequestInterviewProgressByStatusDTO.getVillageId());
            List<InterviewHeader> interviewHeaders = new ArrayList<>();

            if ((!role.getCode().equals(RoleCode.ACTUATOR)) && (!role.getCode().equals(RoleCode.VOLUNTEER) && (!role.getCode().equals(RoleCode.DIREKSI)) && (!role.getCode().equals(RoleCode.ADMIN)))) {
                LOGGER.info("Found Tenant Role");
                ResponseUserDTO user = principalFinder.findUserByUserName(p_Bearer, p_Principal);
                if (user != null) {
                    if (user.getBranch() != null) {
                        filterHeader.setBranchId(user.getBranch().getId());
                        interviewHeaders = findInterviewHeaderByAreaAndEventAndBranch(filterHeader);
                    }else {
                        LOGGER.error("Branch is Not Found");
                    }
                }else {
                    LOGGER.error("User is Not Found");
                }
            } else if (role.getCode().equals(RoleCode.ADMIN) || role.getCode().equals(RoleCode.DIREKSI)) {
                LOGGER.info("Found Admin Role");
                interviewHeaders = findInterviewHeaderByAreaAndEventAndBranch(filterHeader);
            } else {
                LOGGER.error("Role is UnAuthorized to Access This Service");
            }
            LOGGER.info("Found {} Interview Header ", interviewHeaders.size());

            List<Long> interviewHeadersId = new ArrayList<>();
            for (InterviewHeader interviewHeader : interviewHeaders) {
                interviewHeadersId.add(interviewHeader.getId());
            }

            if (p_RequestInterviewProgressByStatusDTO.getValidityStatus() != null) {
                if (p_RequestInterviewProgressByStatusDTO.getValidityStatus().equals("0")) {
                    interviewDetails = interviewDetailService.findByInterviewHeaderIdAndValidityStatusNotValid(interviewHeadersId, p_PageRequest);
                }else {
                    interviewDetails = interviewDetailService.findByInterviewHeaderIdAndValidityStatus(interviewHeadersId, p_RequestInterviewProgressByStatusDTO.getValidityStatus(), p_PageRequest);
                }
            }else {
                interviewDetails = interviewDetailService.findByInterviewHeaderIdAndValidityStatusNotValid(interviewHeadersId, p_PageRequest);
            }

            LOGGER.info("Found {} Interview Details", interviewDetails.getTotalElements());

            for (InterviewDetail interviewDetail : interviewDetails.getContent()) {
                ContentInterviewProgressDTO contentInterviewProgressDTO = new ContentInterviewProgressDTO();
                contentInterviewProgressDTO.setInterviewDetailId(interviewDetail.getId());
                contentInterviewProgressDTO.setRespondentName(interviewDetail.getRespondentName());
                contentInterviewProgressDTO.setRespondentPhoneNumber(interviewDetail.getRespondentPhoneNumber());
                contentInterviewProgressDTO.setRespondentAddress(interviewDetail.getRespondentAddress());
                contentInterviewProgressDTO.setLatitude(interviewDetail.getLatitude());
                contentInterviewProgressDTO.setLongitude(interviewDetail.getLongitude());
                contentInterviewProgressDTO.setValidityStatus(interviewDetail.getValidityStatus());
                contentInterviewProgressDTO.setRemarks(interviewDetail.getRemarks());
                List<ResponseBasicInterviewImageDTO> interviewImages = new ArrayList<>();
                if (interviewDetail.getInterviewImages() != null) {
                    if (interviewDetail.getInterviewImages().size() > 0) {
                        for (InterviewImage interviewImage : interviewDetail.getInterviewImages()) {
                            interviewImages.add(interviewImageMapper.convertBasic(interviewImage));
                        }
                    }
                }
                contentInterviewProgressDTO.setEvidences(interviewImages);

                RequestFindByIdDTO requestFindByIdDTO = new RequestFindByIdDTO();
                if (interviewDetail.getInterviewHeader().getVillageId() != null) {
                    requestFindByIdDTO.setId(interviewDetail.getInterviewHeader().getVillageId());
                    GenericSingleDATAResponseDTO<ResponseVillageDTO> responseVillageDTO = villageClient.findVillageById(p_Bearer, requestFindByIdDTO);
                    if (responseVillageDTO != null) {
                        contentInterviewProgressDTO.setVillage(responseVillageDTO.getContent());
                    }
                }
                result.add(contentInterviewProgressDTO);
            }
        }else {
            LOGGER.error("Role is not found");
        }
        return new PageImpl<>(result, p_PageRequest, interviewDetails.getTotalElements());
    }

    @Override
    public List<InterviewHeader> findInterviewHeaderByAreaAndEventAndBranch(RequestFilterInterviewHeader p_RequestFilterInterviewHeader) throws ServiceException {
        List<InterviewHeader> interviewHeaders = new ArrayList<>();
        if (p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
            if (p_RequestFilterInterviewHeader.getBranchId() != null) {
                LOGGER.info("Filter Interview Header with Branch {}", p_RequestFilterInterviewHeader.getBranchId());
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                    try {
                        LOGGER.info("Find By Province Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndBranchIdAndInterviewEvent_Id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId());
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province And City Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndBranchIdAndInterviewEvent_id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId());
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City and District Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndBranchIdAndInterviewEvent_id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId());
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City And District {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() != null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City District and Village Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndBranchIdAndInterviewEvent_id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getVillageId(), p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId());
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() == null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find All Event Only");
                        interviewHeaders = interviewHeaderDAO.findByBranchIdAndInterviewEvent_Id(p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId());
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Event Only {}", e.toString());
                    }
                }
            }else {
                LOGGER.info("Filter Interview Header without Branch");
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                    try {
                        LOGGER.info("Find By Province Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndInterviewEvent_Id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getInterviewEventId());
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province And City Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndInterviewEvent_id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getInterviewEventId());
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City and District Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndInterviewEvent_id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getInterviewEventId());
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City And District {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() != null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City District and Village Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndInterviewEvent_id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getVillageId(), p_RequestFilterInterviewHeader.getInterviewEventId());
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() == null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find All - By Event Only");
                        interviewHeaders = interviewHeaderDAO.findByInterviewEvent_Id(p_RequestFilterInterviewHeader.getInterviewEventId());
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Event Only {}", e.toString());
                    }
                }
            }
        }else {
            LOGGER.error("Interview Event Id can not be NULL");
        }
        return interviewHeaders;
    }

    @Override
    public Page<InterviewHeader> findInterviewHeaderByAreaAndEventAndBranch(RequestFilterInterviewHeader p_RequestFilterInterviewHeader, PageRequest p_PageRequest) throws ServiceException {
        Page<InterviewHeader> interviewHeaders = null;
        if (p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
            if (p_RequestFilterInterviewHeader.getBranchId() != null) {
                LOGGER.info("Filter Interview Header with Branch {}", p_RequestFilterInterviewHeader.getBranchId());
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                    try {
                        LOGGER.info("Find By Province Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndBranchIdAndInterviewEvent_IdOrderByCreatedOnDesc(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId(), p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province And City Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndBranchIdAndInterviewEvent_idOrderByCreatedOnDesc(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId(), p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City and District Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndBranchIdAndInterviewEvent_idOrderByCreatedOnDesc(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId(), p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City And District {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() != null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City District and Village Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndBranchIdAndInterviewEvent_idOrderByCreatedOnDesc(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getVillageId(), p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId(), p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() == null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find All Event Only");
                        interviewHeaders = interviewHeaderDAO.findByBranchIdAndInterviewEvent_IdOrderByCreatedOnDesc(p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId(), p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Event Only {}", e.toString());
                    }
                }
            }else {
                LOGGER.info("Filter Interview Header without Branch");
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                    try {
                        LOGGER.info("Find By Province Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndInterviewEvent_IdOrderByCreatedOnDesc(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getInterviewEventId(), p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province And City Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndInterviewEvent_idOrderByCreatedOnDesc(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getInterviewEventId(), p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City and District Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndInterviewEvent_idOrderByCreatedOnDesc(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getInterviewEventId(), p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City And District {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() != null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City District and Village Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndInterviewEvent_idOrderByCreatedOnDesc(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getVillageId(), p_RequestFilterInterviewHeader.getInterviewEventId(), p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() == null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find All - By Event Only");
                        interviewHeaders = interviewHeaderDAO.findByInterviewEvent_IdOrderByCreatedOnDesc(p_RequestFilterInterviewHeader.getInterviewEventId(), p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Event Only {}", e.toString());
                    }
                }
            }
        }else {
            LOGGER.error("Interview Event Id can not be NULL");
        }
        return interviewHeaders;
    }

    @Override
    public Page<InterviewHeader> findInterviewHeaderByAreaAndEventAndBranchAndUserId(RequestFilterInterviewHeader p_RequestFilterInterviewHeader, Long p_UserId, PageRequest p_PageRequest) throws ServiceException {
        Page<InterviewHeader> interviewHeaders = null;
        if (p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
            if (p_RequestFilterInterviewHeader.getBranchId() != null  && p_UserId == null) {
                LOGGER.info("Filter Interview Header with Branch {} ", p_RequestFilterInterviewHeader.getBranchId());
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                    try {
                        LOGGER.info("Find By Province Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndBranchIdAndInterviewEvent_IdOrderByCreatedOnDesc(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId(), p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province And City Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndBranchIdAndInterviewEvent_idOrderByCreatedOnDesc(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId(), p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City and District Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndBranchIdAndInterviewEvent_idOrderByCreatedOnDesc(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId(), p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City And District {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() != null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City District and Village Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndBranchIdAndInterviewEvent_idOrderByCreatedOnDesc(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getVillageId(), p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId(), p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() == null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find All Event Only");
                        interviewHeaders = interviewHeaderDAO.findByBranchIdAndInterviewEvent_IdOrderByCreatedOnDesc(p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId(), p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Event Only {}", e.toString());
                    }
                }
            }else if (p_RequestFilterInterviewHeader.getBranchId() != null  && p_UserId != null) {
                LOGGER.info("Filter Interview Header with Branch {} And User Id {}", p_RequestFilterInterviewHeader.getBranchId(), p_UserId);
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                    try {
                        LOGGER.info("Find By Province Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndBranchIdAndInterviewEvent_IdAndUserId(
                                p_RequestFilterInterviewHeader.getProvinceId(),
                                p_RequestFilterInterviewHeader.getBranchId(),
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                p_UserId,
                                p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province And City Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndBranchIdAndInterviewEvent_idAndUserId(
                                p_RequestFilterInterviewHeader.getProvinceId(),
                                p_RequestFilterInterviewHeader.getCityId(),
                                p_RequestFilterInterviewHeader.getBranchId(),
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                p_UserId,
                                p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City and District Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndBranchIdAndInterviewEvent_idAndUserId(
                                p_RequestFilterInterviewHeader.getProvinceId(),
                                p_RequestFilterInterviewHeader.getCityId(),
                                p_RequestFilterInterviewHeader.getDistrictId(),
                                p_RequestFilterInterviewHeader.getBranchId(),
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                p_UserId,
                                p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City And District {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() != null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City District and Village Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndBranchIdAndInterviewEvent_idAndUserId(
                                p_RequestFilterInterviewHeader.getProvinceId(),
                                p_RequestFilterInterviewHeader.getCityId(),
                                p_RequestFilterInterviewHeader.getDistrictId(),
                                p_RequestFilterInterviewHeader.getVillageId(),
                                p_RequestFilterInterviewHeader.getBranchId(),
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                p_UserId,
                                p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() == null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find All Event Only");
                        interviewHeaders = interviewHeaderDAO.findByBranchIdAndInterviewEvent_IdAndUserId(
                                p_RequestFilterInterviewHeader.getBranchId(),
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                p_UserId,
                                p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Event Only {}", e.toString());
                    }
                }
            } else if (p_RequestFilterInterviewHeader.getBranchId() == null && p_UserId == null) {
                LOGGER.info("Filter Interview Header without Branch + Without User");
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                    try {
                        LOGGER.info("Find By Province Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndInterviewEvent_IdOrderByCreatedOnDesc(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getInterviewEventId(), p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province And City Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndInterviewEvent_idOrderByCreatedOnDesc(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getInterviewEventId(), p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City and District Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndInterviewEvent_idOrderByCreatedOnDesc(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getInterviewEventId(), p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City And District {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() != null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City District and Village Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndInterviewEvent_idOrderByCreatedOnDesc(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getVillageId(), p_RequestFilterInterviewHeader.getInterviewEventId(), p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() == null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find All - By Event Only");
                        interviewHeaders = interviewHeaderDAO.findByInterviewEvent_IdOrderByCreatedOnDesc(p_RequestFilterInterviewHeader.getInterviewEventId(), p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Event Only {}", e.toString());
                    }
                }
            } else if (p_RequestFilterInterviewHeader.getBranchId() == null && p_UserId != null) {
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                    try {
                        LOGGER.info("Find By Province Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndInterviewEvent_IdAndUserId(
                                p_RequestFilterInterviewHeader.getProvinceId(),
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                p_UserId,
                                p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province And City Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndInterviewEvent_idAndUserId(
                                p_RequestFilterInterviewHeader.getProvinceId(),
                                p_RequestFilterInterviewHeader.getCityId(),
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                p_UserId,
                                p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City and District Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndInterviewEvent_idAndUserId(
                                p_RequestFilterInterviewHeader.getProvinceId(),
                                p_RequestFilterInterviewHeader.getCityId(),
                                p_RequestFilterInterviewHeader.getDistrictId(),
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                p_UserId,
                                p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City And District {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() != null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City District and Village Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndInterviewEvent_idAndUserId(
                                p_RequestFilterInterviewHeader.getProvinceId(),
                                p_RequestFilterInterviewHeader.getCityId(),
                                p_RequestFilterInterviewHeader.getDistrictId(),
                                p_RequestFilterInterviewHeader.getVillageId(),
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                p_UserId,
                                p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() == null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find All - By Event Only");
                        interviewHeaders = interviewHeaderDAO.findByInterviewEvent_IdAndUserId(
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                p_UserId,
                                p_PageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Event Only {}", e.toString());
                    }
                }
            }
        }else {
            LOGGER.error("Interview Event Id can not be NULL");
        }
        return interviewHeaders;
    }

    @Override
    public List<InterviewHeader> findInterviewHeaderByAreaAndEventAndBranchAndUserId(RequestFilterInterviewHeader p_RequestFilterInterviewHeader, Long p_UserId) throws ServiceException {
        List<InterviewHeader> interviewHeaders = new ArrayList<>();
        if (p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
            if (p_RequestFilterInterviewHeader.getBranchId() != null  && p_UserId == null) {
                LOGGER.info("Filter Interview Header with Branch {} ", p_RequestFilterInterviewHeader.getBranchId());
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                    try {
                        LOGGER.info("Find By Province Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndBranchIdAndInterviewEvent_Id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId());
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province And City Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndBranchIdAndInterviewEvent_id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId());
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City and District Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndBranchIdAndInterviewEvent_id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId());
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City And District {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() != null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City District and Village Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndBranchIdAndInterviewEvent_id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getVillageId(), p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId());
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() == null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find All Event Only");
                        interviewHeaders = interviewHeaderDAO.findByBranchIdAndInterviewEvent_Id(p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId());
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Event Only {}", e.toString());
                    }
                }
            }else if (p_RequestFilterInterviewHeader.getBranchId() != null  && p_UserId != null) {
                LOGGER.info("Filter Interview Header with Branch {} And User Id {}", p_RequestFilterInterviewHeader.getBranchId(), p_UserId);
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                    try {
                        LOGGER.info("Find By Province Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndBranchIdAndInterviewEvent_IdAndUserId(
                                p_RequestFilterInterviewHeader.getProvinceId(),
                                p_RequestFilterInterviewHeader.getBranchId(),
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                p_UserId);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province And City Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndBranchIdAndInterviewEvent_idAndUserId(
                                p_RequestFilterInterviewHeader.getProvinceId(),
                                p_RequestFilterInterviewHeader.getCityId(),
                                p_RequestFilterInterviewHeader.getBranchId(),
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                p_UserId);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City and District Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndBranchIdAndInterviewEvent_idAndUserId(
                                p_RequestFilterInterviewHeader.getProvinceId(),
                                p_RequestFilterInterviewHeader.getCityId(),
                                p_RequestFilterInterviewHeader.getDistrictId(),
                                p_RequestFilterInterviewHeader.getBranchId(),
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                p_UserId);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City And District {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() != null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City District and Village Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndBranchIdAndInterviewEvent_idAndUserId(
                                p_RequestFilterInterviewHeader.getProvinceId(),
                                p_RequestFilterInterviewHeader.getCityId(),
                                p_RequestFilterInterviewHeader.getDistrictId(),
                                p_RequestFilterInterviewHeader.getVillageId(),
                                p_RequestFilterInterviewHeader.getBranchId(),
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                p_UserId);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() == null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find All Event Only");
                        interviewHeaders = interviewHeaderDAO.findByBranchIdAndInterviewEvent_IdAndUserId(
                                p_RequestFilterInterviewHeader.getBranchId(),
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                p_UserId);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Event Only {}", e.toString());
                    }
                }
            } else if (p_RequestFilterInterviewHeader.getBranchId() == null && p_UserId == null) {
                LOGGER.info("Filter Interview Header without Branch + Without User");
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                    try {
                        LOGGER.info("Find By Province Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndInterviewEvent_Id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getInterviewEventId());
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province And City Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndInterviewEvent_id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getInterviewEventId());
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City and District Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndInterviewEvent_id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getInterviewEventId());
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City And District {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() != null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City District and Village Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndInterviewEvent_id(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getVillageId(), p_RequestFilterInterviewHeader.getInterviewEventId());
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() == null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find All - By Event Only");
                        interviewHeaders = interviewHeaderDAO.findByInterviewEvent_Id(p_RequestFilterInterviewHeader.getInterviewEventId());
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Event Only {}", e.toString());
                    }
                }
            } else if (p_RequestFilterInterviewHeader.getBranchId() == null && p_UserId != null) {
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                    try {
                        LOGGER.info("Find By Province Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndInterviewEvent_IdAndUserId(
                                p_RequestFilterInterviewHeader.getProvinceId(),
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                p_UserId);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province And City Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndInterviewEvent_idAndUserId(
                                p_RequestFilterInterviewHeader.getProvinceId(),
                                p_RequestFilterInterviewHeader.getCityId(),
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                p_UserId);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City and District Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndInterviewEvent_idAndUserId(
                                p_RequestFilterInterviewHeader.getProvinceId(),
                                p_RequestFilterInterviewHeader.getCityId(),
                                p_RequestFilterInterviewHeader.getDistrictId(),
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                p_UserId);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City And District {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() != null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City District and Village Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndInterviewEvent_idAndUserId(
                                p_RequestFilterInterviewHeader.getProvinceId(),
                                p_RequestFilterInterviewHeader.getCityId(),
                                p_RequestFilterInterviewHeader.getDistrictId(),
                                p_RequestFilterInterviewHeader.getVillageId(),
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                p_UserId);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() == null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find All - By Event Only");
                        interviewHeaders = interviewHeaderDAO.findByInterviewEvent_IdAndUserId(
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                p_UserId);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Event Only {}", e.toString());
                    }
                }
            }
        }else {
            LOGGER.error("Interview Event Id can not be NULL");
        }
        return interviewHeaders;
    }

    public Page<InterviewHeader> findInterviewHeaderByAreaAndEventAndBranchAndUserIdPaged(RequestFilterInterviewHeader p_RequestFilterInterviewHeader, PageRequest p_pageRequest, List<Long> userId) throws ServiceException {
        Page<InterviewHeader> interviewHeaders = null;
        if (p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
            if (p_RequestFilterInterviewHeader.getBranchId() != null ) {
                LOGGER.info("Filter Interview Header with Branch {} ", p_RequestFilterInterviewHeader.getBranchId());
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                    try {
                        LOGGER.info("Find By Province Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndBranchIdAndInterviewEvent_IdAndUserIdIn(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId(), userId, p_pageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province And City Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndBranchIdAndInterviewEvent_idAndUserIdIn(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId(), userId, p_pageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City and District Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndBranchIdAndInterviewEvent_idAndUserIdIn(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId(), userId, p_pageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City And District {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() != null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City District and Village Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndBranchIdAndInterviewEvent_idAndUserIdIn(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getVillageId(), p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId(), userId, p_pageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() == null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find All Event Only");
                        interviewHeaders = interviewHeaderDAO.findByBranchIdAndInterviewEvent_IdAndUserIdIn(p_RequestFilterInterviewHeader.getBranchId(), p_RequestFilterInterviewHeader.getInterviewEventId(), userId, p_pageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Event Only {}", e.toString());
                    }
                }
            }else if (p_RequestFilterInterviewHeader.getBranchId() != null) {
                LOGGER.info("Filter Interview Header with Branch {}", p_RequestFilterInterviewHeader.getBranchId());
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                    try {
                        LOGGER.info("Find By Province Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndBranchIdAndInterviewEvent_IdAndUserIdIn(
                                p_RequestFilterInterviewHeader.getProvinceId(),
                                p_RequestFilterInterviewHeader.getBranchId(),
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                userId,
                                p_pageRequest
                        );
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province And City Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndBranchIdAndInterviewEvent_idAndUserIdIn(
                                p_RequestFilterInterviewHeader.getProvinceId(),
                                p_RequestFilterInterviewHeader.getCityId(),
                                p_RequestFilterInterviewHeader.getBranchId(),
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                userId,
                                p_pageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City and District Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndBranchIdAndInterviewEvent_idAndUserIdIn(
                                p_RequestFilterInterviewHeader.getProvinceId(),
                                p_RequestFilterInterviewHeader.getCityId(),
                                p_RequestFilterInterviewHeader.getDistrictId(),
                                p_RequestFilterInterviewHeader.getBranchId(),
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                userId,
                                p_pageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City And District {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() != null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City District and Village Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndBranchIdAndInterviewEvent_idAndUserIdIn(
                                p_RequestFilterInterviewHeader.getProvinceId(),
                                p_RequestFilterInterviewHeader.getCityId(),
                                p_RequestFilterInterviewHeader.getDistrictId(),
                                p_RequestFilterInterviewHeader.getVillageId(),
                                p_RequestFilterInterviewHeader.getBranchId(),
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                userId,
                                p_pageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() == null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find All Event Only");
                        interviewHeaders = interviewHeaderDAO.findByBranchIdAndInterviewEvent_IdAndUserIdIn(
                                p_RequestFilterInterviewHeader.getBranchId(),
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                userId, p_pageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Event Only {}", e.toString());
                    }
                }
            } else if (p_RequestFilterInterviewHeader.getBranchId() == null) {
                LOGGER.info("Filter Interview Header without Branch + Without User");
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                    try {
                        LOGGER.info("Find By Province Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndInterviewEvent_IdAndUserIdIn(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getInterviewEventId(), userId, p_pageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province And City Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndInterviewEvent_idAndUserIdIn(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getInterviewEventId(), userId, p_pageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City and District Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndInterviewEvent_idAndUserIdIn(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getInterviewEventId(), userId, p_pageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City And District {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() != null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City District and Village Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndInterviewEvent_idAndUserIdIn(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getVillageId(), p_RequestFilterInterviewHeader.getInterviewEventId(), userId, p_pageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() == null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find All - By Event Only");
                        interviewHeaders = interviewHeaderDAO.findByInterviewEvent_IdAndUserIdIn(p_RequestFilterInterviewHeader.getInterviewEventId(), userId, p_pageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Event Only {}", e.toString());
                    }
                }
            } else if (p_RequestFilterInterviewHeader.getBranchId() == null) {
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                    try {
                        LOGGER.info("Find By Province Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndInterviewEvent_IdAndUserIdIn(
                                p_RequestFilterInterviewHeader.getProvinceId(),
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                userId,
                                p_pageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province And City Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndInterviewEvent_idAndUserIdIn(
                                p_RequestFilterInterviewHeader.getProvinceId(),
                                p_RequestFilterInterviewHeader.getCityId(),
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                userId,
                                p_pageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City and District Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndInterviewEvent_idAndUserIdIn(
                                p_RequestFilterInterviewHeader.getProvinceId(),
                                p_RequestFilterInterviewHeader.getCityId(),
                                p_RequestFilterInterviewHeader.getDistrictId(),
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                userId,
                                p_pageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province And City And District {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() != null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find By Province City District and Village Id");
                        interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndInterviewEvent_idAndUserIdIn(
                                p_RequestFilterInterviewHeader.getProvinceId(),
                                p_RequestFilterInterviewHeader.getCityId(),
                                p_RequestFilterInterviewHeader.getDistrictId(),
                                p_RequestFilterInterviewHeader.getVillageId(),
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                userId,
                                p_pageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Province {}", e.toString());
                    }
                }
                if (p_RequestFilterInterviewHeader.getProvinceId() == null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null && p_RequestFilterInterviewHeader.getInterviewEventId() != null) {
                    try {
                        LOGGER.info("Find All - By Event Only");
                        interviewHeaders = interviewHeaderDAO.findByInterviewEvent_IdAndUserIdIn(
                                p_RequestFilterInterviewHeader.getInterviewEventId(),
                                userId,
                                p_pageRequest);
                    } catch (DAOException e) {
                        LOGGER.error("Error Search Int Header By Event Only {}", e.toString());
                    }
                }
            }
        }else {
            LOGGER.error("Interview Event Id can not be NULL");
        }
        return interviewHeaders;
    }

    @Override
    public List<InterviewHeader> findInterviewHeaderByArea(RequestFilterInterviewHeader p_RequestFilterInterviewHeader) throws ServiceException {
        List<InterviewHeader> interviewHeaders = new ArrayList<>();
        if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null) {
            try {
                LOGGER.info("Find By Province Id");
                interviewHeaders = interviewHeaderDAO.findByProvinceId(p_RequestFilterInterviewHeader.getProvinceId());
            } catch (DAOException e) {
                LOGGER.error("Error Search Int Header By Province {}", e.toString());
            }
        }
        if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
            try {
                LOGGER.info("Find By Province And City Id");
                interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityId(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId());
            } catch (DAOException e) {
                LOGGER.error("Error Search Int Header By Province And City {}", e.toString());
            }
        }
        if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() == null) {
            try {
                LOGGER.info("Find By Province City and District Id");
                interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictId(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId());
            } catch (DAOException e) {
                LOGGER.error("Error Search Int Header By Province And City And District {}", e.toString());
            }
        }
        if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() != null) {
            try {
                LOGGER.info("Find By Province City District and Village Id");
                interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndVillageId(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getVillageId());
            } catch (DAOException e) {
                LOGGER.error("Error Search Int Header By Province {}", e.toString());
            }
        }
        if (p_RequestFilterInterviewHeader.getProvinceId() == null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
            LOGGER.info("Find All");
            interviewHeaders = interviewHeaderDAO.findAll();
        }
        return interviewHeaders;
    }


    @Override
    public Page<InterviewHeader> findInterviewHeaderByAreaAndUserId(RequestFilterInterviewHeader p_RequestFilterInterviewHeader, List<Long> p_UserId, PageRequest p_PageRequest) throws ServiceException {
        Page<InterviewHeader> interviewHeaders = null;
        if (p_UserId == null) {
            if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null) {
                try {
                    LOGGER.info("Find By Province Id");
                    interviewHeaders = interviewHeaderDAO.findByProvinceId(p_RequestFilterInterviewHeader.getProvinceId(), p_PageRequest);
                } catch (DAOException e) {
                    LOGGER.error("Error Search Int Header By Province {}", e.toString());
                }
            }
            if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                try {
                    LOGGER.info("Find By Province And City Id");
                    interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityId(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_PageRequest);
                } catch (DAOException e) {
                    LOGGER.error("Error Search Int Header By Province And City {}", e.toString());
                }
            }
            if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                try {
                    LOGGER.info("Find By Province City and District Id");
                    interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictId(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_PageRequest);
                } catch (DAOException e) {
                    LOGGER.error("Error Search Int Header By Province And City And District {}", e.toString());
                }
            }
            if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() != null) {
                try {
                    LOGGER.info("Find By Province City District and Village Id");
                    interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndVillageId(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getVillageId(), p_PageRequest);
                } catch (DAOException e) {
                    LOGGER.error("Error Search Int Header By Province {}", e.toString());
                }
            }
            if (p_RequestFilterInterviewHeader.getProvinceId() == null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                LOGGER.info("Find All");
                interviewHeaders = findAll(p_PageRequest);
            }
        }else {
            if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null) {
                try {
                    LOGGER.info("Find By Province Id");
                    interviewHeaders = interviewHeaderDAO.findByProvinceIdAndUserIdIn(
                            p_RequestFilterInterviewHeader.getProvinceId(),
                            p_UserId,
                            p_PageRequest);
                } catch (DAOException e) {
                    LOGGER.error("Error Search Int Header By Province {}", e.toString());
                }
            }
            if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                try {
                    LOGGER.info("Find By Province And City Id");
                    interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndUserIdIn(
                            p_RequestFilterInterviewHeader.getProvinceId(),
                            p_RequestFilterInterviewHeader.getCityId(),
                            p_UserId,
                            p_PageRequest);
                } catch (DAOException e) {
                    LOGGER.error("Error Search Int Header By Province And City {}", e.toString());
                }
            }
            if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                try {
                    LOGGER.info("Find By Province City and District Id");
                    interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndUserIdIn(
                            p_RequestFilterInterviewHeader.getProvinceId(),
                            p_RequestFilterInterviewHeader.getCityId(),
                            p_RequestFilterInterviewHeader.getDistrictId(),
                            p_UserId,
                            p_PageRequest);
                } catch (DAOException e) {
                    LOGGER.error("Error Search Int Header By Province And City And District {}", e.toString());
                }
            }
            if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() != null) {
                try {
                    LOGGER.info("Find By Province City District and Village Id");
                    interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndUserIdIn(
                            p_RequestFilterInterviewHeader.getProvinceId(),
                            p_RequestFilterInterviewHeader.getCityId(),
                            p_RequestFilterInterviewHeader.getDistrictId(),
                            p_RequestFilterInterviewHeader.getVillageId(),
                            p_UserId,
                            p_PageRequest);
                } catch (DAOException e) {
                    LOGGER.error("Error Search Int Header By Province {}", e.toString());
                }
            }
            if (p_RequestFilterInterviewHeader.getProvinceId() == null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                LOGGER.info("Find All");
                try {
                    interviewHeaders = interviewHeaderDAO.findByUserIdIn(p_UserId, p_PageRequest);
                } catch (DAOException e) {
                    LOGGER.error("Error Find By User Id {}" ,e.toString());
                }
            }
        }
        return interviewHeaders;
    }

    @Override
    public List<InterviewHeader> findInterviewHeaderByAreaAndUserId(RequestFilterInterviewHeader p_RequestFilterInterviewHeader, Long p_UserId) throws ServiceException {
        List<InterviewHeader> interviewHeaders = new ArrayList<>();
        if (p_UserId == null) {
            if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null) {
                try {
                    LOGGER.info("Find By Province Id");
                    interviewHeaders = interviewHeaderDAO.findByProvinceId(p_RequestFilterInterviewHeader.getProvinceId());
                } catch (DAOException e) {
                    LOGGER.error("Error Search Int Header By Province {}", e.toString());
                }
            }
            if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                try {
                    LOGGER.info("Find By Province And City Id");
                    interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityId(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId());
                } catch (DAOException e) {
                    LOGGER.error("Error Search Int Header By Province And City {}", e.toString());
                }
            }
            if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                try {
                    LOGGER.info("Find By Province City and District Id");
                    interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictId(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId());
                } catch (DAOException e) {
                    LOGGER.error("Error Search Int Header By Province And City And District {}", e.toString());
                }
            }
            if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() != null) {
                try {
                    LOGGER.info("Find By Province City District and Village Id");
                    interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndVillageId(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getVillageId());
                } catch (DAOException e) {
                    LOGGER.error("Error Search Int Header By Province {}", e.toString());
                }
            }
            if (p_RequestFilterInterviewHeader.getProvinceId() == null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                LOGGER.info("Find All");
                interviewHeaders = findAll();
            }
        }else {
            LOGGER.info("By Area and User Id");
            if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null) {
                try {
                    LOGGER.info("Find By Province Id");
                    interviewHeaders = interviewHeaderDAO.findByProvinceIdAndUserId(
                            p_RequestFilterInterviewHeader.getProvinceId(),
                            p_UserId);
                } catch (DAOException e) {
                    LOGGER.error("Error Search Int Header By Province {}", e.toString());
                }
            }
            if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                try {
                    LOGGER.info("Find By Province And City Id");
                    interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndUserId(
                            p_RequestFilterInterviewHeader.getProvinceId(),
                            p_RequestFilterInterviewHeader.getCityId(),
                            p_UserId);
                } catch (DAOException e) {
                    LOGGER.error("Error Search Int Header By Province And City {}", e.toString());
                }
            }
            if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                try {
                    LOGGER.info("Find By Province City and District Id");
                    interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndUserId(
                            p_RequestFilterInterviewHeader.getProvinceId(),
                            p_RequestFilterInterviewHeader.getCityId(),
                            p_RequestFilterInterviewHeader.getDistrictId(),
                            p_UserId);
                } catch (DAOException e) {
                    LOGGER.error("Error Search Int Header By Province And City And District {}", e.toString());
                }
            }
            if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() != null) {
                try {
                    LOGGER.info("Find By Province City District and Village Id");
                    interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndUserId(
                            p_RequestFilterInterviewHeader.getProvinceId(),
                            p_RequestFilterInterviewHeader.getCityId(),
                            p_RequestFilterInterviewHeader.getDistrictId(),
                            p_RequestFilterInterviewHeader.getVillageId(),
                            p_UserId);
                } catch (DAOException e) {
                    LOGGER.error("Error Search Int Header By Province {}", e.toString());
                }
            }
            if (p_RequestFilterInterviewHeader.getProvinceId() == null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                LOGGER.info("Find All");
                try {
                    interviewHeaders = interviewHeaderDAO.findByUserId(p_UserId);
                } catch (DAOException e) {
                    LOGGER.error("Error Find By User Id {}" ,e.toString());
                }
            }
        }
        return interviewHeaders;
    }

    public Page<InterviewHeader> findInterviewHeaderByAreaAndUserIdPaged(RequestFilterInterviewHeader p_RequestFilterInterviewHeader, Long p_UserId, PageRequest p_PageRequest) throws ServiceException {
        List<InterviewHeader> interviewHeaders = new ArrayList<>();
        if (p_UserId == null) {
            if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null) {
                try {
                    LOGGER.info("Find By Province Id");
                    interviewHeaders = interviewHeaderDAO.findByProvinceId(p_RequestFilterInterviewHeader.getProvinceId());
                } catch (DAOException e) {
                    LOGGER.error("Error Search Int Header By Province {}", e.toString());
                }
            }
            if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                try {
                    LOGGER.info("Find By Province And City Id");
                    interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityId(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId());
                } catch (DAOException e) {
                    LOGGER.error("Error Search Int Header By Province And City {}", e.toString());
                }
            }
            if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                try {
                    LOGGER.info("Find By Province City and District Id");
                    interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictId(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId());
                } catch (DAOException e) {
                    LOGGER.error("Error Search Int Header By Province And City And District {}", e.toString());
                }
            }
            if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() != null) {
                try {
                    LOGGER.info("Find By Province City District and Village Id");
                    interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndVillageId(p_RequestFilterInterviewHeader.getProvinceId(), p_RequestFilterInterviewHeader.getCityId(), p_RequestFilterInterviewHeader.getDistrictId(), p_RequestFilterInterviewHeader.getVillageId());
                } catch (DAOException e) {
                    LOGGER.error("Error Search Int Header By Province {}", e.toString());
                }
            }
            if (p_RequestFilterInterviewHeader.getProvinceId() == null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                LOGGER.info("Find All");
                interviewHeaders = findAll();
            }
        }else {
            LOGGER.info("By Area and User Id");
            if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null) {
                try {
                    LOGGER.info("Find By Province Id");
                    interviewHeaders = interviewHeaderDAO.findByProvinceIdAndUserId(
                            p_RequestFilterInterviewHeader.getProvinceId(),
                            p_UserId);
                } catch (DAOException e) {
                    LOGGER.error("Error Search Int Header By Province {}", e.toString());
                }
            }
            if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                try {
                    LOGGER.info("Find By Province And City Id");
                    interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndUserId(
                            p_RequestFilterInterviewHeader.getProvinceId(),
                            p_RequestFilterInterviewHeader.getCityId(),
                            p_UserId);
                } catch (DAOException e) {
                    LOGGER.error("Error Search Int Header By Province And City {}", e.toString());
                }
            }
            if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                try {
                    LOGGER.info("Find By Province City and District Id");
                    interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndUserId(
                            p_RequestFilterInterviewHeader.getProvinceId(),
                            p_RequestFilterInterviewHeader.getCityId(),
                            p_RequestFilterInterviewHeader.getDistrictId(),
                            p_UserId);
                } catch (DAOException e) {
                    LOGGER.error("Error Search Int Header By Province And City And District {}", e.toString());
                }
            }
            if (p_RequestFilterInterviewHeader.getProvinceId() != null && p_RequestFilterInterviewHeader.getCityId() != null && p_RequestFilterInterviewHeader.getDistrictId() != null && p_RequestFilterInterviewHeader.getVillageId() != null) {
                try {
                    LOGGER.info("Find By Province City District and Village Id");
                    interviewHeaders = interviewHeaderDAO.findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndUserId(
                            p_RequestFilterInterviewHeader.getProvinceId(),
                            p_RequestFilterInterviewHeader.getCityId(),
                            p_RequestFilterInterviewHeader.getDistrictId(),
                            p_RequestFilterInterviewHeader.getVillageId(),
                            p_UserId);
                } catch (DAOException e) {
                    LOGGER.error("Error Search Int Header By Province {}", e.toString());
                }
            }
            if (p_RequestFilterInterviewHeader.getProvinceId() == null && p_RequestFilterInterviewHeader.getCityId() == null && p_RequestFilterInterviewHeader.getDistrictId() == null && p_RequestFilterInterviewHeader.getVillageId() == null) {
                LOGGER.info("Find All");
                try {
                    interviewHeaders = interviewHeaderDAO.findByUserId(p_UserId);
                } catch (DAOException e) {
                    LOGGER.error("Error Find By User Id {}" ,e.toString());
                }
            }
        }
        return new PaginationUtil<InterviewHeader>().makePagination(interviewHeaders, p_PageRequest);
    }

    @Override
    public Page<ContentVolunteerProgressDTO> findInterviewProgressByVolunteer(String p_Bearer, String p_Principal, RequestInterviewProgressByVolunteerDTO p_RequestInterviewProgressByVolunteerDTO, PageRequest p_PageRequest) throws ServiceException {
        List<ContentVolunteerProgressDTO> volunteerProgresses = new ArrayList<>();
        Page<InterviewHeader> interviewHeaders = null;
        ResponseRoleDTO role = principalFinder.findRoleByUserName(p_Bearer, p_Principal);
        if (role != null) {
            RequestFilterInterviewHeader filterHeader = new RequestFilterInterviewHeader();
            filterHeader.setInterviewEventId(p_RequestInterviewProgressByVolunteerDTO.getInterviewEventId());
            filterHeader.setProvinceId(p_RequestInterviewProgressByVolunteerDTO.getProvinceId());
            filterHeader.setCityId(p_RequestInterviewProgressByVolunteerDTO.getCityId());
            filterHeader.setDistrictId(p_RequestInterviewProgressByVolunteerDTO.getDistrictId());
            filterHeader.setVillageId(p_RequestInterviewProgressByVolunteerDTO.getVillageId());

            List<ResponseUserDTO> users;
            List<Long> userIds = new ArrayList<>();
            if (p_RequestInterviewProgressByVolunteerDTO.getUserName() != null && p_RequestInterviewProgressByVolunteerDTO.getUserName().length() > 0) {
                RequestFindByNameDTO requestFindByNameDTO = new RequestFindByNameDTO();
                requestFindByNameDTO.setName(p_RequestInterviewProgressByVolunteerDTO.getUserName());
                users = userClient.findVolunteerByNameContaining(p_Bearer, requestFindByNameDTO);
                for (ResponseUserDTO responseUserDTO : users) {
                    userIds.add(responseUserDTO.getId());
                }
            }

            if ((!role.getCode().equals(RoleCode.ACTUATOR)) && (!role.getCode().equals(RoleCode.VOLUNTEER) && (!role.getCode().equals(RoleCode.DIREKSI)) && (!role.getCode().equals(RoleCode.ADMIN)))) {
                LOGGER.info("Found Tenant Role");
                ResponseUserDTO user = principalFinder.findUserByUserName(p_Bearer, p_Principal);
                if (user != null) {
                    if (user.getBranch() != null) {
                        filterHeader.setBranchId(user.getBranch().getId());
                        if (userIds.size() > 0) {
                            LOGGER.info("Filter by user Id");
                            interviewHeaders = findInterviewHeaderByAreaAndEventAndBranchAndUserIdPaged(filterHeader, p_PageRequest, userIds);
                        }else {
                            LOGGER.info("Filter Without user Id");
                            interviewHeaders = findInterviewHeaderByAreaAndEventAndBranch(filterHeader, p_PageRequest);
                        }
                    } else {
                        LOGGER.error("Branch is Not Found");
                    }
                } else {
                    LOGGER.error("User is Not Found");
                }
            } else if (role.getCode().equals(RoleCode.ADMIN) || role.getCode().equals(RoleCode.DIREKSI)) {
                LOGGER.info("Found Admin Role");
                if (userIds.size() > 0) {
                    LOGGER.info("Filter by user Id");
                    interviewHeaders = findInterviewHeaderByAreaAndEventAndBranchAndUserIdPaged(filterHeader, p_PageRequest, userIds);
                }else {
                    LOGGER.info("Filter Without user Id");
                    interviewHeaders = findInterviewHeaderByAreaAndEventAndBranch(filterHeader, p_PageRequest);
                }

            } else {
                LOGGER.error("Your Role is UnAuthorized to Access This Service");
            }
            if (interviewHeaders != null) {
                LOGGER.info("Found {} Interview Header By Area", interviewHeaders.getContent().size());
                RequestFindByIdDTO requestFindByIdDTO = new RequestFindByIdDTO();
                for (InterviewHeader interviewHeader : interviewHeaders.getContent()) {
                    ContentVolunteerProgressDTO volunteerProgress = new ContentVolunteerProgressDTO();
                    volunteerProgress.setInterviewHeaderId(interviewHeader.getId());
                    if (interviewHeader.getVillageId() != null) {
                        requestFindByIdDTO.setId(interviewHeader.getVillageId());
                        GenericSingleDATAResponseDTO<ResponseVillageDTO> responseVillageDTO = villageClient.findVillageById(p_Bearer, requestFindByIdDTO);
                        if (responseVillageDTO != null) {
                            volunteerProgress.setVillage(responseVillageDTO.getContent());
                        }
                    }
                    if (interviewHeader.getUserId() != null) {
                        requestFindByIdDTO.setId(interviewHeader.getUserId());
                        GenericSingleDATAResponseDTO<ResponseUserDTO> responseUserDTO = userClient.findByUserId(p_Bearer, requestFindByIdDTO);
                        if (responseUserDTO != null) {
                            volunteerProgress.setVolunteer(responseUserDTO.getContent());
                        }
                    }
                    Integer numberOfCompletedInterview = interviewDetailService.countInterviewDetailByInterviewHeaderIdAndInterviewStatus(
                            interviewHeader.getId(), LsiThirdPartyConstant.InterviewStatus.COMPLETED
                    );
                    Integer numberOfDataValid = interviewDetailService.countInterviewDetailByInterviewHeaderIdAndScore(
                            interviewHeader.getId(), 25
                    );

                    String dataInterviewCompletion = numberOfCompletedInterview+"/"+interviewHeader.getNumberOfRespondent()+" ("+getInterviewCompletion(interviewHeader.getNumberOfRespondent(), numberOfCompletedInterview)+"%)";
                    String dataInterviewValidity = numberOfDataValid+"/"+numberOfCompletedInterview+" ("+getInterviewValidity(numberOfDataValid, numberOfCompletedInterview)+"%)";

                    volunteerProgress.setInterviewCompletion(dataInterviewCompletion);
                    volunteerProgress.setInterviewValidity(dataInterviewValidity);

                    volunteerProgresses.add(volunteerProgress);
                }
                return new PageImpl<>(volunteerProgresses, p_PageRequest, interviewHeaders.getTotalElements());
            }else {
                LOGGER.info("Interview Header Not Found");
                return new PageImpl<>(volunteerProgresses, p_PageRequest, 0);
            }
        }else {
            return new PageImpl<>(volunteerProgresses, p_PageRequest, 0);
        }
    }

    @Override
    public Page<ContentAreaProgressDTO> findInterviewProgressByArea(String p_Bearer, String p_Principal, RequestInterviewProgressByAreaDTO p_RequestInterviewProgressByAreaDTO, PageRequest p_PageRequest) throws ServiceException {
        List<ContentAreaProgressDTO> areaProgresses = new ArrayList<>();
        ResponseRoleDTO role = principalFinder.findRoleByUserName(p_Bearer, p_Principal);
        Page<Object[]> dataProgressArea = null;
        if (role != null) {
            RequestFilterInterviewHeader filterHeader = new RequestFilterInterviewHeader();
            filterHeader.setInterviewEventId(p_RequestInterviewProgressByAreaDTO.getInterviewEventId());
            filterHeader.setProvinceId(p_RequestInterviewProgressByAreaDTO.getProvinceId());
            filterHeader.setCityId(p_RequestInterviewProgressByAreaDTO.getCityId());
            filterHeader.setDistrictId(p_RequestInterviewProgressByAreaDTO.getDistrictId());
            filterHeader.setVillageId(p_RequestInterviewProgressByAreaDTO.getVillageId());

            if ((!role.getCode().equals(RoleCode.ACTUATOR)) && (!role.getCode().equals(RoleCode.VOLUNTEER) && (!role.getCode().equals(RoleCode.DIREKSI)) && (!role.getCode().equals(RoleCode.ADMIN)))) {
                LOGGER.info("Found Tenant Role");
                ResponseUserDTO user = principalFinder.findUserByUserName(p_Bearer, p_Principal);
                if (user != null) {
                    if (user.getBranch() != null) {
                        filterHeader.setBranchId(user.getBranch().getId());
//                        dataProgressArea = interviewDetailService.findInterviewHeaderByAreaAndEventAndBranch(filterHeader, p_PageRequest);
                        dataProgressArea = interviewDetailService.findInterviewHeaderByAreaAndEventAndBranchPaged(filterHeader, p_PageRequest);
                    } else {
                        LOGGER.error("Branch is Not Found");
                    }
                } else {
                    LOGGER.error("User is Not Found");
                }
            } else if (role.getCode().equals(RoleCode.ADMIN) || role.getCode().equals(RoleCode.DIREKSI)) {
                LOGGER.info("Found Admin Role");
                dataProgressArea = interviewDetailService.findInterviewHeaderByAreaAndEventAndBranchPaged(filterHeader, p_PageRequest);
            } else {
                LOGGER.error("Role is UnAuthorized to Access This Service");
            }

            if (dataProgressArea != null) {
                LOGGER.info("Found {} Data Progress By Area", dataProgressArea.getContent().size());
                for (Object[] progressArea : dataProgressArea) {
                    ContentAreaProgressDTO contentAreaProgressDTO = new ContentAreaProgressDTO();
                    RequestFindByIdDTO request = new RequestFindByIdDTO();
                    BigInteger id = (BigInteger) progressArea[1];
                    request.setId(id.longValue());
                    GenericSingleDATAResponseDTO<ResponseVillageDTO> villageDTO = villageClient.findVillageById(p_Bearer, request);
                    contentAreaProgressDTO.setVillage(villageDTO.getContent());
                    BigDecimal nRespondent = (BigDecimal) progressArea[0];
                    contentAreaProgressDTO.setNumberOfRespondent(nRespondent.intValue());
                    BigInteger nCompleteInterview = (BigInteger) progressArea[2];
                    contentAreaProgressDTO.setNumberOfCompletedInterview(nCompleteInterview.intValue());
                    BigInteger nDataValid = (BigInteger) progressArea[3];
                    contentAreaProgressDTO.setNumberOfDataValid(nDataValid.intValue());
                    Double interviewCompletion = getInterviewCompletion(contentAreaProgressDTO.getNumberOfRespondent(), contentAreaProgressDTO.getNumberOfCompletedInterview());
                    Double interviewValidity = getInterviewValidity(contentAreaProgressDTO.getNumberOfDataValid(), contentAreaProgressDTO.getNumberOfCompletedInterview());
                    contentAreaProgressDTO.setNumberOfInterviewCompletion(interviewCompletion);
                    contentAreaProgressDTO.setNumberOfInterviewValidity(interviewValidity);
                    areaProgresses.add(contentAreaProgressDTO);
                }
                return new PageImpl<>(areaProgresses, p_PageRequest, dataProgressArea.getTotalElements());
            }else {
                LOGGER.error("Interview Headers Not Found");
                return new PageImpl<>(areaProgresses, p_PageRequest, 0);
            }
        }else {
            LOGGER.error("Role Not Found");
            return new PageImpl<>(areaProgresses, p_PageRequest, 0);
        }
    }

    private Double getInterviewCompletion(Integer nRespondent, Integer numberOfCompletedInterview) {
        RequestFormulaInterviewCompletion requestFormulaInterviewCompletion = new RequestFormulaInterviewCompletion();
        requestFormulaInterviewCompletion.setNumberOfRespondent(nRespondent);
        requestFormulaInterviewCompletion.setNumberOfCompletedInterview(numberOfCompletedInterview);
        formulaInterviewCompletion.setParam(requestFormulaInterviewCompletion);
        formulaInterviewCompletion.calculate();
        return formulaInterviewCompletion.getResult();
    }

    private Double getInterviewValidity(Integer numberOfDataValid, Integer numberOfCompletedInterview) {
        RequestFormulaInterviewValidity requestFormulaInterviewValidity = new RequestFormulaInterviewValidity();
        requestFormulaInterviewValidity.setNumberOfDataValid(numberOfDataValid);
        requestFormulaInterviewValidity.setNumberOfCompletedInterview(numberOfCompletedInterview);
        formulaInterviewValidity.setParam(requestFormulaInterviewValidity);
        formulaInterviewValidity.calculate();
        return formulaInterviewValidity.getResult();
    }

    @Override
    public ResponseDetailInterviewDTO findInterviewDetailByRespondentName(String p_Bearer, String p_RespondentName) throws ServiceException {
        ResponseDetailInterviewDTO response = new ResponseDetailInterviewDTO();
        InterviewDetail interviewDetail = interviewDetailService.findByRespondentName(p_RespondentName);
        if (interviewDetail != null) {
            InterviewHeader interviewHeader = interviewDetail.getInterviewHeader();
            List<ContentDetailInterviewComponent> contentDetails = new ArrayList<>();
            List<ResponseInterviewImageDTO> interviewImages = new ArrayList<>();
            if (interviewHeader != null) {
                RequestFindByIdDTO requestFindByIdDTO = new RequestFindByIdDTO();
                if (interviewHeader.getVillageId() != null) {
                    requestFindByIdDTO.setId(interviewHeader.getVillageId());
                    GenericSingleDATAResponseDTO<ResponseVillageDTO> village = villageClient.findVillageById(p_Bearer, requestFindByIdDTO);
                    if (village != null) {
                        if (village.getContent() != null) {
                            response.setVillage(village.getContent().getName());
                            if (village.getContent().getDistrict() != null) {
                                response.setDistrict(village.getContent().getDistrict().getName());
                                if (village.getContent().getDistrict().getCity() != null) {
                                    response.setCity(village.getContent().getDistrict().getCity().getName());
                                    if (village.getContent().getDistrict().getCity().getProvince() != null) {
                                        response.setProvince(village.getContent().getDistrict().getCity().getProvince().getName());
                                    }
                                }
                            }
                        }
                    }
                }
                if (interviewHeader.getUserId() != null) {
                    requestFindByIdDTO.setId(interviewHeader.getUserId());
                    GenericSingleDATAResponseDTO<ResponseUserDTO> user = userClient.findByUserId(p_Bearer, requestFindByIdDTO);
                    if (user != null) {
                        if (user.getContent() != null) {
                            response.setVolunteerName(user.getContent().getName());
                            requestFindByIdDTO.setId(user.getContent().getId());
                            ResponsePhoneNumberEmail responsePhoneNumberEmail = userClient.getPhoneNumberEmail(p_Bearer, requestFindByIdDTO);
                            if (responsePhoneNumberEmail.getPhoneNumber() != null) {
                                response.setPhoneNumber(responsePhoneNumberEmail.getPhoneNumber());
                            }
                            if (responsePhoneNumberEmail.getEmail() != null) {
                                response.setEmail(responsePhoneNumberEmail.getEmail());
                            }

                        }
                    }
                }
                response.setEventName(interviewHeader.getInterviewEvent().getName());

                List<InterviewComponent> interviewComponents = new ArrayList<>();
                if (interviewHeader.getInterviewEvent() != null) {
                    interviewComponents = interviewComponentService.findByInterviewEventId(interviewHeader.getInterviewEvent().getId());
                }

                for (InterviewComponent interviewComponent : interviewComponents) {
                    ContentDetailInterviewComponent contentDetail = new ContentDetailInterviewComponent();
                    contentDetail.setInterviewComponent(interviewComponentMapper.convert(interviewComponent));

                    List<Question> questions = questionService.findByComponentId(interviewComponent.getId());
                    List<ContentQuestionAnswerDTO> contentQuestionAnswers = new ArrayList<>();
                    for (Question question : questions) {
                        ContentQuestionAnswerDTO contentQuestionAnswer = new ContentQuestionAnswerDTO();
                        contentQuestionAnswer.setQuestion(questionMapper.convert(question));

                        List<ResponseQuestionAnswerDTO> answers = new ArrayList<>();
                        for (QuestionAnswer questionAnswer : question.getQuestionAnswers()) {
                            answers.add(questionAnswerMapper.convert(questionAnswer));
                        }
                        contentQuestionAnswer.setAnswers(answers);


                        contentQuestionAnswer.setUserAnswer(interviewUserAnswerMapper.convert(interviewUserAnswerService.findByQuestionIdAndInterviewDetailId(question.getId(), interviewDetail.getId())));
                        contentQuestionAnswers.add(contentQuestionAnswer);
                    }
                    contentDetail.setQuestionAnswers(contentQuestionAnswers);

                    contentDetails.add(contentDetail);
                }
            } else {
                LOGGER.error("Interview Header Not Found");
            }
            response.setRespondentName(interviewDetail.getRespondentName());
            response.setRespondentPhoneNumber(interviewDetail.getRespondentPhoneNumber());
            response.setRespondentAddress(interviewDetail.getRespondentAddress());
            response.setInterviewStartTime(FormatDateConstant.DEFAULT.format(interviewDetail.getInterviewStartTime()));
            response.setInterviewEndTime(FormatDateConstant.DEFAULT.format(interviewDetail.getInterviewEndTime()));
            response.setLatitude(String.valueOf(interviewDetail.getLatitude()));
            response.setLongitude(String.valueOf(interviewDetail.getLongitude()));
            response.setValidityStatus(interviewDetail.getValidityStatus());
            response.setRemarks(interviewDetail.getRemarks());
            response.setDetailComponents(contentDetails);
            for (InterviewImage interviewImage : interviewDetail.getInterviewImages()) {
                interviewImages.add(interviewImageMapper.convert(interviewImage));
            }
            response.setEvidences(interviewImages);
            response.setResponseData(new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg()));
        }else {
            response.setResponseData(new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg()));
            LOGGER.error("Interview Detail Not Found");
        }
        return null;
    }

    @Override
    public List<Long> findVillageIdByUserId(Long p_UserId) throws ServiceException {
        try {
            return interviewHeaderDAO.findVillageIdByUserId(p_UserId);
        } catch (DAOException e) {
            LOGGER.error("Error Find Village By User Id {}", e.toString());
            return null;
        }
    }

    @Override
    public ResponseData assignAgent(String p_Bearer, Long eventId, Long p_BranchId, Long p_UserId, Map<Long, Integer> mapVillageRespondent) throws ServiceException {
        ResponseData responseData = new ResponseData(EResponseCode.RC_SUCCESS.getResponseCode(), EResponseCode.RC_SUCCESS.getResponseMsg());
        InterviewEvent interviewEvent = interviewEventService.findById(eventId);
        if (interviewEvent != null) {
            for (Map.Entry<Long, Integer> mapVillageResp : mapVillageRespondent.entrySet()) {
                InterviewHeader interviewHeader = new InterviewHeader();
                interviewHeader.setUserId(p_UserId);
                interviewHeader.setInterviewEvent(interviewEvent);
                if (mapVillageResp.getKey() != null) {
                    interviewHeader.setVillageId(mapVillageResp.getKey());
                    RequestFindByIdDTO requestFindById = new RequestFindByIdDTO();
                    requestFindById.setId(mapVillageResp.getKey());
                    GenericSingleDATAResponseDTO<ResponseVillageDTO> village = villageClient.findVillageById(p_Bearer, requestFindById);
                    if (p_BranchId != null) {
                        interviewHeader.setBranchId(p_BranchId);
                    }
                    if (village != null) {
                        if (village.getContent() != null) {
                            if (village.getContent().getDistrict() != null) {
                                interviewHeader.setDistrictId(village.getContent().getDistrict().getId());
                                if (village.getContent().getDistrict().getCity() != null) {
                                    interviewHeader.setCityId(village.getContent().getDistrict().getCity().getId());
                                    if (village.getContent().getDistrict().getCity().getProvince() != null) {
                                        interviewHeader.setProvinceId(village.getContent().getDistrict().getCity().getProvince().getId());
                                    } else {
                                        LOGGER.info("Province Not Found");
                                    }
                                }else {
                                    LOGGER.info("City Not Found");
                                }
                            }else {
                                LOGGER.info("District Not Found");
                            }
                        } else {
                            LOGGER.error("Village Content is Empty");
                        }
                    }else {
                        LOGGER.error("Village is not Found");
                    }
                }else {
                    LOGGER.error("Key Village is NULL");
                }

                interviewHeader.setNumberOfRespondent(mapVillageResp.getValue());
                insert(interviewHeader);
            }
        }else {
            LOGGER.error("Event Not Found");
            responseData = new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg());
        }
        return responseData;
    }

    @Override
    public List<InterviewHeader> findByVolunteerId(Long p_VolunteerId) throws ServiceException {
        try {
            return interviewHeaderDAO.findByUserId(p_VolunteerId);
        } catch (DAOException e) {
            LOGGER.error("Error Find By User Id {}", e.toString());
            return null;
        }
    }

    @Override
    public Integer countTotalNumberOfRespondentByUserId(Long p_UserId) throws ServiceException {
        try {
            return interviewHeaderDAO.countTotalNumberOfRespondentByUserId(p_UserId);
        } catch (DAOException e) {
            LOGGER.error("Error Count Total Number Of Respondent {}", e.toString());
            return null;
        }
    }

    @Override
    public List<Long> findDistinctEventIdByUserId(Long p_UserId) throws ServiceException {
        try {
            return interviewHeaderDAO.findDistinctEventIdByUserId(p_UserId);
        } catch (DAOException e) {
            LOGGER.error("Error Find Distict Event Id By User Id {}", e.toString());
            return null;
        }
    }

    @Override
    public List<InterviewHeader> findByEventId(Long p_EventId) throws ServiceException {
        try {
            return interviewHeaderDAO.findByInterviewEvent_id(p_EventId);
        } catch (DAOException e) {
            LOGGER.error("Error Find By Event Id {}", e.toString());
            return null;
        }
    }

    @Override
    public InterviewHeader findByEventIdAndUserIdAndVillageId(Long p_EventId, Long p_UserId, Long p_VillageId) throws ServiceException {
        try {
            return interviewHeaderDAO.findByInterviewEvent_idAndUserIdAndVillageId(p_EventId, p_UserId, p_VillageId);
        } catch (DAOException e) {
            LOGGER.error("Error Find By Event Id, UserId and Village Id {}", e.toString());
            return null;
        }
    }

    @Override
    public List<LOVBaseResponseDTO> findProvinceLOVByBranch(String p_Bearer, Long p_BranchId) throws ServiceException {
        List<LOVBaseResponseDTO> result = new ArrayList<>();
        List<Long> provinceIds = new ArrayList<>();
        try {
            provinceIds = interviewHeaderDAO.findDistinctProvinceByBranch(p_BranchId);
        } catch (DAOException e) {
            LOGGER.error("Error Search Province IDs {}", e.toString());
        }
        if (provinceIds.size() > 0) {
            for (Long id : provinceIds) {
                RequestFindByIdDTO requestFindByIdDTO = new RequestFindByIdDTO();
                requestFindByIdDTO.setId(id);
                GenericSingleDATAResponseDTO<ResponseProvinceDTO> province = provinceClient.findProvinceById(p_Bearer, requestFindByIdDTO);
                LOVBaseResponseDTO lovBaseResponseDTO = new LOVBaseResponseDTO();
                lovBaseResponseDTO.setId(province.getContent().getId());
                lovBaseResponseDTO.setName(province.getContent().getName());
                result.add(lovBaseResponseDTO);
            }
        }else {
            LOGGER.info("Province Ids Not Found");
        }
        return result;
    }
}
