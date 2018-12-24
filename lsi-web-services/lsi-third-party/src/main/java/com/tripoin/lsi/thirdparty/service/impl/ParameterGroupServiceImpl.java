package com.tripoin.lsi.thirdparty.service.impl;

import com.tripoin.lsi.thirdparty.dao.IParameterGroupDAO;
import com.tripoin.lsi.thirdparty.data.dto.request.RequestParameterGroupDTO;
import com.tripoin.lsi.thirdparty.data.model.ParameterGroup;
import com.tripoin.lsi.thirdparty.service.IParameterGroupService;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.AScaffoldingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created on 9/12/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class ParameterGroupServiceImpl extends AScaffoldingService<ParameterGroup, RequestParameterGroupDTO> implements IParameterGroupService {

    @Autowired
    private IParameterGroupDAO parameterGroupDAO;

    @PostConstruct
    @Override
    public void initService() {
        scaffoldingDAO = parameterGroupDAO;
    }


    @Override
    public ParameterGroup generateEntityForCommonTrx(RequestParameterGroupDTO requestParameterGroupDTO) throws ServiceException {
        ParameterGroup result = new ParameterGroup();
        result = matchBaseFields(result, requestParameterGroupDTO);
        return result;
    }
}
