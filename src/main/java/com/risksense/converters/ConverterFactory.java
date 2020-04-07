package com.risksense.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Factory class for creating instances of {@link XMLJSONConverterI}.
 */
@Service
public final class ConverterFactory {

	@Autowired
	private ApplicationContext applicationContext;
	
	private static XMLJSONConverterI xmljsonConverterI;
	
	@Autowired
    public void setSomeThing(XMLJSONConverterI converterI){
		ConverterFactory.xmljsonConverterI = (XMLJSONConverterI) applicationContext.getBean("jsonToXml");
    }
    /**
     * You should implement this method having it return your version of
     * {@link com.risksense.converters.XMLJSONConverterI}.
     *
     * @return {@link com.risksense.converters.XMLJSONConverterI} implementation you created.
     */
    public static final XMLJSONConverterI createXMLJSONConverter() {
       return xmljsonConverterI;
    }
}
