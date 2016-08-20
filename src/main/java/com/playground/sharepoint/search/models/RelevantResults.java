package com.playground.sharepoint.search.models;

import javax.xml.bind.annotation.XmlElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class RelevantResults {
	@XStreamAlias("RowCount")
	@XmlElement(name = "RowCount")
	protected int RowCount;

	@XStreamAlias("TotalRows")
	@XmlElement(name = "TotalRows")
	protected int TotalRows;

	@XStreamAlias("TotalRowsIncludingDuplicates")
	@XmlElement(name = "TotalRowsIncludingDuplicates")
	protected int TotalRowsIncludingDuplicates;
}
