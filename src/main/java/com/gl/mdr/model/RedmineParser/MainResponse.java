
package com.gl.mdr.model.RedmineParser;


import org.springframework.stereotype.Component;

@Component
public class MainResponse{
    public Issue issue;

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}
    
    
}

