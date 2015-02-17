package no.posten.lm.util;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import no.posten.lm.domain.CallGraph;

@Component
public class ProcessCallGraph {

	Map<String, Map<String, CallGraph>> hashMap = null;
	Set<CallGraph> jclCallGraph = null;
	
	public Set<CallGraph> getJclCallGraph() {
		return jclCallGraph;
	}

	public void setJclCallGraph(Set<CallGraph> jclCallGraph) {
		this.jclCallGraph = jclCallGraph;
	}

	public Map<String, Map<String, CallGraph>> getListOfJCLProgram(){
//		if(hashMap == null){
			hashMap= ParseExcel.getSharedInstance().parseExcelFile();
				
//		}
		try {
			writeExcel();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hashMap;
	}
	
	private void writeExcel() throws IOException, WriteException{
		Set<String> setOfJCLProgram=  hashMap.keySet();
		Iterator<String> iterator=  setOfJCLProgram.iterator();
		WritableWorkbook workbook = null;
		try {
			String dir = System.getProperty("user.dir");
			String filePath = dir + "/Call_Graph_output.xls";
			 workbook = Workbook.createWorkbook(new File(filePath));

//			 workbook = Workbook.createWorkbook(new File("/Users/manojbehl/Documents/workspace-spring/CallGraph/Call_Graph_output.xls"));
//			 workbook = Workbook.createWorkbook(new File("C:/workspace/CallGraph/Call_Graph_output.xls"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WritableSheet sheet = workbook.createSheet("Output", 0);
		int col =1;
		Integer row = 1;
		String jclProgram= null;
		Map<String, CallGraph> childMap= null;
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
//		WritableCellFormat fCellstatusForJCL  = fCellstatus;
//	    fCellstatusForJCL.setBackground(Colour.LIGHT_BLUE);
//	    WritableCellFormat fCellstatusForSub  = fCellstatus;
//	    WritableCellFormat fCellstatusForCopy  = fCellstatus;
		 jclCallGraph = new HashSet<CallGraph>();
		CallGraph callGraph = null;
		while(iterator.hasNext()){
			 jclProgram= iterator.next();
			 callGraph = new CallGraph();
			 callGraph.setJCL(true);
			 callGraph.setRoutineName(jclProgram);
			 int column = col;
			 int rowvalue =  row++;
			label = new Label(column,rowvalue,jclProgram);
			sheet.addCell(label);
			childMap = hashMap.get(jclProgram);
			if(!childMap.isEmpty()){
				CallGraph[] callGraphChild=   childMap.values().toArray(new CallGraph[]{});
				if(callGraphChild[0].isJCL()){
					label = new Label(0,rowvalue,"JCL");
					sheet.addCell(label);
				}
			}
			int startRow = row;
			row = writeChildExcel(childMap, row, col+1, sheet,callGraph);
			//sheet.mergeCells(startRow, row,1, 1);
			jclCallGraph.add(callGraph);
		}
		workbook.write();
		workbook.close();
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
