package com.tripoin.lsi.thirdparty.dao;

import com.tripoin.lsi.thirdparty.data.model.InterviewHeader;
import com.wissensalt.scaffolding.dao.IScaffoldingDAO;
import com.wissensalt.scaffolding.exception.DAOException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IInterviewHeaderDAO extends IScaffoldingDAO<InterviewHeader> {

    List<InterviewHeader> findByInterviewEvent_Id(Long p_InterviewEventId) throws DAOException;

    Page<InterviewHeader> findByInterviewEvent_IdAndUserIdIn(Long p_InterviewEventId, List<Long> userId, Pageable p_Pageable) throws DAOException;

    Page<InterviewHeader> findByInterviewEvent_IdOrderByCreatedOnDesc(Long p_InterviewEventId, Pageable p_Pageable) throws DAOException;

    Page<InterviewHeader> findByInterviewEvent_IdAndUserId(Long p_InterviewEventId, Long p_UserId, Pageable p_Pageable) throws DAOException;

    List<InterviewHeader> findByInterviewEvent_IdAndUserId(Long p_InterviewEventId, Long p_UserId) throws DAOException;

    List<InterviewHeader> findByBranchIdAndInterviewEvent_Id(Long p_BranchId, Long p_InterviewEventId) throws DAOException;

    Page<InterviewHeader> findByBranchIdAndInterviewEvent_IdOrderByCreatedOnDesc(Long p_BranchId, Long p_InterviewEventId, Pageable p_Pageable) throws DAOException;

    List<InterviewHeader> findByBranchIdAndInterviewEvent_IdAndUserId(Long p_BranchId, Long p_InterviewEventId, Long p_UserId) throws DAOException;

    Page<InterviewHeader> findByBranchIdAndInterviewEvent_IdAndUserIdIn(Long p_BranchId, Long p_InterviewEventId, List<Long> p_UserId, Pageable p_PageRequest) throws DAOException;

    Page<InterviewHeader> findByBranchIdAndInterviewEvent_IdAndUserId(Long p_BranchId, Long p_InterviewEventId, Long p_UserId, Pageable p_Pageable) throws DAOException;

    List<InterviewHeader> findByBranchId(Long p_BranchId) throws DAOException;

    List<InterviewHeader> findByProvinceId(Long p_ProvinceId) throws DAOException;

    List<InterviewHeader> findByProvinceIdAndInterviewEvent_Id(Long p_ProvinceId, Long p_InterviewEventId) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndInterviewEvent_IdAndUserIdIn(Long p_ProvinceId, Long p_InterviewEventId, List<Long> userId, Pageable pageable) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndInterviewEvent_IdOrderByCreatedOnDesc(Long p_ProvinceId, Long p_InterviewEventId, Pageable p_Pageable) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndInterviewEvent_IdAndUserId(Long p_ProvinceId, Long p_InterviewEventId, Long p_UserId, Pageable p_Pageable) throws DAOException;

    List<InterviewHeader> findByProvinceIdAndInterviewEvent_IdAndUserId(Long p_ProvinceId, Long p_InterviewEventId, Long p_UserId) throws DAOException;

    List<InterviewHeader> findByProvinceIdAndBranchIdAndInterviewEvent_Id(Long p_ProvinceId, Long p_BranchId, Long p_InterviewEventId) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndBranchIdAndInterviewEvent_IdAndUserIdIn(Long p_ProvinceId, Long p_BranchId, Long p_InterviewEventId, List<Long> userIds, Pageable p_Pageable) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndBranchIdAndInterviewEvent_IdOrderByCreatedOnDesc(Long p_ProvinceId, Long p_BranchId, Long p_InterviewEventId, Pageable p_Pageable) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndBranchIdAndInterviewEvent_IdAndUserId(Long p_ProvinceId, Long p_BranchId, Long p_InterviewEventId, Long p_UserId, Pageable p_Pageable) throws DAOException;

    List<InterviewHeader> findByProvinceIdAndBranchIdAndInterviewEvent_IdAndUserId(Long p_ProvinceId, Long p_BranchId, Long p_InterviewEventId, Long p_UserId) throws DAOException;

    Page<InterviewHeader> findByProvinceId(Long p_ProvinceId, Pageable p_Pageable) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndUserId(Long p_ProvinceId, Long p_UserId, Pageable p_Pageable) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndUserIdIn(Long p_ProvinceId, List<Long> p_UserId, Pageable p_Pageable) throws DAOException;

    List<InterviewHeader> findByProvinceIdAndUserId(Long p_ProvinceId, Long p_UserId) throws DAOException;

    List<InterviewHeader> findByProvinceIdAndCityId(Long p_ProvinceId, Long p_CityId) throws DAOException;

    List<InterviewHeader> findByProvinceIdAndCityIdAndInterviewEvent_id(Long p_ProvinceId, Long p_CityId, Long p_InterviewEventId) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndCityIdAndInterviewEvent_idAndUserIdIn(Long p_ProvinceId, Long p_CityId, Long p_InterviewEventId, List<Long> userId, Pageable pageable) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndCityIdAndInterviewEvent_idOrderByCreatedOnDesc(Long p_ProvinceId, Long p_CityId, Long p_InterviewEventId, Pageable p_Pageable) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndCityIdAndInterviewEvent_idAndUserId(Long p_ProvinceId, Long p_CityId, Long p_InterviewEventId, Long p_UserId, Pageable p_Pageable) throws DAOException;

    List<InterviewHeader> findByProvinceIdAndCityIdAndInterviewEvent_idAndUserId(Long p_ProvinceId, Long p_CityId, Long p_InterviewEventId, Long p_UserId) throws DAOException;

    List<InterviewHeader> findByProvinceIdAndCityIdAndBranchIdAndInterviewEvent_id(Long p_ProvinceId, Long p_CityId, Long p_BranchId, Long p_InterviewEventId) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndCityIdAndBranchIdAndInterviewEvent_idAndUserIdIn(Long p_ProvinceId, Long p_CityId, Long p_BranchId, Long p_InterviewEventId, List<Long> userIds, Pageable p_Pageable) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndCityIdAndBranchIdAndInterviewEvent_idOrderByCreatedOnDesc(Long p_ProvinceId, Long p_CityId, Long p_BranchId, Long p_InterviewEventId, Pageable p_Pageable) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndCityIdAndBranchIdAndInterviewEvent_idAndUserId(Long p_ProvinceId, Long p_CityId, Long p_BranchId, Long p_InterviewEventId, Long p_UserId, Pageable p_Pageable) throws DAOException;

    List<InterviewHeader> findByProvinceIdAndCityIdAndBranchIdAndInterviewEvent_idAndUserId(Long p_ProvinceId, Long p_CityId, Long p_BranchId, Long p_InterviewEventId, Long p_UserId) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndCityId(Long p_ProvinceId, Long p_CityId, Pageable p_Pageable) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndCityIdAndUserId(Long p_ProvinceId, Long p_CityId, Long p_UserId, Pageable p_Pageable) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndCityIdAndUserIdIn(Long p_ProvinceId, Long p_CityId, List<Long> p_UserId, Pageable p_Pageable) throws DAOException;

    List<InterviewHeader> findByProvinceIdAndCityIdAndUserId(Long p_ProvinceId, Long p_CityId, Long p_UserId) throws DAOException;

    List<InterviewHeader> findByProvinceIdAndCityIdAndDistrictId(Long p_ProvinceId, Long p_CityId, Long p_DistrictId) throws DAOException;

    List<InterviewHeader> findByProvinceIdAndCityIdAndDistrictIdAndInterviewEvent_id(Long p_ProvinceId, Long p_CityId, Long p_DistrictId, Long p_InterviewEventId) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndCityIdAndDistrictIdAndInterviewEvent_idAndUserIdIn(Long p_ProvinceId, Long p_CityId, Long p_DistrictId, Long p_InterviewEventId, List<Long> userId, Pageable pageable) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndCityIdAndDistrictIdAndInterviewEvent_idOrderByCreatedOnDesc(Long p_ProvinceId, Long p_CityId, Long p_DistrictId, Long p_InterviewEventId, Pageable p_Pageable) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndCityIdAndDistrictIdAndInterviewEvent_idAndUserId(Long p_ProvinceId, Long p_CityId, Long p_DistrictId, Long p_InterviewEventId, Long p_UserId, Pageable p_Pageable) throws DAOException;

    List<InterviewHeader> findByProvinceIdAndCityIdAndDistrictIdAndInterviewEvent_idAndUserId(Long p_ProvinceId, Long p_CityId, Long p_DistrictId, Long p_InterviewEventId, Long p_UserId) throws DAOException;

    List<InterviewHeader> findByProvinceIdAndCityIdAndDistrictIdAndBranchIdAndInterviewEvent_id(Long p_ProvinceId, Long p_CityId, Long p_DistrictId, Long p_BranchId, Long p_InterviewEventId) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndCityIdAndDistrictIdAndBranchIdAndInterviewEvent_idAndUserIdIn(Long p_ProvinceId, Long p_CityId, Long p_DistrictId, Long p_BranchId, Long p_InterviewEventId, List<Long> userIds, Pageable p_Pageable) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndCityIdAndDistrictIdAndBranchIdAndInterviewEvent_idOrderByCreatedOnDesc(Long p_ProvinceId, Long p_CityId, Long p_DistrictId, Long p_BranchId, Long p_InterviewEventId, Pageable p_Pageable) throws DAOException;

    List<InterviewHeader> findByProvinceIdAndCityIdAndDistrictIdAndBranchIdAndInterviewEvent_idAndUserId(Long p_ProvinceId, Long p_CityId, Long p_DistrictId, Long p_BranchId, Long p_InterviewEventId, Long p_UserId) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndCityIdAndDistrictIdAndBranchIdAndInterviewEvent_idAndUserId(Long p_ProvinceId, Long p_CityId, Long p_DistrictId, Long p_BranchId, Long p_InterviewEventId, Long p_UserId, Pageable p_Pageable) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndCityIdAndDistrictId(Long p_ProvinceId, Long p_CityId, Long p_DistrictId, Pageable p_Pageable) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndCityIdAndDistrictIdAndUserId(Long p_ProvinceId, Long p_CityId, Long p_DistrictId, Long p_UserId, Pageable p_Pageable) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndCityIdAndDistrictIdAndUserIdIn(Long p_ProvinceId, Long p_CityId, Long p_DistrictId, List<Long> p_UserId, Pageable p_Pageable) throws DAOException;

    List<InterviewHeader> findByProvinceIdAndCityIdAndDistrictIdAndUserId(Long p_ProvinceId, Long p_CityId, Long p_DistrictId, Long p_UserId) throws DAOException;

    List<InterviewHeader> findByProvinceIdAndCityIdAndDistrictIdAndVillageId(Long p_ProvinceId, Long p_CityId, Long p_DistrictId, Long p_VillageId) throws DAOException;

    List<InterviewHeader> findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndInterviewEvent_id(Long p_ProvinceId, Long p_CityId, Long p_DistrictId, Long p_VillageId, Long p_InteriewEventId) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndInterviewEvent_idAndUserIdIn(Long p_ProvinceId, Long p_CityId, Long p_DistrictId, Long p_VillageId, Long p_InteriewEventId, List<Long> userId, Pageable pageable) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndInterviewEvent_idOrderByCreatedOnDesc(Long p_ProvinceId, Long p_CityId, Long p_DistrictId, Long p_VillageId, Long p_InteriewEventId, Pageable p_Pageable) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndInterviewEvent_idAndUserId(Long p_ProvinceId, Long p_CityId, Long p_DistrictId, Long p_VillageId, Long p_InteriewEventId, Long p_UserId, Pageable p_Pageable) throws DAOException;

    List<InterviewHeader> findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndInterviewEvent_idAndUserId(Long p_ProvinceId, Long p_CityId, Long p_DistrictId, Long p_VillageId, Long p_InteriewEventId, Long p_UserId) throws DAOException;

    List<InterviewHeader> findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndBranchIdAndInterviewEvent_id(Long p_ProvinceId, Long p_CityId, Long p_DistrictId, Long p_VillageId, Long p_BranchId, Long p_InteriewEventId) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndBranchIdAndInterviewEvent_idAndUserIdIn(Long p_ProvinceId, Long p_CityId, Long p_DistrictId, Long p_VillageId, Long p_BranchId, Long p_InteriewEventId, List<Long> userId, Pageable p_Pageable) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndBranchIdAndInterviewEvent_idOrderByCreatedOnDesc(Long p_ProvinceId, Long p_CityId, Long p_DistrictId, Long p_VillageId, Long p_BranchId, Long p_InteriewEventId, Pageable p_Pageable) throws DAOException;

    List<InterviewHeader> findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndBranchIdAndInterviewEvent_idAndUserId(Long p_ProvinceId, Long p_CityId, Long p_DistrictId, Long p_VillageId, Long p_BranchId, Long p_InteriewEventId, Long p_UserId) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndBranchIdAndInterviewEvent_idAndUserId(Long p_ProvinceId, Long p_CityId, Long p_DistrictId, Long p_VillageId, Long p_BranchId, Long p_InteriewEventId, Long p_UserId, Pageable p_Pageable) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndCityIdAndDistrictIdAndVillageId(Long p_ProvinceId, Long p_CityId, Long p_DistrictId, Long p_VillageId, Pageable p_Pageable) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndUserId(Long p_ProvinceId, Long p_CityId, Long p_DistrictId, Long p_VillageId, Long p_UserId, Pageable p_Pageable) throws DAOException;

    Page<InterviewHeader> findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndUserIdIn(Long p_ProvinceId, Long p_CityId, Long p_DistrictId, Long p_VillageId, List<Long> p_UserId, Pageable p_Pageable) throws DAOException;

    List<InterviewHeader> findByProvinceIdAndCityIdAndDistrictIdAndVillageIdAndUserId(Long p_ProvinceId, Long p_CityId, Long p_DistrictId, Long p_VillageId, Long p_UserId) throws DAOException;

    Page<InterviewHeader> findByUserId(Long p_UserId, Pageable p_Pageable) throws DAOException;

    Page<InterviewHeader> findByUserIdIn(List<Long> p_UserId, Pageable p_Pageable) throws DAOException;

    @Query("SELECT a.villageId FROM InterviewHeader a WHERE a.userId = ?1")
    List<Long> findVillageIdByUserId(Long p_UserId) throws DAOException;

    List<InterviewHeader> findByUserId(Long p_UserId) throws DAOException;

    @Query("SELECT SUM(a.numberOfRespondent) FROM InterviewHeader a WHERE a.userId = ?1")
    Integer countTotalNumberOfRespondentByUserId(Long p_UserId) throws DAOException;

    @Query("SELECT DISTINCT (b.id) FROM InterviewHeader a JOIN a.interviewEvent b WHERE a.userId = ?1")
    List<Long> findDistinctEventIdByUserId(Long p_UserId) throws DAOException;

    List<InterviewHeader> findByInterviewEvent_id(Long p_EventId) throws DAOException;

    InterviewHeader findByInterviewEvent_idAndUserIdAndVillageId(Long p_EventId, Long p_UserId, Long p_VillageId) throws DAOException;

    @Query("SELECT DISTINCT a.provinceId FROM InterviewHeader a WHERE a.branchId = ?1")
    List<Long> findDistinctProvinceByBranch(Long p_BranchId) throws DAOException;

    /*FILTER PROGRESS BY AREA 2*/
    @Query(value = "select a.province_id from lsi_core.trx_interview_header a group by a.province_id, a.interview_event_id having interview_event_id = ?1 LIMIT ?2, ?3", nativeQuery = true)
    List<BigInteger> findProvinceByEvent(Long p_InterviewEventId, Integer p_PageSize, Integer p_Offset) throws DAOException;

    @Query(value = "select a.province_id from lsi_core.trx_interview_header a group by a.province_id, a.interview_event_id having interview_event_id = ?1 ", nativeQuery = true)
    List<BigInteger> findProvinceByEvent(Long p_InterviewEventId) throws DAOException;

    @Query("SELECT COUNT(DISTINCT a.cityId) FROM InterviewHeader a where a.interviewEvent.id = ?1 and a.provinceId =?2")
    Integer countCityByProvinceId(Long p_InterviewEventId, Long p_ProvinceId) throws DAOException;

    @Query("SELECT COUNT(DISTINCT a.districtId) FROM InterviewHeader a where a.interviewEvent.id = ?1 and a.provinceId =?2")
    Integer countDistrictByProvinceId(Long p_InterviewEventId, Long p_ProvinceId) throws DAOException;

    @Query("SELECT COUNT(DISTINCT a.villageId) FROM InterviewHeader a where a.interviewEvent.id = ?1 and a.provinceId =?2")
    Integer countVillageByProvinceId(Long p_InterviewEventId, Long p_ProvinceId) throws DAOException;

    @Query("SELECT COUNT(DISTINCT a.userId) FROM InterviewHeader a where a.interviewEvent.id = ?1 and a.provinceId =?2")
    Integer countUserIdByProvinceId(Long p_InterviewEventId, Long p_ProvinceId) throws DAOException;

    @Query("SELECT COUNT(b) FROM InterviewHeader a " +
            "JOIN a.interviewDetails b " +
            "WHERE a.interviewEvent.id = ?1 AND a.provinceId =?2 AND b.interviewStatus =?3")
    Integer countDataCompleteByProvinceId(Long p_InterviewEventId, Long p_ProvinceId, String p_InterviewStatus) throws DAOException;

    @Query(value = "select sum(total) from ( " +
            "select count(*) as total from lsi_core.trx_interview_header a " +
            "join lsi_core.trx_interview_detail b on a.id = b.interview_header_id " +
            "where a.interview_event_id = ?1 and a.province_id=?2 and b.interview_score >= ?3" +
            ") as detail", nativeQuery = true)
    Integer countDataValidByProvinceId(Long p_InterviewEventId, Long p_ProvinceId, Integer p_InterviewScore) throws DAOException;

    @Query(value = "select sum(number_of_respondent) from lsi_core.trx_interview_header a  where a.interview_event_id = ?1 and a.province_id=?2", nativeQuery = true)
    Integer sumRespondentByProvinceId(Long p_InterviewEventId, Long p_ProvinceId) throws DAOException;

    /*PROVINCE*/
    @Query(value = "select distinct(a.city_id) from lsi_core.trx_interview_header a where interview_event_id = ?1 and a.province_id=?2 LIMIT ?3, ?4", nativeQuery = true)
    List<BigInteger> findCityByEvent(Long p_InterviewEventId, Long p_ProvinceId, Integer p_PageSize, Integer p_Offset) throws DAOException;

    @Query(value = "select distinct(a.city_id) from lsi_core.trx_interview_header a where interview_event_id = ?1 and a.province_id=?2 ", nativeQuery = true)
    List<BigInteger> findCityByEvent(Long p_InterviewEventId, Long p_ProvinceId) throws DAOException;

    @Query("SELECT COUNT(DISTINCT a.districtId) FROM InterviewHeader a where a.interviewEvent.id = ?1 and a.cityId =?2")
    Integer countDistrictByCityId(Long p_InterviewEventId, Long p_CityId) throws DAOException;

    @Query("SELECT COUNT(DISTINCT a.villageId) FROM InterviewHeader a where a.interviewEvent.id = ?1 and a.cityId =?2")
    Integer countVillageByCityId(Long p_InterviewEventId, Long p_CityId) throws DAOException;

    @Query("SELECT COUNT(DISTINCT a.userId) FROM InterviewHeader a where a.interviewEvent.id = ?1 and a.cityId =?2")
    Integer countUserIdByCityId(Long p_InterviewEventId, Long p_CityId) throws DAOException;

    @Query("SELECT COUNT(b) FROM InterviewHeader a " +
            "JOIN a.interviewDetails b " +
            "WHERE a.interviewEvent.id = ?1 AND a.cityId =?2 AND b.interviewStatus =?3")
    Integer countDataCompleteByCityId(Long p_InterviewEventId, Long p_CityId, String p_InterviewStatus) throws DAOException;

    @Query(value = "select sum(total) from ( " +
            "select count(*) as total from lsi_core.trx_interview_header a " +
            "join lsi_core.trx_interview_detail b on a.id = b.interview_header_id " +
            "where a.interview_event_id = ?1 and a.city_id=?2 and b.interview_score >= ?3" +
            ") as detail", nativeQuery = true)
    Integer countDataValidByCityId(Long p_InterviewEventId, Long p_CityId, Integer p_InterviewScore) throws DAOException;

    @Query(value = "select sum(number_of_respondent) from lsi_core.trx_interview_header a  where a.interview_event_id = ?1 and a.city_id=?2", nativeQuery = true)
    Integer sumRespondentByCityId(Long p_InterviewEventId, Long p_CityId) throws DAOException;

    /*CITY*/
    @Query(value = "select distinct(a.district_id) from lsi_core.trx_interview_header a where interview_event_id = ?1 and a.city_id =?2 LIMIT ?3, ?4", nativeQuery = true)
    List<BigInteger> findDistrictByEvent(Long p_InterviewEventId, Long p_CityId, Integer p_PageSize, Integer p_Offset) throws DAOException;

    @Query(value = "select distinct(a.district_id) from lsi_core.trx_interview_header a where interview_event_id = ?1 and a.city_id =?2 ", nativeQuery = true)
    List<BigInteger> findDistrictByEvent(Long p_InterviewEventId, Long p_CityId) throws DAOException;

    @Query("SELECT COUNT(DISTINCT a.villageId) FROM InterviewHeader a where a.interviewEvent.id = ?1 and a.districtId =?2")
    Integer countVillageByDistrictId(Long p_InterviewEventId, Long p_DistrictId) throws DAOException;

    @Query("SELECT COUNT(DISTINCT a.userId) FROM InterviewHeader a where a.interviewEvent.id = ?1 and a.districtId =?2")
    Integer countUserIdByDistrictId(Long p_InterviewEventId, Long p_DistrictId) throws DAOException;

    @Query("SELECT COUNT(b) FROM InterviewHeader a " +
            "JOIN a.interviewDetails b " +
            "WHERE a.interviewEvent.id = ?1 AND a.districtId =?2 AND b.interviewStatus =?3")
    Integer countDataCompleteByDistrictId(Long p_InterviewEventId, Long p_DistrictId, String p_InterviewStatus) throws DAOException;

    @Query(value = "select sum(total) from ( " +
            "select count(*) as total from lsi_core.trx_interview_header a " +
            "join lsi_core.trx_interview_detail b on a.id = b.interview_header_id " +
            "where a.interview_event_id = ?1 and a.district_id=?2 and b.interview_score >= ?3" +
            ") as detail", nativeQuery = true)
    Integer countDataValidByDistrictId(Long p_InterviewEventId, Long p_DistrictId, Integer p_InterviewScore) throws DAOException;

    @Query(value = "select sum(number_of_respondent) from lsi_core.trx_interview_header a  where a.interview_event_id = ?1 and a.district_id=?2", nativeQuery = true)
    Integer sumRespondentByDistrictId(Long p_InterviewEventId, Long p_DistrictId) throws DAOException;

    /*DISTRICT*/
    @Query(value = "select distinct(a.village_id) from lsi_core.trx_interview_header a where interview_event_id = ?1 and a.district_id =?2 LIMIT ?3, ?4", nativeQuery = true)
    List<BigInteger> findVillageByEvent(Long p_InterviewEventId, Long p_DistrictId, Integer p_PageSize, Integer p_Offset) throws DAOException;

    @Query(value = "select distinct(a.village_id) from lsi_core.trx_interview_header a where interview_event_id = ?1 and a.district_id =?2 ", nativeQuery = true)
    List<BigInteger> findVillageByEvent(Long p_InterviewEventId, Long p_DistrictId) throws DAOException;


    @Query("SELECT COUNT(DISTINCT a.userId) FROM InterviewHeader a where a.interviewEvent.id = ?1 and a.villageId =?2")
    Integer countUserIdByVillageId(Long p_InterviewEventId, Long p_VillageId) throws DAOException;

    @Query("SELECT COUNT(b) FROM InterviewHeader a " +
            "JOIN a.interviewDetails b " +
            "WHERE a.interviewEvent.id = ?1 AND a.villageId =?2 AND b.interviewStatus =?3")
    Integer countDataCompleteByVillageId(Long p_InterviewEventId, Long p_VillageId, String p_InterviewStatus) throws DAOException;

    @Query(value = "select sum(total) from ( " +
            "select count(*) as total from lsi_core.trx_interview_header a " +
            "join lsi_core.trx_interview_detail b on a.id = b.interview_header_id " +
            "where a.interview_event_id = ?1 and a.village_id=?2 and b.interview_score >= ?3" +
            ") as detail", nativeQuery = true)
    Integer countDataValidByVillageId(Long p_InterviewEventId, Long p_VillageId, Integer p_InterviewScore) throws DAOException;

    @Query(value = "select sum(number_of_respondent) from lsi_core.trx_interview_header a  where a.interview_event_id = ?1 and a.village_id=?2", nativeQuery = true)
    Integer sumRespondentByVillageId(Long p_InterviewEventId, Long p_VillageId) throws DAOException;

    /*VILLAGE*/
    @Query(value = "select distinct(a.user_id) from lsi_core.trx_interview_header a where interview_event_id = ?1 and a.village_id =?2 LIMIT ?3, ?4", nativeQuery = true)
    List<BigInteger> findUserByEvent(Long p_InterviewEventId, Long p_VillageId, Integer p_PageSize, Integer p_Offset) throws DAOException;

    @Query(value = "select distinct(a.user_id) from lsi_core.trx_interview_header a where interview_event_id = ?1 and a.village_id =?2 ", nativeQuery = true)
    List<BigInteger> findUserByEvent(Long p_InterviewEventId, Long p_VillageId) throws DAOException;

    @Query("SELECT COUNT(b) FROM InterviewHeader a " +
            "JOIN a.interviewDetails b " +
            "WHERE a.interviewEvent.id = ?1 AND a.userId =?2 AND b.interviewStatus =?3")
    Integer countDataCompleteByUserId(Long p_InterviewEventId, Long p_UserId, String p_InterviewStatus) throws DAOException;

    @Query(value = "select sum(total) from ( " +
            "select count(*) as total from lsi_core.trx_interview_header a " +
            "join lsi_core.trx_interview_detail b on a.id = b.interview_header_id " +
            "where a.interview_event_id = ?1 and a.user_id=?2 and b.interview_score >= ?3" +
            ") as detail", nativeQuery = true)
    Integer countDataValidByUserId(Long p_InterviewEventId, Long p_UserId, Integer p_InterviewScore) throws DAOException;

    @Query(value = "select sum(number_of_respondent) from lsi_core.trx_interview_header a  where a.interview_event_id = ?1 and a.user_id=?2", nativeQuery = true)
    Integer sumRespondentByUserId(Long p_InterviewEventId, Long p_UserId) throws DAOException;

    /*FILTER PROGRESS BY AREA 2 - BRANCH*/
    @Query(value = "select a.province_id from lsi_core.trx_interview_header a group by a.province_id, a.interview_event_id, a.branch_id having interview_event_id = ?1  and a.branch_id = ?2 LIMIT ?3, ?4", nativeQuery = true)
    List<BigInteger> findProvinceByEvent(Long p_InterviewEventId, Long p_BranchId, Integer p_PageSize, Integer p_Offset) throws DAOException;

    @Query(value = "select a.province_id from lsi_core.trx_interview_header a group by a.province_id, a.interview_event_id, a.branch_id having interview_event_id = ?1 and a.branch_id = ?2 ", nativeQuery = true)
    List<BigInteger> findProvinceByEvent(Long p_InterviewEventId, Long p_Branch) throws DAOException;

    @Query("SELECT COUNT(DISTINCT a.cityId) FROM InterviewHeader a where a.interviewEvent.id = ?1 and a.provinceId =?2 and a.branchId = ?3")
    Integer countCityByProvinceId(Long p_InterviewEventId, Long p_ProvinceId, Long p_BranchId) throws DAOException;

    @Query("SELECT COUNT(DISTINCT a.districtId) FROM InterviewHeader a where a.interviewEvent.id = ?1 and a.provinceId =?2 and a.branchId=?3")
    Integer countDistrictByProvinceId(Long p_InterviewEventId, Long p_ProvinceId, Long p_BranchId) throws DAOException;

    @Query("SELECT COUNT(DISTINCT a.villageId) FROM InterviewHeader a where a.interviewEvent.id = ?1 and a.provinceId =?2 and a.branchId =?3")
    Integer countVillageByProvinceId(Long p_InterviewEventId, Long p_ProvinceId, Long p_BranchId) throws DAOException;

    @Query("SELECT COUNT(DISTINCT a.userId) FROM InterviewHeader a where a.interviewEvent.id = ?1 and a.provinceId =?2 and a.branchId=?3")
    Integer countUserIdByProvinceId(Long p_InterviewEventId, Long p_ProvinceId, Long p_BranchId) throws DAOException;

    @Query("SELECT COUNT(b) FROM InterviewHeader a " +
            "JOIN a.interviewDetails b " +
            "WHERE a.interviewEvent.id = ?1 AND a.provinceId =?2 AND b.interviewStatus =?3 and a.branchId=?4")
    Integer countDataCompleteByProvinceId(Long p_InterviewEventId, Long p_ProvinceId, String p_InterviewStatus, Long p_BranchId) throws DAOException;

    @Query(value = "select sum(total) from ( " +
            "select count(*) as total from lsi_core.trx_interview_header a " +
            "join lsi_core.trx_interview_detail b on a.id = b.interview_header_id " +
            "where a.interview_event_id = ?1 and a.province_id=?2 and b.interview_score >= ?3 and a.branch_id=?4" +
            ") as detail", nativeQuery = true)
    Integer countDataValidByProvinceId(Long p_InterviewEventId, Long p_ProvinceId, Integer p_InterviewScore, Long p_BranchId) throws DAOException;

    @Query(value = "select sum(number_of_respondent) from lsi_core.trx_interview_header a  where a.interview_event_id = ?1 and a.province_id=?2 and a.branch_id = ?3", nativeQuery = true)
    Integer sumRespondentByProvinceId(Long p_InterviewEventId, Long p_ProvinceId, Long p_BranchId) throws DAOException;

    /*PROVINCE*/
    @Query(value = "select distinct(a.city_id) from lsi_core.trx_interview_header a where interview_event_id = ?1 and a.province_id=?2 and a.branch_id = ?3 LIMIT ?4, ?5", nativeQuery = true)
    List<BigInteger> findCityByEvent(Long p_InterviewEventId, Long p_ProvinceId, Long p_BranchId, Integer p_PageSize, Integer p_Offset) throws DAOException;

    @Query(value = "select distinct(a.city_id) from lsi_core.trx_interview_header a where interview_event_id = ?1 and a.province_id=?2 and a.branch_id = ?3", nativeQuery = true)
    List<BigInteger> findCityByEvent(Long p_InterviewEventId, Long p_ProvinceId, Long p_BranchId) throws DAOException;

    @Query("SELECT COUNT(DISTINCT a.districtId) FROM InterviewHeader a where a.interviewEvent.id = ?1 and a.cityId =?2 and a.branchId = ?3")
    Integer countDistrictByCityId(Long p_InterviewEventId, Long p_CityId, Long p_BranchId) throws DAOException;

    @Query("SELECT COUNT(DISTINCT a.villageId) FROM InterviewHeader a where a.interviewEvent.id = ?1 and a.cityId =?2 and a.branchId = ?3")
    Integer countVillageByCityId(Long p_InterviewEventId, Long p_CityId, Long p_BranchId) throws DAOException;

    @Query("SELECT COUNT(DISTINCT a.userId) FROM InterviewHeader a where a.interviewEvent.id = ?1 and a.cityId =?2 and a.branchId = ?3")
    Integer countUserIdByCityId(Long p_InterviewEventId, Long p_CityId, Long p_BranchId) throws DAOException;

    @Query("SELECT COUNT(b) FROM InterviewHeader a " +
            "JOIN a.interviewDetails b " +
            "WHERE a.interviewEvent.id = ?1 AND a.cityId =?2 AND b.interviewStatus =?3 and a.branchId = ?4")
    Integer countDataCompleteByCityId(Long p_InterviewEventId, Long p_CityId, String p_InterviewStatus, Long p_BranchId) throws DAOException;

    @Query(value = "select sum(total) from ( " +
            "select count(*) as total from lsi_core.trx_interview_header a " +
            "join lsi_core.trx_interview_detail b on a.id = b.interview_header_id " +
            "where a.interview_event_id = ?1 and a.city_id=?2 and b.interview_score >= ?3 and a.branch_id = ?4" +
            ") as detail", nativeQuery = true)
    Integer countDataValidByCityId(Long p_InterviewEventId, Long p_CityId, Integer p_InterviewScore, Long p_BranchId) throws DAOException;

    @Query(value = "select sum(number_of_respondent) from lsi_core.trx_interview_header a  where a.interview_event_id = ?1 and a.city_id=?2 and a.branch_id=?3", nativeQuery = true)
    Integer sumRespondentByCityId(Long p_InterviewEventId, Long p_CityId, Long p_BranchId) throws DAOException;

    /*CITY*/
    @Query(value = "select distinct(a.district_id) from lsi_core.trx_interview_header a where interview_event_id = ?1 and a.city_id =?2 and a.branch_id = ?3 LIMIT ?4, ?5", nativeQuery = true)
    List<BigInteger> findDistrictByEvent(Long p_InterviewEventId, Long p_CityId, Long p_BranchId, Integer p_PageSize, Integer p_Offset) throws DAOException;

    @Query(value = "select distinct(a.district_id) from lsi_core.trx_interview_header a where interview_event_id = ?1 and a.city_id =?2 and a.branch_id = ?3", nativeQuery = true)
    List<BigInteger> findDistrictByEvent(Long p_InterviewEventId, Long p_CityId, Long p_BranchId) throws DAOException;

    @Query("SELECT COUNT(DISTINCT a.villageId) FROM InterviewHeader a where a.interviewEvent.id = ?1 and a.districtId =?2 and a.branchId = ?3")
    Integer countVillageByDistrictId(Long p_InterviewEventId, Long p_DistrictId, Long p_BranchId) throws DAOException;

    @Query("SELECT COUNT(DISTINCT a.userId) FROM InterviewHeader a where a.interviewEvent.id = ?1 and a.districtId =?2 and a.branchId = ?3")
    Integer countUserIdByDistrictId(Long p_InterviewEventId, Long p_DistrictId, Long p_BranchId) throws DAOException;

    @Query("SELECT COUNT(b) FROM InterviewHeader a " +
            "JOIN a.interviewDetails b " +
            "WHERE a.interviewEvent.id = ?1 AND a.districtId =?2 AND b.interviewStatus =?3 and a.branchId = ?4")
    Integer countDataCompleteByDistrictId(Long p_InterviewEventId, Long p_DistrictId, String p_InterviewStatus, Long p_BranchId) throws DAOException;

    @Query(value = "select sum(total) from ( " +
            "select count(*) as total from lsi_core.trx_interview_header a " +
            "join lsi_core.trx_interview_detail b on a.id = b.interview_header_id " +
            "where a.interview_event_id = ?1 and a.district_id=?2 and b.interview_score >= ?3 and a.branch_id = ?4" +
            ") as detail", nativeQuery = true)
    Integer countDataValidByDistrictId(Long p_InterviewEventId, Long p_DistrictId, Integer p_InterviewScore, Long p_BranchId) throws DAOException;

    @Query(value = "select sum(number_of_respondent) from lsi_core.trx_interview_header a  where a.interview_event_id = ?1 and a.district_id=?2 and a.branch_id = ?3", nativeQuery = true)
    Integer sumRespondentByDistrictId(Long p_InterviewEventId, Long p_DistrictId, Long p_BranchId) throws DAOException;


    /*DISTRICT*/
    @Query(value = "select distinct(a.village_id) from lsi_core.trx_interview_header a where interview_event_id = ?1 and a.district_id =?2 and a.branch_id = ?3 LIMIT ?4, ?5", nativeQuery = true)
    List<BigInteger> findVillageByEvent(Long p_InterviewEventId, Long p_DistrictId, Long p_BranchId, Integer p_PageSize, Integer p_Offset) throws DAOException;

    @Query(value = "select distinct(a.village_id) from lsi_core.trx_interview_header a where interview_event_id = ?1 and a.district_id =?2 and a.branch_id = ?3 ", nativeQuery = true)
    List<BigInteger> findVillageByEvent(Long p_InterviewEventId, Long p_DistrictId, Long p_BranchId) throws DAOException;


    @Query("SELECT COUNT(DISTINCT a.userId) FROM InterviewHeader a where a.interviewEvent.id = ?1 and a.villageId =?2 and a.branchId = ?3")
    Integer countUserIdByVillageId(Long p_InterviewEventId, Long p_VillageId, Long p_BranchId) throws DAOException;

    @Query("SELECT COUNT(b) FROM InterviewHeader a " +
            "JOIN a.interviewDetails b " +
            "WHERE a.interviewEvent.id = ?1 AND a.villageId =?2 AND b.interviewStatus =?3 and a.branchId = ?4")
    Integer countDataCompleteByVillageId(Long p_InterviewEventId, Long p_VillageId, String p_InterviewStatus, Long p_BranchId) throws DAOException;

    @Query(value = "select sum(total) from ( " +
            "select count(*) as total from lsi_core.trx_interview_header a " +
            "join lsi_core.trx_interview_detail b on a.id = b.interview_header_id " +
            "where a.interview_event_id = ?1 and a.village_id=?2 and b.interview_score >= ?3 and a.branch_id = ?4" +
            ") as detail", nativeQuery = true)
    Integer countDataValidByVillageId(Long p_InterviewEventId, Long p_VillageId, Integer p_InterviewScore, Long p_BranchId) throws DAOException;

    @Query(value = "select sum(number_of_respondent) from lsi_core.trx_interview_header a  where a.interview_event_id = ?1 and a.village_id=?2 and a.branch_id = ?3", nativeQuery = true)
    Integer sumRespondentByVillageId(Long p_InterviewEventId, Long p_VillageId, Long p_BranchId) throws DAOException;

    /*VILLAGE*/
    @Query(value = "select distinct(a.user_id) from lsi_core.trx_interview_header a where interview_event_id = ?1 and a.village_id =?2 and a.branch_id = ?3 LIMIT ?4, ?5", nativeQuery = true)
    List<BigInteger> findUserByEvent(Long p_InterviewEventId, Long p_VillageId, Long p_BranchId, Integer p_PageSize, Integer p_Offset) throws DAOException;

    @Query(value = "select distinct(a.user_id) from lsi_core.trx_interview_header a where interview_event_id = ?1 and a.village_id =?2 and a.branch_id = ?3", nativeQuery = true)
    List<BigInteger> findUserByEvent(Long p_InterviewEventId, Long p_VillageId, Long p_BranchId) throws DAOException;

    @Query("SELECT COUNT(b) FROM InterviewHeader a " +
            "JOIN a.interviewDetails b " +
            "WHERE a.interviewEvent.id = ?1 AND a.userId =?2 AND b.interviewStatus =?3 and a.branchId = ?4")
    Integer countDataCompleteByUserId(Long p_InterviewEventId, Long p_UserId, String p_InterviewStatus, Long p_BranchId) throws DAOException;

    @Query(value = "select sum(total) from ( " +
            "select count(*) as total from lsi_core.trx_interview_header a " +
            "join lsi_core.trx_interview_detail b on a.id = b.interview_header_id " +
            "where a.interview_event_id = ?1 and a.user_id=?2 and b.interview_score >= ?3 and a.branch_id = ?4" +
            ") as detail", nativeQuery = true)
    Integer countDataValidByUserId(Long p_InterviewEventId, Long p_UserId, Integer p_InterviewScore, Long p_BranchId) throws DAOException;

    @Query(value = "select sum(number_of_respondent) from lsi_core.trx_interview_header a  where a.interview_event_id = ?1 and a.user_id=?2 and a.branch_id = ?3", nativeQuery = true)
    Integer sumRespondentByUserId(Long p_InterviewEventId, Long p_UserId, Long p_BranchId) throws DAOException;
}
