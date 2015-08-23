package cn.com.yarose.card;

import java.util.List;

import cn.com.eduedu.jee.service.BaseSearchService;

public interface MemberCardService extends BaseSearchService<MemberCard, Long> {

	List<MemberCard> listByUserId(String userId);

	Long findMaxSeqNum();

}