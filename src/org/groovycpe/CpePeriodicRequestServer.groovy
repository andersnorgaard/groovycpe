package org.groovycpe
public class CpePeriodicRequestServer implements Runnable {
	
	def cpeActions		void run(){		try {						while(true){
				int sleepInterval = Integer.parseInt(cpeActions.confdb.confs['InternetGatewayDevice.ManagementServer.PeriodicInformInterval'].value)				Thread.sleep(sleepInterval * 1000);				new CpeSession([ cpeActions.doInform(['2 PERIODIC']) ], cpeActions).run()			}								} catch(final IOException e) {			throw new RuntimeException(e)		}			}}