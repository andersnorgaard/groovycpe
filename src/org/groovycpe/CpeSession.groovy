package org.groovycpe


import org.apache.commons.httpclient.HttpVersion;


		
		
		println "Saved ${cpeActions.class.name} as ${this.cpeActions.class.name}"
	
	 * Run synchronously - not in a thread.
	 */
	public void run(){
			/*
			if( reply == "" ){
		println "No reply from server. We will close the connection"
		httpclient.getHttpConnectionManager().closeIdleConnections(0)
		
		println "CpeActions ${cpeActions.class.name}"
		println("Headers: ")
		pm.getResponseHeaders().each {
			println "\t ${it.toString().trim()}"
		}
		if(result == 200){
			final String response = pm.getResponseBodyAsStream().text
			println("Response body: $response");
			return response
		}		
		return ""