package com.minwoo.sic.dto;

public class StockStatisticsInfo {
	private String traderName;
	private int totalTradeStock;
	private int recentThreeTradeStock;
	private int recentTradeStock;

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName;
	}

	public int getTotalTradeStock() {
		return totalTradeStock;
	}

	public void setTotalTradeStock(int totalTradeStock) {
		this.totalTradeStock = totalTradeStock;
	}

	public int getRecentThreeTradeStock() {
		return recentThreeTradeStock;
	}

	public void setRecentThreeTradeStock(int recentThreeTradeStock) {
		this.recentThreeTradeStock = recentThreeTradeStock;
	}

	public int getRecentTradeStock() {
		return recentTradeStock;
	}

	public void setRecentTradeStock(int recentTradeStock) {
		this.recentTradeStock = recentTradeStock;
	}

	@Override
	public String toString() {
		return "StockStatisticsInfo{" +
				"traderName='" + traderName + '\'' +
				", totalTradeStock=" + totalTradeStock +
				", recentThreeTradeStock=" + recentThreeTradeStock +
				", recentTradeStock=" + recentTradeStock +
				'}';
	}

}