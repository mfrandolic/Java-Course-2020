package hr.fer.zemris.java.custom.scripting.exec.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Demo program that shows usage of {@link SmartScriptEngine}.
 */
public class SmartScriptEngineDemo3 {

	/**
	 * Main method of the program.
	 * 
	 * @param args         command-line arguments (not used)
	 * @throws IOException if I/O error occurs
	 */
	public static void main(String[] args) throws IOException {
		String documentBody = new String(
			Files.readAllBytes(Paths.get("webroot/scripts/brojPoziva.smscr")), 
			StandardCharsets.UTF_8
		);
		
		Map<String,String> parameters = new HashMap<>();
		Map<String,String> persistentParameters = new HashMap<>();
		List<RCCookie> cookies = new ArrayList<>();
		persistentParameters.put("brojPoziva", "3");
		
		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters, cookies);
		new SmartScriptEngine(
			new SmartScriptParser(documentBody).getDocumentNode(), 
			rc
		).execute();
		
		System.out.println("Vrijednost u mapi: " + rc.getPersistentParameter("brojPoziva"));
	}
	
}
