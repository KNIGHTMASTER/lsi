package com.tripoin.lsi.thirdparty.data.model;

import com.wissensalt.shared.entity.AAuditTrail;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * Created on 8/2/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
@Entity
@Table(name = "sys_parameter_group")
public class ParameterGroup extends AAuditTrail {
    /**
     *
     *
     */
    private static final long serialVersionUID = -7525045679547568123L;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = false, mappedBy = "parameterGroup")
    private Set<Parameter> parameters;
}
