package com.playground.sharepoint.search.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.playground.xstream.collection.LinkedHashMapOfKeyValue;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(value = "query")
@XmlRootElement(name = "query")
public class FastSEResponse {

	@XStreamAlias("ElapsedTime")
	@XmlElement(name = "ElapsedTime")
	protected int ElapsedTime;

	@XStreamAlias("PrimaryQueryResult")
	@XmlElement(name = "PrimaryQueryResult")
	protected QueryResult PrimaryQueryResult;

}
