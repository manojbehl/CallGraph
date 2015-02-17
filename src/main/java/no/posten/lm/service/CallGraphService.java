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
		try {
			writeExcel(filePath, prepareCallGraphHierarchy(allCallGraphObjects));
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
				   if(cg == null){
					   System.err.println(childCallGraph.getParent());
				   }else
				   cg.getChildCallGraph().add(childCallGraph);
				}
				
			}
			collection.add(callGraph);
			
		}
		
		return collection;
	}
	
	
	
	private void writeExcel(String filePath, Collection<CallGraph> listOfCallGraph) throws WriteException, IOException{

		WritableWorkbook workbook = null;
		try {
			String fileLocation = filePath +"/Call_Graph_Final_Output.xls";
//			 workbook = Workbook.createWorkbook(new File("/Users/manojbehl/Documents/workspace-spring/CallGraph/Call_Graph_output.xls"));
			 workbook = Workbook.createWorkbook(new File(fileLocation));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WritableSheet sheet = workbook.createSheet("Output", 0);
		int col =1;
		Integer row = 1;
		CallGraph jclProgram= null;
		Set<CallGraph> childMap= null;
		Label label = null;
		WritableFont  wfontStatus = new WritableFont(WritableFont.createFont("Arial"), WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD, false);
		 WritableCellFormat fCellstatus = new WritableCellFormat(wfontStatus);
//	    fCellstatus.setWrap(true);
	    fCellstatus.setBackground(Colour.BROWN);
	    fCellstatus.setAlignment(jxl.format.Alignment.CENTRE);
	    fCellstatus.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
//	    fCellstatus.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM, jxl.format.Colour.BLUE2);
		
		label = new Label(0,0,"Type", fCellstatus);
		sheet.addCell(label);
		label = new Label(1,0,"JCL Program", fCellstatus);
		sheet.addCell(label);
		label = new Label(2,0,"Program1", fCellstatus);
		sheet.addCell(label);
		label = new Label(3,0,"Program2", fCellstatus);
		sheet.addCell(label);
		label = new Label(4,0,"Program3", fCellstatus);
		sheet.addCell(label);

		
		label = new Label(5,0,"Frequency", fCellstatus);
		sheet.addCell(label);

		label = new Label(6,0,"Input", fCellstatus);
		sheet.addCell(label);
		label = new Label(7,0,"Output", fCellstatus);
		sheet.addCell(label);
		label = new Label(8,0,"Remarks", fCellstatus);
		sheet.addCell(label);
		

//		WritableCellFormat fCellstatusForJCL  = fCellstatus;
//	    fCellstatusForJCL.setBackground(Colour.LIGHT_BLUE);
//	    WritableCellFormat fCellstatusForSub  = fCellstatus;
//	    WritableCellFormat fCellstatusForCopy  = fCellstatus;
		Set<CallGraph> jclCallGraph = new HashSet<CallGraph>();
		CallGraph callGraph = null;
		Iterator<CallGraph> iterator = listOfCallGraph.iterator();
		while(iterator.hasNext()){
			 jclProgram= iterator.next();
			 int column = col;
			 int rowvalue =  row++;
			label = new Label(column,rowvalue,jclProgram.getRoutineName());
			sheet.addCell(label);
			
			label = new Label(5,rowvalue,jclProgram.getFrequency());
			sheet.addCell(label);
			label = new Label(6,rowvalue,jclProgram.getInput());
			sheet.addCell(label);
			label = new Label(7,rowvalue,jclProgram.getOutput());
			sheet.addCell(label);
			label = new Label(8,rowvalue,jclProgram.getRemarks());
			sheet.addCell(label);
			
			childMap = jclProgram.getChildCallGraph();
//			if(!childMap.isEmpty()){
//				HashSet<CallGraph> callGraphs = (HashSet<CallGraph>)childMap;
//				if(callGraphs.){
//					label = new Label(0,rowvalue,"JCL");
//					sheet.addCell(label);
//				}
//			}
			int startRow = row;
			row = writeChildExcel(childMap, row, col+1, sheet);
			//sheet.mergeCells(startRow, row,1, 1);
		}
		workbook.write();
		workbook.close();
	}
	
	private int writeChildExcel(Collection<CallGraph> childGraphList, Integer row, int col, WritableSheet sheet) throws RowsExceededException, WriteException{
		Iterator<CallGraph> iterator = childGraphList.iterator();
		Label label = null;
		while(iterator.hasNext()){
			CallGraph child = iterator.next();
			String childSubProgram = child.getRoutineName();
			if(child.isCodeCopy()){
				childSubProgram +=  "  - Code Copy";
			}
			else if(child.isREXX()){
				childSubProgram +=  "  - REXX";
			}else{
				
				childSubProgram +=  "  - Sub Program";
			}
			
			
			
			label = new Label(col,row++,childSubProgram);
			sheet.addCell(label);
			 
			row = writeChildExcel(child.getChildCallGraph(), row, col+1, sheet);
		}
		return row;
	}
}

