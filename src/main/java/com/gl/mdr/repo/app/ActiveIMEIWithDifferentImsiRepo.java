package com.gl.mdr.repo.app;

import com.gl.mdr.model.app.ActiveIMEIWithDifferentImsi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActiveIMEIWithDifferentImsiRepo extends JpaRepository<ActiveIMEIWithDifferentImsi, Long> {
    public List<ActiveIMEIWithDifferentImsi> findTop1ByImei(String imei);
    public List<ActiveIMEIWithDifferentImsi> findByImei(String imei);
}