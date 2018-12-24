package com.tripoin.lsi.core.dao;

import com.tripoin.lsi.core.data.model.Parameter;
import com.wissensalt.scaffolding.dao.IScaffoldingDAO;
import com.wissensalt.scaffolding.exception.DAOException;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created on 9/12/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IParameterDAO extends IScaffoldingDAO<Parameter> {

    @Query("SELECT a.id, a.code, a.name FROM Parameter a JOIN a.parameterGroup b WHERE b.id = ?1")
    List<Parameter> selectLOVByGroup(Long p_GroupId) throws DAOException;

    List<Parameter> findByParameterGroup_Code(String p_Code) throws DAOException;
}
