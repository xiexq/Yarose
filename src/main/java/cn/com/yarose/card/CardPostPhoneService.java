package cn.com.yarose.card;

import java.util.List;

import cn.com.eduedu.jee.service.BaseSearchService;

public interface CardPostPhoneService extends
		BaseSearchService<CardPostPhone, Long> {

	List<CardPostPhone> listByCardId(long parseLong, int offset, int count);

}