package no.posten.lm.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.util.Hash;

public class CallGraph {

	
	private String id;
	String programOverview;
	String input;
	String output;
	String remarks;
	String routineName;
	String frequency;
	boolean isCodeCopy;
	boolean isJCL =false;
	boolean isREXX = false;

	@Transient
	Map<String, CallGraph> childRoutine = new HashMap<String, CallGraph>();
	
	
	private Set<CallGraph> childCallGraph = new HashSet<CallGraph>();
	
	public Set<CallGraph> getChildCallGraph() {
		return childCallGraph;
	}

	public void setChildCallGraph(Set<CallGraph> childCallGraph) {
		this.childCallGraph = childCallGraph;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getProgramOverview() {
		return programOverview;
	}

	public void setProgramOverview(String programOverview) {
		this.programOverview = programOverview;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	
	public boolean isREXX() {
		return isREXX;
	}

	public void setREXX(boolean isREXX) {
		this.isREXX = isREXX;
	}

	public boolean isJCL() {
		return isJCL;
	}

	public void setJCL(boolean isJCL) {
		this.isJCL = isJCL;
	}

	public boolean isCodeCopy() {
		return isCodeCopy;
	}

	public void setCodeCopy(boolean isCodeCopy) {
		this.isCodeCopy = isCodeCopy;
	}

	public String getRoutineName() {
		return routineName;
	}

	public void setRoutineName(String routineName) {
		this.routineName = routineName;
	}


	

	public Map<String, CallGraph> getChildRoutine() {
		return childRoutine;
	}

	public void setChildRoutine(Map<String, CallGraph> childRoutine) {
		this.childRoutine = childRoutine;
	}
	
}