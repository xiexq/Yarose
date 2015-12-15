package cn.com.yarose.web.controller.home.shopmanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.mvc.controller.DictionaryModel;
import cn.com.eduedu.jee.mvc.response.ResponseItem;
import cn.com.eduedu.jee.mvc.response.ResponseObject;
import cn.com.eduedu.jee.order.OrderProperties;
import cn.com.eduedu.jee.security.account.Account;
import cn.com.eduedu.jee.security.account.AccountService;
import cn.com.yarose.base.CourseTeacher;
import cn.com.yarose.base.CourseTeacherService;
import cn.com.yarose.base.Shop;
import cn.com.yarose.base.ShopService;
import cn.com.yarose.card.Appointment;
import cn.com.yarose.card.AppointmentService;
import cn.com.yarose.card.MemberCard;
import cn.com.yarose.card.MemberCardService;
import cn.com.yarose.utils.Constants;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/shop/manager/appointment")
@CRUDControllerMeta(title = "预约管理", service = AppointmentService.class, listable = true, createable = true, editable = true, deleteable = true, viewable = true, searchable = true)
public class AppointmentAdminController extends
		BaseCRUDControllerExt<Appointment, Long> {

	@Resource(name = "memberCardService")
	private MemberCardService memberCardService;

	@Resource(name = "account_accountService")
	private AccountService accountService;

	@Resource(name = "courseTeacherService")
	private CourseTeacherService courseTeacherService;

	@Resource(name = "shopService")
	private ShopService shopService;

	/**
	 * 验证是否是核销预约操作
	 * 
	 * @param request
	 * @return
	 */
	public boolean isCheckAction(HttpServletRequest request) {
		String isCheckAction = request.getParameter("_type");
		return StringUtils.hasText(isCheckAction);
	}

	/**
	 * 验证是否是核销页面
	 * 
	 * @param request
	 * @return
	 */
	private boolean isCheckedRequest(HttpServletRequest request) {
		String checked = request.getParameter("_checked");
		return StringUtils.hasText(checked);
	}

	@Override
	public Set<String> customListFields(HttpServletRequest request)
			throws Exception {
		return this.generateStringSortedSet("code", "userId", "userWeiXin",
				"userNick", "courseName", "shopName", "teacherName");
	}

	@Override
	public Set<String> customEditFields(HttpServletRequest request,
			Appointment cmd) throws Exception {
		// 核销操作页面
		if (isCheckAction(request)) {
			return this.generateStringSortedSet("otherConsum", "remark",
					"lesson");
		}
		// 添加预约
		return this.generateStringSortedSet("userId", "mCard", "shop",
				"schoolDate", "courseTeacher");
	}

	@Override
	public Set<String> customViewFields(HttpServletRequest request)
			throws Exception {
		return this.generateStringSortedSet("code", "userId", "userWeiXin",
				"userNick", "cardNo", "courseName", "shopName", "teacherName",
				"createTime");
	}

	@Override
	public Set<String> customSearchFields(HttpServletRequest request)
			throws Exception {
		return this.generateStringSortedSet("userId");
	}

	@Override
	public void customSearchExample(Appointment cmd, HttpServletRequest request)
			throws Exception {
		if (isCheckedRequest(request)) {// 核销页面
			cmd.setStatus(Constants.APPOINTMENT_CHECKED);
		} else {
			cmd.setStatus(Constants.APPOINTMENT_UNCHECKED);
		}
		super.customSearchExample(cmd, request);
	}

	@Override
	public List<Appointment> customList(int offset, int count,
			OrderProperties orders, HttpServletRequest request)
			throws Exception {
		Integer status = isCheckedRequest(request) ? Constants.APPOINTMENT_CHECKED
				: Constants.APPOINTMENT_UNCHECKED;
		return ((AppointmentService) this.getCrudService()).listByCheckStatus(
				status, offset, count);
	}

	@Override
	public long customCount(HttpServletRequest request) throws Exception {
		Integer status = isCheckedRequest(request) ? Constants.APPOINTMENT_CHECKED
				: Constants.APPOINTMENT_UNCHECKED;
		return ((AppointmentService) this.getCrudService())
				.countByCheckStatus(status);
	}

	@Override
	public Appointment customSave(Appointment cmd, BindingResult result,
			HttpServletRequest request, ResponseObject response, boolean create)
			throws Exception {
		if (this.validate(cmd, result, request, create)) {
			// 创建预约或则核销预约，没有修改预约,考虑到修改预约会同时修改会员和会员卡,并且从安全和一致性去考虑
			if (create) {
				// 创建预约时就计算会员卡信息
				MemberCard memberCard = memberCardService.findById(cmd
						.getmCard().getId());
				CourseTeacher courseTeacher = courseTeacherService.findById(cmd
						.getCourseTeacher().getId());
				Integer needLesson = courseTeacher.getLesson();
				if (memberCard.getCanUseLesson() < needLesson) {
					result.rejectValue(
							"mCard",
							"mCard.error",
							"该会员卡剩余课时不足，需" + needLesson + "课时,剩余"
									+ memberCard.getCanUseLesson() + "课时");
					return cmd;
				}
				// 已使用课时=本次需要课时+以前已使用课时
				memberCard.setUsedLesson(needLesson
						+ memberCard.getUsedLesson());
				memberCardService.save(memberCard);
				cmd.setCreateTime(new Date());
				cmd.setStatus(Constants.APPOINTMENT_UNCHECKED);
				Account account = accountService.findByUserId(cmd.getUserId());
				cmd.setUserAccount(account);
			} else {
				cmd.setStatus(Constants.APPOINTMENT_CHECKED);
				cmd.setCheckUserId(this.getAccount().getUserid());
			}
		}
		return this.getCrudService().save(cmd);
	}

	@Override
	public void customDelete(Long id, HttpServletRequest request)
			throws Exception {
		// 删除预约时，需同步更新会员卡课时
		Appointment app = this.getCrudService().findById(id);
		if (app != null) {
			MemberCard memCard = app.getmCard();
			memCard.setUsedLesson(memCard.getUsedLesson() - app.getLesson());
			memberCardService.save(memCard);
		}
		super.customDelete(id, request);
	}

	@DictionaryModel(cascade = true, cascadeField = "userId", label = "cardNo", val = "id")
	public Collection<MemberCard> _mCards(HttpServletRequest request) {
		String id = this.getParameter(request, "__id");
		if (StringUtils.hasText(id)) {
			return memberCardService.listByUserId(id);
		}
		return null;
	}

	@DictionaryModel(header = true, label = "courseName", val = "id")
	public List<CourseTeacher> _courseTeachers(HttpServletRequest request) {
		return null;
	}

	@DictionaryModel(label = "alias", val = "userid")
	public List<Account> _ac_userIds(String value, HttpServletRequest request) {
		if (StringUtils.hasText(value)) {
			return accountService.findByUserId(value + "%", 0, 10);
		}
		return null;
	}

	@DictionaryModel(label = "name", val = "id")
	public List<Shop> _shops(HttpServletRequest request) {
		return shopService.listAll(-1, -1);
	}

	@ResponseBody
	@RequestMapping("/course/{shopId}/{dateStr}")
	public ResponseObject getCourseTeachers(
			@PathVariable("shopId") Long shopId,
			@PathVariable("dateStr") String dateStr) throws ParseException {
		ResponseObject resp = new ResponseObject(false);
		if (shopId != null && StringUtils.hasText(dateStr)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(dateStr);
			Date beginDate = Constants.customBeginTime(date);
			Date endDate = Constants.customEndTime(date);
			List<CourseTeacher> ccs = courseTeacherService.listByShopAndDay(
					shopId, beginDate, endDate);
			if (ccs != null && ccs.size() > 0) {
				List<ResponseItem> items = resp.createListChildren("courses");
				for (CourseTeacher cc : ccs) {
					// 获取开始结束时间的时间点
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(cc.getBeginTime());
					String beginStr = calendar.get(Calendar.HOUR_OF_DAY) + ":"
							+ calendar.get(Calendar.MINUTE);
					calendar.setTime(cc.getEndTime());
					String endStr = calendar.get(Calendar.HOUR_OF_DAY) + ":"
							+ calendar.get(Calendar.MINUTE);
					ResponseItem item = new ResponseItem();
					item.put("id", cc.getId()+"");
					item.put("courseName", cc.getCourseName() + "("
							+ beginStr + "——" + endStr + ")");
					items.add(item);
				}
				resp.setSuccess(true);
			}
		}
		return resp;
	}
}
