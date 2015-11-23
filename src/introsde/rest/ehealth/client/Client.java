package introsde.rest.ehealth.client;

import java.net.URI;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.InputStream;

import java.util.List;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.Source;
import java.io.StringWriter;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.OutputKeys;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import javax.xml.xpath.*;
import java.net.URLEncoder;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Client {

	public static File file_json, file_xml;
    public static FileWriter fw_json, fw_xml;
    public static BufferedWriter bw_xml, bw_json;
    public static String BASE_URI;

	public static void main(String[] args) throws Exception{

        BASE_URI = "https://agile-tundra-4340.herokuapp.com/sdelab/";

		// ###############
        // Init file
        // ###############

        initFile();

        // ###############

        String res_xml = "";

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc;
        NodeList nList;
        Node nNode;
        int person_size = 0;
        Element eElement;
        String first_person_id = "";
        doc = dBuilder.parse(BASE_URI+"person");
        nList = doc.getElementsByTagName("person");

        try {
        	//System.out.println("nList => " +nList);
	        if(doc != null){
	        	nList = doc.getElementsByTagName("person");
		        nNode = nList.item(0);
		        person_size = 0;
		        eElement = (Element) nNode;
		        first_person_id = eElement.getElementsByTagName("idPerson").item(0).getTextContent();
	        }
	        //System.out.println("doc => " + doc);
        } catch(Exception e){}

        URL url;
        HttpURLConnection connection;
        BufferedReader in, ins;
        String inputLine;
        StringBuffer response = new StringBuffer();

        // ##################
        // ## Step 3.1 XML ##
        // ##################

        try {
            System.out.println("Executing Step 3.1 XML");
            url = new URL(BASE_URI+"person");
            connection = (HttpURLConnection)url.openConnection();
            //System.out.println(connection);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/xml");
            connection.setRequestProperty("Content-Type", "application/xml");

            person_size = nList.getLength();

            res_xml += "Request #1 GET /person Accept: APPLICATION/XML Content-Type: APPLICATION/XML \n";
            System.out.println("Request #1 GET /person Accept: APPLICATION/XML Content-Type: APPLICATION/XML \n");

            if(person_size > 3){
                res_xml += "=> Result: OK \n";
                System.out.println("=> Result: OK \n");
            } else {
                res_xml += "=> Result: ERROR \n";
                System.out.println("=> Result: ERROR \n");
            }
            res_xml += "=> HTTP Status: " + connection.getResponseCode()  + "\n";
            System.out.println("=> HTTP Status: " + connection.getResponseCode()  + "\n");
            res_xml += "\n";
            System.out.println("\n");

            in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();


            res_xml += prettyFormat(response.toString());
            System.out.println(prettyFormat(response.toString()));

            res_xml += "\n\n";
            System.out.println("\n\n");
        } catch(Exception e){}

        // ###################
        // ## Step 3.1 JSON ##
        // ###################

        String res_json = "";
        ObjectMapper mapper = new ObjectMapper();
        Object ob = new Object();

        try {
            System.out.println("Executing Step 3.1 JSON");
            url = new URL(BASE_URI+"person");
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");

            res_json += "Request #1 GET /person Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON \n";
            System.out.println("Request #1 GET /person Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON \n");
            if(person_size > 3){
                res_json += "=> Result: OK \n";
                System.out.println("=> Result: OK \n");
            } else {
                res_json += "=> Result: ERROR \n";
                System.out.println("=> Result: ERROR \n");
            }
            res_json += "=> HTTP Status: " + connection.getResponseCode()  + "\n";
            System.out.println("=> HTTP Status: " + connection.getResponseCode()  + "\n");
            res_json += "\n";
            System.out.println("\n");

            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            ob = mapper.readValue(response.toString(), Object.class);        

            res_json += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ob);
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ob));

            res_json += "\n\n";
            System.out.println("\n\n");
        } catch(Exception e){}

        // ##################
        // ## Step 3.2 XML ##
        // ##################

        try{
            System.out.println("Executing Step 3.2 XML");
            url = new URL(BASE_URI+"person/"+first_person_id);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/xml");
            connection.setRequestProperty("Content-Type", "application/xml");

            res_xml += "Request #2 GET /person/first_person_id Accept: APPLICATION/XML Content-Type: APPLICATION/XML \n";
            System.out.println("Request #2 GET /person/first_person_id Accept: APPLICATION/XML Content-Type: APPLICATION/XML \n");
            if(connection.getResponseCode() == 200 || connection.getResponseCode() == 202){
                res_xml += "=> Result: OK \n";
                System.out.println("=> Result: OK \n");
            } else {
                res_xml += "=> Result: ERROR \n";
                System.out.println("=> Result: ERROR \n");
            }
            res_xml += "=> HTTP Status: " + connection.getResponseCode()  + "\n";
            System.out.println("=> HTTP Status: " + connection.getResponseCode()  + "\n");
            res_xml += "\n";
            System.out.println("\n");

            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            res_xml += prettyFormat(response.toString());
            System.out.println(prettyFormat(response.toString()));

            res_xml += "\n\n";
            System.out.println("\n\n");
        } catch(Exception e){}

        // ###################
        // ## Step 3.2 JSON ##
        // ###################

        try{
            System.out.println("Executing Step 3.2 JSON");
            url = new URL(BASE_URI+"person/"+first_person_id);
            connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(2000);
            
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");

            res_json += "Request #2 GET /person/first_person_id Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON \n";
            System.out.println("Request #2 GET /person/first_person_id Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON \n");
            if(connection.getResponseCode() == 200 || connection.getResponseCode() == 202){
                res_json += "=> Result: OK \n";
                System.out.println("=> Result: OK \n");
            } else {
                res_json += "=> Result: ERROR \n";
                System.out.println("=> Result: ERROR \n");
            }
            res_json += "=> HTTP Status: " + connection.getResponseCode()  + "\n";
            System.out.println("=> HTTP Status: " + connection.getResponseCode()  + "\n");
            res_json += "\n";
            System.out.println("\n");

            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            ob = mapper.readValue(response.toString(), Object.class);        

            res_json += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ob);
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ob));

            res_json += "\n\n";
            System.out.println("\n\n");
        } catch(Exception e){}

        // ##################
        // ## Step 3.3 XML ##
        // ##################

        OutputStreamWriter out;
        try{
            System.out.println("Executing Step 3.3 XML");
            url = new URL(BASE_URI+"person/"+first_person_id);
            connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(3000);
            
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Accept", "application/xml");
            connection.setRequestProperty("Content-Type", "application/xml");
            connection.setDoOutput(true);
            out = new OutputStreamWriter(connection.getOutputStream());
            out.write("<person><firstname>Damiano</firstname></person>");
            out.close();
            //connection.getInputStream();

            res_xml += "Request #3 PUT /person/first_person_id Accept: APPLICATION/XML Content-Type: APPLICATION/XML \n";
            System.out.println("Request #3 PUT /person/first_person_id Accept: APPLICATION/XML Content-Type: APPLICATION/XML \n");
            if(connection.getResponseCode() == 201){
                res_xml += "=> Result: OK \n";
                System.out.println("=> Result: OK \n");
            } else {
                res_xml += "=> Result: ERROR \n";
                System.out.println("=> Result: ERROR \n");
            }
            res_xml += "=> HTTP Status: " + connection.getResponseCode()  + "\n";
            System.out.println("=> HTTP Status: " + connection.getResponseCode()  + "\n");

            res_xml += "\n\n";
            System.out.println("\n\n");
        } catch(Exception e){}

        // ###################
        // ## Step 3.3 JSON ##
        // ###################

        try{
            System.out.println("Executing Step 3.3 JSON");
            url = new URL(BASE_URI+"person/"+first_person_id);
            connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(2000);
            
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            out = new OutputStreamWriter(connection.getOutputStream());
            out.write("{\"firstname\":\"Damiano\"}");
            out.close();
            //connection.getInputStream();

            res_json += "Request #3 PUT /person/first_person_id Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON \n";
            System.out.println("Request #3 PUT /person/first_person_id Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON \n");
            if(connection.getResponseCode() == 201){
                res_json += "=> Result: OK \n";
                System.out.println("=> Result: OK \n");
            } else {
                res_json += "=> Result: ERROR \n";
                System.out.println("=> Result: ERROR \n");
            }
            res_json += "=> HTTP Status: " + connection.getResponseCode()  + "\n";
            System.out.println("=> HTTP Status: " + connection.getResponseCode()  + "\n");

            res_json += "\n\n";
            System.out.println("\n\n");
        } catch(Exception e){}
        
        // ##################
        // ## Step 3.4 XML ##
        // ##################

        String last_person_id_xml = "", last_person_id_json = "";
        XPath xpath;

        try{
            System.out.println("Executing Step 3.4 XML");
            url = new URL(BASE_URI+"person/");
            connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(2000);
            
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/xml");
            connection.setRequestProperty("Content-Type", "application/xml");
            connection.setDoOutput(true);
            
            // Send post request
            out = new OutputStreamWriter(connection.getOutputStream());
            out.write("<person><birthdate>1945-01-01</birthdate><lastname>Norris</lastname><lifeStatus><lifeStatus><MeasureType>weight</MeasureType><value>78.9</value></lifeStatus><lifeStatus><MeasureType>height</MeasureType><value>172</value></lifeStatus></lifeStatus><firstname>Chuck</firstname></person>");
            out.close();

            res_xml += "Request #4 POST /person Accept: APPLICATION/XML Content-Type: APPLICATION/XML \n";
            System.out.println("Request #4 POST /person Accept: APPLICATION/XML Content-Type: APPLICATION/XML \n");
            if(connection.getResponseCode() == 200){
                res_xml += "=> Result: OK \n";
                System.out.println("=> Result: OK \n");
            } else {
                res_xml += "=> Result: ERROR \n";
                System.out.println("=> Result: ERROR \n");
            }
            res_xml += "=> HTTP Status: " + connection.getResponseCode()  + "\n";
            System.out.println("=> HTTP Status: " + connection.getResponseCode()  + "\n");

            res_xml += "\n\n";
            System.out.println("\n\n");

            xpath = XPathFactory.newInstance().newXPath();
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse("https://warm-earth-3665.herokuapp.com/sdelab/person/");
            last_person_id_xml = xpath.evaluate("//person[last()]/idPerson/text()", doc);
        } catch(Exception e){}

        // ##################
        // ## Step 3.5 XML ##
        // ##################

        try{
            System.out.println("Executing Step 3.5 XML");
            url = new URL(BASE_URI+"person/"+last_person_id_xml);
            connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(2000);
            
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/xml");
            connection.setRequestMethod("DELETE");
            connection.connect();

            res_xml += "Request #5 DELETE /person/"+last_person_id_xml +" Accept: APPLICATION/XML Content-Type: APPLICATION/XML \n";
            System.out.println("Request #5 DELETE /person/"+last_person_id_xml +" Accept: APPLICATION/XML Content-Type: APPLICATION/XML \n");
            if(connection.getResponseCode() == 204){
                res_xml += "=> Result: OK \n";
                System.out.println("=> Result: OK \n");
            } else {
                res_xml += "=> Result: ERROR \n";
                System.out.println("=> Result: ERROR \n");
            }
            res_xml += "=> HTTP Status: " + connection.getResponseCode()  + "\n";
            System.out.println("=> HTTP Status: " + connection.getResponseCode()  + "\n");

            res_xml += "\n\n";
            System.out.println("\n\n");
        }catch(Exception e){}

        // ###################
        // ## Step 3.4 JSON ##
        // ###################

        try{
            System.out.println("Executing Step 3.4 JSON");
            url = new URL(BASE_URI);
            connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(2000);
            
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            
            // Send post request
            out = new OutputStreamWriter(connection.getOutputStream());
            out.write("{\"firstname\":\"Chuck\",\"lastname\":\"Norris\",\"birthdate\":\"1945-01-01\",\"lifeStatus\":[{\"value\":78.9,\"MeasureType\":\"weight\"},{\"value\":172,\"MeasureType\":\"height\"}]}");
            out.close();

            res_json += "Request #4 POST /person Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON \n";
            System.out.println("Request #4 POST /person Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON \n");
            if(connection.getResponseCode() == 200){
                res_json += "=> Result: OK \n";
                System.out.println("=> Result: OK \n");
            } else {
                res_json += "=> Result: ERROR \n";
                System.out.println("=> Result: ERROR \n");
            }
            res_json += "=> HTTP Status: " + connection.getResponseCode()  + "\n";
            System.out.println("=> HTTP Status: " + connection.getResponseCode()  + "\n");

            res_json += "\n\n";
            System.out.println("\n\n");

            xpath = XPathFactory.newInstance().newXPath();
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse("https://warm-earth-3665.herokuapp.com/sdelab/person/");
            last_person_id_json = xpath.evaluate("//person[last()]/idPerson/text()", doc);
        } catch(Exception e){}

        // ###################
        // ## Step 3.5 JSON ##
        // ###################
    
        try{
            System.out.println("Executing Step 3.5 JSON");
            url = new URL(BASE_URI+"person/"+last_person_id_json);
            connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(2000);
            
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("DELETE");
            connection.connect();

            res_json += "Request #5 DELETE /person/"+last_person_id_json +" Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON \n";
            System.out.println("Request #5 DELETE /person/"+last_person_id_json +" Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON \n");
            if(connection.getResponseCode() == 204){
                res_json += "=> Result: OK \n";
                System.out.println("=> Result: OK \n");
            } else {
                res_json += "=> Result: ERROR \n";
                System.out.println("=> Result: ERROR \n");
            }
            res_json += "=> HTTP Status: " + connection.getResponseCode()  + "\n";
            System.out.println("=> HTTP Status: " + connection.getResponseCode()  + "\n");

            res_json += "\n\n";
            System.out.println("\n\n");
        } catch(Exception e){}
        
        // #############################################
        // ## Step 3.2 XML to the person just deleted ##
        // #############################################

        try{
            System.out.println("Executing Step 3.2 XML to the person just deleted");
            url = new URL(BASE_URI+"person/"+last_person_id_xml);
            connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(6000);
            
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/xml");
            connection.setRequestProperty("Content-Type", "application/xml");

            res_xml += "Request #2 GET /person/"+last_person_id_xml + " Accept: APPLICATION/XML Content-Type: APPLICATION/XML \n";
            System.out.println("Request #2 GET /person/"+last_person_id_xml + " Accept: APPLICATION/XML Content-Type: APPLICATION/XML \n");
            if(connection.getResponseCode() != 500 && connection.getResponseCode() != 404){
                res_xml += "=> Result: OK \n";
                System.out.println("=> Result: OK \n");
            } else {
                res_xml += "=> Result: ERROR \n";
                System.out.println("=> Result: ERROR \n");
            }
            res_xml += "=> HTTP Status: " + connection.getResponseCode()  + "\n";
            System.out.println("=> HTTP Status: " + connection.getResponseCode()  + "\n");
            res_xml += "\n";
            System.out.println("\n");

            response = new StringBuffer();

            try{
                if (connection.getResponseCode() == 500) {
                    ins = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                } else {
                    ins = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                }

                while ((inputLine = ins.readLine()) != null) {
                    response.append(inputLine);
                }

                ins.close();
            } catch(Exception e){}
            
            res_xml += prettyFormat(response.toString());
            System.out.println(prettyFormat(response.toString()));

            res_xml += "\n\n";
            System.out.println(prettyFormat(response.toString()));
        } catch(Exception e){}

        // ##############################################
        // ## Step 3.2 JSON to the person just deleted ##
        // ##############################################

        try{
        	System.out.println("Executing Step 3.2 JSON to the person just deleted");
            url = new URL(BASE_URI+"person/"+last_person_id_json);
            connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(2000);
            
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");

            res_json += "Request #2 GET /person/"+last_person_id_json + " Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON \n";
            System.out.println("Request #2 GET /person/"+last_person_id_json + " Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON \n");
            if(connection.getResponseCode() != 500 && connection.getResponseCode() != 404){
                res_json += "=> Result: OK \n";
                System.out.println("=> Result: OK \n");
            } else {
                res_json += "=> Result: ERROR \n";
                System.out.println("=> Result: ERROR \n");
            }
            res_json += "=> HTTP Status: " + connection.getResponseCode()  + "\n";
            System.out.println("=> HTTP Status: " + connection.getResponseCode()  + "\n");
            res_json += "\n";
            System.out.println("\n");

            response = new StringBuffer();
            try{
                if (connection.getResponseCode() == 500) {
                    ins = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                } else {
                    ins = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                }
                
                response = new StringBuffer();

                while ((inputLine = ins.readLine()) != null) {
                    response.append(inputLine);
                }

                ins.close();
            } catch(Exception e){}

            ob = mapper.readValue(response.toString(), Object.class);        

            res_json += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ob);
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ob));

            res_json += "\n\n";
            System.out.println("\n\n");
        } catch(Exception e){}

        // ##################
        // ## Step 3.6 XML ##
        // ##################

        int size = 0, measures_size = 0;
        List<String> measure_types = new ArrayList<String>();

        try {
            System.out.println("Executing Step 3.6 XML");
            url = new URL(BASE_URI+"measureTypes");
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/xml");
            connection.setRequestProperty("Content-Type", "application/xml");

            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(BASE_URI+"measureTypes");

            nList = doc.getElementsByTagName("measureType");

            measures_size = nList.getLength();

            for (int temp = 0; temp < nList.getLength(); temp++) {
                nNode = nList.item(temp);

                String content = nNode.getTextContent();
                measure_types.add(content);
            }
            
            size = measure_types.size();

            res_xml += "Request #9 GET /measureTypes Accept: APPLICATION/XML Content-Type: APPLICATION/XML \n";
            System.out.println("Request #9 GET /measureTypes Accept: APPLICATION/XML Content-Type: APPLICATION/XML \n");
            if(size >= 2){
                res_xml += "=> Result: OK \n";
                System.out.println("=> Result: OK \n");
            } else {
                res_xml += "=> Result: ERROR \n";
                System.out.println("=> Result: ERROR \n");
            }
            res_xml += "=> HTTP Status: " + connection.getResponseCode()  + "\n";
            System.out.println("=> HTTP Status: " + connection.getResponseCode()  + "\n");
            res_xml += "\n";
            System.out.println("\n");

            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            res_xml += prettyFormat(response.toString());
            System.out.println(prettyFormat(response.toString()));

            res_xml += "\n\n";
            System.out.println("\n\n");
        } catch(Exception e){}

        // ###################
        // ## Step 3.6 JSON ##
        // ###################

        try{
            System.out.println("Executing Step 3.6 JSON");
            url = new URL(BASE_URI+"measureTypes");
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");

            res_json += "Request #9 GET /measureTypes Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON \n";
            System.out.println("Request #9 GET /measureTypes Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON \n");
            if(size >= 2){
                res_json += "=> Result: OK \n";
                System.out.println("=> Result: OK \n");
            } else {
                res_json += "=> Result: ERROR \n";
                System.out.println("=> Result: ERROR \n");
            }
            res_json += "=> HTTP Status: " + connection.getResponseCode()  + "\n";
            System.out.println("=> HTTP Status: " + connection.getResponseCode()  + "\n");
            res_json += "\n";
            System.out.println("\n");

            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            ob = mapper.readValue(response.toString(), Object.class);        

            res_json += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ob);
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ob));

            res_json += "\n\n";
            System.out.println("\n\n");
        } catch(Exception e){}

        // ####################################

        xpath = XPathFactory.newInstance().newXPath();
        dbFactory = DocumentBuilderFactory.newInstance();
        dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.parse(BASE_URI+"person/");
        last_person_id_xml = xpath.evaluate("//person[last()]/idPerson/text()", doc);

        String measure_id = "";
        String measureType = "";
        boolean measure_id_bool = false;
        boolean measureType_bool = false;
        String[] parts;

        for(int i=0; i<size; i++) {

            try {
                // ##################
                // ## Step 3.7 XML ##
                // ##################

                System.out.println("Executing Step 3.7 XML");

                parts = measure_types.get(i).split(" ");

                if(parts.length >= 2){
                    url = new URL(BASE_URI+"person/"+first_person_id+"/"+URLEncoder.encode(measure_types.get(i), "UTF-8"));
                } else {
                    url = new URL(BASE_URI+"person/"+first_person_id+"/"+measure_types.get(i));
                }

                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/xml");
                connection.setRequestProperty("Content-Type", "application/xml");

                xpath = XPathFactory.newInstance().newXPath();
                dbFactory = DocumentBuilderFactory.newInstance();
                dBuilder = dbFactory.newDocumentBuilder();
                if(parts.length >= 2){
                    doc = dBuilder.parse(BASE_URI+"person/"+first_person_id+"/"+URLEncoder.encode(measure_types.get(i), "UTF-8"));
                } else {
                    doc = dBuilder.parse(BASE_URI+ "person/"+first_person_id+"/"+measure_types.get(i));
                }

                if(!measure_id_bool && !measureType_bool){
                    measure_id = xpath.evaluate("//Measure[1]/mid/text()", doc);
                    measureType = xpath.evaluate("//Measure[1]/MeasureType/text()", doc);
                    if(!measure_id.isEmpty() && !measureType.isEmpty()){
                        measure_id_bool = true;
                        measureType_bool = true;
                    }
                }

                nList = doc.getElementsByTagName("Measure");

                measures_size = nList.getLength();

                res_xml += "Request #6 GET /person/"+first_person_id+"/"+measure_types.get(i) + " Accept: APPLICATION/XML Content-Type: APPLICATION/XML \n";
                System.out.println("Request #6 GET /person/"+first_person_id+"/"+measure_types.get(i) + " Accept: APPLICATION/XML Content-Type: APPLICATION/XML \n");
                if(measures_size == 0){
                    res_xml += "=> Result: ERROR \n";
                    System.out.println("=> Result: ERROR \n");
                } else {
                    if(connection.getResponseCode() != 500 && connection.getResponseCode() != 505 && connection.getResponseCode() != 404){
                        res_xml += "=> Result: OK \n";
                        System.out.println("=> Result: OK \n");
                    } else if(measures_size == 0) {
                        res_xml += "=> Result: ERROR \n";
                        System.out.println("=> Result: ERROR \n");
                    }
                }
                res_xml += "=> HTTP Status: " + connection.getResponseCode()  + "\n";
                System.out.println("=> HTTP Status: " + connection.getResponseCode()  + "\n");
                res_xml += "\n";
                System.out.println("\n");

                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                res_xml += prettyFormat(response.toString());
                System.out.println(prettyFormat(response.toString()));

                res_xml += "\n\n";
                System.out.println("\n\n");

                // ##################
                // ## Step 3.7 XML ##
                // ##################

                if(Integer.parseInt(last_person_id_xml) > Integer.parseInt(first_person_id)){
                    System.out.println("Executing Step 3.7 XML");
                    if(parts.length >= 2){
                        url = new URL(BASE_URI+"person/"+last_person_id_xml+"/"+URLEncoder.encode(measure_types.get(i), "UTF-8"));
                    } else {
                        url = new URL(BASE_URI+"person/"+last_person_id_xml+"/"+measure_types.get(i));
                    }
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Accept", "application/xml");
                    connection.setRequestProperty("Content-Type", "application/xml");

                    if(parts.length >= 2){
                        doc = dBuilder.parse(BASE_URI+"person/"+last_person_id_xml+"/"+URLEncoder.encode(measure_types.get(i), "UTF-8"));
                    } else {
                        doc = dBuilder.parse(BASE_URI+"person/"+last_person_id_xml+"/"+measure_types.get(i));
                    }

                    nList = doc.getElementsByTagName("Measure");

                    measures_size = nList.getLength();

                    res_xml += "Request #6 GET /person/"+last_person_id_xml+"/"+measure_types.get(i) + " Accept: APPLICATION/XML Content-Type: APPLICATION/XML \n";
                    System.out.println("Request #6 GET /person/"+last_person_id_xml+"/"+measure_types.get(i) + " Accept: APPLICATION/XML Content-Type: APPLICATION/XML \n");
                    if(measures_size == 0){
                        res_xml += "=> Result: ERROR \n";
                        System.out.println("=> Result: ERROR \n");
                    } else {
                        if(connection.getResponseCode() != 500 && connection.getResponseCode() != 505 && connection.getResponseCode() != 404){
                            res_xml += "=> Result: OK \n";
                            System.out.println("=> Result: OK \n");
                        } else if(measures_size == 0) {
                            res_xml += "=> Result: ERROR \n";
                            System.out.println("=> Result: ERROR \n");
                        }
                    }
                    res_xml += "=> HTTP Status: " + connection.getResponseCode()  + "\n";
                    System.out.println("=> HTTP Status: " + connection.getResponseCode()  + "\n");
                    res_xml += "\n";
                    System.out.println("\n");

                    in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    in.close();

                    res_xml += prettyFormat(response.toString());
                    System.out.println(prettyFormat(response.toString()));

                    res_xml += "\n\n";
                    System.out.println("\n\n");
                }
            } catch (Exception e) {}                
        }

        for(int i=0; i<size; i++) {

            try {
                // ###################
                // ## Step 3.7 JSON ##
                // ###################

                System.out.println("Executing Step 3.7 JSON");

                parts = measure_types.get(i).split(" ");

                if(parts.length >= 2){
                    url = new URL(BASE_URI+"person/"+first_person_id+"/"+URLEncoder.encode(measure_types.get(i), "UTF-8"));
                } else {
                    url = new URL(BASE_URI+"person/"+first_person_id+"/"+measure_types.get(i));
                }
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Content-Type", "application/json");

                res_json += "Request #6 GET /person/"+first_person_id+"/"+measure_types.get(i) + " Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON \n";
                System.out.println("Request #6 GET /person/"+first_person_id+"/"+measure_types.get(i) + " Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON \n");
                if(measures_size == 0){
                    res_json += "=> Result: ERROR \n";
                    System.out.println("=> Result: ERROR \n");
                } else {
                    if(connection.getResponseCode() != 500 && connection.getResponseCode() != 505 && connection.getResponseCode() != 404){
                        res_json += "=> Result: OK \n";
                        System.out.println("=> Result: OK \n");
                    } else if(measures_size == 0) {
                        res_json += "=> Result: ERROR \n";
                        System.out.println("=> Result: ERROR \n");
                    }
                }
                res_json += "=> HTTP Status: " + connection.getResponseCode()  + "\n";
                System.out.println("=> HTTP Status: " + connection.getResponseCode()  + "\n");
                res_json += "\n";
                System.out.println("\n");

                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                ob = mapper.readValue(response.toString(), Object.class);        

                res_json += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ob);
                System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ob));

                res_json += "\n\n";
                System.out.println("\n\n");

                // ###################
                // ## Step 3.7 JSON ##
                // ###################

                if(Integer.parseInt(last_person_id_json) > Integer.parseInt(first_person_id)){
                    System.out.println("Executing Step 3.7 JSON");
                    if(parts.length >= 2){
                        url = new URL(BASE_URI+"person/"+last_person_id_json+"/"+URLEncoder.encode(measure_types.get(i), "UTF-8"));
                    } else {
                        url = new URL(BASE_URI+"person/"+last_person_id_json+"/"+measure_types.get(i));
                    }
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Accept", "application/json");
                    connection.setRequestProperty("Content-Type", "application/json");

                    res_json += "Request #6 GET /person/"+last_person_id_json+"/"+measure_types.get(i) + " Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON \n";
                    System.out.println("Request #6 GET /person/"+last_person_id_json+"/"+measure_types.get(i) + " Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON \n");
                    if(measures_size == 0){
                        res_json += "=> Result: ERROR \n";
                        System.out.println("=> Result: ERROR \n");
                    } else {
                        if(connection.getResponseCode() != 500 && connection.getResponseCode() != 505 && connection.getResponseCode() != 404){
                            res_json += "=> Result: OK \n";
                            System.out.println("=> Result: OK \n");
                        } else if(measures_size == 0) {
                            res_json += "=> Result: ERROR \n";
                            System.out.println("=> Result: ERROR \n");
                        }
                    }
                    res_json += "=> HTTP Status: " + connection.getResponseCode()  + "\n";
                    System.out.println("=> HTTP Status: " + connection.getResponseCode()  + "\n");
                    res_json += "\n";
                    System.out.println("\n");

                    in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    in.close();

                    ob = mapper.readValue(response.toString(), Object.class);        

                    res_json += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ob);
                    System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ob));

                    res_json += "\n\n";
                    System.out.println("\n\n");
                }
            } catch(Exception e){}

        }

        // ##################
        // ## Step 3.8 XML ##
        // ##################

        try {
            System.out.println("Executing Step 3.8 XML");
            
            url = new URL(BASE_URI+"person/"+first_person_id+"/"+measureType+"/"+measure_id);
            connection = (HttpURLConnection)url.openConnection();
            
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/xml");
            connection.setRequestProperty("Content-Type", "application/xml");

			res_xml += "Request #7 GET /person/"+first_person_id+"/"+measureType+"/"+measure_id +" Accept: APPLICATION/XML Content-Type: APPLICATION/XML \n";
			System.out.println("Request #7 GET /person/"+first_person_id+"/"+measureType+"/"+measure_id +" Accept: APPLICATION/XML Content-Type: APPLICATION/XML \n");
            if(connection.getResponseCode() == 200){
                res_xml += "=> Result: OK \n";
                System.out.println("=> Result: OK \n");
            } else {
                res_xml += "=> Result: ERROR \n";
                System.out.println("=> Result: ERROR \n");
            }

            res_xml += "=> HTTP Status: " + connection.getResponseCode() + "\n";
            res_xml += "\n";

            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            res_xml += prettyFormat(response.toString());
            System.out.println(prettyFormat(response.toString()));

            res_xml += "\n\n";
            System.out.println("\n\n");
        } catch(Exception e){}

        // ###################
        // ## Step 3.8 JSON ##
        // ###################

        try {
            System.out.println("Executing Step 3.8 JSON");
            
            url = new URL(BASE_URI+"person/"+first_person_id+"/"+measureType+"/"+measure_id);
            connection = (HttpURLConnection)url.openConnection();
            
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");

            res_json += "Request #7 GET /person/"+first_person_id+"/"+measureType+"/"+measure_id +" Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON \n";
            System.out.println("Request #7 GET /person/"+first_person_id+"/"+measureType+"/"+measure_id +" Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON \n");
            if(connection.getResponseCode() == 200){
                res_json += "=> Result: OK \n";
                System.out.println("=> Result: OK \n");
            } else {
                res_json += "=> Result: ERROR \n";
                System.out.println("=> Result: ERROR \n");
            }
            res_json += "=> HTTP Status: " + connection.getResponseCode() + "\n";
            System.out.println("=> HTTP Status: " + connection.getResponseCode() + "\n");
            res_json += "\n";
            System.out.println("\n");

            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            ob = mapper.readValue(response.toString(), Object.class);        

            res_json += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ob);
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ob));

            res_json += "\n\n";
            System.out.println("\n\n");
        } catch(Exception e){}

        // ##################################
        // ## Getting the list of measures ##
        // ##################################

        System.out.println("Getting the list of measures!");
        dbFactory = DocumentBuilderFactory.newInstance();
        dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.parse(BASE_URI+"measureTypes");

        nList = doc.getElementsByTagName("measureType");

        nNode = nList.item(0);

        String my_measure = nNode.getTextContent();

        // #############################################
        // ## Executing R#6 to get the measures count ##
        // #############################################

        int measure_count_first = 0;

        try{
        	System.out.println("Executing R#6 to get the measure count");
	        dbFactory = DocumentBuilderFactory.newInstance();
	        dBuilder = dbFactory.newDocumentBuilder();
	        doc = dBuilder.parse(BASE_URI+"person/"+first_person_id+"/"+my_measure);

	        nList = doc.getElementsByTagName("Measure");
	        measure_count_first = nList.getLength();
        } catch(Exception e){}

        // ###################
        // ## Step 3.9 JSON ##
        // ###################

        try{
        	System.out.println("Step 3.9 JSON");
            url = new URL(BASE_URI+"person/"+first_person_id+"/"+my_measure);
            connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(2000);
            
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            
            // Send post request
            out = new OutputStreamWriter(connection.getOutputStream());
            out.write("{\"value\": \"160\"}");
            out.close();

            res_json += "Request #8 POST /person/"+first_person_id+"/"+my_measure+" Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON \n";
            System.out.println("Request #8 POST /person/"+first_person_id+"/"+my_measure+" Accept: APPLICATION/JSON Content-Type: APPLICATION/JSON \n");

            System.out.println("Executing R#6 to get the measure count");
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(BASE_URI+"person/"+first_person_id+"/"+my_measure);

            nList = doc.getElementsByTagName("Measure");

            int measure_count_last = nList.getLength();
            System.out.println(measure_count_last);
            System.out.println(measure_count_first);

            if(measure_count_last - measure_count_first == 1){
                res_json += "=> Result: OK \n";
                System.out.println("=> Result: OK \n");
            } else {
                res_json += "=> Result: ERROR \n";
                System.out.println("=> Result: ERROR \n");
            }

            res_json += "=> HTTP Status: " + connection.getResponseCode() + "\n";
            System.out.println("=> HTTP Status: " + connection.getResponseCode() + "\n");
            res_json += "\n";
            System.out.println("\n");

            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            ob = mapper.readValue(response.toString(), Object.class);        

            res_json += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ob);
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ob));

            res_json += "\n\n";
            System.out.println("\n\n");
        } catch(Exception e){}

        // #############################################
        // ## Executing R#6 to get the measures count ##
        // #############################################

        try{
        	System.out.println("Executing R#6 to get the measure count");
	        dbFactory = DocumentBuilderFactory.newInstance();
	        dBuilder = dbFactory.newDocumentBuilder();
	        doc = dBuilder.parse(BASE_URI+"person/"+first_person_id+"/"+my_measure);

	        nList = doc.getElementsByTagName("Measure");

	        measure_count_first = nList.getLength();
        } catch(Exception e){}

        // ##################
        // ## Step 3.9 XML ##
        // ##################
        
        try{
        	System.out.println("Step 3.9 XML");
            url = new URL(BASE_URI+"person/"+first_person_id+"/"+my_measure);
            connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(2000);
            
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/xml");
            connection.setRequestProperty("Content-Type", "application/xml");
            connection.setDoOutput(true);
            
            // Send post request
            out = new OutputStreamWriter(connection.getOutputStream());
            out.write("<Measure><value>72.3</value></Measure>");
            out.close();

            res_xml += "Request #8 POST /person/"+first_person_id+"/"+my_measure+" Accept: APPLICATION/XML Content-Type: APPLICATION/XML \n";
            System.out.println("Request #8 POST /person/"+first_person_id+"/"+my_measure+" Accept: APPLICATION/XML Content-Type: APPLICATION/XML \n");

            //System.out.println("Executing R#6 to get the measure count");
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(BASE_URI+"person/"+first_person_id+"/"+my_measure);

            nList = doc.getElementsByTagName("Measure");

            int measure_count_last = nList.getLength();

            if(measure_count_last - measure_count_first == 1){
                res_xml += "=> Result: OK \n";
                System.out.println("=> Result: OK \n");
            } else {
                res_xml += "=> Result: ERROR \n";
                System.out.println("=> Result: ERROR \n");
            }

            res_xml += "=> HTTP Status: " + connection.getResponseCode() + "\n";
            System.out.println("=> HTTP Status: " + connection.getResponseCode() + "\n");
            res_xml += "\n";
            System.out.println("\n");

            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            res_xml += prettyFormat(response.toString());

            res_xml += "\n\n";
        } catch(Exception e){}

        //System.out.println(BASE_URI);

        bw_xml.write(res_xml);
        bw_json.write(res_json);

        bw_xml.close();
        bw_json.close();

	}

	public static void initFile(){
        try {

            file_json = new File("client-server-json.log");
            file_xml = new File("client-server-xml.log");

            // if file doesnt exists, then create it
            if (!file_json.exists()) {
                file_json.createNewFile();
            }

            if (!file_xml.exists()) {
                file_xml.createNewFile();
            }

            fw_json = new FileWriter(file_json.getAbsoluteFile());
            bw_json = new BufferedWriter(fw_json);

            fw_xml = new FileWriter(file_xml.getAbsoluteFile());
            bw_xml = new BufferedWriter(fw_xml);

            System.out.println("Done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String prettyFormat(String input, int indent) {
        try {
            Source xmlInput = new StreamSource(new StringReader(input));
            StringWriter stringWriter = new StringWriter();
            StreamResult xmlOutput = new StreamResult(stringWriter);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", indent);
            Transformer transformer = transformerFactory.newTransformer(); 
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String prettyFormat(String input) {
        return prettyFormat(input, 2);
    }
}