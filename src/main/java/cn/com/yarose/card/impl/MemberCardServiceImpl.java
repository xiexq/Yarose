package cn.com.yarose.card.impl;

import java.util.List;

import cn.com.eduedu.jee.db.orm.DaoBasedServiceImpl;
import cn.com.eduedu.jee.db.orm.QueryCmdType;
import cn.com.yarose.card.MemberCard;
import cn.com.yarose.card.MemberCardService;

public class MemberCardServiceImpl extends
		DaoBasedServiceImpl<MemberCard, Long> implements MemberCardService {

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberCard> listByUserId(String userId) {
		return (List<MemberCard>) this.getDao().executeQueryList("", QueryCmdType.QUERY_NAME, -1, -1, userId);
	}

}