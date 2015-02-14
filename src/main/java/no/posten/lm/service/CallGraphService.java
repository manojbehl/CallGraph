package no.posten.lm.service;

import java.util.Collection;

import no.posten.lm.dao.CallGraphDAO;
import no.posten.lm.domain.CallGraph;
import no.posten.lm.dto.CallGraphDTO;
import no.posten.lm.transform.DomainToDTOTransformer;
import no.posten.lm.util.ParseExcel;
import no.posten.lm.util.ProcessCallGraph;

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
	
	public void populateCallGraph(){
		processCallGraph.getListOfJCLProgram();
		callGraphDAO.InsertData(processCallGraph.getJclCallGraph());
//		Collection<CallGraph> listOfCACallGraphs = callGraphDAO.getListOfAllRecords();
//		System.err.println(listOfCACallGraphs.size());
	}
	
	public Collection<CallGraphDTO> loadGraph(){
		Collection<CallGraph> callGraphCollection = callGraphDAO.getAllCallGraph();
		return domainToDTOTransformer.transform(callGraphCollection);
	}
}
