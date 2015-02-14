package no.posten.lm.controller;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import no.posten.lm.dto.CallGraphDTO;
import no.posten.lm.service.CallGraphService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
	@RequestMapping(value="/update")
	@ResponseBody
	public void updateGridValue(CallGraphDTO callGraphDTO, HttpSession httpSession){
		Collection<CallGraphDTO> callGraphCollection = (Collection<CallGraphDTO>)httpSession.getAttribute("callGraphCollection");
		System.err.println("ffmbdjhv");
		
	}
	
	@RequestMapping(value="/show")
	public String showPage(){
		
		return "callGraph1";
		
	}
}
