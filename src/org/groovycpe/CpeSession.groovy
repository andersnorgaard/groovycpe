package org.groovycpe
import org.xmlsoap.schemas.soap.envelope.Envelope
import org.apache.commons.httpclient.methods.PostMethodimport org.apache.commons.httpclient.methods.GetMethodimport org.apache.commons.httpclient.methods.StringRequestEntityimport org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.HttpVersion;

public class CpeSession {		HttpClient httpclient = new HttpClient()	List messages	CpeActions cpeActions		public CpeSession(messages, cpeActions){		System.out.println 'Got ' + messages.size() + ' messages'		this.messages = messages
				this.cpeActions = cpeActions
		
		println "Saved ${cpeActions.class.name} as ${this.cpeActions.class.name}"	}	
		/** 
	 * Run synchronously - not in a thread.
	 */
	public void run(){		println 'Running CpeSession.run()'		for(message in messages){						sendData(marshalData(message))							}				def reply = sendData('')						while(reply != "" ){			//System.out.println "cmd: >" + reply + "<"			def cmdObj = unMarshalDataToBody(reply)						def respObj = cpeActions.invokeMethod('do' + cmdObj.getClass().getSimpleName(), cmdObj)			reply = sendData( marshalData(respObj) )			
			/*
			if( reply == "" ){				// try again to accomodate our non-spec ACS				reply = sendData('')			}*/		}
		println "No reply from server. We will close the connection"
		httpclient.getHttpConnectionManager().closeIdleConnections(0)
			}		private String marshalData(Envelope envelope){		return new JAXBHelper().marshalToString(envelope)	}		private Object unMarshalDataToBody(String data){		return unMarshalData(data).getBody().getAny().get(0)	}		private Envelope unMarshalData(String data){				return new JAXBHelper().unmarshal(data.getBytes())	}		private String getAdr(){
		println "CpeActions ${cpeActions.class.name}"		return this.cpeActions.confdb.confs['InternetGatewayDevice.ManagementServer.URL'].value	}		def String sendData(String data){		println "Sending to ${getAdr()} data: $data"		final PostMethod pm = new PostMethod(getAdr())				pm.setRequestEntity(new StringRequestEntity(data, "text/xml",  "UTF-8"))						final int result = httpclient.executeMethod(pm);						println("Response status code: " + result);
		println("Headers: ")
		pm.getResponseHeaders().each {
			println "\t ${it.toString().trim()}"
		}
		if(result == 200){
			final String response = pm.getResponseBodyAsStream().text
			println("Response body: $response");
			return response
		}				if(result > 204){			throw new RuntimeException()		}
		return ""	}}