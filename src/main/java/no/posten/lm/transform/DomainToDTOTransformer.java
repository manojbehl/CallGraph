package no.posten.lm.transform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import no.posten.lm.domain.CallGraph;
import no.posten.lm.dto.CallGraphDTO;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class DomainToDTOTransformer {

	public Collection<CallGraphDTO> transform(Collection<CallGraph> callGraphCollection){
		Collection<CallGraphDTO> callGrCollection = new ArrayList<CallGraphDTO>();
		CallGraphDTO callGraphDTO = null;
		for (Iterator iterator = callGraphCollection.iterator(); iterator
				.hasNext();) {
			callGraphDTO =  new CallGraphDTO();
			CallGraph callGraph = (CallGraph) iterator.next();
			BeanUtils.copyProperties(callGraph, callGraphDTO);
			callGraphDTO.setLevel(0);
			
			callGrCollection.add(callGraphDTO);
			//addChildCallGraph(callGrCollection, callGraph.getChildCallGraph(),callGraphDTO.getLevel()+1);
			
		}
		return callGrCollection;
	}
	
	private void addChildCallGraph(Collection<CallGraphDTO> callGraphDTOs, Collection<CallGraph> callGraphs,int level){
		CallGraphDTO callGraphDTO = null;
		for (Iterator iterator = callGraphs.iterator(); iterator.hasNext();) {
			CallGraph callGraph = (CallGraph) iterator.next();
			callGraphDTO = new CallGraphDTO();
			BeanUtils.copyProperties(callGraph, callGraphDTO);
			callGraphDTO.setLevel(level);
			callGraphDTO.setParent_id(""+level);
			if(callGraph.getChildCallGraph().size() == 0)
				callGraphDTO.setLeaf(true);
			callGraphDTOs.add(callGraphDTO);
			addChildCallGraph(callGraphDTOs, callGraph.getChildCallGraph(), level+1);
			
		}
	}
}
