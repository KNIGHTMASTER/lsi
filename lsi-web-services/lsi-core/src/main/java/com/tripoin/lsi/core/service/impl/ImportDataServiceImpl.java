package com.tripoin.lsi.core.service.impl;

import com.tripoin.lsi.core.dao.IImportDataDAO;
import com.tripoin.lsi.core.data.model.ImportData;
import com.tripoin.lsi.core.service.IImportDataService;
import com.wissensalt.scaffolding.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 9/11/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class ImportDataServiceImpl implements IImportDataService {

    @Autowired
    private IImportDataDAO importDataDAO;

    @Override
    public void insert(ImportData p_ImportData) throws ServiceException {
        importDataDAO.save(p_ImportData);
    }

    @Override
    public void insert(List<ImportData> p_ImportDatas) throws ServiceException {
        for (ImportData importData : p_ImportDatas) {
            insert(importData);
        }
    }

    @Override
    public void update(ImportData p_ImportData, Long p_Id) throws ServiceException {
        ImportData dataToUpdate = importDataDAO.findOne(p_Id);
        if (dataToUpdate != null) {
            dataToUpdate = p_ImportData;
            importDataDAO.save(dataToUpdate);
        }
    }
}
