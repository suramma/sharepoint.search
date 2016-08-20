package com.playground.xstream.converter;

import com.playground.sharepoint.search.models.KeyValue;
import com.playground.xstream.collection.LinkedHashMapOfKeyValue;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class LinkedHashMapOfKeyValueConverter implements Converter {

	public boolean canConvert(Class object) {
		return object.equals(LinkedHashMapOfKeyValue.class);
	}

	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {

		LinkedHashMapOfKeyValue list = (LinkedHashMapOfKeyValue) value;

		list.forEach((key, val) -> {
			writer.startNode(key);
			context.convertAnother(val);
			writer.endNode();
		});
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

		LinkedHashMapOfKeyValue list = new LinkedHashMapOfKeyValue();

		while (reader.hasMoreChildren()) {
			reader.moveDown();
			String key = reader.getNodeName();
			Object value = context.convertAnother(reader, KeyValue.class);
			list.put(key, (KeyValue) value);
			reader.moveUp();
		}

		return list;
	}
}
