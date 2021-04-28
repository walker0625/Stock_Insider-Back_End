package com.minwoo.sic.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.minwoo.sic.dao.StockTradeInfoGetMapper;
import com.minwoo.sic.dto.StockStatisticsInfo;
import com.minwoo.sic.dto.StockTradeInfo;

@Service
public class StockTradeInfoGetService {
	private final StockTradeInfoGetMapper stockTradeInfoGetMapper;
	private final List<String> traderNames = Arrays.asList("COOK TIMOTHY D", "Pichai Sundar", "Nadella Satya", "Musk Elon", "Zuckerberg Mark");

	public StockTradeInfoGetService(StockTradeInfoGetMapper stockTradeInfoGetMapper) {
		this.stockTradeInfoGetMapper = stockTradeInfoGetMapper;
	}

	public List<Object> getStockTradeInfo() {
		List<StockTradeInfo> stockTradeInfos = stockTradeInfoGetMapper.getStockTradeInfo();
		List<StockStatisticsInfo> stockStatisticsInfos = new ArrayList<>();

		for (String traderName : traderNames) {
			StockStatisticsInfo stockStatisticsInfo = new StockStatisticsInfo();
			int totalTradeStock = 0;
			int recentThreeTradeStock = 0;
			int recentTradeStock = 0;
			int recentTradeCnt = 1; // CEO별 거래 횟수 구분용으로 계속 초기화해야 하므로, 실제 거래 횟수는 Front에서 계산

			for (StockTradeInfo stockTradeInfo : stockTradeInfos) {
				if (traderName.equals(stockTradeInfo.getTraderName())) {

					if (recentTradeCnt == 1) {
						recentTradeStock = stockTradeInfo.getStockAmount();
					}

					if (recentTradeCnt <= 3) {
						recentThreeTradeStock += stockTradeInfo.getStockAmount();
					}

					totalTradeStock += stockTradeInfo.getStockAmount();

					recentTradeCnt++;
				}
			}
			
			recentTradeCnt = 1; // CEO 한명이 끝나면 Cnt 초기화,
			
			// 주식 통계정보 Setting
			stockStatisticsInfo.setTraderName(traderName);
			stockStatisticsInfo.setTotalTradeStock(totalTradeStock);
			stockStatisticsInfo.setRecentThreeTradeStock(recentThreeTradeStock);
			stockStatisticsInfo.setRecentTradeStock(recentTradeStock);

			stockStatisticsInfos.add(stockStatisticsInfo);
		}

		List<Object> TradeInfosAndStatisticsInfos = new ArrayList<>();
		TradeInfosAndStatisticsInfos.add(stockTradeInfos);
		TradeInfosAndStatisticsInfos.add(stockStatisticsInfos);

		return TradeInfosAndStatisticsInfos;
	}

}