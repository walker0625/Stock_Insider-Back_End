package com.minwoo.sic.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minwoo.sic.service.StockTradeInfoGetService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class StockInsiderCrawlingController {
	private final StockTradeInfoGetService stockTradeInfoGetService;

	public StockInsiderCrawlingController(StockTradeInfoGetService stockTradeInfoGetService) {
		this.stockTradeInfoGetService = stockTradeInfoGetService;
	}

	@GetMapping("/trade-datas")
	public List<Object> StockInsiderMain(){
		log.info("DEV - mainPage Loading");
		return stockTradeInfoGetService.getStockTradeInfo();
	}

}
