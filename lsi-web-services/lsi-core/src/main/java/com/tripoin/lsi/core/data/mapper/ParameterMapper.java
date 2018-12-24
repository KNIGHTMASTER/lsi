package com.tripoin.lsi.core.data.mapper;

import com.tripoin.lsi.core.data.dto.response.ResponseParameterDTO;
import com.tripoin.lsi.core.data.model.Parameter;
import com.wissensalt.shared.entity.mapper.ADATAMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 9/12/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class ParameterMapper extends ADATAMapper<Parameter, ResponseParameterDTO> {

    @Autowired
    private ParameterGroupMapper parameterGroupMapper;

    @Override
    public ResponseParameterDTO convert(Parameter parameter) {
        ResponseParameterDTO result = new ResponseParameterDTO();
        result.setId(parameter.getId());
        result.setCode(parameter.getCode());
        result.setName(parameter.getName());
        result.setRemarks(parameter.getRemarks());
        result.setStatus(parameter.getStatus());
        if (parameter.getParameterGroup() != null) {
            result.setParameterGroup(parameterGroupMapper.convert(parameter.getParameterGroup()));
        }
        return result;
    }
}
