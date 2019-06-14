
#MATAWS version 1.4.0

-----------------------------------------------------------------------

Copyright 2010 Cihan Aksoy and Koray Mançuhan & 2011-2013 Cihan Aksoy and Vincent Labatut
Galatasaray University - Bit Lab
http://bit.gsu.edu.tr/compnet
cihan.aksoy@tubitak.gov.tr

MATAWS is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation.

For source availability and license information see `licence.txt`

-----------------------------------------------------------------------

This product includes softwares developed by:

  + Yvan Rivierre:
    + Sine (now known as WS-Next)
    + Copyright 2009-2011 Ivan Riviere 
    + Used to extract the parameters of the syntactic collection
    
  + Adam Pease:
    + Sigma library
    + Copyright 2003-2007 Adam Pease, GNU Licence
    + Used to find an ontological concept for a given word
    + Class `com.articulate.sigma.WordNet` has been modified by initializing a `com.articulate.sigma.KBManager` instance in the `initOnce()` method
    + Class `com.articulate.sigma.KBmanager` has been modified by removing read/write of XML configurations in the `initializeOnce()` method	
    
  + Evren Sirin:
    + OWL-S API
    + Copyright 2004 Evren Sirin & 2008-2009 Thorsten Möller, GNU Licence
    + http://www.mindswap.org/2004/owl-s/api/
    + Used to generate the semantic collection from the annotated syntactic collection
    + Libraries required: all remaining libraries in the lib folder

