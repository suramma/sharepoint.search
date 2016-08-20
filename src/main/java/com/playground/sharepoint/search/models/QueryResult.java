package com.playground.sharepoint.search.models;

import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class QueryResult {
	@XStreamAlias("QueryId")
	@XmlElement(name = "QueryId")
	public String QueryId;

	@XStreamAlias("QueryRuleId")
	@XmlElement(name = "QueryRuleId")
	public UUID QueryRuleId;

	@XStreamAlias("RelevantResults")
	@XmlElement(name = "RelevantResults")
	public RelevantResults RelevantResults;
}
