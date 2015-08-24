package cn.com.yarose.card;

import java.util.List;

import cn.com.eduedu.jee.service.BaseSearchService;

public interface EvaluationService extends BaseSearchService<Evaluation, Long> {

	List<Evaluation> listByCourseTeacherId(long courseTeacherId);

	long countByCourseTeacherId(long courseTeacherId);

}