package com.gl.mdr.repo.app;

import com.gl.mdr.model.app.ExceptionListHis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ExceptionListHistoryRepo extends JpaRepository<ExceptionListHis, Long>, JpaSpecificationExecutor<ExceptionListHis> {


    List<ExceptionListHis> findByImei(String imei);

    List<ExceptionListHis> findTop1ByImei(String imei);
}
