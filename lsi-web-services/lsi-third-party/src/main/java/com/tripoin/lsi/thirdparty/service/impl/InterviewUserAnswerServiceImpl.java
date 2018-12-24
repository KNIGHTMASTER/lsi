package com.tripoin.lsi.thirdparty.service.impl;

import com.tripoin.lsi.thirdparty.LsiThirdPartyConstant.QuestionType;
import com.tripoin.lsi.thirdparty.client.impl.VillageClientImpl;
import com.tripoin.lsi.thirdparty.dao.IInterviewUserAnswerDAO;
import com.tripoin.lsi.thirdparty.data.dto.request.RequestFilterInterviewHeader;
import com.tripoin.lsi.thirdparty.data.dto.request.RequestInterviewUserAnswerDTO;
import com.tripoin.lsi.thirdparty.data.dto.request.RequestSummaryUserAnswerDTO;
import com.tripoin.lsi.thirdparty.data.dto.response.ResponseQuerySummaryUserAnswerDTO;
import com.tripoin.lsi.thirdparty.data.dto.response.ResponseSummaryUserAnswerDTO;
import com.tripoin.lsi.thirdparty.data.model.InterviewHeader;
import com.tripoin.lsi.thirdparty.data.model.InterviewUserAnswer;
import com.tripoin.lsi.thirdparty.data.model.Question;
import com.tripoin.lsi.thirdparty.data.model.QuestionAnswer;
import com.tripoin.lsi.thirdparty.service.IInterviewHeaderService;
import com.tripoin.lsi.thirdparty.service.IInterviewUserAnswerService;
import com.tripoin.lsi.thirdparty.service.IQuestionAnswerService;
import com.tripoin.lsi.thirdparty.service.IQuestionService;
import com.tripoin.lsi.thirdparty.util.PaginationUtil;
import com.tripoin.lsi.thirdparty.util.PrincipalFinder;
import com.wissensalt.scaffolding.constant.ScaffoldingSecurityConstant;
import com.wissensalt.scaffolding.exception.DAOException;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.AScaffoldingService;
import com.wissensalt.scaffolding.service.IScaffoldingResponseBuilder;
import com.wissensalt.shared.dto.request.RequestFindByIdDTO;
import com.wissensalt.shared.dto.response.base.GenericSingleDATAResponseDTO;
import com.wissensalt.shared.dto.response.master.ResponseVillageDTO;
import com.wissensalt.shared.dto.response.security.ResponseRoleDTO;
import com.wissensalt.shared.dto.response.security.ResponseUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 9/16/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class InterviewUserAnswerServiceImpl extends AScaffoldingService<InterviewUserAnswer, RequestInterviewUserAnswerDTO> implements IInterviewUserAnswerService {

    @Autowired
    private IInterviewUserAnswerDAO interviewUserAnswerDAO;

    @Autowired
    private IQuestionAnswerService questionAnswerService;

    @Autowired
    private IInterviewHeaderService interviewHeaderService;

    @Autowired
    private PrincipalFinder principalFinder;

    @Autowired
    private VillageClientImpl villageClient;

    @Autowired
    private IScaffoldingResponseBuilder scaffoldingResponseBuilder;

    @Autowired
    private IQuestionService questionService;

    private static final Logger LOGGER = LoggerFactory.getLogger(InterviewUserAnswerServiceImpl.class);

    @PostConstruct
    @Override
    public void initService() {
        scaffoldingDAO = interviewUserAnswerDAO;
    }

    @Override
    public InterviewUserAnswer generateEntityForCommonTrx(RequestInterviewUserAnswerDTO requestInterviewUserAnswerDTO) throws ServiceException {
        InterviewUserAnswer result = new InterviewUserAnswer();
        result = matchBaseFields(result, requestInterviewUserAnswerDTO);
        result.setInterviewQuestionId(requestInterviewUserAnswerDTO.getInterviewQuestionId());
        result.setQuestionAnswerId(requestInterviewUserAnswerDTO.getQuestionAnswerId());
        result.setOtherAnswer(requestInterviewUserAnswerDTO.getOtherAnswer());
        return result;
    }

    @Override
    public List<InterviewUserAnswer> findByQuestionId(Long p_QuestionId) throws ServiceException {
        try {
            return interviewUserAnswerDAO.findByInterviewQuestionId(p_QuestionId);
        } catch (DAOException e) {
            LOGGER.error("Error Find By Question Id {}", e.toString());
            return null;
        }
    }

    @Override
    public InterviewUserAnswer findByQuestionIdAndInterviewDetailId(Long p_QuestionId, Long p_InterviewDetailId) throws ServiceException {
        try {
            return interviewUserAnswerDAO.findByInterviewQuestionIdAndInterviewDetail_id(p_QuestionId, p_InterviewDetailId);
        } catch (DAOException e) {
            LOGGER.error("Error Find By Question Id And Interview Detail Id {}", e.toString());
            return null;
        }
    }

    @Override
    public List<InterviewUserAnswer> findByDetailId(Long p_DetailId) throws ServiceException {
        try {
            return interviewUserAnswerDAO.findByInterviewDetail_idAndQuestionAnswerIdNotNull(p_DetailId);
        } catch (DAOException e) {
            LOGGER.error("Error Find By Detail Id {}", e.toString());
            return null;
        }
    }

    @Override
    public Map<String, Integer> findMapUserAnswerByInterviewDetailId(Long p_InterviewDetailId) throws ServiceException {
        Map<String, Integer> result = new HashMap<>();
        List<InterviewUserAnswer> userAnswers = null;
        try {
            userAnswers = interviewUserAnswerDAO.findByInterviewDetail_idAndQuestionAnswerIdNotNull(p_InterviewDetailId);
        } catch (DAOException e) {
            LOGGER.error("Error Find By Interview Detail Id {}", e.toString());
        }
        if (userAnswers != null) {
            for (InterviewUserAnswer userAnswer : userAnswers) {
                QuestionAnswer answer = questionAnswerService.findById(userAnswer.getQuestionAnswerId());
                if (answer != null) {
                    if (result.containsKey(answer.getName())) {
                        result.put(answer.getName(), result.get(answer.getName())+1);
                    }else {
                        result.put(answer.getName(), 1);
                    }
                }
            }
        }else {
            LOGGER.error("User Answers Not Found");
        }
        return result;
    }

    @Override
    public Map<String, Map<String, Integer>> findMapsUserAnswerByInterviewDetailId(Long p_InterviewDetailId) throws ServiceException {
        Map<String, Map<String, Integer>> result = new HashMap<>();
        List<InterviewUserAnswer> userAnswers = new ArrayList<>();
        try {
            userAnswers = interviewUserAnswerDAO.findByInterviewDetail_idAndQuestionAnswerIdNotNull(p_InterviewDetailId);
        } catch (DAOException e) {
            LOGGER.error("Error Find By Interview Detail Id {}", e.toString());
        }
        for (InterviewUserAnswer userAnswer : userAnswers) {
            QuestionAnswer answer = questionAnswerService.findById(userAnswer.getQuestionAnswerId());
            Question question = questionService.findById(userAnswer.getInterviewQuestionId());
            if (question != null && question.getQuestionType().equals(QuestionType.MULTIPLE_CHOICE)) {
                if (answer != null) {
                    if (result.containsKey(question.getName())) {
//                        System.out.println("old question "+question.getName());
                            /*UPDATE QUESTION*/
                        Map<String, Integer> mapTemp = result.get(question.getName());
                        if (result.get(question.getName()).containsKey(answer.getName())) {
//                            System.out.println("old answer "+answer.getName()+" "+mapTemp.get(answer.getName()));
                                /*UPDATE ANSWER*/
                            mapTemp.put(answer.getName(), (mapTemp.get(answer.getName()) + 1));
                        }else {
                                /*NEW ANSWER*/
//                            System.out.println("new answer "+answer.getName()+" "+mapTemp.get(answer.getName()));
                            mapTemp.put(answer.getName(), 1);
                        }
                        result.put(question.getName(), mapTemp);
                    }else {
//                        System.out.println("new question "+question.getName());
                            /*NEW QUESTION*/
                        Map<String, Integer> answerMap = new HashMap<>();
                        answerMap.put(answer.getName(), 1);
                        result.put(question.getName(), answerMap);
                        /*for (Map.Entry<String, Map<String, Integer>> map : result.entrySet()) {
                            System.out.println("EXIST KEY "+map.getKey());
                        }*/
                    }
                }else {
                    LOGGER.warn("Answer is Null");
                }
            }else {
                LOGGER.warn("Question is Null");
            }
        }
        return result;
    }

    @Override
    public Page<ResponseSummaryUserAnswerDTO> findSummaryUserAnswer(String p_Bearer, String p_Principal, RequestSummaryUserAnswerDTO p_RequestSummaryUserAnswerDTO) throws ServiceException {
        PageRequest pageRequest = scaffoldingResponseBuilder.buildPageRequest(p_RequestSummaryUserAnswerDTO);
        List<ResponseSummaryUserAnswerDTO> responseSummaries = new ArrayList<>();
        ResponseRoleDTO role = principalFinder.findRoleByUserName(p_Bearer, p_Principal);
        if (role != null) {
            RequestFilterInterviewHeader filterHeader = new RequestFilterInterviewHeader();
            filterHeader.setInterviewEventId(p_RequestSummaryUserAnswerDTO.getInterviewEventId());
            filterHeader.setProvinceId(p_RequestSummaryUserAnswerDTO.getProvinceId());
            filterHeader.setCityId(p_RequestSummaryUserAnswerDTO.getCityId());
            filterHeader.setDistrictId(p_RequestSummaryUserAnswerDTO.getDistrictId());
            filterHeader.setVillageId(p_RequestSummaryUserAnswerDTO.getVillageId());
            List<InterviewHeader> interviewHeaders = new ArrayList<>();

            if ((!role.getCode().equals(ScaffoldingSecurityConstant.RoleCode.ACTUATOR)) && (!role.getCode().equals(ScaffoldingSecurityConstant.RoleCode.VOLUNTEER) && (!role.getCode().equals(ScaffoldingSecurityConstant.RoleCode.DIREKSI)) && (!role.getCode().equals(ScaffoldingSecurityConstant.RoleCode.ADMIN)) && (!role.getCode().equals(ScaffoldingSecurityConstant.RoleCode.THIRD_PARTY)))) {
                LOGGER.info("Found Tenant Role");
                ResponseUserDTO user = principalFinder.findUserByUserName(p_Bearer, p_Principal);
                if (user != null) {
                    if (user.getBranch() != null) {
                        filterHeader.setBranchId(user.getBranch().getId());
                        interviewHeaders = interviewHeaderService.findInterviewHeaderByAreaAndEventAndBranch(filterHeader);
                    } else {
                        LOGGER.error("Branch is Not Found");
                    }
                } else {
                    LOGGER.error("User is Not Found");
                }
            } else if (role.getCode().equals(ScaffoldingSecurityConstant.RoleCode.ADMIN) || role.getCode().equals(ScaffoldingSecurityConstant.RoleCode.THIRD_PARTY) || role.getCode().equals(ScaffoldingSecurityConstant.RoleCode.DIREKSI)) {
                LOGGER.info("Found Admin || Direksi || Third Party Role");
                interviewHeaders = interviewHeaderService.findInterviewHeaderByAreaAndEventAndBranch(filterHeader);
            } else {
                LOGGER.error("Role is UnAuthorized to Access This Service");
            }
            LOGGER.info("Found {} Interview Header ", interviewHeaders.size());
            Long[] headerIds = new Long[interviewHeaders.size()];
            int a=0;
            for (InterviewHeader interviewHeader : interviewHeaders) {
                headerIds[a] = interviewHeader.getId();
                a++;
            }

            List<ResponseQuerySummaryUserAnswerDTO> userAnswers = new ArrayList<>();
            List<Object[]> tempQuerySummary = new ArrayList<>();
            if (headerIds.length > 0) {
                try {
                    tempQuerySummary = interviewUserAnswerDAO.findSummaryUserAnswerByHeaders(headerIds);
                } catch (DAOException e) {
                    LOGGER.error("Error Searching Response Summary By Query {}", e.toString());
                }
            }
            for (Object[] o : tempQuerySummary) {
                ResponseQuerySummaryUserAnswerDTO responseQuerySummaryUserAnswerDTO = new ResponseQuerySummaryUserAnswerDTO();
                responseQuerySummaryUserAnswerDTO.setVillageId(new Long(String.valueOf(o[0])));
                responseQuerySummaryUserAnswerDTO.setQuestion((String) o[1]);
                responseQuerySummaryUserAnswerDTO.setAnswer((String) o[2]);
                userAnswers.add(responseQuerySummaryUserAnswerDTO);
            }

            LOGGER.info("Found {} query summary user answer", userAnswers.size());


            for (ResponseQuerySummaryUserAnswerDTO response : userAnswers) {
                String answer = response.getAnswer().substring(2, response.getAnswer().length());
                if (checkMapContainVillageId(responseSummaries, response.getVillageId())) {
                    ResponseSummaryUserAnswerDTO responseSummaryUserAnswer = findByVillageId(responseSummaries, response.getVillageId());
                    if (responseSummaryUserAnswer.getMapContent().containsKey(response.getQuestion())) {
                        Map<String, Integer> tempMap = responseSummaryUserAnswer.getMapContent().get(response.getQuestion());
                        if (tempMap.containsKey(answer)) {
                            responseSummaryUserAnswer.getMapContent().get(response.getQuestion()).put(answer, (tempMap.get(answer) + 1));
                        }else {
                            responseSummaryUserAnswer.getMapContent().get(response.getQuestion()).put(answer, 1);
                        }
                    }else {
                        Map<String, Integer> mapSubContent = new HashMap<>();
                        mapSubContent.put(answer, 1);
                        responseSummaryUserAnswer.getMapContent().put(response.getQuestion(), mapSubContent);
                    }

                }else {
                    ResponseSummaryUserAnswerDTO summaryUserAnswerDTO = new ResponseSummaryUserAnswerDTO();
                    RequestFindByIdDTO requestFindByIdDTO = new RequestFindByIdDTO();
                    requestFindByIdDTO.setId(response.getVillageId());
                    GenericSingleDATAResponseDTO<ResponseVillageDTO> village = villageClient.findVillageById(p_Bearer, requestFindByIdDTO);
                    if (village != null) {
                        summaryUserAnswerDTO.setVillage(village.getContent());
                    }
                    Map<String, Integer> mapSubContent = new HashMap<>();
                    mapSubContent.put(answer, 1);
                    Map<String, Map<String, Integer>> mapContent = new HashMap<>();
                    mapContent.put(response.getQuestion(), mapSubContent);
                    summaryUserAnswerDTO.setMapContent(mapContent);

                    responseSummaries.add(summaryUserAnswerDTO);
                }
            }
        } else {
            LOGGER.error("Role is not found");
        }
        return new PaginationUtil<ResponseSummaryUserAnswerDTO>().makePagination(responseSummaries, pageRequest);
    }

    private boolean checkMapContainVillageId(List<ResponseSummaryUserAnswerDTO> mapUserAnswers, Long villageId) {
        for (ResponseSummaryUserAnswerDTO mapUserAnswer : mapUserAnswers) {
            if (mapUserAnswer.getVillage().getId().equals(villageId)) {
                return true;
            }
        }
        return false;
    }
    private ResponseSummaryUserAnswerDTO findByVillageId(List<ResponseSummaryUserAnswerDTO> responseSummaries, Long p_VillageId) {
        for (ResponseSummaryUserAnswerDTO summaryUserAnswerDTO : responseSummaries) {
            if (summaryUserAnswerDTO.getVillage().getId().equals(p_VillageId)) {
                return summaryUserAnswerDTO;
            }
        }
        return null;
    }
}
