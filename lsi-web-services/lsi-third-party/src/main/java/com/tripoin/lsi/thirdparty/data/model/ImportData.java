package com.tripoin.lsi.thirdparty.data.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created on 9/11/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
@Entity
@Table(name = "trx_import_data")
public class ImportData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "upload_start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadStartDate;

    @Column(name = "upload_end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadEndDate;

    @Column(name = "upload_status")
    private Integer uploadStatus;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "import_start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date importStartDate;

    @Column(name = "import_end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date importEndDate;

    @Column(name = "import_status")
    private Integer importStatus;
}
