package tr.edu.gsu.mataws.components;

import java.util.ArrayList;
import java.util.List;

import sine.col.Parameter;

public class TraceableParameter extends Parameter {

	private List<TraceType> traceList = new ArrayList<TraceType>(); 
	
	private Parameter parameter;
	
	public TraceableParameter(Parameter parameter) {
		super(parameter.getName());
		this.setParameter(parameter);
	}

	public List<TraceType> getTraceList() {
		return traceList;
	}

	public void setTraceList(List<TraceType> traceList) {
		this.traceList = traceList;
	}

	public void addTraceList(TraceType trace){
		traceList.add(trace);
	}

	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}

	public Parameter getParameter() {
		return parameter;
	}
}
