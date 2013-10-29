package org.groovycpe

import java.lang.RuntimeExceptionimport org.xmlsoap.schemas.soap.envelope.Envelopeimport org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
public class CpeDownloadJobServer extends Thread {		def download;	HttpClient httpclient = new HttpClient();		void run(){				try {			Thread.yield()			Thread.sleep(2000);						final GetMethod gm = new GetMethod(download.getURL());											int result = httpclient.executeMethod(gm);				 			println("Response status code: " + result);					println("Response body lenght: " + gm.getResponseBody().length);			println gm.getResponseBodyAsString()			if(result > 200){				throw new RuntimeException()			}											} catch(final IOException e) {			println e		}			}}