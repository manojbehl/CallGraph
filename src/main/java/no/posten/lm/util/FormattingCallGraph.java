package no.posten.lm.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import no.posten.lm.domain.CallGraph;

@Component
public class FormattingCallGraph {

	int counter = 0;
	Logger logger = Logger.getLogger(FormattingCallGraph.class.getName());
	public  Collection<CallGraph> formatCallGraphCollection(Collection<CallGraph> unorganisedCallGraphList){
		Collection<CallGraph> formattedCallGraphList = new ArrayList<CallGraph>();
		CallGraph formattedCallGraph =null;
		counter=0;
		for (Iterator iterator = unorganisedCallGraphList.iterator(); iterator
				.hasNext();) {
			CallGraph callGraph = (CallGraph) iterator.next();
			formattedCallGraph = new CallGraph();
			BeanUtils.copyProperties(callGraph, formattedCallGraph, new String[]{"childCallGraph","childRoutine","id"});
			formattedCallGraph.setId(""+counter++);
			formattedCallGraph.setPath(formattedCallGraph.getId());
			formattedCallGraph.setLevel(0);
//			counter = counter +1;
			
			formattedCallGraphList.add(formattedCallGraph);
			storeChildRecord(formattedCallGraphList, formattedCallGraph, callGraph.getChildCallGraph(),1);
			
		}
		return formattedCallGraphList;
	}
	
	private void storeChildRecord(Collection<CallGraph> formattedCallGraphList, CallGraph parentCallGraph, Collection<CallGraph> childCallGraphList,int level){
		CallGraph formattedCallGraph =null;
		for (Iterator iterator = childCallGraphList.iterator(); iterator
				.hasNext();) {
			
			logger.log(Level.INFO, "counter is :"+counter);
			CallGraph childCallGraph = (CallGraph) iterator.next();
			formattedCallGraph = new CallGraph();
			BeanUtils.copyProperties(childCallGraph, formattedCallGraph, new String[]{"childCallGraph","childRoutine","id"});
			formattedCallGraph.setId(""+counter++);
			formattedCallGraph.setPath(parentCallGraph.getPath() + CallGraph.PATH_SEPARATOR + formattedCallGraph.getId());
			formattedCallGraph.setParent(parentCallGraph.getId());
			formattedCallGraph.setLevel(level);
//			counter = counter +1;
			
			if(childCallGraph.getChildCallGraph().size() == 0)
				formattedCallGraph.setIsLeaf("true");
			
			formattedCallGraphList.add(formattedCallGraph);
			storeChildRecord(formattedCallGraphList, formattedCallGraph, childCallGraph.getChildCallGraph(),level+1);
		}
	}
	
}
