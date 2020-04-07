package com.risksense.converters;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("jsonToXml")
public class XMLJSONConverterImpl implements XMLJSONConverterI {

	private static final Logger logger = LoggerFactory.getLogger(XMLJSONConverterImpl.class);
	
	private static ObjectMapper objectMapper = new ObjectMapper();

	public static String convert(String json) throws IOException {
		JsonNode jsonNode = null;
		try {
			jsonNode = objectMapper.readTree(json);
		} catch (JsonProcessingException e) {
			logger.error("Error while parsing the Json", e);
			throw new IOException("Error while parsing the Jsons");
		}
		StringBuilder stringBuilder = new StringBuilder();

		processJsonNode(stringBuilder, null, jsonNode);

		return stringBuilder.toString();
	}

	public static void processJsonNode(StringBuilder sb, String key, JsonNode jsonNode) {

		switch (jsonNode.getNodeType()) {

		case ARRAY:
			if (key != null)
				sb.append("<array name=\"" + key + "\">");
			else
				sb.append("<array>");
			jsonNode.forEach(node -> processJsonNode(sb, null, node));
			sb.append("</array>");
			break;
		case OBJECT:
			if (key != null)
				sb.append("<object name=\"" + key + "\">");
			else
				sb.append("<object>");
			Iterator<Map.Entry<String, JsonNode>> it = jsonNode.fields();
			while (it.hasNext()) {
				Map.Entry<String, JsonNode> entry = it.next();
				processJsonNode(sb, entry.getKey(), entry.getValue());
			}
			sb.append("</object>");
			break;
		case NULL:
			if (key != null)
				sb.append("<null name=\"" + key + "\"/>");
			else
				sb.append("<null/>");
			break;
		case NUMBER:
			switch (jsonNode.numberType()) {

			case INT:
			case LONG:
			case BIG_INTEGER:
				if (key != null)
					sb.append("<number name=\"" + key + "\">");
				else
					sb.append("<number>");
				sb.append(jsonNode.bigIntegerValue());
				sb.append("</number>");
				break;
			case DOUBLE:
			case FLOAT:
			case BIG_DECIMAL:
				if (key != null)
					sb.append("<number name=\"" + key + "\">");
				else
					sb.append("<number>");
				sb.append(jsonNode.decimalValue());
				sb.append("</number>");
				break;
			}
			break;
		case BOOLEAN:
			if (key != null)
				sb.append("<boolean name=\"" + key + "\">");
			else
				sb.append("<boolean>");
			sb.append(jsonNode.booleanValue());
			sb.append("</boolean>");
			break;
		case STRING:
			if (key != null)
				sb.append("<string name=\"" + key + "\">");
			else
				sb.append("<string>");
			sb.append(jsonNode.textValue());
			sb.append("</string>");
			break;

		default:

		}

	}

	public static String readFile(File jsonFile) throws IOException {

		StringBuilder sb = new StringBuilder();
		InputStream in = new FileInputStream(jsonFile);
		Charset encoding = Charset.defaultCharset();

		try(Reader reader = new InputStreamReader(in, encoding)){
			int r = 0;
			while ((r = reader.read()) != -1)// Note! use read() rather than readLine()
												// Can process much larger files with read()
			{
				char ch = (char) r;
				sb.append(ch);
			}
		}

		in.close();

		return sb.toString();
	}

	public static void writeFile(File xmlFile, String output) throws IOException {
		FileWriter ofstream = new FileWriter(xmlFile);
		try (BufferedWriter out = new BufferedWriter(ofstream)) {
			out.write(output);
		}
	}

	@Override
	public void convertJSONtoXML(File jsonFile, File xmlFile) throws IOException {
		
		// Reading JSON File
		String json = null;
		json = readFile(jsonFile);

		// Convert JSON to XML
		String xml = convert(json);// State name of root element tag

		// Writing XML File
		writeFile(xmlFile, xml);
	}
}
