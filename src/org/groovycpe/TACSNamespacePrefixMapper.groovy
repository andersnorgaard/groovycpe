package org.groovycpe

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class TACSNamespacePrefixMapper extends NamespacePrefixMapper {		private PREFIX_MAP
	
	public String getPreferredPrefix(final String namespaceUri, final String suggestion, final boolean requirePrefix) {
		if(PREFIX_MAP.containsKey(namespaceUri)){
			return PREFIX_MAP.get(namespaceUri);
		} 
		return suggestion;												
	}

	public String[] getPreDeclaredNamespaceUris() {
		return new ArrayList<String>(PREFIX_MAP.keySet()).toArray(new String[1]);
	}
}
