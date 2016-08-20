package com.playground.sharepoint.search;

import java.io.OutputStream;
import java.io.Writer;
import java.util.Date;

import javax.xml.stream.XMLStreamException;

import org.codehaus.jettison.AbstractXMLStreamWriter;
import org.codehaus.jettison.mapped.Configuration;

import com.playground.sharepoint.search.models.FastSEResponse;
import com.playground.xstream.converter.DateConverter;
import com.playground.xstream.converter.LinkedHashMapOfKeyValueConverter;
import com.playground.xstream.converter.LinkedHashMapOfStringConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JettisonStaxWriter;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.io.xml.StaxWriter;

public class XstreamUtil {

	public static XStream getXstream() {
		XStream xstream = new XStream(new JettisonMappedXmlDriver(new Configuration() {
			{
				// Configuration initializer
				setDropRootElement(true);
				setAttributeKey("$");
			}
		}, true) {
			{
				// JettisonMappedXmlDriver initializer
			}

			@Override
			public HierarchicalStreamWriter createWriter(OutputStream output) {
				HierarchicalStreamWriter streamWriter = super.createWriter(output);
				return streamWriter;
			}

			@Override
			public HierarchicalStreamWriter createWriter(Writer writer) {
				try {
					AbstractXMLStreamWriter streamWriter = (AbstractXMLStreamWriter) mof.createXMLStreamWriter(writer);

					streamWriter.serializeAsArray("query/Properties");
					streamWriter.serializeAsArray("query/PrimaryQueryResult/RelevantResults/Properties");

					if (useSerializeAsArray) {
						return new JettisonStaxWriter(new QNameMap(), streamWriter, getNameCoder(), convention);
					} else {
						return new StaxWriter(new QNameMap(), streamWriter, getNameCoder());
					}
				} catch (final XMLStreamException e) {
					throw new StreamException(e);
				}
			}
		}) {
			// XStream initializer
			{
				// Register custom converts for classes
				registerConverter(new DateConverter());
				registerConverter(new LinkedHashMapOfStringConverter());
				registerConverter(new LinkedHashMapOfKeyValueConverter());

				// Set XStream mode to generate $id for objects for references.
				setMode(XStream.ID_REFERENCES);

				// Ignore unknown attributes or elements of an object.
				ignoreUnknownElements();

				// Automatically process annotations on entities
				autodetectAnnotations(true);

				processAnnotations(FastSEResponse.class);

				alias("query", FastSEResponse.class);

			}

			@Override
			protected void setupImmutableTypes() {
				super.setupImmutableTypes();

				addImmutableType(Date.class);
			}

			@Override
			public String toXML(Object obj) {
				String xml = super.toXML(obj);

				xml = xml.replaceAll("\\$reference", "\\$ref");

				return xml;
			}

			@Override
			public Object fromXML(String xml) {
				xml = "{\"query\":" + (xml.length() > 0 ? xml : "{}") + "}";
				xml = xml.replaceAll("\\$ref", "\\$reference");

				return super.fromXML(xml);
			}
		};
		return xstream;
	}
}