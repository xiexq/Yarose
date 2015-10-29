package cn.com.yarose.web.controller.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.mvc.response.ResponseObject;
import cn.com.eduedu.jee.order.OrderProperties;
import cn.com.yarose.card.Appointment;
import cn.com.yarose.card.AppointmentService;
import cn.com.yarose.card.MemberCard;
import cn.com.yarose.card.MemberCardService;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/my/member/card")
@CRUDControllerMeta(title = "我的会员的卡", service = MemberCardService.class, listable = true, deleteable = false, editable = false, viewable = true, searchable = false, paged = false)
public class MyMemberCardController extends
		BaseCRUDControllerExt<MemberCard, Long> {

	@Resource(name = "appointmentService")
	AppointmentService appointmentService;

	@Override
	public Set<String> customListFields(HttpServletRequest arg0)
			throws Exception {
		return this.generateStringSortedSet("cardNo", "totalLesson",
				"usedLesson");
	}

	@Override
	public Map<String, String> customFieldsLabel(HttpServletRequest request,
			int action) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("createDate", "购买时间");
		return map;
	}

	@Override
	public Set<String> customViewFields(HttpServletRequest request)
			throws Exception {
		return this.generateStringSortedSet("cardNo", "typeName",
				"purchaseLesson", "givingLesson", "expireDate", "usedLesson",
				"totalLesson", "price", "shopName", "createDate");
	}

	@Override
	public List<MemberCard> customList(int offset, int count,
			OrderProperties orders, HttpServletRequest request)
			throws Exception {
		String userId = this.getAccount().getUserid();
		return ((MemberCardService) this.getCrudService()).listByUserId(userId);
	}

	/**
	 * 验证当前用户是否有会员卡
	 * 
	 * @param courseId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/canappoint/{courseId}")
	public ResponseObject validateCardLesson(
			@PathVariable("courseId") Long courseId) {
		ResponseObject resp = new ResponseObject(false);
		if (courseId != null) {
			String userId = this.getAccount().getUserid();
			// 验证是否有会员卡
			List<MemberCard> cards = ((MemberCardService) this.getCrudService())
					.listByUserId(userId);
			if (cards == null || cards.size() == 0) {
				resp.put("nocard", true);
			} else {
				// 验证是否已预约过该课程
				List<Appointment> apponints = appointmentService
						.listByUserIdAndCourseId(userId, courseId);
				if (apponints != null && apponints.size() > 0) {
					resp.put("appointed", true);
				} else {
					resp.setSuccess(true);
				}
			}
		}
		return resp;
	}
}
