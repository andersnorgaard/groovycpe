package org.groovycpe
import junit.framework.TestCase

import org.dslforum.cwmp_1_0.GetParameterAttributes
import org.dslforum.cwmp_1_0.SetParameterAttributes
import org.dslforum.cwmp_1_0.GetParameterValues
import org.dslforum.cwmp_1_0.SetParameterValues
import org.dslforum.cwmp_1_0.GetParameterNames
import org.dslforum.cwmp_1_0.ParameterNames;
import org.dslforum.cwmp_1_0.RebootResponse
import org.dslforum.cwmp_1_0.Reboot
import org.dslforum.cwmp_1_0.EventStruct
import org.xmlsoap.schemas.soap.envelope.Body
import org.xmlsoap.schemas.soap.envelope.Envelope
import org.dslforum.cwmp_1_0.ParameterAttributeStruct
import org.dslforum.cwmp_1_0.ParameterAttributeList
import org.dslforum.cwmp_1_0.GetParameterAttributesResponse
import org.dslforum.cwmp_1_0.SetParameterAttributesResponse
import org.dslforum.cwmp_1_0.GetParameterValuesResponse
import org.dslforum.cwmp_1_0.SetParameterValuesResponse
import org.dslforum.cwmp_1_0.ParameterInfoStruct
import org.dslforum.cwmp_1_0.ParameterInfoList
import org.dslforum.cwmp_1_0.GetParameterNamesResponse
import org.dslforum.cwmp_1_0.EventList
import org.dslforum.cwmp_1_0.ParameterValueStruct
import org.dslforum.cwmp_1_0.ParameterValueList
import org.dslforum.cwmp_1_0.ParameterAttributeStruct
import org.dslforum.cwmp_1_0.ParameterAttributeList
import org.dslforum.cwmp_1_0.SetParameterAttributesList;
import org.dslforum.cwmp_1_0.SetParameterAttributesStruct;
import org.dslforum.cwmp_1_0.AccessList;
import org.dslforum.cwmp_1_0.DeviceIdStruct
import org.dslforum.cwmp_1_0.Inform;

/**
 * @author biehl
 *
 */
public class CpeActionsTest extends TestCase{
	
	static CpeActions cpeActions = new CpeActions(confdb: CpeConfDB.deserialize('test.txt'))
	
	void testReboot(){		
		def resp = cpeActions.doReboot(new Reboot())
		
		assert unwrap(resp) instanceof RebootResponse		
	}
	
	void testGetParameterNames(){		
		GetParameterNamesResponse gpnr = unwrap( cpeActions.doGetParameterNames(new GetParameterNames(parameterPath: '')) )
		
		assert gpnr.getParameterList().getAny().size() == 4591
	}
		
	void testGetParameterValues(){
		def gpv = new GetParameterValues(parameterNames: new ParameterNames(any: ['']))
		GetParameterValuesResponse gpvr = unwrap( cpeActions.doGetParameterValues( gpv ) )
		assert gpvr.getParameterList().getAny().size() == 3978
	}
	
	void testGetParameterAttributes(){
		def gpv = new GetParameterAttributes(parameterNames: new ParameterNames(any: ['']))
		GetParameterAttributesResponse gpar = unwrap( cpeActions.doGetParameterAttributes( gpv ) )
		assert gpar.getParameterList().getAny().size() == 3978
	}
	
	void testSetParameterValues(){
		def spv = new SetParameterValues(parameterList: 
			new ParameterValueList(any: [
			                             new ParameterValueStruct(name:'InternetGatewayDevice.DeviceInfo.ProvisioningCode', value:'ProvCode2'),
			                             new ParameterValueStruct(name:'InternetGatewayDevice.DeviceConfig.X_000E50_AutoSave', value:'false'),
			                             ]))
		SetParameterValuesResponse spvr = unwrap( cpeActions.doSetParameterValues( spv ) )
		assert spvr.getStatus() == 0
	}
		
	void testSetParameterAttributes(){
		def spa = new SetParameterAttributes(parameterList: 
			new SetParameterAttributesList(any: [
			                             new SetParameterAttributesStruct(
			                            		 name:'InternetGatewayDevice.DeviceInfo.ProvisioningCode',
			                            		 notification: 2,
			                            		 notificationChange: true,
			                            		 accessList: new AccessList(any:["subscriber"]),
			                            		 accessListChange: true
			                            		 ),
			                             new SetParameterAttributesStruct(name:'InternetGatewayDevice.DeviceConfig.X_000E50_AutoSave', 
			                            		 notification: 2,
			                            		 notificationChange: true,
			                            		 accessList: new AccessList(any:["subscriber"]),
			                            		 accessListChange: true
					                             ),
			                             ]))
		SetParameterValuesResponse spar = unwrap( cpeActions.doSetParameterAttributes( spa ) )
		assert spar.getStatus() == 0
	}
	
	
	
	def unwrap(obj){
		return obj.getBody().getAny().get(0)
	}
	
}
