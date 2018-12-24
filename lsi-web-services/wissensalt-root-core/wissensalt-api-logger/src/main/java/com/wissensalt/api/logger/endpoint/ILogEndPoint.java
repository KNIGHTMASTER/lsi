package com.wissensalt.api.logger.endpoint;

import com.wissensalt.api.logger.exception.APILoggerEndPointException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created on 6/3/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Logging End Point", value = "API Logger", description = "Rest Web Service for Logging")
public interface ILogEndPoint<DATA> {

    @PostMapping("/insert")
    @ApiOperation(value = "Operation to insert Log Contents", response = Void.class)
    void insert(@RequestBody DATA p_DATA) throws APILoggerEndPointException;
}
