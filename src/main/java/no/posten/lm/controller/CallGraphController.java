package no.posten.lm.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import no.posten.lm.dto.CallGraphDTO;
import no.posten.lm.service.CallGraphService;
import no.posten.lm.util.FormattingCallGraph;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CallGraphController {

	@Autowired
	CallGraphService callGraphService;
	
	Logger logger = Logger.getLogger(FormattingCallGraph.class.getName());
	
	@RequestMapping(value="/populate", method=RequestMethod.GET)
	@ResponseBody
	public String populateDataWithCallGraph(){
		
		callGraphService.populateCallGraph();
		return "success";
	}
	
	@RequestMapping(value="/load",method=RequestMethod.GET)
	@ResponseBody
	public Collection<CallGraphDTO> loadDataWithCallGraph(Model model, HttpSession httpSession,@RequestParam int rows,
															@RequestParam int page,HttpServletResponse response){
		Collection<CallGraphDTO> callGraphCollection= callGraphService.loadGraph();
		Collections.sort((List)callGraphCollection);
		httpSession.setAttribute("callGraphCollection", callGraphCollection);
		return callGraphCollection;
		
	}
	
	
	@RequestMapping(value="/writeExcel",method=RequestMethod.GET)
	@ResponseBody
	public String loadDataWithCallGraph( HttpServletResponse response) throws IOException{
		String str = callGraphService.exportToExcel();
		 response.setContentType("application/vnd.ms-excel");
		 response.setHeader("Expires", "0");
		 response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		 response.setHeader("Pragma", "public");
		 response.setHeader("Content-Disposition", "attachment; filename=final_output.xls");
		 OutputStream os = response.getOutputStream();
		 os.write(str.getBytes());
		 os.flush();
		 os.close();
		return str;
	}
	
	@RequestMapping(value="/loadChild",method=RequestMethod.GET)
	@ResponseBody
	public String loadChildGraph(@RequestParam String id, HttpSession httpSession){
		Collection<CallGraphDTO> callGraphCollection = (Collection<CallGraphDTO>)httpSession.getAttribute("callGraphCollection");
		if(callGraphCollection == null){
			callGraphCollection= callGraphService.loadGraph();
			httpSession.setAttribute("callGraphCollection", callGraphCollection);
		}
		
		CallGraphDTO callGraphDTO = new CallGraphDTO();
		callGraphDTO.setId(id);
		
		int index=  ((ArrayList<CallGraphDTO>)callGraphCollection).indexOf(callGraphDTO);
		CallGraphDTO mainObject =  ((ArrayList<CallGraphDTO>)callGraphCollection).get(index);
		
//		Collection<CallGraphDTO> callGraphDTOs = callGraphService.loadChilds(id);
		
//		Collection<CallGraphDTO> finalizeCallGraphDTOs = new ArrayList<CallGraphDTO>();
//		return breakupChildIntoSeperateRecords(((ArrayList<CallGraphDTO>)callGraphCollection).get(index).getChildCallGraph(),finalizeCallGraphDTOs);
//		StringBuffer sb= new StringBuffer();
//		sb.append("{\"response\": [");
//		populateTreeData(mainObject, sb);
//		sb.append("]}");
		String string =  mainObject.createTreeResponse();
		logger.info(string);
		return string;
		
	}
	
	private String populateTreeData(CallGraphDTO mainObject,StringBuffer sb){
		
		loadEntries(sb, mainObject);
		Collection<CallGraphDTO> callGraphDTOs = callGraphService.loadChilds(mainObject.getId());
		for (Iterator iterator = callGraphDTOs.iterator(); iterator.hasNext();) {
			CallGraphDTO type = (CallGraphDTO) iterator.next();
			populateTreeData(type, sb);
			
		} mainObject.getChildCallGraph();
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

	private Collection<CallGraphDTO> breakupChildIntoSeperateRecords(Collection<CallGraphDTO> mainCallGraphDTOs, Collection<CallGraphDTO> finalizeGraphDTOs){
		
		
		
		for (Iterator iterator = mainCallGraphDTOs.iterator(); iterator.hasNext();) {
			CallGraphDTO callGraphDTO = (CallGraphDTO) iterator.next();
			finalizeGraphDTOs.add(callGraphDTO);
			breakupChildIntoSeperateRecords(callGraphDTO.getChildCallGraph(), finalizeGraphDTOs);
			
		}
		return finalizeGraphDTOs;
	}
	
	@RequestMapping(value="/update")
	@ResponseBody
	public void updateGridValue(CallGraphDTO callGraphDTO, HttpSession httpSession){
		Collection<CallGraphDTO> callGraphCollection = (Collection<CallGraphDTO>)httpSession.getAttribute("callGraphCollection");
		System.err.println(callGraphCollection.size());
		callGraphService.update(callGraphDTO);
		callGraphCollection.remove(callGraphDTO);
		callGraphCollection.add(callGraphDTO);
		System.err.println(callGraphCollection.size());
		Collections.sort((List)callGraphCollection);
		httpSession.setAttribute("callGraphCollection", callGraphCollection);
		
	}
	
	@RequestMapping(value="/show")
	public String showPage(){
		
		return "callGraph1";
		
	}
}
