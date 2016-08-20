package com.playground.sharepoint.search;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.Gson;
import com.playground.sharepoint.search.models.FastSEResponse;
import com.thoughtworks.xstream.XStream;

public class App {

	static PoolingHttpClientConnectionManager connManager;
	static CloseableHttpClient httpclient;

	private final static Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		FastSEResponse var = getFromXml();
		Gson gson = new Gson();
		String json = gson.toJson(var);
		logger.info("From XML Done!!");
		logger.info(json);

		FastSEResponse var1 = getFromJson();
		String json1 = gson.toJson(var1);
		logger.info("From JSON Done!!");
		logger.info(json1);
	}

	public static FastSEResponse getFromXml() {

		FastSEResponse fastseResponse = new FastSEResponse();

		try {
			String fileName = "resources/query.xml";

			String abspath = new File(fileName).getAbsolutePath();
			File file = new File(abspath);
			String response = FileUtils.readFileToString(file);

			JAXBContext jaxbContext;
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				dbf.setNamespaceAware(true);
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(IOUtils.toInputStream(response));
				Element fooSubtree = doc.getDocumentElement();

				jaxbContext = JAXBContext.newInstance(FastSEResponse.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				fastseResponse = jaxbUnmarshaller.unmarshal(fooSubtree, FastSEResponse.class).getValue();

			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Done with extracting XML response");

			return fastseResponse;
		} catch (Exception e) {
			logger.error("EXCEPTION: " + e.getMessage(), e);
		}

		return null;
	}

	public static FastSEResponse getFromJson() {

		FastSEResponse fastseResponse = new FastSEResponse();

		try {
			String fileName = "resources/query.json";

			String abspath = new File(fileName).getAbsolutePath();
			File file = new File(abspath);
			String response = FileUtils.readFileToString(file);

			try {
				FastSEResponse user = null;
				XStream xstream = XstreamUtil.getXstream();

				if (StringUtils.isNotEmpty(response)) {
					user = (FastSEResponse) xstream.fromXML(response);
				}
				return user;

			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Done with extracting JSON response");

			return fastseResponse;
		} catch (Exception e) {
			logger.error("EXCEPTION: " + e.getMessage(), e);
		}

		return null;
	}
}
