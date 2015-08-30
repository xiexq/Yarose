package cn.com.yarose.card.impl;

import java.util.List;

import cn.com.eduedu.jee.db.orm.DaoBasedServiceImpl;
import cn.com.eduedu.jee.db.orm.QueryCmdType;
import cn.com.yarose.card.CardPostPhone;
import cn.com.yarose.card.CardPostPhoneService;

public class CardPostPhoneServiceImpl extends
		DaoBasedServiceImpl<CardPostPhone, Long> implements
		CardPostPhoneService {

	@SuppressWarnings("unchecked")
	@Override
	public List<CardPostPhone> listByCardId(long cardId, int offset, int count) {
		return (List<CardPostPhone>) this.getDao().executeQueryList(
				"CardPostPhone.listByCardId", QueryCmdType.QUERY_NAME, offset,
				count, cardId);
	}
}