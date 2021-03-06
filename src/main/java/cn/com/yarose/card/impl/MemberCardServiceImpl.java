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
		return (List<MemberCard>) this.getDao().executeQueryList(
				"MemberCard.listByUserId", QueryCmdType.QUERY_NAME, -1, -1,
				userId);
	}

	@Override
	public Long findMaxSeqNum() {
		Long seqNum = (Long) this.getDao().executeQueryUnique(
				"MemberCard.findMaxSeqNum", QueryCmdType.QUERY_NAME);
		return seqNum == null ? 0l : seqNum;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberCard> listByArea(Long id, int offset, int count) {
		return (List<MemberCard>) this.getDao().executeQueryList(
				"MemberCard.listByArea", QueryCmdType.QUERY_NAME, offset,
				count, id);
	}

	@Override
	public Long countByArea(Long id) {
		return (Long) this.getDao().executeQueryUnique("MemberCard.countByArea",
				QueryCmdType.QUERY_NAME, id);
	}
}