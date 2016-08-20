package com.playground.xstream.converter;

import com.playground.xstream.collection.LinkedHashMapOfString;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class LinkedHashMapOfStringConverter implements Converter {

	public boolean canConvert(Class object) {
		return object.equals(LinkedHashMapOfString.class);
	}

	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {

		LinkedHashMapOfString list = (LinkedHashMapOfString) value;

		list.forEach((key, val) -> {
			writer.startNode(key);
			context.convertAnother(val);
			writer.endNode();
		});
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

		LinkedHashMapOfString list = new LinkedHashMapOfString();

		while (reader.hasMoreChildren()) {
			reader.moveDown();
			String key = reader.getNodeName();
			Object value = context.convertAnother(reader, String.class);
			list.put(key, (String) value);
			reader.moveUp();
		}

		return list;
	}
}
