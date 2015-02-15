package no.posten.lm.dao;


import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import no.posten.lm.domain.CallGraph;
import no.posten.lm.dto.CallGraphDTO;
import no.posten.lm.transform.DTOTODomainTransformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class CallGraphDAO {
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	DTOTODomainTransformer dtoToDomainTransformer;
	
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
	
	public Collection<CallGraph> loadChild(String id){
//		CallGraph parentCallGraph = mongoTemplate.findById(id, CallGraph.class);
		Collection<CallGraph> callGrCollection   =mongoTemplate.find( 
								Query.query( new Criteria( "path" ).regex( "^" + id + "[.]" ) ),
					                CallGraph.class
					            );
		return callGrCollection;
//		for (Iterator iterator = childCallGraphCollection.iterator(); iterator
//				.hasNext();) {
//			CallGraph callGraph = (CallGraph) iterator.next();
//			String path = callGraph.getPath().substring(0,callGraph.getPath().lastIndexOf(CallGraph.PATH_SEPARATOR));
//			
//		}
//		
//		return 
	}
	
	public void update(CallGraphDTO callGraphDTO){
		String str = "{routineName:\"" + callGraphDTO.getRoutineName() + "\"}";
		BasicQuery basicQuery = new BasicQuery(str);
		CallGraph callGraph = mongoTemplate.findOne(basicQuery, CallGraph.class);
		callGraph = dtoToDomainTransformer.transform(callGraphDTO, callGraph);
		mongoTemplate.save(callGraph);
		
	}

}
