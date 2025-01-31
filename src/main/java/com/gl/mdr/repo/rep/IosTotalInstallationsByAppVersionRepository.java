package com.gl.mdr.repo.rep;


import com.gl.mdr.model.rep.IosTotalInstallationsByAppVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IosTotalInstallationsByAppVersionRepository extends JpaRepository<IosTotalInstallationsByAppVersion, Integer> {
}

