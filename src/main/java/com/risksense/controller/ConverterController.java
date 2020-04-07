package com.risksense.controller;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.risksense.converters.ConverterFactory;

@RestController
public class ConverterController {

	private static final Logger logger = LoggerFactory.getLogger(ConverterController.class);

	@GetMapping("jsonToXml")
	public String convertJsonToXml(@RequestParam String jsonFilePath, @RequestParam String xmlFilePath) {

		try {
			File jsonFile = new File(jsonFilePath);
			File xmlFile = new File(xmlFilePath);
			ConverterFactory.createXMLJSONConverter().convertJSONtoXML(jsonFile, xmlFile);
			return "Given Json file has been converted to desired XML and stored in the destination file path";
		} catch (IOException e) {
			logger.error("Error while reading/writing the files", e);
		} 

		return "Error while reading/writing the files";
	}

}
