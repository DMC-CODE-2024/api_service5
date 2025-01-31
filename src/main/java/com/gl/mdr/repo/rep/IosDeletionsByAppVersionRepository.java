package com.gl.mdr.repo.rep;


import com.gl.mdr.model.rep.IosDeletionsByAppVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IosDeletionsByAppVersionRepository extends JpaRepository<IosDeletionsByAppVersion, Integer> {
}

