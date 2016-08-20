package com.playground.xstream.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DateConverter implements Converter {

	public boolean canConvert(Class object) {
		return object.equals(Date.class);
	}

	DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		Date date = (Date) value;
		String rawDate = df.format(date);
		writer.setValue(rawDate);
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		Date date = null;
		try {
			String rawDate = reader.getValue();
			date = df.parse(rawDate);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return date;
	}
}
