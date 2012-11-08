#!/bin/bash
# -------------------------------------------------------------------
#
# This is a BASH (Bourne-Again SHell) script, 
# it should work on most Linux, Unix, and Mac OS systems.
# It launches MATAWS with the appropriate parameters.
#
# v.1.0.0
#
# -------------------------------------------------------------------
#
# Mataws - Multimodal Approach for Automatic WS Semantic Annotation
# Copyright 2010 Cihan Aksoy and Koray Mançuhan
# Copyright 2011 Cihan Aksoy
# Copyright 2012 Cihan Aksoy and Vincent Labatut
# 
# This file is part of Mataws - Multimodal Approach for Automatic WS Semantic Annotation.
# 
# Mataws - Multimodal Approach for Automatic WS Semantic Annotation is free software: 
# you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 2 of the License, or
# (at your option) any later version.
# 
# Mataws - Multimodal Approach for Automatic WS Semantic Annotation is distributed 
# in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the 
# implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
# 
# You should have received a copy of the GNU General Public License
# along with Mataws - Multimodal Approach for Automatic WS Semantic Annotation.  
# If not, see <http://www.gnu.org/licenses/>.
# 
# -------------------------------------------------------------------
#
# define path variables
	activation="./lib/activation-1.0.2.jar"
	arq="./lib/arq-2.8.1.jar"
	aterm="./lib/aterm-java-1.6.jar"
	axis="./lib/axis-1.4.jar"
	axis2="./lib/axis-wsdl4j-1.5.1.jar"
	commonsdiscovery="./lib/commons-discovery-0.2.jar"
	icu4j="./lib/icu4j-3.4.4.jar"
	iri="./lib/iri-0.7.jar"
	jaxrpcapi="./lib/jaxrpc-api-1.1.jar"
	jcloverslf4j="./lib/jcl-over-slf4j-1.5.8.jar"
	jena="./lib/jena-2.6.2.jar"
	jgraphtjdk1="./lib/jgrapht-jdk1.5-0.7.3.jar"
	junit="./lib/junit-4.5.jar"
	log4j="./lib/log4j-1.2.14.jar"
	lucenecore="./lib/lucene-core-2.3.1.jar"
	mataws="./lib/mataws-1.0.0.jar"
	owlsapi="./lib/owls-api-3.0.jar"
	pellet="./lib/pellet-2.0.jar"
	relaxngDatatype="./lib/relaxngDatatype-20020414.jar"
	saajapi="./lib/saaj-api-1.2.jar"
	sine="./lib/sine-0.3.15.jar"
	slf4japi="./lib/slf4j-api-1.5.8.jar"
	slf4jlog4j12="./lib/slf4j-log4j12-1.5.8.jar"
	staxapi="./lib/stax-api-1.0.1.jar"
	upnp="./lib/upnp-1.0.jar"
	wstxasl="./lib/wstx-asl-3.2.9.jar"
	xercesImpl="./lib/xercesImpl-2.7.1.jar"
	xsdlib="./lib/xsdlib-20030225.jar"
	cp="${activation}:${arq}:${aterm}:${axis}:${axis2}:${commonsdiscovery}:${icu4j}:${iri}:${jaxrpcapi}:${jcloverslf4j}:${jena}:${jgraphtjdk1}:${junit}:${log4j}:${lucenecore}:${mataws}:${owlsapi}:${pellet}:${relaxngDatatype}:${saajapi}:${sine}:${slf4japi}:${slf4jlog4j12}:${staxapi}:${upnp}:${wstxasl}:${xercesImpl}:${xsdlib}"
	launcher="tr.edu.gsu.mataws.app.MainApp"
#
# launch MATAWS
	java -Xmx512m -classpath $cp $launcher
#
#

