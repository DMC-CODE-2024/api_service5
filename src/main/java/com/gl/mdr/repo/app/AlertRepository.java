package com.gl.mdr.repo.app;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gl.mdr.model.app.AlertMessages;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<AlertMessages, Integer> {

	public AlertMessages findByAlertId(String alertId);
}
