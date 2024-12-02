package com.gl.mdr.model.RedmineParser;
public class Status{
    public int id;
    public String name;
    public boolean is_closed;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isIs_closed() {
		return is_closed;
	}
	public void setIs_closed(boolean is_closed) {
		this.is_closed = is_closed;
	}
    
}

