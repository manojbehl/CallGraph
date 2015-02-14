package no.posten.lm.transform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import no.posten.lm.domain.CallGraph;
import no.posten.lm.dto.CallGraphDTO;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class DTOTODomainTransformer {

	public CallGraph transform(CallGraphDTO callGraphDTO, CallGraph callGraph){
		BeanUtils.copyProperties(callGraphDTO, callGraph, new String[]{"childRoutine","childCallGraph"});
		return callGraph;
	}
}
