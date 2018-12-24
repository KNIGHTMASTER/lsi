package com.wissensalt.acl.endpoint.address.impl;

import com.wissensalt.acl.endpoint.address.IAddressEndPoint;
import com.wissensalt.scaffolding.dto.request.RequestAddressDTO;
import com.wissensalt.scaffolding.integration.AScaffoldingEndPoint;
import com.wissensalt.scaffolding.service.address.IAddressService;
import com.wissensalt.shared.dto.response.master.ResponseAddressDTO;
import com.wissensalt.shared.entity.mapper.master.AddressMapper;
import com.wissensalt.shared.entity.master.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created on 5/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class AddressEndPointImpl extends AScaffoldingEndPoint<Address, RequestAddressDTO, ResponseAddressDTO> implements IAddressEndPoint {

    @Autowired
    private IAddressService addressService;

    @Autowired
    private AddressMapper addressMapper;

    @PostConstruct
    @Override
    public void initEndPoint() {
        scaffoldingService = addressService;
        dataMapperIntegration = addressMapper;
    }
}
