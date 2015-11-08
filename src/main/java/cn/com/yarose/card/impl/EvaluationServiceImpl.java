package cn.com.yarose.card.impl;

import java.util.List;

import cn.com.eduedu.jee.db.orm.DaoBasedServiceImpl;
import cn.com.eduedu.jee.db.orm.QueryCmdType;
import cn.com.yarose.card.Evaluation;
import cn.com.yarose.card.EvaluationService;

public class EvaluationServiceImpl extends
		DaoBasedServiceImpl<Evaluation, Long> implements EvaluationService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Evaluation> listByCourseTeacherId(long courseTeacherId) {
		List<Evaluation> evalList = (List<Evaluation>) this.getDao()
				.executeQueryList("Evaluation.listByCourseTeacherId",
						QueryCmdType.QUERY_NAME, -1, -1, courseTeacherId);
		if (evalList != null && evalList.size() > 0) {
			for (Evaluation eval : evalList) {
				eval.getAccount();
				eval.getCourseTeacher();
			}
			return evalList;
		}
		return null;
	}

	@Override
	public long countByCourseTeacherId(long courseTeacherId) {
		return (Long) this.getDao().executeQueryUnique(
				"Evaluation.countByCourseTeacherId", QueryCmdType.QUERY_NAME,
				courseTeacherId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Evaluation> listByCourseId(Long id, int offset, int count) {
		return (List<Evaluation>) this.getDao().executeQueryList(
				"Evaluation.listByCourseId", QueryCmdType.QUERY_NAME, offset,
				count, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Evaluation> listByUserAndCourseTeacher(Long accountId, Long cid) {
		return (List<Evaluation>) this.getDao().executeQueryList(
				"Evaluation.listByUserAndCourseTeacher",
				QueryCmdType.QUERY_NAME, -1, -1, accountId, cid);
	}
}