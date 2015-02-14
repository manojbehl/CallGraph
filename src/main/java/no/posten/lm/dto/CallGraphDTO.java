package no.posten.lm.dto;

public class CallGraphDTO {

	private String id;
	String programOverview;
	String input;
	String output;
	String remarks;
	String routineName;
	String frequency;
	String parent_id;
	boolean isCodeCopy;
	boolean isJCL =false;
	boolean isREXX = false;
	int level;
	boolean loaded = true;
	boolean expanded = true;
	
	boolean isLeaf = false;
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
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	
	public boolean isLeaf() {
		return isLeaf;
	}
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
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
	
	
}
