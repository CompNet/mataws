
MATAWS version 1.4.0
===============================

Copyright 
* 2010 Cihan Aksoy & Koray Mançuhan
* 2011-2013 Cihan Aksoy
* 2014 Vincent Labatut

Galatasaray University - Bit Lab
http://bit.gsu.edu.tr/compnet
cihan.aksoy@tubitak.gov.tr

MATAWS is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation.

For source availability and license information see `licence.txt`

This software was first developed by C. Aksoy & K. Mançuhan during their BSc thesis, 
both advised by V. Labatut; C. Aksoy then largely extended this work during its MSc thesis,
also under the supervision of V. Labatut; which then made minor modifications and
corrected a few bugs in the resulting source code.

If you use this software, please cite reference [ALCS'11].

-----------------------------------------------------------------------

# Dependencies
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


# References
This software was used in the following papers:
 * **[ALCS'11]** C. Aksoy, V. Labatut, C. Cherifi & J.-F. Santucci. *MATAWS : A Multimodal Approach for Automatic WS Semantic Annotation*. 3rd International Conference on Networked Digital Technologies (NDT). Communications in Computer and Information Science, Springer, 136:319-333, 2011. [doi: 10.1007/978-3-642-22185-9_27](https://doi.org/10.1007/978-3-642-22185-9_27) - [⟨hal-00620566⟩](https://hal.archives-ouvertes.fr/hal-00620566)
 * **[AL'12]** C. Aksoy & V. Labatut. *A Fully Automatic Approach to the Semantic Annotation of Web Service Descriptions*. Technical report, Galatasaray University, Computer Science Department, 21p, 2012. [⟨hal-01112241⟩](https://hal.archives-ouvertes.fr/hal-01112241)
