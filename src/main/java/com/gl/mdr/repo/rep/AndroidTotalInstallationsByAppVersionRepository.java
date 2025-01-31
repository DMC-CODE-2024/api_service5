package com.gl.mdr.repo.rep;


import com.gl.mdr.model.rep.AndroidTotalInstallationsByAppVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AndroidTotalInstallationsByAppVersionRepository extends JpaRepository<AndroidTotalInstallationsByAppVersion, Integer> {
}

