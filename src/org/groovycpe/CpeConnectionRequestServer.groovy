package org.groovycpe
/**
 * @author anders
 *
 */
public class CpeConnectionRequestServer implements Runnable {
		def serverSocket;	def cpeActions
		void run(){		try {			println("Starting CpeConnectionRequestServer");
			int port = new URL(cpeActions.confdb.confs['InternetGatewayDevice.ManagementServer.ConnectionRequestURL'].value).getPort()			this.serverSocket = new ServerSocket(port);						while(true){							def socket = serverSocket.accept();						def reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));								def line				while((line = reader.readLine()) != null) {											println("Read [" + line + "]");					if(line.equals("")) {						break;					}				}												final OutputStream outputStream = socket.getOutputStream();				outputStream.write("HTTP/1.1 200 OK".getBytes());				outputStream.close();				socket.close();				println 'Closed sockets...now send'				def informMessage = cpeActions.doInform(['6 REQUEST'])				new CpeSession([ informMessage ]).run()				println '...sent stuff'			}			serverSocket.close();						} catch(final IOException e) {			println "Failed to bind to port " + new URL(cpeActions.confdb.confs['InternetGatewayDevice.ManagementServer.ConnectionRequestURL'].value).getPort() + " " + e		}			}	
}