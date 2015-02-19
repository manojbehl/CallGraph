package no.posten.lm.dto;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import no.posten.lm.domain.CallGraph;

public class CallGraphDTO implements Comparable<CallGraphDTO>{

	private String id;
	String programOverview;
	String input;
	String output;
	String remarks;
	String routineName;
	String frequency;
	String parent="";
	boolean isCodeCopy;
	boolean isJCL =false;
	boolean isREXX = false;
	int level;
	boolean loaded = true;
	boolean expanded = true;
	
	private int page;
	private int max;
	private int total;
	
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
	public int compareTo(CallGraphDTO o) {
		// TODO Auto-generated method stub
//		Integer first = Integer.parseInt(this.getId());
//		Integer second = Integer.parseInt(o.getId());
//		if(first < second)
//			return -1;
//		else if (first > second)
//			return 1;
//		else 
//			return 0;
		return this.getId().compareTo(o.getId());
		}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	public String createTreeResponse(){
		StringBuffer sb= new StringBuffer();
		sb.append("{\"response\": [");
		populateTreeData(this, sb);
		sb.append("]}");
		return sb.toString();
	}
	
	private String populateTreeData(CallGraphDTO mainObject,StringBuffer sb){
		
		loadEntries(sb, mainObject);
		Collection<CallGraphDTO> callGraphDTOs = mainObject.getChildCallGraph();
		for (Iterator iterator = callGraphDTOs.iterator(); iterator.hasNext();) {
			CallGraphDTO type = (CallGraphDTO) iterator.next();
			populateTreeData(type, sb);
			
		} ;
		return sb.toString();
	}
	
	private void loadEntries(StringBuffer sb, CallGraphDTO mainObject){
		sb.append("{");
		sb.append("\"id\":\""+mainObject.getId()+"\",");
		sb.append("\"routineName\":\""+mainObject.getRoutineName()+"\",");
		sb.append("\"frequency\":\""+(mainObject.getFrequency()==null?"":mainObject.getFrequency())+"\",");
		sb.append("\"input\":\""+(mainObject.getInput()==null?"":mainObject.getInput())+"\",");
		sb.append("\"output\":\""+(mainObject.getOutput()==null?"":mainObject.getOutput())+"\",");
		sb.append("\"remarks\":\""+(mainObject.getRemarks()==null?"":mainObject.getRemarks())+"\",");
		
		
		sb.append("level:\""+mainObject.getLevel()+"\",");
		sb.append("parent:\""+mainObject.getParent()+"\",");
		sb.append("isLeaf:"+mainObject.getIsLeaf().equalsIgnoreCase("true")+",");
		sb.append("expanded:"+mainObject.isExpanded()+",");
		sb.append("loaded:"+mainObject.isLoaded());
		sb.append("},");
	}
	
	
}
