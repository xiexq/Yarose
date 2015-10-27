package cn.com.yarose.card;

import java.util.List;

import cn.com.eduedu.jee.service.BaseSearchService;

public interface EvaluationService extends BaseSearchService<Evaluation, Long> {

	List<Evaluation> listByCourseTeacherId(long courseTeacherId);

	long countByCourseTeacherId(long courseTeacherId);

	/**
	 * 列出指定课程的所有评价
	 * 
	 * @param id 课程id
	 * @param offset
	 * @param count
	 * @return
	 */
	List<Evaluation> listByCourseId(Long id, int offset, int count);
}