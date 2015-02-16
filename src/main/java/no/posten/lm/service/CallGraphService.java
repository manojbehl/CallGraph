package no.posten.lm.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import no.posten.lm.dao.CallGraphDAO;
import no.posten.lm.domain.CallGraph;
import no.posten.lm.dto.CallGraphDTO;
import no.posten.lm.transform.DomainToDTOTransformer;
import no.posten.lm.util.FormattingCallGraph;
import no.posten.lm.util.ParseExcel;
import no.posten.lm.util.ProcessCallGraph;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CallGraphService {

	@Autowired
	CallGraphDAO callGraphDAO;
	
	@Autowired
	ProcessCallGraph processCallGraph;
	
	@Autowired
	DomainToDTOTransformer domainToDTOTransformer;
	
	@Autowired
	FormattingCallGraph formattingCallGraph;
	
	public void populateCallGraph(){
		processCallGraph.getListOfJCLProgram();
		
		callGraphDAO.InsertData(formattingCallGraph.formatCallGraphCollection(processCallGraph.getJclCallGraph()));
//		Collection<CallGraph> listOfCACallGraphs = callGraphDAO.getListOfAllRecords();
//		System.err.println(listOfCACallGraphs.size());
	}
	
	public Collection<CallGraphDTO> loadGraph(){
		Collection<CallGraph> callGraphCollection = callGraphDAO.getAllCallGraph();
		return domainToDTOTransformer.transform(callGraphCollection,true);
	}
	
	public Collection<CallGraphDTO> loadChilds(String id){
		Collection<CallGraph> callGraphCollection = callGraphDAO.loadChild(id);
		return domainToDTOTransformer.transform(callGraphCollection,false);
	}
	
	public void update(CallGraphDTO callGraphDTO){
		callGraphDAO.update(callGraphDTO);
	}
	
	public void exportToExcel(String filePath){

		Collection<CallGraph> allCallGraphObjects = callGraphDAO.getParentChildGraphs();
		writeExcel(filePath, prepareCallGraphHierarchy(allCallGraphObjects));
		
	}
	
	private Collection<CallGraph> prepareCallGraphHierarchy(Collection<CallGraph> callGraphList){
		HashMap<String, CallGraph> hashMap = null;
		Collection<CallGraph> collection = new ArrayList<CallGraph>();
		for (Iterator iterator = callGraphList.iterator(); iterator.hasNext();) {
			CallGraph callGraph = (CallGraph) iterator.next();
			hashMap = new HashMap<String, CallGraph>();
			Collection<CallGraph> listOfChilds =  callGraphDAO.loadChild(callGraph.getId());
			for (Iterator iterator2 = listOfChilds.iterator(); iterator2
					.hasNext();) {
				CallGraph childCallGraph = (CallGraph) iterator2.next();
				hashMap.put(childCallGraph.getId(), childCallGraph);
				
			}
			for (Iterator iterator2 = listOfChilds.iterator(); iterator2
					.hasNext();) {
				CallGraph childCallGraph = (CallGraph) iterator2.next();
				if(childCallGraph.getParent().equals(callGraph.getId())){
					callGraph.getChildCallGraph().add(childCallGraph);
				}else{
				   CallGraph cg =	hashMap.get(childCallGraph.getParent());
				   cg.getChildCallGraph().add(childCallGraph);
				}
				
			}
			collection.add(callGraph);
			
		}
		
		return callGraphList;
	}
	
	
	
	private void writeExcel(String filePath, Collection<CallGraph> listOfCallGraph){
		System.err.println("asfdsfhfjasgfjasd");
////		Set<String> setOfJCLProgram=  hashMap.keySet();
//		Iterator<String> iterator=  setOfJCLProgram.iterator();
//		WritableWorkbook workbook = null;
//		try {
////			 workbook = Workbook.createWorkbook(new File("/Users/manojbehl/Documents/workspace-spring/CallGraph/Call_Graph_output.xls"));
//			 workbook = Workbook.createWorkbook(new File("C:/workspace/CallGraph/Call_Graph_output.xls"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		WritableSheet sheet = workbook.createSheet("Output", 0);
//		int col =1;
//		Integer row = 1;
//		String jclProgram= null;
//		Map<String, CallGraph> childMap= null;
//		Label label = null;
//		WritableFont  wfontStatus = new WritableFont(WritableFont.createFont("Arial"), WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD, false);
//		 WritableCellFormat fCellstatus = new WritableCellFormat(wfontStatus);
////	    fCellstatus.setWrap(true);
//	    fCellstatus.setBackground(Colour.BROWN);
//	    fCellstatus.setAlignment(jxl.format.Alignment.CENTRE);
//	    fCellstatus.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
////	    fCellstatus.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM, jxl.format.Colour.BLUE2);
//		
//		label = new Label(0,0,"Type", fCellstatus);
//		sheet.addCell(label);
//		label = new Label(1,0,"JCL Program", fCellstatus);
//		sheet.addCell(label);
//		label = new Label(2,0,"Program1", fCellstatus);
//		sheet.addCell(label);
//		label = new Label(3,0,"Program2", fCellstatus);
//		sheet.addCell(label);
//		label = new Label(4,0,"Program3", fCellstatus);
//		sheet.addCell(label);
////		WritableCellFormat fCellstatusForJCL  = fCellstatus;
////	    fCellstatusForJCL.setBackground(Colour.LIGHT_BLUE);
////	    WritableCellFormat fCellstatusForSub  = fCellstatus;
////	    WritableCellFormat fCellstatusForCopy  = fCellstatus;
//		Set<CallGraph> jclCallGraph = new HashSet<CallGraph>();
//		CallGraph callGraph = null;
//		while(iterator.hasNext()){
//			 jclProgram= iterator.next();
//			 callGraph = new CallGraph();
//			 callGraph.setJCL(true);
//			 callGraph.setRoutineName(jclProgram);
//			 int column = col;
//			 int rowvalue =  row++;
//			label = new Label(column,rowvalue,jclProgram);
//			sheet.addCell(label);
//			childMap = hashMap.get(jclProgram);
//			if(!childMap.isEmpty()){
//				CallGraph[] callGraphChild=   childMap.values().toArray(new CallGraph[]{});
//				if(callGraphChild[0].isJCL()){
//					label = new Label(0,rowvalue,"JCL");
//					sheet.addCell(label);
//				}
//			}
//			int startRow = row;
//			row = writeChildExcel(childMap, row, col+1, sheet,callGraph);
//			//sheet.mergeCells(startRow, row,1, 1);
//			jclCallGraph.add(callGraph);
//		}
//		workbook.write();
//		workbook.close();
	}
	
	private int writeChildExcel(Map<String, CallGraph> childMap, Integer row, int col, WritableSheet sheet, CallGraph jclCallGraph) throws RowsExceededException, WriteException{
		Iterator<String> iterator = childMap.keySet().iterator();
		Label label = null;
		while(iterator.hasNext()){
			String child = iterator.next();
			CallGraph callGraph =  childMap.get(child);
			CallGraph childCallGraph = new CallGraph();
			BeanUtils.copyProperties(callGraph, childCallGraph, new String[]{"childCallGraph","childRoutine"});
			jclCallGraph.getChildCallGraph().add(callGraph);
			if(callGraph.isCodeCopy()){
				child +=  "  - Code Copy";
			}
			else if(callGraph.isREXX()){
				child +=  "  - REXX";
			}else{
				
				child +=  "  - Sub Program";
			}
			
			
			
			label = new Label(col,row++,child);
			sheet.addCell(label);
			 
			row = writeChildExcel(callGraph.getChildRoutine(), row, col+1, sheet,callGraph);
		}
		return row;
	}
}

