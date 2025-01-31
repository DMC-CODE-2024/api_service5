package com.gl.mdr.repo.rep;


import com.gl.mdr.model.rep.AndroidUninstallEvents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AndroidUninstallEventsRepository extends JpaRepository<AndroidUninstallEvents, Integer> {
}

