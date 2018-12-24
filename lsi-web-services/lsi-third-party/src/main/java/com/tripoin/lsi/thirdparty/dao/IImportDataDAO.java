package com.tripoin.lsi.thirdparty.dao;

import com.tripoin.lsi.thirdparty.data.model.ImportData;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created on 9/11/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IImportDataDAO extends JpaRepository<ImportData, Long> {
}
