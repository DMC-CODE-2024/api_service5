package com.gl.mdr.model.RedmineParser;

import java.util.ArrayList;
import java.util.Date;

public class Issue{
    public int id;
    public Project project;
    public Tracker tracker;
    public Status status;
    public Priority priority;
    public Author author;
    public Object category;
    public String subject;
    public String description;
    public String start_date;
    public Object due_date;
    public int done_ratio;
    public Object estimated_hours;
    public Object total_estimated_hours;
    public double spent_hours;
    public double total_spent_hours;
    public Object custom_fields;
    public Date created_on;
    public Date updated_on;
    public Date closed_on;
    public ArrayList<Journal> journals;
    public Object uploads;
    public boolean is_private;
    public ArrayList<Attachment> attachments;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public Tracker getTracker() {
		return tracker;
	}
	public void setTracker(Tracker tracker) {
		this.tracker = tracker;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Priority getPriority() {
		return priority;
	}
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	public Object getCategory() {
		return category;
	}
	public void setCategory(Object category) {
		this.category = category;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public Object getDue_date() {
		return due_date;
	}
	public void setDue_date(Object due_date) {
		this.due_date = due_date;
	}
	public int getDone_ratio() {
		return done_ratio;
	}
	public void setDone_ratio(int done_ratio) {
		this.done_ratio = done_ratio;
	}
	public Object getEstimated_hours() {
		return estimated_hours;
	}
	public void setEstimated_hours(Object estimated_hours) {
		this.estimated_hours = estimated_hours;
	}
	public Object getTotal_estimated_hours() {
		return total_estimated_hours;
	}
	public void setTotal_estimated_hours(Object total_estimated_hours) {
		this.total_estimated_hours = total_estimated_hours;
	}
	public double getSpent_hours() {
		return spent_hours;
	}
	public void setSpent_hours(double spent_hours) {
		this.spent_hours = spent_hours;
	}
	public double getTotal_spent_hours() {
		return total_spent_hours;
	}
	public void setTotal_spent_hours(double total_spent_hours) {
		this.total_spent_hours = total_spent_hours;
	}
	public Object getCustom_fields() {
		return custom_fields;
	}
	public void setCustom_fields(Object custom_fields) {
		this.custom_fields = custom_fields;
	}
	public Date getCreated_on() {
		return created_on;
	}
	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}
	public Date getUpdated_on() {
		return updated_on;
	}
	public void setUpdated_on(Date updated_on) {
		this.updated_on = updated_on;
	}
	public Date getClosed_on() {
		return closed_on;
	}
	public void setClosed_on(Date closed_on) {
		this.closed_on = closed_on;
	}
	public ArrayList<Journal> getJournals() {
		return journals;
	}
	public void setJournals(ArrayList<Journal> journals) {
		this.journals = journals;
	}
	public Object getUploads() {
		return uploads;
	}
	public void setUploads(Object uploads) {
		this.uploads = uploads;
	}
	public boolean isIs_private() {
		return is_private;
	}
	public void setIs_private(boolean is_private) {
		this.is_private = is_private;
	}
	public ArrayList<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(ArrayList<Attachment> attachments) {
		this.attachments = attachments;
	}
    
    
}

