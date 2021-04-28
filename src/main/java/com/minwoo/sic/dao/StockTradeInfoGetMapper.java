package com.minwoo.sic.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.minwoo.sic.dto.StockTradeInfo;

@Mapper
public interface StockTradeInfoGetMapper {
	List<StockTradeInfo> getStockTradeInfo();
}
