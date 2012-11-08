package tr.edu.gsu.mataws.components;

/*
 * Mataws - Multimodal Automatic Tool for the Annotation of Web Services
 * Copyright 2011-12 Cihan Aksoy, Koray Mançuhan & Vincent Labatut
 * 
 * This file is part of Mataws - Multimodal Automatic Tool for the Annotation of Web Services.
 * 
 * Mataws - Multimodal Automatic Tool for the Annotation of Web Services is 
 * free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 * 
 * Mataws - Multimodal Automatic Tool for the Annotation of Web Services 
 * is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Mataws - Multimodal Automatic Tool for the Annotation of Web Services.
 * If not, see <http://www.gnu.org/licenses/>.
 * 
 */

import java.util.ArrayList;
import java.util.List;

import sine.col.Parameter;

public class TraceableParameter extends Parameter {

	private List<TraceType> traceList = new ArrayList<TraceType>();
	private List<String> controlList = new ArrayList<String>();
	
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

	public void setControlList(List<String> controlList) {
		this.controlList = controlList;
	}

	public List<String> getControlList() {
		return controlList;
	}
	public void addControlList(String string){
		controlList.add(string);
	}
}
