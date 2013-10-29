package org.groovycpe
import groovy.mock.interceptor.StubForimport org.apache.commons.httpclient.methods.PostMethodimport org.apache.commons.httpclient.methods.StringRequestEntityimport org.apache.commons.httpclient.HttpClientimport junit.framework.TestCase
import org.xmlsoap.schemas.soap.envelope.Envelopeimport org.xmlsoap.schemas.soap.envelope.Body;import org.dslforum.cwmp_1_0.Rebootimport org.dslforum.cwmp_1_0.InformResponseimport org.dslforum.cwmp_1_0.GetParameterAttributesimport org.dslforum.cwmp_1_0.GetParameterValuesimport org.dslforum.cwmp_1_0.GetParameterNamesimport org.dslforum.cwmp_1_0.ParameterNames;

/**
 * @author anders
 *
 */
public class CpeServerTest extends TestCase{		
	void testCpeConnectionRequestServerRun(){		println "\n\n\n+++++++++++++++ Running testCpeConnectionRequestServerRun() +++++++++++++++"		def cpeSessionStub = new StubFor(CpeSession)		cpeSessionStub.demand.run {  }				cpeSessionStub.use {					def ccrs = new CpeConnectionRequestServer()
			def ccrst = new Thread(ccrs);
			ccrst.start()			
		
			HttpClient httpclient = new HttpClient()			PostMethod pm = new PostMethod("http://localhost:11111/")								StringRequestEntity sre = new StringRequestEntity('Hello world!', "text/xml",  "ISO-8859-1")			pm.setRequestEntity(sre)					assert httpclient.executeMethod(pm) == 200				 			ccrs.serverSocket.close()			ccrst.join()		}
	}		void testCpeSessionNoMsg(){		println "\n\n\n+++++++++++++++ Running testCpeSessionNoMsg() +++++++++++++++"		def cpeSession = new CpeSession( [ CpeServer.cpeActions.doInform(['2 PERIODIC']) ] )				def informResponseXML = new JAXBHelper().marshalToString(wrap(new InformResponse()))		def calledCount = 0				def msgList = [informResponseXML, ""]		cpeSession.metaClass.sendData = { String string -> System.out.println "Sending: " + string +   			"\nGetting reply data: " + msgList[calledCount]; 			calledCount++ ; return msgList[calledCount-1] }				cpeSession.run()				assert calledCount == 2	}		void testCpeSessionRebootMsg(){		println "\n\n\n+++++++++++++++ Running testCpeSessionRebootMsg() +++++++++++++++"				def informResponseXML = new JAXBHelper().marshalToString(wrap(new InformResponse()))		def rebootXML = new JAXBHelper().marshalToString(wrap(new Reboot())) 		def msgList = [informResponseXML, rebootXML, ""]		def cpeSession = new CpeSession( [ CpeServer.cpeActions.doInform(['2 PERIODIC']) ] )		def calledCount = 0		cpeSession.metaClass.sendData = { String string -> System.out.println "Sending: " + string +   			"\nGetting reply data: " + msgList[calledCount]; 			calledCount++ ; return msgList[calledCount-1] }							cpeSession.run()					assert calledCount == 3			}	private Envelope wrap(object){				def body = new Body()		body.getAny().add(object)		def envelope = new Envelope(body: body)		return envelope	}		
}
