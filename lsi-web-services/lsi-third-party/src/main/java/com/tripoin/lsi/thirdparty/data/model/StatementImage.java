package com.tripoin.lsi.thirdparty.data.model;

import com.wissensalt.shared.entity.AAuditTrail;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
@Entity
@Table(name = "mst_statement_image")
public class StatementImage extends AAuditTrail {
    /**
     *
     *
     */
    private static final long serialVersionUID = 8008045166728640486L;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_component_id")
    private InterviewComponent interviewComponent;
}
