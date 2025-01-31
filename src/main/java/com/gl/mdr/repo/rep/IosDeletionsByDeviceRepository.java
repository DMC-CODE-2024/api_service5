package com.gl.mdr.repo.rep;


import com.gl.mdr.model.rep.IosDeletionsByDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IosDeletionsByDeviceRepository extends JpaRepository<IosDeletionsByDevice, Integer> {
}

