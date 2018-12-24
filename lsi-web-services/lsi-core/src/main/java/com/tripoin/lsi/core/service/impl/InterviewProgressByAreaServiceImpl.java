package com.tripoin.lsi.core.service.impl;

import com.tripoin.lsi.core.LsiCoreConstant;
import com.tripoin.lsi.core.client.impl.*;
import com.tripoin.lsi.core.dao.IInterviewHeaderDAO;
import com.tripoin.lsi.core.data.dto.request.RequestInterviewProgressByAreaDTO;
import com.tripoin.lsi.core.data.dto.response.ContentAreaProgress2DTO;
import com.tripoin.lsi.core.service.IInterviewProgressByAreaService;
import com.tripoin.lsi.core.util.PrincipalFinder;
import com.wissensalt.scaffolding.constant.ScaffoldingSecurityConstant;
import com.wissensalt.scaffolding.exception.DAOException;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.shared.dto.request.RequestFindByIdDTO;
import com.wissensalt.shared.dto.response.base.GenericSingleDATAResponseDTO;
import com.wissensalt.shared.dto.response.master.ResponseCityDTO;
import com.wissensalt.shared.dto.response.master.ResponseDistrictDTO;
import com.wissensalt.shared.dto.response.master.ResponseProvinceDTO;
import com.wissensalt.shared.dto.response.master.ResponseVillageDTO;
import com.wissensalt.shared.dto.response.security.ResponseRoleDTO;
import com.wissensalt.shared.dto.response.security.ResponseUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 12/5/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class InterviewProgressByAreaServiceImpl implements IInterviewProgressByAreaService {

    @Autowired
    private PrincipalFinder principalFinder;

    @Autowired
    private IInterviewHeaderDAO interviewHeaderDAO;

    @Autowired
    private ProvinceClientImpl provinceClient;

    @Autowired
    private CityClientImpl cityClient;

    @Autowired
    private DistrictClientImpl districtClient;

    @Autowired
    private VillageClientImpl villageClient;

    @Autowired
    private UserClientImpl userClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(InterviewProgressByAreaServiceImpl.class);

    @Override
    public Page<ContentAreaProgress2DTO> findByArea(String p_Bearer, String p_Principal, @RequestBody RequestInterviewProgressByAreaDTO p_RequestInterviewProgressByAreaDTO, PageRequest p_PageRequest) throws ServiceException {
        ResponseRoleDTO role = principalFinder.findRoleByUserName(p_Bearer, p_Principal);
        if (role != null) {
            if ((!role.getCode().equals(ScaffoldingSecurityConstant.RoleCode.ACTUATOR)) && (!role.getCode().equals(ScaffoldingSecurityConstant.RoleCode.VOLUNTEER) && (!role.getCode().equals(ScaffoldingSecurityConstant.RoleCode.DIREKSI)) && (!role.getCode().equals(ScaffoldingSecurityConstant.RoleCode.ADMIN)))) {
                LOGGER.info("Found Tenant Role");
                ResponseUserDTO user = principalFinder.findUserByUserName(p_Bearer, p_Principal);
                if (user != null) {
                    if (user.getBranch() != null) {
                        return filter(
                                p_Bearer,
                                p_RequestInterviewProgressByAreaDTO.getInterviewEventId(),
                                p_RequestInterviewProgressByAreaDTO.getProvinceId(),
                                p_RequestInterviewProgressByAreaDTO.getCityId(),
                                p_RequestInterviewProgressByAreaDTO.getDistrictId(),
                                p_RequestInterviewProgressByAreaDTO.getVillageId(),
                                user.getBranch().getId(),
                                p_PageRequest
                        );
                    } else {
                        LOGGER.error("Branch is Not Found");
                    }
                } else {
                    LOGGER.error("User is Not Found");
                }
            } else if (role.getCode().equals(ScaffoldingSecurityConstant.RoleCode.ADMIN) || role.getCode().equals(ScaffoldingSecurityConstant.RoleCode.DIREKSI)) {
                LOGGER.info("Found Admin || Direksi Role");
                return filter(
                        p_Bearer,
                        p_RequestInterviewProgressByAreaDTO.getInterviewEventId(),
                        p_RequestInterviewProgressByAreaDTO.getProvinceId(),
                        p_RequestInterviewProgressByAreaDTO.getCityId(),
                        p_RequestInterviewProgressByAreaDTO.getDistrictId(),
                        p_RequestInterviewProgressByAreaDTO.getVillageId(),
                        null,
                        p_PageRequest
                );
            } else {
                LOGGER.error("Role is UnAuthorized to Access This Service");
                throw new UnauthorizedUserException("Role is UnAuthorized to Access This Service");
            }
            return null;
        }
        return null;
    }

    Page<ContentAreaProgress2DTO> filter(String p_Bearer, Long p_InterviewEventId, Long p_ProvinceId, Long p_CityId, Long p_DistrictId, Long p_VillageId, Long p_BranchId, PageRequest p_PageRequest) {
        List<ContentAreaProgress2DTO> result = new ArrayList<>();
        List<BigInteger> provinceIds = new ArrayList<>();
        List<BigInteger> cityIds = new ArrayList<>();
        List<BigInteger> districtIds = new ArrayList<>();
        List<BigInteger> villageIds = new ArrayList<>();
        List<BigInteger> userIds = new ArrayList<>();
        List<BigInteger> contentSize = new ArrayList<>();
        Integer limitA = (p_PageRequest.getPageNumber() * p_PageRequest.getPageSize());
        if (p_BranchId == null) {
            LOGGER.info("Filter Without Branch");
            if (p_ProvinceId == null) {
                LOGGER.info("Filter All - By Event Only");
                try {
                    provinceIds = interviewHeaderDAO.findProvinceByEvent(p_InterviewEventId, limitA, p_PageRequest.getPageSize());
                    contentSize = interviewHeaderDAO.findProvinceByEvent(p_InterviewEventId);
                } catch (DAOException e) {
                    LOGGER.error("Error Find Province By Event {}", e.toString());
                }
                if (provinceIds.size() > 0) {
                    RequestFindByIdDTO requestFindById = new RequestFindByIdDTO();
                    for (BigInteger pId : provinceIds) {
                        ContentAreaProgress2DTO content = new ContentAreaProgress2DTO();
                        requestFindById.setId(pId.longValue());
                        GenericSingleDATAResponseDTO<ResponseProvinceDTO> province = provinceClient.findProvinceById(p_Bearer, requestFindById);
                        content.setProvince(province.getContent().getName());
                        try {
                            Integer nCity = interviewHeaderDAO.countCityByProvinceId(p_InterviewEventId, pId.longValue());
                            content.setCity(String.valueOf(nCity));
                        } catch (DAOException e) {
                            LOGGER.error("Error Count City By Province Id {}", e.toString());
                        }
                        try {
                            Integer nDistrict = interviewHeaderDAO.countDistrictByProvinceId(p_InterviewEventId, pId.longValue());
                            content.setDistrict(String.valueOf(nDistrict));
                        } catch (DAOException e) {
                            LOGGER.error("Error Count District By Province Id {}", e.toString());
                        }
                        try {
                            Integer nVillage = interviewHeaderDAO.countVillageByProvinceId(p_InterviewEventId, pId.longValue());
                            content.setVillage(String.valueOf(nVillage));
                        } catch (DAOException e) {
                            LOGGER.error("Error Count Village By Province Id {}", e.toString());
                        }
                        try {
                            Integer nVolunteer = interviewHeaderDAO.countUserIdByProvinceId(p_InterviewEventId, pId.longValue());
                            content.setVolunteer(String.valueOf(nVolunteer));
                        } catch (DAOException e) {
                            LOGGER.error("Error Count User By Province id {}", e.toString());
                        }
                        try {
                            Integer nDataComplete = interviewHeaderDAO.countDataCompleteByProvinceId(p_InterviewEventId, pId.longValue(), LsiCoreConstant.InterviewStatus.COMPLETED);
                            content.setCompletedInterview(nDataComplete);
                        } catch (DAOException e) {
                            LOGGER.error("Error Count Data Complete By Province Id {}", e.toString());
                        }
                        try {
                            Integer nDataValid = interviewHeaderDAO.countDataValidByProvinceId(p_InterviewEventId, pId.longValue(), 25);
                            content.setValidInterview(nDataValid);
                        } catch (DAOException e) {
                            LOGGER.error("Error Count Data Valid By Province Id {}", e.toString());
                        }
                        try {
                            Integer nRespondent = interviewHeaderDAO.sumRespondentByProvinceId(p_InterviewEventId, pId.longValue());
                            content.setRespondent(nRespondent);
                        } catch (DAOException e) {
                            LOGGER.error("Error Sum Respondent By Province Id {}", e.toString());
                        }
                        result.add(content);
                    }
                }
            }else if (p_ProvinceId != null && p_CityId == null) {
                LOGGER.info("Filter By Province Id {}", p_ProvinceId);
                try {
                    cityIds = interviewHeaderDAO.findCityByEvent(p_InterviewEventId, p_ProvinceId, limitA, p_PageRequest.getPageSize());
                    contentSize = interviewHeaderDAO.findCityByEvent(p_InterviewEventId, p_ProvinceId);
                } catch (DAOException e) {
                    LOGGER.error("Error Find Citys By PRovince {}", p_ProvinceId);
                }
                RequestFindByIdDTO requestFindById = new RequestFindByIdDTO();
                requestFindById.setId(p_ProvinceId);
                GenericSingleDATAResponseDTO<ResponseProvinceDTO> provinceData = provinceClient.findProvinceById(p_Bearer, requestFindById);
                String provinceName = "";
                if (provinceData != null) {
                    if (provinceData.getContent() != null) {
                        provinceName = provinceData.getContent().getName();
                    }else {
                        LOGGER.info("Province Not Found 2");
                    }
                }else{
                    LOGGER.info("Province Not Found");
                }
                for (BigInteger cityId : cityIds) {
                    ContentAreaProgress2DTO content = new ContentAreaProgress2DTO();
                    content.setProvince(provinceName);
                    requestFindById.setId(cityId.longValue());
                    GenericSingleDATAResponseDTO<ResponseCityDTO> cityData = cityClient.findCityById(p_Bearer, requestFindById);
                    if (cityData != null) {
                        if (cityData.getContent() != null) {
                            content.setCity(cityData.getContent().getName());
                        }
                    }
                    try {
                        Integer nDistrict = interviewHeaderDAO.countDistrictByCityId(p_InterviewEventId, cityId.longValue());
                        content.setDistrict(String.valueOf(nDistrict));
                    } catch (DAOException e) {
                        LOGGER.error("Error Count District By City Id {}", e.toString());
                    }
                    try {
                        Integer nVillage = interviewHeaderDAO.countVillageByCityId(p_InterviewEventId, cityId.longValue());
                        content.setVillage(String.valueOf(nVillage));
                    } catch (DAOException e) {
                        LOGGER.error("Error Count Village By City Id {}", e.toString());
                    }
                    try {
                        Integer nVolunteer = interviewHeaderDAO.countUserIdByCityId(p_InterviewEventId, cityId.longValue());
                        content.setVolunteer(String.valueOf(nVolunteer));
                    } catch (DAOException e) {
                        LOGGER.error("Error Count Volunteer By City Id {}", e.toString());
                    }
                    try {
                        Integer nDataComplete = interviewHeaderDAO.countDataCompleteByCityId(p_InterviewEventId, cityId.longValue(), LsiCoreConstant.InterviewStatus.COMPLETED);
                        content.setCompletedInterview(nDataComplete);
                    } catch (DAOException e) {
                        LOGGER.error("Error Count Data Complete By City Id {}", e.toString());
                    }
                    try {
                        Integer nDataValid = interviewHeaderDAO.countDataValidByCityId(p_InterviewEventId, cityId.longValue(), 25);
                        content.setValidInterview(nDataValid);
                    } catch (DAOException e) {
                        LOGGER.error("Error Count Data Complete By City Id {}", e.toString());
                    }
                    try {
                        Integer nRespondent = interviewHeaderDAO.sumRespondentByCityId(p_InterviewEventId, cityId.longValue());
                        content.setRespondent(nRespondent);
                    } catch (DAOException e) {
                        LOGGER.error("Error Sum Respondent By City Id {}", e.toString());
                    }
                    result.add(content);
                }
            }else if (p_ProvinceId != null && p_CityId != null && p_DistrictId == null) {
                LOGGER.info("Filter By City Id {}", p_CityId);
                try {
                    districtIds = interviewHeaderDAO.findDistrictByEvent(p_InterviewEventId, p_CityId, limitA, p_PageRequest.getPageSize());
                    contentSize = interviewHeaderDAO.findDistrictByEvent(p_InterviewEventId, p_CityId);
                } catch (DAOException e) {
                    LOGGER.error("Error Find Districts By City {}", p_CityId);
                }
                RequestFindByIdDTO requestFindById = new RequestFindByIdDTO();
                requestFindById.setId(p_ProvinceId);
                GenericSingleDATAResponseDTO<ResponseProvinceDTO> provinceData = provinceClient.findProvinceById(p_Bearer, requestFindById);
                String provinceName = "";
                if (provinceData != null) {
                    if (provinceData.getContent() != null) {
                        provinceName = provinceData.getContent().getName();
                    }else {
                        LOGGER.info("Province Not Found 2");
                    }
                }else{
                    LOGGER.info("Province Not Found");
                }

                requestFindById.setId(p_CityId);
                GenericSingleDATAResponseDTO<ResponseCityDTO> cityData = cityClient.findCityById(p_Bearer, requestFindById);
                String cityName = "";
                if (cityData != null) {
                    if (cityData.getContent() != null) {
                        cityName = cityData.getContent().getName();
                    }else {
                        LOGGER.info("City Not Found 2");
                    }
                }else{
                    LOGGER.info("City Not Found");
                }

                for (BigInteger districtId : districtIds) {
                    ContentAreaProgress2DTO content = new ContentAreaProgress2DTO();
                    content.setProvince(provinceName);
                    content.setCity(cityName);
                    requestFindById.setId(districtId.longValue());
                    GenericSingleDATAResponseDTO<ResponseDistrictDTO> districtData = districtClient.findDistrictById(p_Bearer, requestFindById);
                    if (districtData != null) {
                        if (districtData.getContent() != null) {
                            content.setDistrict(districtData.getContent().getName());
                        }
                    }
                    try {
                        Integer nVillage = interviewHeaderDAO.countVillageByDistrictId(p_InterviewEventId, districtId.longValue());
                        content.setVillage(String.valueOf(nVillage));
                    } catch (DAOException e) {
                        LOGGER.error("Error Count Village By District Id {}", e.toString());
                    }
                    try {
                        Integer nVolunteer = interviewHeaderDAO.countUserIdByDistrictId(p_InterviewEventId, districtId.longValue());
                        content.setVolunteer(String.valueOf(nVolunteer));
                    } catch (DAOException e) {
                        LOGGER.error("Error Count Volunteer By District Id {}", e.toString());
                    }
                    try {
                        Integer nDataComplete = interviewHeaderDAO.countDataCompleteByDistrictId(p_InterviewEventId, districtId.longValue(), LsiCoreConstant.InterviewStatus.COMPLETED);
                        content.setCompletedInterview(nDataComplete);
                    } catch (DAOException e) {
                        LOGGER.error("Error Count Data Complete By District Id {}", e.toString());
                    }
                    try {
                        Integer nDataValid = interviewHeaderDAO.countDataValidByDistrictId(p_InterviewEventId, districtId.longValue(), 25);
                        content.setValidInterview(nDataValid);
                    } catch (DAOException e) {
                        LOGGER.error("Error Count Data Complete By District Id {}", e.toString());
                    }
                    try {
                        Integer nRespondent = interviewHeaderDAO.sumRespondentByDistrictId(p_InterviewEventId, districtId.longValue());
                        content.setRespondent(nRespondent);
                    } catch (DAOException e) {
                        LOGGER.error("Error Sum Respondent By District Id {}", e.toString());
                    }
                    result.add(content);
                }
            }else if (p_ProvinceId != null && p_CityId != null && p_DistrictId != null && p_VillageId == null) {
                LOGGER.info("Filter By District Id {}", p_DistrictId);
                try {
                    villageIds = interviewHeaderDAO.findVillageByEvent(p_InterviewEventId, p_DistrictId, limitA, p_PageRequest.getPageSize());
                    contentSize = interviewHeaderDAO.findVillageByEvent(p_InterviewEventId, p_DistrictId);
                } catch (DAOException e) {
                    LOGGER.error("Error Find Village By District {}", p_CityId);
                }
                RequestFindByIdDTO requestFindById = new RequestFindByIdDTO();
                requestFindById.setId(p_ProvinceId);
                GenericSingleDATAResponseDTO<ResponseProvinceDTO> provinceData = provinceClient.findProvinceById(p_Bearer, requestFindById);
                String provinceName = "";
                if (provinceData != null) {
                    if (provinceData.getContent() != null) {
                        provinceName = provinceData.getContent().getName();
                    }else {
                        LOGGER.info("Province Not Found 2");
                    }
                }else{
                    LOGGER.info("Province Not Found");
                }

                requestFindById.setId(p_CityId);
                GenericSingleDATAResponseDTO<ResponseCityDTO> cityData = cityClient.findCityById(p_Bearer, requestFindById);
                String cityName = "";
                if (cityData != null) {
                    if (cityData.getContent() != null) {
                        cityName = cityData.getContent().getName();
                    }else {
                        LOGGER.info("City Not Found 2");
                    }
                }else{
                    LOGGER.info("City Not Found");
                }

                requestFindById.setId(p_DistrictId);
                GenericSingleDATAResponseDTO<ResponseDistrictDTO> districtData = districtClient.findDistrictById(p_Bearer, requestFindById);
                String districtName = "";
                if (districtData != null) {
                    if (districtData.getContent() != null) {
                        districtName = districtData.getContent().getName();
                    }else {
                        LOGGER.info("District Not Found 2");
                    }
                }else{
                    LOGGER.info("District Not Found");
                }

                for (BigInteger villageId : villageIds) {
                    ContentAreaProgress2DTO content = new ContentAreaProgress2DTO();
                    content.setProvince(provinceName);
                    content.setCity(cityName);
                    content.setDistrict(districtName);
                    requestFindById.setId(villageId.longValue());
                    GenericSingleDATAResponseDTO<ResponseVillageDTO> villageData = villageClient.findVillageById(p_Bearer, requestFindById);
                    if (villageData != null) {
                        if (villageData.getContent() != null) {
                            content.setVillage(villageData.getContent().getName());
                        }
                    }
                    try {
                        Integer nVolunteer = interviewHeaderDAO.countUserIdByVillageId(p_InterviewEventId, villageId.longValue());
                        content.setVolunteer(String.valueOf(nVolunteer));
                    } catch (DAOException e) {
                        LOGGER.error("Error Count Volunteer By Village Id {}", e.toString());
                    }
                    try {
                        Integer nDataComplete = interviewHeaderDAO.countDataCompleteByVillageId(p_InterviewEventId, villageId.longValue(), LsiCoreConstant.InterviewStatus.COMPLETED);
                        content.setCompletedInterview(nDataComplete);
                    } catch (DAOException e) {
                        LOGGER.error("Error Count Data Complete By Village Id {}", e.toString());
                    }
                    try {
                        Integer nDataValid = interviewHeaderDAO.countDataValidByVillageId(p_InterviewEventId, villageId.longValue(), 25);
                        content.setValidInterview(nDataValid);
                    } catch (DAOException e) {
                        LOGGER.error("Error Count Data Complete By Village Id {}", e.toString());
                    }
                    try {
                        Integer nRespondent = interviewHeaderDAO.sumRespondentByVillageId(p_InterviewEventId, villageId.longValue());
                        content.setRespondent(nRespondent);
                    } catch (DAOException e) {
                        LOGGER.error("Error Sum Respondent By Village Id {}", e.toString());
                    }
                    result.add(content);
                }
            } else if (p_ProvinceId != null && p_CityId != null && p_DistrictId != null && p_VillageId != null) {
                LOGGER.info("Filter By Village Id {}", p_VillageId);
                try {
                    userIds = interviewHeaderDAO.findUserByEvent(p_InterviewEventId, p_VillageId, limitA, p_PageRequest.getPageSize());
                    contentSize = interviewHeaderDAO.findUserByEvent(p_InterviewEventId, p_VillageId);
                } catch (DAOException e) {
                    LOGGER.error("Error Find User By Village Id {}", p_VillageId);
                }
                RequestFindByIdDTO requestFindById = new RequestFindByIdDTO();
                requestFindById.setId(p_ProvinceId);
                GenericSingleDATAResponseDTO<ResponseProvinceDTO> provinceData = provinceClient.findProvinceById(p_Bearer, requestFindById);
                String provinceName = "";
                if (provinceData != null) {
                    if (provinceData.getContent() != null) {
                        provinceName = provinceData.getContent().getName();
                    }else {
                        LOGGER.info("Province Not Found 2");
                    }
                }else{
                    LOGGER.info("Province Not Found");
                }
                requestFindById.setId(p_CityId);
                GenericSingleDATAResponseDTO<ResponseCityDTO> cityData = cityClient.findCityById(p_Bearer, requestFindById);
                String cityName = "";
                if (cityData != null) {
                    if (cityData.getContent() != null) {
                        cityName = cityData.getContent().getName();
                    }else {
                        LOGGER.info("City Not Found 2");
                    }
                }else{
                    LOGGER.info("City Not Found");
                }

                requestFindById.setId(p_DistrictId);
                GenericSingleDATAResponseDTO<ResponseDistrictDTO> districtData = districtClient.findDistrictById(p_Bearer, requestFindById);
                String districtName = "";
                if (districtData != null) {
                    if (districtData.getContent() != null) {
                        districtName = districtData.getContent().getName();
                    }else {
                        LOGGER.info("District Not Found 2");
                    }
                }else{
                    LOGGER.info("District Not Found");
                }

                requestFindById.setId(p_VillageId);
                GenericSingleDATAResponseDTO<ResponseVillageDTO> villageData = villageClient.findVillageById(p_Bearer, requestFindById);
                String villageName = "";
                if (villageData != null) {
                    if (villageData.getContent() != null) {
                        villageName = villageData.getContent().getName();
                    }else {
                        LOGGER.info("Village Not Found 2");
                    }
                }else{
                    LOGGER.info("Village Not Found");
                }

                for (BigInteger userId : userIds) {
                    ContentAreaProgress2DTO content = new ContentAreaProgress2DTO();
                    content.setProvince(provinceName);
                    content.setCity(cityName);
                    content.setDistrict(districtName);
                    content.setVillage(villageName);

                    requestFindById.setId(userId.longValue());
                    GenericSingleDATAResponseDTO<ResponseUserDTO> userData = userClient.findByUserId(p_Bearer, requestFindById);
                    if (userData != null) {
                        if (userData.getContent() != null) {
                            content.setVolunteer(userData.getContent().getName());
                        }
                    }
                    try {
                        Integer nDataComplete = interviewHeaderDAO.countDataCompleteByUserId(p_InterviewEventId, userId.longValue(), LsiCoreConstant.InterviewStatus.COMPLETED);
                        content.setCompletedInterview(nDataComplete);
                    } catch (DAOException e) {
                        LOGGER.error("Error Count Data Complete By User Id {}", e.toString());
                    }
                    try {
                        Integer nDataValid = interviewHeaderDAO.countDataValidByUserId(p_InterviewEventId, userId.longValue(), 25);
                        content.setValidInterview(nDataValid);
                    } catch (DAOException e) {
                        LOGGER.error("Error Count Data Complete By User Id {}", e.toString());
                    }
                    try {
                        Integer nRespondent = interviewHeaderDAO.sumRespondentByUserId(p_InterviewEventId, userId.longValue());
                        content.setRespondent(nRespondent);
                    } catch (DAOException e) {
                        LOGGER.error("Error Sum Respondent By City Id {}", e.toString());
                    }
                    result.add(content);
                }
            }
        }else {
            LOGGER.info("Filter With Branch {}", p_BranchId);
            if (p_ProvinceId == null) {
                LOGGER.info("Filter All - By Event Only");
                try {
                    provinceIds = interviewHeaderDAO.findProvinceByEvent(p_InterviewEventId, p_BranchId, limitA, p_PageRequest.getPageSize());
                    contentSize = interviewHeaderDAO.findProvinceByEvent(p_InterviewEventId, p_BranchId);
                } catch (DAOException e) {
                    LOGGER.error("Error Find Province By Event {}", e.toString());
                }
                if (provinceIds.size() > 0) {
                    RequestFindByIdDTO requestFindById = new RequestFindByIdDTO();
                    for (BigInteger pId : provinceIds) {
                        ContentAreaProgress2DTO content = new ContentAreaProgress2DTO();
                        requestFindById.setId(pId.longValue());
                        GenericSingleDATAResponseDTO<ResponseProvinceDTO> province = provinceClient.findProvinceById(p_Bearer, requestFindById);
                        content.setProvince(province.getContent().getName());
                        try {
                            Integer nCity = interviewHeaderDAO.countCityByProvinceId(p_InterviewEventId, pId.longValue(), p_BranchId);
                            content.setCity(String.valueOf(nCity));
                        } catch (DAOException e) {
                            LOGGER.error("Error Count City By Province Id {}", e.toString());
                        }
                        try {
                            Integer nDistrict = interviewHeaderDAO.countDistrictByProvinceId(p_InterviewEventId, pId.longValue(), p_BranchId);
                            content.setDistrict(String.valueOf(nDistrict));
                        } catch (DAOException e) {
                            LOGGER.error("Error Count District By Province Id {}", e.toString());
                        }
                        try {
                            Integer nVillage = interviewHeaderDAO.countVillageByProvinceId(p_InterviewEventId, pId.longValue(), p_BranchId);
                            content.setVillage(String.valueOf(nVillage));
                        } catch (DAOException e) {
                            LOGGER.error("Error Count Village By Province Id {}", e.toString());
                        }
                        try {
                            Integer nVolunteer = interviewHeaderDAO.countUserIdByProvinceId(p_InterviewEventId, pId.longValue(), p_BranchId);
                            content.setVolunteer(String.valueOf(nVolunteer));
                        } catch (DAOException e) {
                            LOGGER.error("Error Count User By Province id {}", e.toString());
                        }
                        try {
                            Integer nDataComplete = interviewHeaderDAO.countDataCompleteByProvinceId(p_InterviewEventId, pId.longValue(), LsiCoreConstant.InterviewStatus.COMPLETED, p_BranchId);
                            content.setCompletedInterview(nDataComplete);
                        } catch (DAOException e) {
                            LOGGER.error("Error Count Data Complete By Province Id {}", e.toString());
                        }
                        try {
                            Integer nDataValid = interviewHeaderDAO.countDataValidByProvinceId(p_InterviewEventId, pId.longValue(), 25, p_BranchId);
                            content.setValidInterview(nDataValid);
                        } catch (DAOException e) {
                            LOGGER.error("Error Count Data Valid By Province Id {}", e.toString());
                        }
                        try {
                            Integer nRespondent = interviewHeaderDAO.sumRespondentByProvinceId(p_InterviewEventId, pId.longValue(), p_BranchId);
                            content.setRespondent(nRespondent);
                        } catch (DAOException e) {
                            LOGGER.error("Error Sum Respondent By Province Id {}", e.toString());
                        }
                        result.add(content);
                    }
                }
            }else if (p_ProvinceId != null && p_CityId == null) {
                LOGGER.info("Filter By Province Id {}", p_ProvinceId);
                try {
                    cityIds = interviewHeaderDAO.findCityByEvent(p_InterviewEventId, p_ProvinceId, p_BranchId, limitA, p_PageRequest.getPageSize());
                    contentSize = interviewHeaderDAO.findCityByEvent(p_InterviewEventId, p_ProvinceId, p_BranchId);
                } catch (DAOException e) {
                    LOGGER.error("Error Find Citys By PRovince {}", p_ProvinceId);
                }
                RequestFindByIdDTO requestFindById = new RequestFindByIdDTO();
                requestFindById.setId(p_ProvinceId);
                GenericSingleDATAResponseDTO<ResponseProvinceDTO> provinceData = provinceClient.findProvinceById(p_Bearer, requestFindById);
                String provinceName = "";
                if (provinceData != null) {
                    if (provinceData.getContent() != null) {
                        provinceName = provinceData.getContent().getName();
                    }else {
                        LOGGER.info("Province Not Found 2");
                    }
                }else{
                    LOGGER.info("Province Not Found");
                }
                for (BigInteger cityId : cityIds) {
                    ContentAreaProgress2DTO content = new ContentAreaProgress2DTO();
                    content.setProvince(provinceName);
                    requestFindById.setId(cityId.longValue());
                    GenericSingleDATAResponseDTO<ResponseCityDTO> cityData = cityClient.findCityById(p_Bearer, requestFindById);
                    if (cityData != null) {
                        if (cityData.getContent() != null) {
                            content.setCity(cityData.getContent().getName());
                        }
                    }
                    try {
                        Integer nDistrict = interviewHeaderDAO.countDistrictByCityId(p_InterviewEventId, cityId.longValue(), p_BranchId);
                        content.setDistrict(String.valueOf(nDistrict));
                    } catch (DAOException e) {
                        LOGGER.error("Error Count District By City Id {}", e.toString());
                    }
                    try {
                        Integer nVillage = interviewHeaderDAO.countVillageByCityId(p_InterviewEventId, cityId.longValue(), p_BranchId);
                        content.setVillage(String.valueOf(nVillage));
                    } catch (DAOException e) {
                        LOGGER.error("Error Count Village By City Id {}", e.toString());
                    }
                    try {
                        Integer nVolunteer = interviewHeaderDAO.countUserIdByCityId(p_InterviewEventId, cityId.longValue(), p_BranchId);
                        content.setVolunteer(String.valueOf(nVolunteer));
                    } catch (DAOException e) {
                        LOGGER.error("Error Count Volunteer By City Id {}", e.toString());
                    }
                    try {
                        Integer nDataComplete = interviewHeaderDAO.countDataCompleteByCityId(p_InterviewEventId, cityId.longValue(), LsiCoreConstant.InterviewStatus.COMPLETED, p_BranchId);
                        content.setCompletedInterview(nDataComplete);
                    } catch (DAOException e) {
                        LOGGER.error("Error Count Data Complete By City Id {}", e.toString());
                    }
                    try {
                        Integer nDataValid = interviewHeaderDAO.countDataValidByCityId(p_InterviewEventId, cityId.longValue(), 25, p_BranchId);
                        content.setValidInterview(nDataValid);
                    } catch (DAOException e) {
                        LOGGER.error("Error Count Data Complete By City Id {}", e.toString());
                    }
                    try {
                        Integer nRespondent = interviewHeaderDAO.sumRespondentByCityId(p_InterviewEventId, cityId.longValue(), p_BranchId);
                        content.setRespondent(nRespondent);
                    } catch (DAOException e) {
                        LOGGER.error("Error Sum Respondent By City Id {}", e.toString());
                    }
                    result.add(content);
                }
            }else if (p_ProvinceId != null && p_CityId != null && p_DistrictId == null) {
                LOGGER.info("Filter By City Id {}", p_CityId);
                try {
                    districtIds = interviewHeaderDAO.findDistrictByEvent(p_InterviewEventId, p_CityId, p_BranchId, limitA, p_PageRequest.getPageSize());
                    contentSize = interviewHeaderDAO.findDistrictByEvent(p_InterviewEventId, p_CityId, p_BranchId);
                } catch (DAOException e) {
                    LOGGER.error("Error Find Districts By City {}", p_CityId);
                }
                RequestFindByIdDTO requestFindById = new RequestFindByIdDTO();
                requestFindById.setId(p_ProvinceId);
                GenericSingleDATAResponseDTO<ResponseProvinceDTO> provinceData = provinceClient.findProvinceById(p_Bearer, requestFindById);
                String provinceName = "";
                if (provinceData != null) {
                    if (provinceData.getContent() != null) {
                        provinceName = provinceData.getContent().getName();
                    }else {
                        LOGGER.info("Province Not Found 2");
                    }
                }else{
                    LOGGER.info("Province Not Found");
                }

                requestFindById.setId(p_CityId);
                GenericSingleDATAResponseDTO<ResponseCityDTO> cityData = cityClient.findCityById(p_Bearer, requestFindById);
                String cityName = "";
                if (cityData != null) {
                    if (cityData.getContent() != null) {
                        cityName = cityData.getContent().getName();
                    }else {
                        LOGGER.info("City Not Found 2");
                    }
                }else{
                    LOGGER.info("City Not Found");
                }

                for (BigInteger districtId : districtIds) {
                    ContentAreaProgress2DTO content = new ContentAreaProgress2DTO();
                    content.setProvince(provinceName);
                    content.setCity(cityName);
                    requestFindById.setId(districtId.longValue());
                    GenericSingleDATAResponseDTO<ResponseDistrictDTO> districtData = districtClient.findDistrictById(p_Bearer, requestFindById);
                    if (districtData != null) {
                        if (districtData.getContent() != null) {
                            content.setDistrict(districtData.getContent().getName());
                        }
                    }
                    try {
                        Integer nVillage = interviewHeaderDAO.countVillageByDistrictId(p_InterviewEventId, districtId.longValue(), p_BranchId);
                        content.setVillage(String.valueOf(nVillage));
                    } catch (DAOException e) {
                        LOGGER.error("Error Count Village By District Id {}", e.toString());
                    }
                    try {
                        Integer nVolunteer = interviewHeaderDAO.countUserIdByDistrictId(p_InterviewEventId, districtId.longValue(), p_BranchId);
                        content.setVolunteer(String.valueOf(nVolunteer));
                    } catch (DAOException e) {
                        LOGGER.error("Error Count Volunteer By District Id {}", e.toString());
                    }
                    try {
                        Integer nDataComplete = interviewHeaderDAO.countDataCompleteByDistrictId(p_InterviewEventId, districtId.longValue(), LsiCoreConstant.InterviewStatus.COMPLETED, p_BranchId);
                        content.setCompletedInterview(nDataComplete);
                    } catch (DAOException e) {
                        LOGGER.error("Error Count Data Complete By District Id {}", e.toString());
                    }
                    try {
                        Integer nDataValid = interviewHeaderDAO.countDataValidByDistrictId(p_InterviewEventId, districtId.longValue(), 25, p_BranchId);
                        content.setValidInterview(nDataValid);
                    } catch (DAOException e) {
                        LOGGER.error("Error Count Data Complete By District Id {}", e.toString());
                    }
                    try {
                        Integer nRespondent = interviewHeaderDAO.sumRespondentByDistrictId(p_InterviewEventId, districtId.longValue(), p_BranchId);
                        content.setRespondent(nRespondent);
                    } catch (DAOException e) {
                        LOGGER.error("Error Sum Respondent By District Id {}", e.toString());
                    }
                    result.add(content);
                }
            }else if (p_ProvinceId != null && p_CityId != null && p_DistrictId != null && p_VillageId == null) {
                LOGGER.info("Filter By District Id {}", p_DistrictId);
                try {
                    villageIds = interviewHeaderDAO.findVillageByEvent(p_InterviewEventId, p_DistrictId, p_BranchId, limitA, p_PageRequest.getPageSize());
                    contentSize = interviewHeaderDAO.findVillageByEvent(p_InterviewEventId, p_DistrictId, p_BranchId);
                } catch (DAOException e) {
                    LOGGER.error("Error Find Village By District {}", p_CityId);
                }
                RequestFindByIdDTO requestFindById = new RequestFindByIdDTO();
                requestFindById.setId(p_ProvinceId);
                GenericSingleDATAResponseDTO<ResponseProvinceDTO> provinceData = provinceClient.findProvinceById(p_Bearer, requestFindById);
                String provinceName = "";
                if (provinceData != null) {
                    if (provinceData.getContent() != null) {
                        provinceName = provinceData.getContent().getName();
                    }else {
                        LOGGER.info("Province Not Found 2");
                    }
                }else{
                    LOGGER.info("Province Not Found");
                }

                requestFindById.setId(p_CityId);
                GenericSingleDATAResponseDTO<ResponseCityDTO> cityData = cityClient.findCityById(p_Bearer, requestFindById);
                String cityName = "";
                if (cityData != null) {
                    if (cityData.getContent() != null) {
                        cityName = cityData.getContent().getName();
                    }else {
                        LOGGER.info("City Not Found 2");
                    }
                }else{
                    LOGGER.info("City Not Found");
                }

                requestFindById.setId(p_DistrictId);
                GenericSingleDATAResponseDTO<ResponseDistrictDTO> districtData = districtClient.findDistrictById(p_Bearer, requestFindById);
                String districtName = "";
                if (districtData != null) {
                    if (districtData.getContent() != null) {
                        districtName = districtData.getContent().getName();
                    }else {
                        LOGGER.info("District Not Found 2");
                    }
                }else{
                    LOGGER.info("District Not Found");
                }

                for (BigInteger villageId : villageIds) {
                    ContentAreaProgress2DTO content = new ContentAreaProgress2DTO();
                    content.setProvince(provinceName);
                    content.setCity(cityName);
                    content.setDistrict(districtName);
                    requestFindById.setId(villageId.longValue());
                    GenericSingleDATAResponseDTO<ResponseVillageDTO> villageData = villageClient.findVillageById(p_Bearer, requestFindById);
                    if (villageData != null) {
                        if (villageData.getContent() != null) {
                            content.setVillage(villageData.getContent().getName());
                        }
                    }
                    try {
                        Integer nVolunteer = interviewHeaderDAO.countUserIdByVillageId(p_InterviewEventId, villageId.longValue(), p_BranchId);
                        content.setVolunteer(String.valueOf(nVolunteer));
                    } catch (DAOException e) {
                        LOGGER.error("Error Count Volunteer By Village Id {}", e.toString());
                    }
                    try {
                        Integer nDataComplete = interviewHeaderDAO.countDataCompleteByVillageId(p_InterviewEventId, villageId.longValue(), LsiCoreConstant.InterviewStatus.COMPLETED, p_BranchId);
                        content.setCompletedInterview(nDataComplete);
                    } catch (DAOException e) {
                        LOGGER.error("Error Count Data Complete By Village Id {}", e.toString());
                    }
                    try {
                        Integer nDataValid = interviewHeaderDAO.countDataValidByVillageId(p_InterviewEventId, villageId.longValue(), 25, p_BranchId);
                        content.setValidInterview(nDataValid);
                    } catch (DAOException e) {
                        LOGGER.error("Error Count Data Complete By Village Id {}", e.toString());
                    }
                    try {
                        Integer nRespondent = interviewHeaderDAO.sumRespondentByVillageId(p_InterviewEventId, villageId.longValue(), p_BranchId);
                        content.setRespondent(nRespondent);
                    } catch (DAOException e) {
                        LOGGER.error("Error Sum Respondent By Village Id {}", e.toString());
                    }
                    result.add(content);
                }
            } else if (p_ProvinceId != null && p_CityId != null && p_DistrictId != null && p_VillageId != null) {
                LOGGER.info("Filter By Village Id {}", p_VillageId);
                try {
                    userIds = interviewHeaderDAO.findUserByEvent(p_InterviewEventId, p_VillageId, p_BranchId, limitA, p_PageRequest.getPageSize());
                    contentSize = interviewHeaderDAO.findUserByEvent(p_InterviewEventId, p_VillageId, p_BranchId);
                } catch (DAOException e) {
                    LOGGER.error("Error Find User By Village Id {}", p_VillageId);
                }
                RequestFindByIdDTO requestFindById = new RequestFindByIdDTO();
                requestFindById.setId(p_ProvinceId);
                GenericSingleDATAResponseDTO<ResponseProvinceDTO> provinceData = provinceClient.findProvinceById(p_Bearer, requestFindById);
                String provinceName = "";
                if (provinceData != null) {
                    if (provinceData.getContent() != null) {
                        provinceName = provinceData.getContent().getName();
                    }else {
                        LOGGER.info("Province Not Found 2");
                    }
                }else{
                    LOGGER.info("Province Not Found");
                }
                requestFindById.setId(p_CityId);
                GenericSingleDATAResponseDTO<ResponseCityDTO> cityData = cityClient.findCityById(p_Bearer, requestFindById);
                String cityName = "";
                if (cityData != null) {
                    if (cityData.getContent() != null) {
                        cityName = cityData.getContent().getName();
                    }else {
                        LOGGER.info("City Not Found 2");
                    }
                }else{
                    LOGGER.info("City Not Found");
                }

                requestFindById.setId(p_DistrictId);
                GenericSingleDATAResponseDTO<ResponseDistrictDTO> districtData = districtClient.findDistrictById(p_Bearer, requestFindById);
                String districtName = "";
                if (districtData != null) {
                    if (districtData.getContent() != null) {
                        districtName = districtData.getContent().getName();
                    }else {
                        LOGGER.info("District Not Found 2");
                    }
                }else{
                    LOGGER.info("District Not Found");
                }

                requestFindById.setId(p_VillageId);
                GenericSingleDATAResponseDTO<ResponseVillageDTO> villageData = villageClient.findVillageById(p_Bearer, requestFindById);
                String villageName = "";
                if (villageData != null) {
                    if (villageData.getContent() != null) {
                        villageName = villageData.getContent().getName();
                    }else {
                        LOGGER.info("Village Not Found 2");
                    }
                }else{
                    LOGGER.info("Village Not Found");
                }

                for (BigInteger userId : userIds) {
                    ContentAreaProgress2DTO content = new ContentAreaProgress2DTO();
                    content.setProvince(provinceName);
                    content.setCity(cityName);
                    content.setDistrict(districtName);
                    content.setVillage(villageName);

                    requestFindById.setId(userId.longValue());
                    GenericSingleDATAResponseDTO<ResponseUserDTO> userData = userClient.findByUserId(p_Bearer, requestFindById);
                    if (userData != null) {
                        if (userData.getContent() != null) {
                            content.setVolunteer(userData.getContent().getName());
                        }
                    }
                    try {
                        Integer nDataComplete = interviewHeaderDAO.countDataCompleteByUserId(p_InterviewEventId, userId.longValue(), LsiCoreConstant.InterviewStatus.COMPLETED, p_BranchId);
                        content.setCompletedInterview(nDataComplete);
                    } catch (DAOException e) {
                        LOGGER.error("Error Count Data Complete By User Id {}", e.toString());
                    }
                    try {
                        Integer nDataValid = interviewHeaderDAO.countDataValidByUserId(p_InterviewEventId, userId.longValue(), 25, p_BranchId);
                        content.setValidInterview(nDataValid);
                    } catch (DAOException e) {
                        LOGGER.error("Error Count Data Complete By User Id {}", e.toString());
                    }
                    try {
                        Integer nRespondent = interviewHeaderDAO.sumRespondentByUserId(p_InterviewEventId, userId.longValue(), p_BranchId);
                        content.setRespondent(nRespondent);
                    } catch (DAOException e) {
                        LOGGER.error("Error Sum Respondent By User Id {}", e.toString());
                    }
                    result.add(content);
                }
            }
        }
        return new PageImpl<>(result, p_PageRequest, contentSize.size());
    }
}