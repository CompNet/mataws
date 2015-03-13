:: -------------------------------------------------------------------
::
:: This is a MS-DOS script, it works only on Windows operating systems.
:: It launches MATAWS.
::
:: v.1.4.0
::
:: -------------------------------------------------------------------
::
:: Mataws - Multimodal Approach for Automatic WS Semantic Annotation
:: Copyright 2010 Cihan Aksoy and Koray Mançuhan & 2011-2013 Cihan Aksoy
:: 
:: This file is part of Mataws - Multimodal Approach for Automatic WS Semantic Annotation.
:: 
:: Mataws - Multimodal Approach for Automatic WS Semantic Annotation is free software: 
:: you can redistribute it and/or modify
:: it under the terms of the GNU General Public License as published by
:: the Free Software Foundation, either version 2 of the License, or
:: (at your option) any later version.
:: 
:: Mataws - Multimodal Approach for Automatic WS Semantic Annotation is distributed 
:: in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the 
:: implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
:: GNU General Public License for more details.
:: 
:: You should have received a copy of the GNU General Public License
:: along with Mataws - Multimodal Approach for Automatic WS Semantic Annotation.  
:: If not, see <http://www.gnu.org/licenses/>.
:: 
:: -------------------------------------------------------------------

	Setlocal

:: define path variables
	
	Set activation=.\lib\activation-1.0.2.jar
	Set arq=.\lib\arq-2.8.1.jar
	Set aterm=.\lib\aterm-java-1.6.jar
	Set axis=.\lib\axis-1.4.jar
	Set axis2=.\lib\axis-wsdl4j-1.5.1.jar
	Set commons-discovery=.\lib\commons-discovery-0.2.jar
	Set icu4j=.\lib\icu4j-3.4.4.jar
	Set iri=.\lib\iri-0.7.jar
	Set jaws=.\lib\jaws-bin.jar
	Set jaxrpc-api=.\lib\jaxrpc-api-1.1.jar
	Set jcl-over-slf4j=.\lib\jcl-over-slf4j-1.5.8.jar
	Set jena=.\lib\jena-2.6.2.jar
	Set jgrapht-jdk1=.\lib\jgrapht-jdk1.5-0.7.3.jar
	Set junit=.\lib\junit-4.5.jar
	Set jws=.\lib\jws.jar
	Set log4j=.\lib\log4j-1.2.14.jar
	Set lucene-core=.\lib\lucene-core-2.3.1.jar
	Set mataws=.\lib\mataws-1.4.0.jar
	Set owls-api=.\lib\owls-api-3.0.jar
	Set pellet=.\lib\pellet-2.0.jar
	Set relaxngDatatype=.\lib\relaxngDatatype-20020414.jar
	Set saaj-api=.\lib\saaj-api-1.2.jar
	Set sine=.\lib\sine-0.3.15.jar
	Set slf4j-api=.\lib\slf4j-api-1.5.8.jar
	Set slf4j-log4j12=.\lib\slf4j-log4j12-1.5.8.jar
	Set stax-api=.\lib\stax-api-1.0.1.jar
	Set upnp=.\lib\upnp-1.0.jar
	Set wstx-asl=.\lib\wstx-asl-3.2.9.jar
	Set xercesImpl=.\lib\xercesImpl-2.7.1.jar
	Set xsdlib=.\lib\xsdlib-20030225.jar
	Set cp=%activation%;%arq%;%aterm%;%axis%;%axis2%;%commons-discovery%;%icu4j%;%iri%;%jaws%;%jaxrpc-api%;%jcl-over-slf4j%;%jena%;%jgrapht-jdk1%;%junit%;%jws%;%log4j%;%lucene-core%;%mataws%;%owls-api%;%pellet%;%relaxngDatatype%;%saaj-api%;%sine%;%slf4j-api%;%slf4j-log4j12%;%stax-api%;%upnp%;%wstx-asl%;%xercesImpl%;%xsdlib%
	Set launcher=tr.edu.gsu.mataws.app.MainApp

:: launch MATAWS
	java -Xmx512m -classpath %cp% %launcher%

:: 	pause

	Endlocal
