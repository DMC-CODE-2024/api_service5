package com.gl.mdr.repo.app;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gl.mdr.model.app.AlertMessages;

@Repository
public interface AlertRepository extends JpaRepository<AlertMessages, Integer> {

	public AlertMessages findByAlertIdIn(String alertId);
}
