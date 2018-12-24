package com.tripoin.lsi.core.data.dto.excel;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created on 9/27/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class AgentImportDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = -6092007172043959339L;

    String[] contents;
}
