package org.groovycpe
/**
 * @author anders
 *
 */
 public class CpeServer{
 	
 	
 	static void main(args){
 		final CpeActions cpeActions = new CpeActions(confdb: CpeConfDB.readFromGetMessages('testfiles/parameters_zyxel2602/'))
 		
 		
 		cpeActions.confdb.confs['InternetGatewayDevice.ManagementServer.PeriodicInformInterval'].value = '120';
 		cpeActions.confdb.confs['InternetGatewayDevice.ManagementServer.ConnectionRequestURL'].value = 'http://127.0.0.1:8282/'
 			
 		def thread1 = new Thread(new CpeConnectionRequestServer(cpeActions: cpeActions)).start() 		
 		def thread2 = new Thread(new CpePeriodicRequestServer(cpeActions: cpeActions)).start()
 		
 		
 		
 		
		//cpeActions.confdb.confs['InternetGatewayDevice.ManagementServer.URL'].value = 'http://localhost:8080/grailsacs/TACSService'
		cpeActions.confdb.confs['InternetGatewayDevice.ManagementServer.URL'].value = 'http://localhost:8080/tacs/TACSService'

 		//cpeActions.confdb.confs['InternetGatewayDevice.DeviceInfo.SerialNumber'].value = 'AN0000NT001'
 	 	cpeActions.confdb.confs['InternetGatewayDevice.DeviceInfo.SerialNumber'].value = 'CP0816NT029'
 	 	cpeActions.confdb.confs['InternetGatewayDevice.DeviceInfo.SoftwareVersion'].value = 'GroovyCPE_FW.1'
 		//new CpeSession([CpeServer.cpeActions.doInform(['2 PERIODIC']) ]).run()
 		new CpeSession([cpeActions.doInform(['0 BOOTSTRAP']) ], cpeActions).run() 		
 	}
 }

