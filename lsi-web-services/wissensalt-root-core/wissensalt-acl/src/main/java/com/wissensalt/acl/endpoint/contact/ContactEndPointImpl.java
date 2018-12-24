package com.wissensalt.acl.endpoint.contact;

import com.wissensalt.scaffolding.dto.request.RequestContactDTO;
import com.wissensalt.scaffolding.integration.AScaffoldingEndPoint;
import com.wissensalt.scaffolding.service.contact.IContactService;
import com.wissensalt.shared.dto.response.master.ResponseContactDTO;
import com.wissensalt.shared.entity.mapper.master.ContactMapper;
import com.wissensalt.shared.entity.master.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created on 5/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class ContactEndPointImpl extends AScaffoldingEndPoint<Contact, RequestContactDTO, ResponseContactDTO> implements IContactEndPoint {

    @Autowired
    private IContactService contactService;

    @Autowired
    private ContactMapper contactMapper;

    @PostConstruct
    @Override
    public void initEndPoint() {
        scaffoldingService = contactService;
        dataMapperIntegration = contactMapper;
    }
}
