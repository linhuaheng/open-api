package com.wwwarehouse.xdw.openapi.model;


/**
 * AppSysParam entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AmAppSysParam {

	// Fields

	private Long paramUkid;
	private Long appUkid;
	private String paramName;
	private String paramValue;
	private Long isHeader;
	private String paramDescription;
	private String matchValue;
	private String valueSource;

	// Constructors

	/** default constructor */
	public AmAppSysParam() {
	}

	/** minimal constructor */
	public AmAppSysParam(Long paramUkid) {
		this.paramUkid = paramUkid;
	}

	/** full constructor */
	public AmAppSysParam(Long paramUkid, Long appUkid, String paramName,
						 String paramValue, Long isHeader, String paramDescription,
						 String matchValue, String valueSource) {
		this.paramUkid = paramUkid;
		this.appUkid = appUkid;
		this.paramName = paramName;
		this.paramValue = paramValue;
		this.isHeader = isHeader;
		this.paramDescription = paramDescription;
		this.matchValue = matchValue;
		this.valueSource = valueSource;
	}

	// Property accessors

	public Long getParamUkid() {
		return this.paramUkid;
	}

	public void setParamUkid(Long paramUkid) {
		this.paramUkid = paramUkid;
	}

	public Long getAppUkid() {
		return this.appUkid;
	}

	public void setAppUkid(Long appUkid) {
		this.appUkid = appUkid;
	}

	public String getParamName() {
		return this.paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return this.paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public Long getIsHeader() {
		return this.isHeader;
	}

	public void setIsHeader(Long isHeader) {
		this.isHeader = isHeader;
	}

	public String getParamDescription() {
		return this.paramDescription;
	}

	public void setParamDescription(String paramDescription) {
		this.paramDescription = paramDescription;
	}

	public String getMatchValue() {
		return this.matchValue;
	}

	public void setMatchValue(String matchValue) {
		this.matchValue = matchValue;
	}

	public String getValueSource() {
		return this.valueSource;
	}

	public void setValueSource(String valueSource) {
		this.valueSource = valueSource;
	}

}