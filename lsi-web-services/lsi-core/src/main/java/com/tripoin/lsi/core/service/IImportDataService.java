package com.tripoin.lsi.core.service;

import com.tripoin.lsi.core.data.model.ImportData;
import com.wissensalt.scaffolding.exception.ServiceException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created on 9/11/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IImportDataService {

    @Transactional
    void insert(ImportData p_ImportData) throws ServiceException;

    @Transactional
    void insert(List<ImportData> p_ImportDatas) throws ServiceException;

    @Modifying
    @Transactional
    void update(ImportData p_ImportData, Long p_Id) throws ServiceException;
}
