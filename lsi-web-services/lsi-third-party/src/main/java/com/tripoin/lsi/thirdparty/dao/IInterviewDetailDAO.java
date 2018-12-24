package com.tripoin.lsi.thirdparty.dao;

import com.tripoin.lsi.thirdparty.data.model.InterviewDetail;
import com.wissensalt.scaffolding.dao.IScaffoldingDAO;
import com.wissensalt.scaffolding.exception.DAOException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IInterviewDetailDAO extends IScaffoldingDAO<InterviewDetail> {

    Page<InterviewDetail> findByInterviewHeader_idAndValidityStatus(Long p_InterviewHeaderId, String interviewValidity, Pageable p_Pageable) throws DAOException;

    /*@Query("SELECT a From InterviewDetail a JOIN a.interviewHeader b WHERE b.id IN (?1) and a.validityStatus = ?2 order by a.createdOn desc")*/
    Page<InterviewDetail> findByInterviewHeader_idInAndValidityStatus(List<Long> ihIds, String interviewValidity, Pageable p_Pageable) throws DAOException;

    List<InterviewDetail> findByInterviewHeader_idAndValidityStatusOrderByCreatedOnDesc(Long p_InterviewHeaderId, String interviewValidity) throws DAOException;

    @Query("SELECT a From InterviewDetail a JOIN a.interviewHeader b WHERE b.id=?1 and a.validityStatus != ?2 order by a.createdOn desc")
    List<InterviewDetail> findByInterviewHeader_idAndNotEqualValidityStatus(Long p_InterviewHeaderId, String interviewValidity) throws DAOException;

    /*@Query("SELECT a From InterviewDetail a JOIN a.interviewHeader b WHERE b.id IN (?1) order by a.createdOn desc")*/
    Page<InterviewDetail> findByInterviewHeader_idInAndValidityStatusNot(List<Long> p_InterviewHeaderId, String p_ValidityStatus, Pageable pageable) throws DAOException;

    Page<InterviewDetail> findByInterviewHeader_id(Long p_InterviewHeaderId, Pageable p_Pageable) throws DAOException;

    List<InterviewDetail> findByInterviewHeader_id(Long p_InterviewHeaderId) throws DAOException;

    InterviewDetail findByRespondentName(String p_RespondentName) throws DAOException;

    @Query("SELECT COUNT(a) FROM InterviewDetail a JOIN a.interviewHeader b WHERE b.id = ?1")
    Integer countInterviewDetailByInterviewHeaderId(Long p_InterviewHeaderId) throws DAOException;

    @Query("SELECT COUNT(a) FROM InterviewDetail a JOIN a.interviewHeader b WHERE b.id = ?1 and a.interviewStatus = ?2")
    Integer countInterviewDetailByInterviewHeaderIdAndInterviewStatus(Long p_InterviewHeaderId, String p_InterviewStatus) throws DAOException;

    @Query("SELECT SUM(b.numberOfRespondent) FROM InterviewDetail a JOIN a.interviewHeader b WHERE b.id = ?1")
    Integer countRespondentAmountByInterviewHeaderId(Long p_InterviewHeaderId) throws DAOException;

    @Query("SELECT COUNT(a) FROM InterviewDetail a JOIN a.interviewHeader b WHERE a.validityStatus = ?1 and b.id = ?2")
    Integer countInterviewDetailByInterviewHeaderIdAndValidityStatus(String p_ValidityStatus, Long p_InterviewHeaderId) throws DAOException;

    @Query("SELECT SUM(a.interviewScore) FROM InterviewDetail a JOIN a.interviewHeader b WHERE b.id = ?1")
    Integer countTotalPointByInterviewHeaderId(Long p_InterviewHeaderId) throws DAOException;

    @Query("SELECT COUNT(a) FROM InterviewDetail a JOIN a.interviewHeader b WHERE b.id = ?1 AND a.interviewScore > 0")
    Integer countDataByPointGreaterThanZeroByInterviewHeaderId(Long p_InteviewHeaderId) throws DAOException;

    @Query("SELECT COUNT(a) FROM InterviewDetail a JOIN a.interviewHeader b WHERE a.interviewScore >= ?1 and b.id = ?2")
    Integer countInterviewDetailByInterviewHeaderIdAndInterviewScore(Integer p_InterviewScore, Long p_InterviewHeaderId) throws DAOException;


    /*START DATA PROGRESS AREA WITH BRANCH*/
    @Query(value = "SELECT SUM(a.number_of_respondent) AS nRespondent,\n" +
            "a.village_id,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and h.branch_id = ?2 and h.interview_event_id = ?3 and d.interview_status =  ?4 AND a.village_id = h.village_id) AS nComplete ,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and h.branch_id = ?2 and h.interview_event_id = ?3 and d.interview_score >= ?5 AND a.village_id = h.village_id) AS nValid \n" +
            "FROM trx_interview_header a WHERE a.province_id = ?1 and a.branch_id = ?2 and a.interview_event_id = ?3 \n" +
            "GROUP BY a.village_id",
            countQuery = "SELECT COUNT(a) \n" +
                    "FROM trx_interview_header a WHERE a.province_id = ?1 and a.branch_id = ?2 and a.interview_event_id = ?3 \n" +
                    "GROUP BY a.village_id",
            nativeQuery = true)
    List<Object[]> findByProvinceIdAndBranchIdAndInterviewEvent_Id(Long p_ProvinceId, Long p_BranchId, Long p_InterviewEventId, String p_InterviewStatus, Integer p_InterviewScore) throws DAOException;

/*    @Query(value = "SELECT SUM(a.number_of_respondent) AS nRespondent,\n" +
            "a.village_id,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and h.branch_id = ?2 and h.interview_event_id = ?3 and d.interview_status =  ?4 AND a.village_id = h.village_id) AS nComplete ,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and h.branch_id = ?2 and h.interview_event_id = ?3 and d.interview_score >= ?5 AND a.village_id = h.village_id) AS nValid \n" +
            "FROM trx_interview_header a WHERE a.province_id = ?1 and a.branch_id = ?2 and a.interview_event_id = ?3 \n" +
            "GROUP BY a.village_id",
            countQuery = "SELECT COUNT(a) \n" +
            "FROM trx_interview_header a WHERE a.province_id = ?1 and a.branch_id = ?2 and a.interview_event_id = ?3 \n" +
            "GROUP BY a.village_id",
            nativeQuery = true)
    Page<Object[]> findByProvinceIdAndBranchIdAndInterviewEvent_Id(Long p_ProvinceId, Long p_BranchId, Long p_InterviewEventId, String p_InterviewStatus, Integer p_InterviewScore, Pageable pageable) throws DAOException;*/

    @Query(value = "SELECT SUM(a.number_of_respondent) AS nRespondent ,\n" +
            "a.village_id,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and a.city_id = ?2 and h.branch_id = ?3 and h.interview_event_id = ?4 and d.interview_status = ?5 AND a.village_id = h.village_id) AS nComplete ,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and a.city_id = ?2 and h.branch_id = ?3 and h.interview_event_id = ?4 and d.interview_score >= ?6 AND a.village_id = h.village_id) AS nValid \n" +
            "FROM trx_interview_header a WHERE a.province_id = ?1 and a.city_id = ?2 and a.branch_id = ?3 and a.interview_event_id = ?4\n" +
            "GROUP BY a.village_id", nativeQuery = true)
    List<Object[]> findByProvinceIdAndCityIdAndBranchIdAndInterviewEvent_Id(Long p_ProvinceId, Long cityId, Long p_BranchId, Long p_InterviewEventId, String p_InterviewStatus, Integer p_InterviewScore) throws DAOException;

/*    @Query(value = "SELECT SUM(a.number_of_respondent) AS nRespondent ,\n" +
            "a.village_id,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and a.city_id = ?2 and h.branch_id = ?3 and h.interview_event_id = ?4 and d.interview_status = ?5 AND a.village_id = h.village_id) AS nComplete ,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and a.city_id = ?2 and h.branch_id = ?3 and h.interview_event_id = ?4 and d.interview_score >= ?6 AND a.village_id = h.village_id) AS nValid \n" +
            "FROM trx_interview_header a WHERE a.province_id = ?1 and a.city_id = ?2 and a.branch_id = ?3 and a.interview_event_id = ?4\n" +
            "GROUP BY a.village_id",
            countQuery = "SELECT COUNT(a) \n" +
            "FROM trx_interview_header a WHERE a.province_id = ?1 and a.city_id = ?2 and a.branch_id = ?3 and a.interview_event_id = ?4\n" +
            "GROUP BY a.village_id",
            nativeQuery = true)
    Page<Object[]> findByProvinceIdAndCityIdAndBranchIdAndInterviewEvent_Id(Long p_ProvinceId, Long cityId, Long p_BranchId, Long p_InterviewEventId, String p_InterviewStatus, Integer p_InterviewScore, Pageable pageable) throws DAOException;*/

    @Query(value = "SELECT SUM(a.number_of_respondent) AS nRespondent ,\n" +
            "a.village_id,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and a.city_id = ?2 and a.district_id = ?3 and h.branch_id = ?4 and h.interview_event_id = ?5 and d.interview_status = ?6 AND a.village_id = h.village_id) AS nComplete ,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and a.city_id = ?2 and a.district_id = ?3 and h.branch_id = ?4 and h.interview_event_id = ?5 and d.interview_score >= ?7 AND a.village_id = h.village_id) AS nValid \n" +
            "FROM trx_interview_header a WHERE a.province_id = ?1 and a.city_id = ?2 and a.district_id = ?3 and a.branch_id = ?4 and a.interview_event_id = ?5\n" +
            "GROUP BY a.village_id", nativeQuery = true)
    List<Object[]> findByProvinceIdAndCityIdAndDistrictIdAndBranchIdAndInterviewEvent_Id(Long p_ProvinceId, Long cityId, Long p_DistrictId, Long p_BranchId, Long p_InterviewEventId, String p_InterviewStatus, Integer p_InterviewScore) throws DAOException;

    /*@Query(value = "SELECT SUM(a.number_of_respondent) AS nRespondent ,\n" +
            "a.village_id,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and a.city_id = ?2 and a.district_id = ?3 and h.branch_id = ?4 and h.interview_event_id = ?5 and d.interview_status = ?6 AND a.village_id = h.village_id) AS nComplete ,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and a.city_id = ?2 and a.district_id = ?3 and h.branch_id = ?4 and h.interview_event_id = ?5 and d.interview_score >= ?7 AND a.village_id = h.village_id) AS nValid \n" +
            "FROM trx_interview_header a WHERE a.province_id = ?1 and a.city_id = ?2 and a.district_id = ?3 and a.branch_id = ?4 and a.interview_event_id = ?5\n" +
            "GROUP BY a.village_id",
            countQuery = "SELECT COUNT(a) \n" +
            "FROM trx_interview_header a WHERE a.province_id = ?1 and a.city_id = ?2 and a.district_id = ?3 and a.branch_id = ?4 and a.interview_event_id = ?5\n" +
            "GROUP BY a.village_id",
            nativeQuery = true)
    Page<Object[]> findByProvinceIdAndCityIdAndDistrictIdAndBranchIdAndInterviewEvent_Id(Long p_ProvinceId, Long cityId, Long p_DistrictId, Long p_BranchId, Long p_InterviewEventId, String p_InterviewStatus, Integer p_InterviewScore, Pageable pageable) throws DAOException;*/

    @Query(value = "SELECT SUM(a.number_of_respondent) AS nRespondent ,\n" +
            "a.village_id,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and a.city_id = ?2 and a.district_id = ?3 and a.village_id = ?4 and h.branch_id = ?5 and h.interview_event_id = ?6 and d.interview_status = ?7 AND a.village_id = h.village_id) AS nComplete ,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and a.city_id = ?2 and a.district_id = ?3 and a.village_id = ?4 and h.branch_id = ?5 and h.interview_event_id = ?6 and d.interview_score >= ?8 AND a.village_id = h.village_id) AS nValid \n" +
            "FROM trx_interview_header a WHERE a.province_id = ?1 and a.city_id = ?2 and a.district_id = ?3 and a.village_id = ?4 and a.branch_id = ?5 and a.interview_event_id = ?6\n" +
            "GROUP BY a.village_id",
            countQuery = "SELECT COUNT(a) \n" +
                    "FROM trx_interview_header a WHERE a.province_id = ?1 and a.city_id = ?2 and a.district_id = ?3 and a.village_id = ?4 and a.branch_id = ?5 and a.interview_event_id = ?6\n" +
                    "GROUP BY a.village_id",
            nativeQuery = true)
    List<Object[]> findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndBranchIdAndInterviewEvent_Id(Long p_ProvinceId, Long cityId, Long p_DistrictId, Long p_VillageId, Long p_BranchId, Long p_InterviewEventId, String p_InterviewStatus, Integer p_InterviewScore) throws DAOException;

    /*@Query(value = "SELECT SUM(a.number_of_respondent) AS nRespondent ,\n" +
            "a.village_id,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and a.city_id = ?2 and a.district_id = ?3 and a.village_id = ?4 and h.branch_id = ?5 and h.interview_event_id = ?6 and d.interview_status = ?7 AND a.village_id = h.village_id) AS nComplete ,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and a.city_id = ?2 and a.district_id = ?3 and a.village_id = ?4 and h.branch_id = ?5 and h.interview_event_id = ?6 and d.interview_score >= ?8 AND a.village_id = h.village_id) AS nValid \n" +
            "FROM trx_interview_header a WHERE a.province_id = ?1 and a.city_id = ?2 and a.district_id = ?3 and a.village_id = ?4 and a.branch_id = ?5 and a.interview_event_id = ?6\n" +
            "GROUP BY a.village_id",
            countQuery = "SELECT COUNT(a) \n" +
                    "FROM trx_interview_header a WHERE a.province_id = ?1 and a.city_id = ?2 and a.district_id = ?3 and a.village_id = ?4 and a.branch_id = ?5 and a.interview_event_id = ?6\n" +
                    "GROUP BY a.village_id",
            nativeQuery = true)
    Page<Object[]> findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndBranchIdAndInterviewEvent_Id(Long p_ProvinceId, Long cityId, Long p_DistrictId, Long p_VillageId, Long p_BranchId, Long p_InterviewEventId, String p_InterviewStatus, Integer p_InterviewScore, Pageable pageable) throws DAOException;*/

    @Query(value = "SELECT SUM(a.number_of_respondent) AS nRespondent ,\n" +
            "a.village_id,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.branch_id = ?1 and h.interview_event_id = ?2 and d.interview_status = ?3 AND a.village_id = h.village_id) AS nComplete ,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.branch_id = ?1 and h.interview_event_id = ?2 and d.interview_score >= ?4 AND a.village_id = h.village_id) AS nValid \n" +
            "FROM trx_interview_header a WHERE a.branch_id = ?1 and a.interview_event_id = ?2\n" +
            "GROUP BY a.village_id", nativeQuery = true)
    List<Object[]> findByBranchIdAndInterviewEvent_Id(Long p_BranchId, Long p_InterviewEventId, String p_InterviewStatus, Integer p_InterviewScore) throws DAOException;

    /*@Query(value = "SELECT SUM(a.number_of_respondent) AS nRespondent ,\n" +
            "a.village_id,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.branch_id = ?1 and h.interview_event_id = ?2 and d.interview_status = ?3 AND a.village_id = h.village_id) AS nComplete ,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.branch_id = ?1 and h.interview_event_id = ?2 and d.interview_score >= ?4 AND a.village_id = h.village_id) AS nValid \n" +
            "FROM trx_interview_header a WHERE a.branch_id = ?1 and a.interview_event_id = ?2\n" +
            "GROUP BY a.village_id",
            countQuery = "SELECT COUNT(a) \n" +
                    "FROM trx_interview_header a WHERE a.branch_id = ?1 and a.interview_event_id = ?2\n" +
                    "GROUP BY a.village_id",
            nativeQuery = true)
    Page<Object[]> findByBranchIdAndInterviewEvent_Id(Long p_BranchId, Long p_InterviewEventId, String p_InterviewStatus, Integer p_InterviewScore, PageRequest pageRequest) throws DAOException;*/
    /*END DATA PROGRESS AREA WITH BRANCH*/

    /*START DATA PROGRESS AREA WITHOUT BRANCH*/
    @Query(value = "SELECT SUM(a.number_of_respondent) AS nRespondent,\n" +
            "a.village_id,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and h.interview_event_id = ?2 and d.interview_status =  ?3 AND a.village_id = h.village_id) AS nComplete ,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and h.interview_event_id = ?2 and d.interview_score >= ?4 AND a.village_id = h.village_id) AS nValid \n" +
            "FROM trx_interview_header a WHERE a.province_id = ?1 and a.interview_event_id = ?2 \n" +
            "GROUP BY a.village_id", nativeQuery = true)
    List<Object[]> findByProvinceIdAndInterviewEvent_Id(Long p_ProvinceId, Long p_InterviewEventId, String p_InterviewStatus, Integer p_InterviewScore) throws DAOException;

    /*@Query(value = "SELECT SUM(a.number_of_respondent) AS nRespondent,\n" +
            "a.village_id,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and h.interview_event_id = ?2 and d.interview_status =  ?3 AND a.village_id = h.village_id) AS nComplete ,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and h.interview_event_id = ?2 and d.interview_score >= ?4 AND a.village_id = h.village_id) AS nValid \n" +
            "FROM trx_interview_header a WHERE a.province_id = ?1 and a.interview_event_id = ?2 \n" +
            "GROUP BY a.village_id",
            countQuery = "SELECT COUNT(a) \n" +
                    "FROM trx_interview_header a WHERE a.province_id = ?1 and a.interview_event_id = ?2 \n" +
                    "GROUP BY a.village_id",
            nativeQuery = true)
    Page<Object[]> findByProvinceIdAndInterviewEvent_Id(Long p_ProvinceId, Long p_InterviewEventId, String p_InterviewStatus, Integer p_InterviewScore, Pageable pageable) throws DAOException;*/

    @Query(value = "SELECT SUM(a.number_of_respondent) AS nRespondent ,\n" +
            "a.village_id,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and a.city_id = ?2 and h.interview_event_id = ?3 and d.interview_status = ?4 AND a.village_id = h.village_id) AS nComplete ,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and a.city_id = ?2 and h.interview_event_id = ?3 and d.interview_score >= ?5 AND a.village_id = h.village_id) AS nValid \n" +
            "FROM trx_interview_header a WHERE a.province_id = ?1 and a.city_id = ?2 and a.interview_event_id = ?3\n" +
            "GROUP BY a.village_id", nativeQuery = true)
    List<Object[]> findByProvinceIdAndCityIdAndInterviewEvent_Id(Long p_ProvinceId, Long cityId, Long p_InterviewEventId, String p_InterviewStatus, Integer p_InterviewScore) throws DAOException;

    /*@Query(value = "SELECT SUM(a.number_of_respondent) AS nRespondent ,\n" +
            "a.village_id,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and a.city_id = ?2 and h.interview_event_id = ?3 and d.interview_status = ?4 AND a.village_id = h.village_id) AS nComplete ,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and a.city_id = ?2 and h.interview_event_id = ?3 and d.interview_score >= ?5 AND a.village_id = h.village_id) AS nValid \n" +
            "FROM trx_interview_header a WHERE a.province_id = ?1 and a.city_id = ?2 and a.interview_event_id = ?3\n" +
            "GROUP BY a.village_id",
            countQuery = "SELECT COUNT(a) \n" +
                    "FROM trx_interview_header a WHERE a.province_id = ?1 and a.city_id = ?2 and a.interview_event_id = ?3\n" +
                    "GROUP BY a.village_id",
            nativeQuery = true)
    Page<Object[]> findByProvinceIdAndCityIdAndInterviewEvent_Id(Long p_ProvinceId, Long cityId, Long p_InterviewEventId, String p_InterviewStatus, Integer p_InterviewScore, Pageable pageable) throws DAOException;*/

    @Query(value = "SELECT SUM(a.number_of_respondent) AS nRespondent ,\n" +
            "a.village_id,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and a.city_id = ?2 and a.district_id = ?3 and h.interview_event_id = ?4 and d.interview_status = ?5 AND a.village_id = h.village_id) AS nComplete ,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and a.city_id = ?2 and a.district_id = ?3 and h.interview_event_id = ?4 and d.interview_score >= ?6 AND a.village_id = h.village_id) AS nValid \n" +
            "FROM trx_interview_header a WHERE a.province_id = ?1 and a.city_id = ?2 and a.district_id = ?3 and a.interview_event_id = ?4\n" +
            "GROUP BY a.village_id", nativeQuery = true)
    List<Object[]> findByProvinceIdAndCityIdAndDistrictIdAndInterviewEvent_Id(Long p_ProvinceId, Long cityId, Long p_DistrictId, Long p_InterviewEventId, String p_InterviewStatus, Integer p_InterviewScore) throws DAOException;

    /*@Query(value = "SELECT SUM(a.number_of_respondent) AS nRespondent ,\n" +
            "a.village_id,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and a.city_id = ?2 and a.district_id = ?3 and h.interview_event_id = ?4 and d.interview_status = ?5 AND a.village_id = h.village_id) AS nComplete ,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and a.city_id = ?2 and a.district_id = ?3 and h.interview_event_id = ?4 and d.interview_score >= ?6 AND a.village_id = h.village_id) AS nValid \n" +
            "FROM trx_interview_header a WHERE a.province_id = ?1 and a.city_id = ?2 and a.district_id = ?3 and a.interview_event_id = ?4\n" +
            "GROUP BY a.village_id",
            countQuery = "SELECT COUNT(a) \n" +
                    "FROM trx_interview_header a WHERE a.province_id = ?1 and a.city_id = ?2 and a.district_id = ?3 and a.interview_event_id = ?4\n" +
                    "GROUP BY a.village_id",
            nativeQuery = true)
    Page<Object[]> findByProvinceIdAndCityIdAndDistrictIdAndInterviewEvent_Id(Long p_ProvinceId, Long cityId, Long p_DistrictId, Long p_InterviewEventId, String p_InterviewStatus, Integer p_InterviewScore, Pageable pageable) throws DAOException;*/

    @Query(value = "SELECT SUM(a.number_of_respondent) AS nRespondent ,\n" +
            "a.village_id,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and a.city_id = ?2 and a.district_id = ?3 and a.village_id = ?4 and h.interview_event_id = ?5 and d.interview_status = ?6 AND a.village_id = h.village_id) AS nComplete ,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and a.city_id = ?2 and a.district_id = ?3 and a.village_id = ?4 and h.interview_event_id = ?5 and d.interview_score >= ?7 AND a.village_id = h.village_id) AS nValid \n" +
            "FROM trx_interview_header a WHERE a.province_id = ?1 and a.city_id = ?2 and a.district_id = ?3 and a.village_id = ?4 and a.interview_event_id = ?6\n" +
            "GROUP BY a.village_id", nativeQuery = true)
    List<Object[]> findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndInterviewEvent_Id(Long p_ProvinceId, Long cityId, Long p_DistrictId, Long p_VillageId, Long p_InterviewEventId, String p_InterviewStatus, Integer p_InterviewScore) throws DAOException;

    /*@Query(value = "SELECT SUM(a.number_of_respondent) AS nRespondent ,\n" +
            "a.village_id,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and a.city_id = ?2 and a.district_id = ?3 and a.village_id = ?4 and h.interview_event_id = ?5 and d.interview_status = ?6 AND a.village_id = h.village_id) AS nComplete ,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.province_id = ?1 and a.city_id = ?2 and a.district_id = ?3 and a.village_id = ?4 and h.interview_event_id = ?5 and d.interview_score >= ?7 AND a.village_id = h.village_id) AS nValid \n" +
            "FROM trx_interview_header a WHERE a.province_id = ?1 and a.city_id = ?2 and a.district_id = ?3 and a.village_id = ?4 and a.interview_event_id = ?6\n" +
            "GROUP BY a.village_id",
            countQuery = "SELECT COUNT(a) \n" +
                    "FROM trx_interview_header a WHERE a.province_id = ?1 and a.city_id = ?2 and a.district_id = ?3 and a.village_id = ?4 and a.interview_event_id = ?6\n" +
                    "GROUP BY a.village_id",
            nativeQuery = true)
    Page<Object[]> findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndInterviewEvent_Id(Long p_ProvinceId, Long cityId, Long p_DistrictId, Long p_VillageId, Long p_InterviewEventId, String p_InterviewStatus, Integer p_InterviewScore, Pageable pageable) throws DAOException;*/

    @Query(value = "SELECT SUM(a.number_of_respondent) AS nRespondent ,\n" +
            "a.village_id,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.interview_event_id = ?1 and d.interview_status = ?2 AND a.village_id = h.village_id) AS nComplete ,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.interview_event_id = ?1 and d.interview_score >= ?3 AND a.village_id = h.village_id) AS nValid \n" +
            "FROM trx_interview_header a WHERE a.interview_event_id = ?1\n" +
            "GROUP BY a.village_id", nativeQuery = true)
    List<Object[]> findByInterviewEvent_Id(Long p_InterviewEventId, String p_InterviewStatus, Integer p_InterviewScore) throws DAOException;

    /*@Query(value = "SELECT SUM(a.number_of_respondent) AS nRespondent ,\n" +
            "a.village_id,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.interview_event_id = ?1 and d.interview_status = ?2 AND a.village_id = h.village_id) AS nComplete ,\n" +
            "(SELECT COUNT(d.id) FROM trx_interview_header h JOIN trx_interview_detail d ON h.id = d.interview_header_id WHERE h.interview_event_id = ?1 and d.interview_score >= ?3 AND a.village_id = h.village_id) AS nValid \n" +
            "FROM trx_interview_header a WHERE a.interview_event_id = ?1\n" +
            "GROUP BY a.village_id",
            countQuery = "SELECT COUNT(a) \n" +
                    "FROM trx_interview_header a WHERE a.interview_event_id = ?1\n" +
                    "GROUP BY a.village_id",
            nativeQuery = true)
    Page<Object[]> findByInterviewEvent_IdPage(Long p_InterviewEventId, String p_InterviewStatus, Integer p_InterviewScore, Pageable pageable) throws DAOException;*/
    /*END DATA PROGRESS AREA WITHOUT BRANCH*/

    List<InterviewDetail> findByMobileUniqueCode(String p_MobileUniqueCode) throws DAOException;
}
