package com.tripoin.lsi.thirdparty.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Created on 10/6/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public class PaginationUtil<RESPONSE_DATA> {

    public Page makePagination(List<RESPONSE_DATA> source, PageRequest p_PageRequest) {
        int start = p_PageRequest.getOffset();
        int end = (start + p_PageRequest.getPageSize()) > source.size() ? source.size() : (start + p_PageRequest.getPageSize());
        return new PageImpl<>(source.subList(start, end), p_PageRequest, source.size());
    }
}
