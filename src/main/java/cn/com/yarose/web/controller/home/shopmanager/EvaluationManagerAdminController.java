package cn.com.yarose.web.controller.home.shopmanager;



import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.mvc.controller.CRUDPhase;
import cn.com.eduedu.jee.mvc.response.ResponseObject;
import cn.com.eduedu.jee.order.OrderProperties;
import cn.com.yarose.card.Evaluation;
import cn.com.yarose.card.EvaluationService;
import cn.com.yarose.utils.Constants;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/shop/evaluation/manager")
@CRUDControllerMeta(title = "评价管理", service = EvaluationService.class, listable = true,viewable = true, searchable = true,createable=true,editable=true,deleteable=true)
public class EvaluationManagerAdminController extends BaseCRUDControllerExt<Evaluation, Long> {
	
	@Override
	public Set<String> customListFields(HttpServletRequest arg0) throws Exception {
		return this.generateStringSortedSet("id","accountName","type","level","createTime");
	}
	
	@Override
	public Set<String> customHideFields(HttpServletRequest request, CRUDPhase phase) {
		return this.generateStringSortedSet("id");
	}
	
	@Override
	public Set<String> customEditFields(HttpServletRequest request, Evaluation entity) throws Exception {
		return this.generateStringSortedSet("content","level");
	}
	@Override
	public List<Evaluation> customList(int offset, int count, OrderProperties orders, HttpServletRequest request)
			throws Exception {
		EvaluationService es = (EvaluationService) this.getCrudService();
		String courseTeacherId = request.getParameter("_id");
		if(courseTeacherId != null){
			return es.listByCourseTeacherId(Long.parseLong(courseTeacherId));
		}
		return null;
	}
	
	@Override
	public long customCount(HttpServletRequest request) throws Exception {
		EvaluationService es = (EvaluationService) this.getCrudService();
		String courseTeacherId = request.getParameter("_id");
		if(courseTeacherId != null){
			return es.countByCourseTeacherId(Long.parseLong(courseTeacherId));
		}
		return 0L;
	}
	
	@Override
	public Evaluation customSave(Evaluation cmd, BindingResult result, HttpServletRequest request, ResponseObject response,
			boolean create) throws Exception {
		if (this.validate(cmd, result, request, create)) {
			if(create){
				cmd.setCreateTime(new Date());
			}
			cmd.setAccount(this.getAccount());
			
			if(true){
				cmd.setType(Constants.EVALUATION_TYPE_USER);
			}else{
				cmd.setType(Constants.EVALUATION_TYPE_SHOP);
			}
		}
		return super.customSave(cmd, result, request, response, create);
	}
	
}
