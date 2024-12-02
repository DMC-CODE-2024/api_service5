package com.gl.mdr.repo.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gl.mdr.model.app.ContactNumberChange;

@Repository
public interface ContactNumberChangeRepository extends JpaRepository<ContactNumberChange, Long>{
	
	@SuppressWarnings("unchecked")
	ContactNumberChange save(ContactNumberChange contactNumberChange);
	public ContactNumberChange findByMsisdn(String msisdn);
}
