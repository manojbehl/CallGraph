package no.posten.lm.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class CallGraph {

	public static final String PATH_SEPARATOR = ".";
	
	private String id;
	String programOverview;
	String parentRoutineName;
	String input;
	String output;
	String remarks;
	String routineName;
	String frequency;
	boolean isCodeCopy;
	boolean isJCL =false;
	boolean isREXX = false;
	@Field String path;
	@Field String parent="";
	int level;
	boolean loaded = true;
	boolean expanded = true;
	String isLeaf = "false";
	String type ;
	boolean subJCL = false;
	
	
	public String getParentRoutineName() {
		return parentRoutineName;
	}

	public void setParentRoutineName(String parentRoutineName) {
		this.parentRoutineName = parentRoutineName;
	}

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public CallGraph() {
		// TODO Auto-generated constructor stub
	}
	
	public CallGraph(int id){
		this.id = "" +id;
	}
	
	

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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
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

	public String getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
	
	@Override
	public String toString() {
		
		StringBuffer sb = new StringBuffer();
		boolean subJCL = false;
		for (Iterator iterator = this.getChildCallGraph().iterator(); iterator.hasNext();) {
			CallGraph callGraph = (CallGraph) iterator.next();
			if(callGraph.isSubJCL()){
				subJCL =true;
				break;
				
			}
		}
		if(subJCL){
			sb.append("\t");
		}else{
			sb.append("JCL\t");
		}
		sb.append(this.getRoutineName()+"\t");
		sb.append("\t\t\t");
		sb.append((this.getFrequency()==null?"":this.getFrequency())+"\t");
		sb.append((this.getInput()==null?"":this.getInput())+"\t");
		sb.append((this.getOutput()==null?"":this.getInput())+"\t");
		sb.append((this.getRemarks()==null?"":this.getRemarks())+"\t");
		sb.append("\n");
		addChildElements(this.getChildCallGraph(), sb);
		return sb.toString();
	}
	
	public void addChildElements(Collection<CallGraph> childCallGraphs,StringBuffer sb){
		for (Iterator iterator = childCallGraphs.iterator(); iterator.hasNext();) {
			CallGraph callGraph = (CallGraph) iterator.next();
			String[] strArray = callGraph.getPath().split("\\.");
			int length = strArray.length;
			for (int i = 0; i < length; i++) {
				sb.append("\t");
			}
			if(callGraph.isCodeCopy()){
				sb.append(callGraph.getRoutineName()+ "- Code Copy" +"\t");
			}
			else{
				sb.append(callGraph.getRoutineName() + "- Sub Program"+"\t");

			}
			sb.append("\n");
			addChildElements(callGraph.getChildCallGraph(), sb);
		}
	}

	public static void main(String[] args) {
		String str  = "1.2.3";
		System.err.println(str.split("\\.").length);
	}

	public boolean isSubJCL() {
		return subJCL;
	}

	public void setSubJCL(boolean subJCL) {
		this.subJCL = subJCL;
	}
	
}