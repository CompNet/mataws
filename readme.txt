
MATAWS version 1.0.0

-----------------------------------------------------------------------

Copyright 2010 Cihan Aksoy and Koray Mançuhan
Copyright 2011 Cihan Aksoy
Copyright 2012 Cihan Aksoy and Vincent Labatut
Galatasaray University - Bit Lab
http://bit.gsu.edu.tr/compnet
caksoy@uekae.tubitak.gov.tr

MATAWS is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation.

For source availability and license information see licence.txt

-----------------------------------------------------------------------

This product includes softwares developed by:

  + Sine (now known as WS-Next)
    Yvan Rivierre
    Modified by Vincent Labatut
    Used to extract the parameters of the syntactic collection
    
  + IBM XL Transform and Query Engine for Java v1.0
    Unknown source
    Used by Sine to parse WSDL and SAWSDL files.
    
  + OWL API v2.2.0
    Matthew Horridge
    http://owlapi.sourceforge.net/publications.html
    Used by Sine to retrieve and compare ontological concepts

  + Mindscape OWL-S API v3.0
    Evren Sirin & Thorsten Möller
    http://www.mindswap.org/2004/owl-s/api/
    http://on.cs.unibas.ch/owls-api/
    Used by Sine to load OWL-S files.
    Used by Mataws to generate OWL-S files.
    
  + Sigma Library
    Adam Pease
    Used to find an ontological concept for a given word
    Class com.articulate.sigma.WordNet has been modified by initializing a com.articulate.sigma.KBManager instance in the initOnce() method
    Class com.articulate.sigma.KBmanager has been modified by removing read/write of XML configurations in the initializeOnce() method	
    
    + Simmetrics 1_6_2_d07_02_07
    Sam Chapman & Fabio Ciravegna
    http://sourceforge.net/projects/simmetrics/
    Used by Sine to syntactically compare strings

TODO to be completed