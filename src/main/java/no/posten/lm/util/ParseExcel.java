package no.posten.lm.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;




import no.posten.lm.domain.CallGraph;
import jxl.Cell;
import jxl.CellView;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class ParseExcel {
	
	private static ParseExcel parseExcel = null;
	private ParseExcel(){
		
	}
	
	
	public static ParseExcel getSharedInstance(){
		if(parseExcel  ==null){
			parseExcel = new ParseExcel();
		}
		
		return parseExcel;
	}
	
	
	private Set<String> parentCallGraph = null;
	
	public Map<String, Map<String,CallGraph>> getJCLToPGMList(Sheet sheet){
		Map<String, Map<String, CallGraph>>  hashMap = new TreeMap<String, Map<String,CallGraph>>();
		ArrayList<String> listOfChild = new ArrayList<String>();
		parentCallGraph = new TreeSet<String>();
		int rows =sheet.getRows();
		for (int i = 1; i < rows; i++) {
			Cell[] cellView = sheet.getRow(i);
			if(cellView.length < 2)
				continue;
			if(hashMap.containsKey(cellView[0].getContents())){
				Map<String, CallGraph> listOfCallGraphs= hashMap.get(cellView[0].getContents());
				
				if(!listOfCallGraphs.containsKey(cellView[1].getContents())){
					if(cellView[1].getContents().trim().length() > 0){
						CallGraph callGraph = new CallGraph();
						callGraph.setJCL(true);
						callGraph.setRoutineName(cellView[1].getContents());
						listOfCallGraphs.put(cellView[1].getContents(),callGraph);
						hashMap.put(cellView[0].getContents().trim().toString(), listOfCallGraphs);
						
					}
				}
				if(cellView.length > 2){
					if(!listOfCallGraphs.containsKey(cellView[2].getContents())){
						if(cellView[2].getContents().trim().length() > 0){
							CallGraph callGraph = new CallGraph();
							callGraph.setJCL(true);
							callGraph.setREXX(true);
							callGraph.setRoutineName(cellView[2].getContents());
							listOfCallGraphs.put(cellView[2].getContents(),callGraph);
							hashMap.put(cellView[0].getContents().trim().toString(), listOfCallGraphs);
							
						}
					}
					
				}
			}else{
				if(cellView[1].getContents() != null && cellView[1].getContents().trim().length() > 0){
					Map<String, CallGraph> listOfCallGraphs= new HashMap<String, CallGraph>();
					CallGraph callGraph = new CallGraph();
					callGraph.setJCL(true);
					callGraph.setRoutineName(cellView[1].getContents());
					listOfCallGraphs.put(cellView[1].getContents(),callGraph);
					hashMap.put(cellView[0].getContents(), listOfCallGraphs);
				}
				if(cellView.length > 2 && cellView[2].getContents() != null && cellView[2].getContents().trim().length() > 0){
					Map<String, CallGraph> listOfCallGraphs= new HashMap<String, CallGraph>();
					CallGraph callGraph = new CallGraph();
					callGraph.setJCL(true);
					callGraph.setREXX(true);
					callGraph.setRoutineName(cellView[2].getContents());
					listOfCallGraphs.put(cellView[2].getContents(),callGraph);
					hashMap.put(cellView[0].getContents(), listOfCallGraphs);
				}
				
			}
			parentCallGraph.add(cellView[1].getContents().trim().toString());
		}	
			
		return hashMap;
	}
	
	public Map<String, Map<String, CallGraph>> getPGM2PGMList(Sheet sheet, Map<String, Map<String, CallGraph>> hashMap){
		
		List<CallGraph> parentCallGraph = new ArrayList<CallGraph>();
		int rows =sheet.getRows();
		Map<String, Map<String, CallGraph>>  moreEntities = new TreeMap<String, Map<String,CallGraph>>();
		
		for (int i = 1; i < rows; i++) {
			
			Cell[] cellView = sheet.getRow(i);
			if(cellView.length < 2){
				Cell[] cellView1 = new Cell[3];
				cellView1[0] = cellView[0];
				cellView = cellView1;
			} else 	if(cellView.length < 3){
				Cell[] cellView1 = new Cell[3];
				cellView1[0] = cellView[0];
				cellView1[1] = cellView[1];
				
				cellView = cellView1;
				
			}
			Collection<Map<String, CallGraph>> collection = hashMap.values();
			for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
				Map<String, CallGraph> JCL2PGMMAP = (Map<String, CallGraph>) iterator
						.next();
				if(JCL2PGMMAP.containsKey(cellView[0].getContents().trim().toString())){
					CallGraph callGraph = JCL2PGMMAP.get(cellView[0].getContents().trim().toString()) ;
					
					if(cellView[1] != null && cellView[1].getContents().trim().length() > 0 &&
							!callGraph.getChildRoutine().containsKey(cellView[1].getContents().trim().toString())){
						CallGraph innerCallGraph = new CallGraph();
						innerCallGraph.setRoutineName(cellView[1].getContents());
						callGraph.getChildRoutine().put(cellView[1].getContents(), innerCallGraph);
					}
					if(cellView[2] != null && cellView[2].getContents().trim().length() > 0 && 
							!callGraph.getChildRoutine().containsKey(cellView[2].getContents().trim().toString())){
						CallGraph innerCallGraph = new CallGraph();
						innerCallGraph.setCodeCopy(true);
						innerCallGraph.setRoutineName(cellView[2].getContents());
						callGraph.getChildRoutine().put(cellView[2].getContents(), innerCallGraph);
					}
				}
					if(!this.parentCallGraph.contains(cellView[0].getContents().trim().toString())){
						CallGraph callGraph = new CallGraph();
						callGraph.setRoutineName(cellView[0].getContents().trim().toString());
						
						CallGraph innerSubCallGraph = new CallGraph();
						innerSubCallGraph .setRoutineName(cellView[0].getContents().trim().toString());
						
						callGraph.getChildRoutine().put(innerSubCallGraph.getRoutineName(), innerSubCallGraph);
						
						Map<String, CallGraph> mapForChildRoutineOuter =  null;
						Map<String, CallGraph> mapForChildRoutine =  null;
						if(moreEntities.get(cellView[0].getContents().trim().toString()) != null){
							mapForChildRoutineOuter  = moreEntities.get(cellView[0].getContents().trim().toString());
							mapForChildRoutine = mapForChildRoutineOuter.get(innerSubCallGraph.getRoutineName()).getChildRoutine();
							innerSubCallGraph.setChildRoutine(mapForChildRoutine);
						}else{
							mapForChildRoutine = new HashMap<String, CallGraph>();
							innerSubCallGraph.setChildRoutine(mapForChildRoutine);
						}
						Map<String, CallGraph> listOfCallGraphs= new HashMap<String, CallGraph>();
						
						
						
						if(cellView[1] != null && cellView[1].getContents().trim().length() > 0 &&
								!callGraph.getChildRoutine().containsKey(cellView[1].getContents().trim().toString())){
							CallGraph innerCallGraph = new CallGraph();
							innerCallGraph.setRoutineName(cellView[1].getContents());
							mapForChildRoutine.put(cellView[1].getContents().trim().toString(), innerCallGraph);
							
						}
						if(cellView[2] != null && cellView[2].getContents().trim().length() > 0 && 
								!callGraph.getChildRoutine().containsKey(cellView[2].getContents().trim().toString())){
							CallGraph innerCallGraph = new CallGraph();
							innerCallGraph.setCodeCopy(true);
							innerCallGraph.setRoutineName(cellView[2].getContents());
							mapForChildRoutine.put(cellView[2].getContents(), innerCallGraph);
						}
						
						moreEntities.put(cellView[0].getContents().trim().toString(),callGraph.getChildRoutine());
					}
					
				}
		}	
			hashMap.putAll(moreEntities);
			return hashMap;
	}
	
	
	public Map<String, Map<String, CallGraph>>  getCPYtoPGMList(Sheet sheet, Map<String, Map<String, CallGraph>> hashMap){
		int rows =sheet.getRows();
		Map<String, Map<String, CallGraph>>  moreEntities = new HashMap<String, Map<String,CallGraph>>();
		CallGraph callGraph =  null;
		for (int i = 1; i < rows; i++) {
			Cell[] cellView = sheet.getRow(i);
			if(cellView.length < 2){
				Cell[] cellView1 = new Cell[2];
				cellView1[0] = cellView[0];
				
				cellView = cellView1;
				
			}
			Collection<Map<String, CallGraph>> collection = hashMap.values();
			for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
				Map<String, CallGraph> PGMToCPYMAP = (Map<String, CallGraph>) iterator
						.next();
				Collection<CallGraph> callGraphs= PGMToCPYMAP.values();
				for (Iterator iterator2 = callGraphs.iterator(); iterator2
						.hasNext();) {
					CallGraph cpyCallGraph = (CallGraph) iterator2.next();
					Collection<CallGraph> callGraphsChild= cpyCallGraph.getChildRoutine().values();
					for (Iterator iterator3 = callGraphsChild.iterator(); iterator3
							.hasNext();) {
						CallGraph callGraph2 = (CallGraph) iterator3.next();
						if(callGraph2.isCodeCopy()){
							if(callGraph2.getRoutineName().equalsIgnoreCase(cellView[0].getContents().trim().toString()) &&  
									cellView[1] != null && cellView[1].getContents().trim().length() > 0){
								 callGraph = new CallGraph();
								 callGraph.setRoutineName(cellView[1].getContents());
								 callGraph2.getChildRoutine().put(cellView[1].getContents().trim().toString(), callGraph);
							}
						}
						
					}
					
				}
				
				
			}
		}
		return hashMap;
	}
	
	public Map<String, Map<String, CallGraph>> parseExcelFile(){
		try {
			String dir = System.getProperty("user.dir");
			String filePath = dir + "/Call_Graph11.xls";
			Workbook workbook = Workbook.getWorkbook(new File(filePath));
//			Workbook workbook = Workbook.getWorkbook(new File("C:/workspace/CallGraph/Call_Graph11.xls"));
			Map<String, Map<String, CallGraph>> hashMap = getJCLToPGMList( workbook.getSheet("JCL2PGM"));
			hashMap = getPGM2PGMList( workbook.getSheet("PGM2PGM"), hashMap);
			hashMap = getCPYtoPGMList(workbook.getSheet("CPY2PGM"), hashMap);
			return hashMap;
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		System.err.println(System.getenv("user.dir"));
		System.err.println(System.getProperty("user.dir"));
	}
	
}
