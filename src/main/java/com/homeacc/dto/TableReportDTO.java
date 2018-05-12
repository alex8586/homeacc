package com.homeacc.dto;

import java.math.BigDecimal;

public class TableReportDTO {

	private String category;
	private BigDecimal january;
	private BigDecimal february;
	private BigDecimal march;
	private BigDecimal april;
	private BigDecimal may;
	private BigDecimal june;
	private BigDecimal july;
	private BigDecimal august;
	private BigDecimal september;
	private BigDecimal october;
	private BigDecimal november;
	private BigDecimal december;

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public BigDecimal getJanuary() {
		return january;
	}
	public void setJanuary(BigDecimal january) {
		this.january = january;
	}
	public BigDecimal getFebruary() {
		return february;
	}
	public void setFebruary(BigDecimal february) {
		this.february = february;
	}
	public BigDecimal getMarch() {
		return march;
	}
	public void setMarch(BigDecimal march) {
		this.march = march;
	}
	public BigDecimal getApril() {
		return april;
	}
	public void setApril(BigDecimal april) {
		this.april = april;
	}
	public BigDecimal getMay() {
		return may;
	}
	public void setMay(BigDecimal may) {
		this.may = may;
	}
	public BigDecimal getJune() {
		return june;
	}
	public void setJune(BigDecimal june) {
		this.june = june;
	}
	public BigDecimal getJuly() {
		return july;
	}
	public void setJuly(BigDecimal july) {
		this.july = july;
	}
	public BigDecimal getAugust() {
		return august;
	}
	public void setAugust(BigDecimal august) {
		this.august = august;
	}
	public BigDecimal getSeptember() {
		return september;
	}
	public void setSeptember(BigDecimal september) {
		this.september = september;
	}
	public BigDecimal getOctober() {
		return october;
	}
	public void setOctober(BigDecimal october) {
		this.october = october;
	}
	public BigDecimal getNovember() {
		return november;
	}
	public void setNovember(BigDecimal november) {
		this.november = november;
	}
	public BigDecimal getDecember() {
		return december;
	}
	public void setDecember(BigDecimal december) {
		this.december = december;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TableReportDTO [category=");
		builder.append(category);
		builder.append(", january=");
		builder.append(january);
		builder.append(", february=");
		builder.append(february);
		builder.append(", march=");
		builder.append(march);
		builder.append(", april=");
		builder.append(april);
		builder.append(", may=");
		builder.append(may);
		builder.append(", june=");
		builder.append(june);
		builder.append(", july=");
		builder.append(july);
		builder.append(", august=");
		builder.append(august);
		builder.append(", september=");
		builder.append(september);
		builder.append(", october=");
		builder.append(october);
		builder.append(", november=");
		builder.append(november);
		builder.append(", december=");
		builder.append(december);
		builder.append("]");
		return builder.toString();
	}

}
