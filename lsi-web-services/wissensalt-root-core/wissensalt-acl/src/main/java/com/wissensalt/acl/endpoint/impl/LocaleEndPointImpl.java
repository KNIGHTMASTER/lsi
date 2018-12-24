package com.wissensalt.acl.endpoint.impl;

import com.wissensalt.acl.endpoint.ILocaleEndPoint;
import com.wissensalt.scaffolding.dto.request.RequestLocaleDTO;
import com.wissensalt.scaffolding.integration.AScaffoldingEndPoint;
import com.wissensalt.scaffolding.service.core.ILocaleService;
import com.wissensalt.shared.dto.response.master.ResponseLocaleDTO;
import com.wissensalt.shared.entity.mapper.master.LocaleMapper;
import com.wissensalt.shared.entity.master.I18NLocale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created on 8/20/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class LocaleEndPointImpl extends AScaffoldingEndPoint<I18NLocale, RequestLocaleDTO, ResponseLocaleDTO> implements ILocaleEndPoint {

    @Autowired
    private ILocaleService localeService;

    @Autowired
    private LocaleMapper localeMapper;

    @PostConstruct
    @Override
    public void initEndPoint() {
        scaffoldingService = localeService;
        dataMapperIntegration = localeMapper;
    }
}
