groovycpe
=========

An emulator for TR069 managed CPEs. 

Hopefully it will be useful for initial bringup of TR069 servers, regression testing and maybe performance testing. The code is licensed under the GPLv3.


The program is done in Groovy : http://groovy.codehaus.org/


The TR069 schemas in etc/*.xsd have been fetched from: http://www.broadband-forum.org/cwmp.php


The xsds are processed with JAXB ( https://jaxb.dev.java.net ) to get Java classes. To get the JAXB processing to work I have deleted 'fixed="1"' from the soapenv:mustUnderstand attributes.


The HTTP communication is done with the help of the Apache HttpComponents ( http://hc.apache.org/httpclient-3.x/ )

  
The initial configuration content in testfiles/parameters/get*.txt is from a Thomson TG784 box.



Contributions welcome. Happy hacking
