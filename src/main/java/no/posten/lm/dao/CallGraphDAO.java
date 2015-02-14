package no.posten.lm.dao;


import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import no.posten.lm.domain.CallGraph;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class CallGraphDAO {
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	
	public void InsertData(Collection<CallGraph> callGraphObList){
		mongoTemplate.insertAll(callGraphObList);
//		for (Iterator iterator = callGraphObList.iterator(); iterator.hasNext();) {
//			CallGraph callGraph = (CallGraph) iterator.next();
//			Set<CallGraph> setCallGraphs =  callGraph.getChildCallGraph();
//			InsertData(setCallGraphs);
//			mongoTemplate.insert(callGraph);
//		}
		
	}
	
	
	public Collection<CallGraph> getAllCallGraph(){
//		BasicQuery query = new BasicQuery("{ isJCL : true}");
//		BasicQuery query = new BasicQuery("{ routineName : \"BJAC001\"}");
//		return mongoTemplate.find(query, CallGraph.class);
		return mongoTemplate.findAll(CallGraph.class);
	}

}
