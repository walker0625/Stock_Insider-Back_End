package com.minwoo.sic.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.minwoo.sic.dao.StockTradeInfoCrawlingMapper;
import com.minwoo.sic.dto.StockTradeInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StockTradeInfoCrawlingService {
	private final StockTradeInfoCrawlingMapper stockTradeInfoCrawlingMapper;
	private final List<String> stockCode = Arrays.asList("AAPL", "GOOG", "GOOGL", "MSFT", "TSLA", "FB");

	public StockTradeInfoCrawlingService(StockTradeInfoCrawlingMapper stockTradeInfoCrawlingMapper) {
		this.stockTradeInfoCrawlingMapper = stockTradeInfoCrawlingMapper;
	}

	// (fixedRateString = "10000000000", initialDelay = 1000) 1초 뒤 시작(Data 수집용)
	// (cron = "0 0 6 * * *") 매일 오전 6시에 실행
	@Scheduled(cron = "0 0 6 * * *")
	public void setCeoUrlSetting() {
		List<String> ceoUrls = new ArrayList<>();

		String appleCeoUrl = "https://www.secform4.com/insider-trading/1214156.htm";
		String googleCeoUrl = "https://www.secform4.com/insider-trading/1534753.htm";
		String microsoftCeoUrl = "https://www.secform4.com/insider-trading/1513142.htm";
		String teslaCeoUrl = "https://www.secform4.com/insider-trading/1494730.htm";
		String facebookCeoUrl = "https://www.secform4.com/insider-trading/1548760.htm";

		ceoUrls.add(appleCeoUrl);
		ceoUrls.add(googleCeoUrl);
		ceoUrls.add(microsoftCeoUrl);
		ceoUrls.add(teslaCeoUrl);
		ceoUrls.add(facebookCeoUrl);

		log.info("DEV - setCeoUrlSetting() is done");
		getFirstElement(ceoUrls);
	}

	public void getFirstElement(List<String> ceoUrls) {
		List<List<StockTradeInfo>> stockTradeInfoLists = new ArrayList<>();
		
		try {
			for (String ceoUrl : ceoUrls) {
				Connection conn = Jsoup.connect(ceoUrl);
				Document html = conn.get();

				Element el = html.getElementById("filing_table");
				Elements els = el.getElementsByTag("tr");
			
				stockTradeInfoLists.add(getDetailData(els));
			}
			
			log.info("DEV - getFirstElement() is done(connection & parsing success -> DB Save)");
			insertDB(stockTradeInfoLists);
		} catch (IOException e) {
			log.info("DEV - getFirstElement()'s connection is fail");
			e.printStackTrace();
		}
		
	}

	public List<StockTradeInfo> getDetailData(Elements els) {
		List<StockTradeInfo> stockTradeInfoList = new ArrayList<>();

		for (int i = els.size()-1; i >= 0; i--) { // 최근 데이터가 DB 상단으로 오도록 역반복
			StockTradeInfo stockTradeInfo = new StockTradeInfo();
			Elements tds = els.get(i).getElementsByTag("td");

			if (stockCode.contains(tds.get(3).text())) {
				String rawDate = tds.get(0).text().substring(0, 10);
				stockTradeInfo.setTradeDate(LocalDate.parse(rawDate, DateTimeFormatter.ISO_DATE));

				String tradeType = tds.get(0).text().substring(11).trim();
				stockTradeInfo.setTradeType(tradeType);

				stockTradeInfo.setStockCode(tds.get(3).text());

				String traderName = tds.get(4).text().substring(0, tds.get(4).text().indexOf("CEO") - 1);
				stockTradeInfo.setTraderName(traderName);

				String stockAmount = tds.get(5).text().replaceAll("[^0-9]", "");
				stockTradeInfo.setStockAmount(Integer.parseInt(stockAmount));

				String stockPrice = tds.get(6).text().replace("$", "");
				stockTradeInfo.setStockPrice(new BigDecimal(stockPrice));

				String totalAmount = tds.get(7).text().replaceAll("[^0-9]", "");
				stockTradeInfo.setTotalAmount(Long.parseLong(totalAmount));

				stockTradeInfoList.add(stockTradeInfo);
			}
		}
		
		log.info("DEV - getDetailData() is done"); // URL수만큼 logging
		return stockTradeInfoList;
	}
	
	public void insertDB(List<List<StockTradeInfo>> stockTradeInfoLists) {
		stockTradeInfoCrawlingMapper.clearStockTradeInfo(); // DB 갱신을 위해 기존 data를 삭제
		
		for (List<StockTradeInfo> stockTradeInfoList : stockTradeInfoLists) {
			stockTradeInfoCrawlingMapper.insertStockTradeInfo(stockTradeInfoList);
		}
		
		log.info("DEV - insertDB() is done");
	}

}