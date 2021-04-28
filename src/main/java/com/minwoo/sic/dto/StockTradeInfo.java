package com.minwoo.sic.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class StockTradeInfo {
	private int infoId; // autoIncrement
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate tradeDate; 
	private String tradeType; // ex)Purchase, Sale
	private String stockCode; 
	private String traderName;
	private int stockAmount;
	private BigDecimal stockPrice;
	private long totalAmount; // = stockNumber * stockPrice

	public int getInfoId() {
		return infoId;
	}

	public void setInfoId(int infoId) {
		this.infoId = infoId;
	}

	public LocalDate getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(LocalDate tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName;
	}

	public int getStockAmount() {
		return stockAmount;
	}

	public void setStockAmount(int stockAmount) {
		this.stockAmount = stockAmount;
	}

	public BigDecimal getStockPrice() {
		return stockPrice;
	}

	public void setStockPrice(BigDecimal stockPrice) {
		this.stockPrice = stockPrice;
	}

	public long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Override
	public String toString() {
		return "StockTradeInfo{" +
				"infoId=" + infoId +
				", tradeDate=" + tradeDate +
				", tradeType='" + tradeType + '\'' +
				", stockCode='" + stockCode + '\'' +
				", traderName='" + traderName + '\'' +
				", stockAmount=" + stockAmount +
				", stockPrice=" + stockPrice +
				", totalAmount=" + totalAmount +
				'}';
	}

}
