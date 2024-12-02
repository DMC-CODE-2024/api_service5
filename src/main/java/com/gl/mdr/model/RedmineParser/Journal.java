package com.gl.mdr.model.RedmineParser;

import java.util.ArrayList;
import java.util.Date;

public class Journal{
    public int id;
    public User user;
    public String notes;
    public Date created_on;
    public boolean private_notes;
    public ArrayList<Detail> details;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Date getCreated_on() {
		return created_on;
	}
	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}
	public boolean isPrivate_notes() {
		return private_notes;
	}
	public void setPrivate_notes(boolean private_notes) {
		this.private_notes = private_notes;
	}
	public ArrayList<Detail> getDetails() {
		return details;
	}
	public void setDetails(ArrayList<Detail> details) {
		this.details = details;
	}
    
    
}

