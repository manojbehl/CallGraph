package no.posten.lm.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpSession;

import no.posten.lm.dto.CallGraphDTO;
import no.posten.lm.service.CallGraphService;

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
	
	@RequestMapping(value="/populate", method=RequestMethod.GET)
	@ResponseBody
	public String populateDataWithCallGraph(){
		
		callGraphService.populateCallGraph();
		return "success";
	}
	
	@RequestMapping(value="/load",method=RequestMethod.GET)
	@ResponseBody
	public Collection<CallGraphDTO> loadDataWithCallGraph(Model model, HttpSession httpSession){
		Collection<CallGraphDTO> callGraphCollection= callGraphService.loadGraph();
		httpSession.setAttribute("callGraphCollection", callGraphCollection);
		return callGraphCollection;
		
	}
	
	@RequestMapping(value="/loadChild",method=RequestMethod.GET)
	@ResponseBody
	public String loadChildGraph(@RequestParam String jclProgramName, HttpSession httpSession){
		Collection<CallGraphDTO> callGraphCollection = (Collection<CallGraphDTO>)httpSession.getAttribute("callGraphCollection");
		if(callGraphCollection == null){
			callGraphCollection= callGraphService.loadGraph();
			httpSession.setAttribute("callGraphCollection", callGraphCollection);
		}
		
		CallGraphDTO callGraphDTO = new CallGraphDTO();
		callGraphDTO.setRoutineName(jclProgramName);
		
		int index=  ((ArrayList<CallGraphDTO>)callGraphCollection).indexOf(callGraphDTO);
		CallGraphDTO mainObject =  ((ArrayList<CallGraphDTO>)callGraphCollection).get(index);
		
//		Collection<CallGraphDTO> finalizeCallGraphDTOs = new ArrayList<CallGraphDTO>();
//		return breakupChildIntoSeperateRecords(((ArrayList<CallGraphDTO>)callGraphCollection).get(index).getChildCallGraph(),finalizeCallGraphDTOs);
		StringBuffer sb= new StringBuffer();
		sb.append("{\"response\": [");
		populateTreeData(mainObject, sb);
		sb.append("]}");
		return sb.toString();
	}
	
	private String populateTreeData(CallGraphDTO mainObject,StringBuffer sb){
		
		loadEntries(sb, mainObject);
		for (Iterator iterator = mainObject.getChildCallGraph().iterator(); iterator.hasNext();) {
			CallGraphDTO type = (CallGraphDTO) iterator.next();
			populateTreeData(type, sb);
			
		} mainObject.getChildCallGraph();
		return sb.toString();
	}
	
	private void loadEntries(StringBuffer sb, CallGraphDTO mainObject){
		sb.append("{");
		sb.append("\"id\":\""+mainObject.getId()+"\",");
		sb.append("\"routineName\":\""+mainObject.getRoutineName()+"\",");
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
		System.err.println("ffmbdjhv");
		callGraphService.update(callGraphDTO);
		callGraphCollection.add(callGraphDTO);
		
	}
	
	@RequestMapping(value="/show")
	public String showPage(){
		
		return "callGraph1";
		
	}
}
