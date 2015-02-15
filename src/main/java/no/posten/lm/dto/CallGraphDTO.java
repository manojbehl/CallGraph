package no.posten.lm.dto;

import java.util.HashSet;
import java.util.Set;

import no.posten.lm.domain.CallGraph;

public class CallGraphDTO {

	private String id;
	String programOverview;
	String input;
	String output;
	String remarks;
	String routineName;
	String frequency;
	String parent="0";
	boolean isCodeCopy;
	boolean isJCL =false;
	boolean isREXX = false;
	int level;
	boolean loaded = true;
	boolean expanded = true;
	private Set<CallGraphDTO> childCallGraph = new HashSet<CallGraphDTO>();
	
	String isLeaf = "false";
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getRoutineName() {
		return routineName;
	}
	public void setRoutineName(String routineName) {
		this.routineName = routineName;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public boolean isCodeCopy() {
		return isCodeCopy;
	}
	public void setCodeCopy(boolean isCodeCopy) {
		this.isCodeCopy = isCodeCopy;
	}
	public boolean isJCL() {
		return isJCL;
	}
	public void setJCL(boolean isJCL) {
		this.isJCL = isJCL;
	}
	public boolean isREXX() {
		return isREXX;
	}
	public void setREXX(boolean isREXX) {
		this.isREXX = isREXX;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	
	
	public boolean isLoaded() {
		return loaded;
	}
	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		CallGraphDTO callGraphDTO = (CallGraphDTO)obj;
		return this.getId().equalsIgnoreCase(callGraphDTO.getId());
	}
	public Set<CallGraphDTO> getChildCallGraph() {
		return childCallGraph;
	}
	public void setChildCallGraph(Set<CallGraphDTO> childCallGraph) {
		this.childCallGraph = childCallGraph;
	}
	public String getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
	
	
}
