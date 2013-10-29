package org.groovycpe

import java.io.Serializableimport org.dslforum.cwmp_1_0.ParameterInfoStructimport org.dslforum.cwmp_1_0.ParameterInfoListimport org.dslforum.cwmp_1_0.GetParameterNamesResponse

/**
 * @author anders
 *
 */
public class CpeConfDB implements Serializable {		private static final long serialVersionUID = -2634321577569129211L;
	
	Map confs = [:]
		transient ConfTreeNode root	
	CpeConfDB(){}		private static getUnmarshalledBody(fileName){				return new JAXBHelper().unmarshal(new File(fileName).readBytes()).body.any.first()	}		public static CpeConfDB readFromGetMessages(String baseDir){
		def confDB = new CpeConfDB()
				getUnmarshalledBody(baseDir + 'getnames.txt').parameterList.any.each{
			//println "Looking at ${it.name}"			if(it.name.endsWith('.')){				confDB.confs.put(it.name, new ConfObject(name: it.name, writable: it.writable))			} else {				confDB.confs.put(it.name, new ConfParameter(name: it.name, writable: it.writable))			}					}				getUnmarshalledBody(baseDir + 'getvalues.txt').parameterList.any.each{
			//println "Looking at ${it.name} - ${it.value}"
			if(! confDB.confs.containsKey(it.name) ){
				if(it.name.endsWith('.')){
					confDB.confs.put(it.name, new ConfObject(name: it.name, writable: 0))
				} else {
					confDB.confs.put(it.name, new ConfParameter(name: it.name, writable: 0))
				}
			}			confDB.confs[it.name].value = it.value		}		
		def keys = confDB.confs.keySet().asList().sort()
		
		//keys.each{ if(confDB.confs[it] instanceof ConfParameter){ println "${it} ${confDB.confs[it].value}" } }
						//getUnmarshalledBody(baseDir + 'getattributes.txt').parameterList.any.each{		//	confDB.confs[it.name].notification = it.notification		//	confDB.confs[it.name].accessList = it.accessList.any.join(",")		//}		//makeTree()
		return confDB	}	
		def makeTree(){				def root = new ConfTreeNode("")						for( conf in confs.values() ){									def curr = root			for( namePart in conf.name.tokenize(".") ){								def child = curr.getChild(namePart);				if(child == null) {					// make node regardless of data					child = new ConfTreeNode(namePart)					curr.addChild(namePart, child);				}				curr = child;			}									curr.data = conf					}				this.root = root	}	
		def serialize(String fileName){		new File(fileName).withObjectOutputStream { out ->					out << this		}	}	static deserialize(String fileName){		def res		new File(fileName).withObjectInputStream { instream ->			instream.eachObject {
				println "Looking at object $it"				res = it							}		}
		println "Deserialized $res"		return res	}
	
	static void main(args){
		println 'Starting CpeConfDB'
				//def c = CpeConfDB.readFromGetMessages('testfiles/parameters_zyxel2602/')	
		def c = CpeConfDB.readFromGetMessages('testfiles/parameters/')
		println c.confs.size()		c.confs['InternetGatewayDevice.DeviceInfo.SerialNumber'].value = 'AN0000NT001'				c.serialize('test.txt');		println CpeConfDB.deserialize('test.txt')
	}	
}class ConfTreeNode {		String name;	Map children = [:];	ConfObject data;		ConfTreeNode(String name){		this.name = name	}		def getChild(String name){		return children[name]	}		def addChild(String name, ConfTreeNode child){		children.put(name, child)	}	}class ConfObject implements Serializable {	private static final long serialVersionUID = -2634321577569129213L;		String name;	String writable;		ConfObject(){}		ConfObject(String name, String writable){		this.name=name		this.writable = writable	}}class ConfParameter extends ConfObject {	private static final long serialVersionUID = -2634321577569129212L;	String value;	String accessList;	String notification;		ConfParameter(){}		ConfParameter(String name, String writable, String value, String notification, String accessList){		super(name, writable)		this.value = value		this.notification = notification		this.accessList = accessList	}	}