package cn.com.yarose.web.controller.home.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.mvc.controller.DictionaryModel;
import cn.com.eduedu.jee.mvc.response.ResponseItem;
import cn.com.eduedu.jee.mvc.response.ResponseObject;
import cn.com.eduedu.jee.security.account.Access;
import cn.com.eduedu.jee.security.account.AccessService;
import cn.com.eduedu.jee.security.account.Account;
import cn.com.eduedu.jee.security.account.AccountService;
import cn.com.yarose.base.Course;
import cn.com.yarose.base.CourseService;
import cn.com.yarose.base.Shop;
import cn.com.yarose.base.ShopService;
import cn.com.yarose.base.TeacherManager;
import cn.com.yarose.base.TeacherManagerService;
import cn.com.yarose.utils.Constants;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/admin/teacher/managers")
@CRUDControllerMeta(viewable = true, title = "课程授权", service = TeacherManagerService.class, listable = true, createable = true, editable = true, deleteable = true, searchable = true)
public class TeacherManagersAdminController extends
		BaseCRUDControllerExt<TeacherManager, Long> {

	private CourseService courseService;
	private ShopService shopService;
	private AccountService accountService;
	@Resource(name = "account_accessService")
	private AccessService accessService;

	@Resource(name = "account_accountService")
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	@Resource(name = "courseService")
	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	@Resource(name = "shopService")
	public void shopService(ShopService shopService) {
		this.shopService = shopService;
	}

	@Override
	public Set<String> customListFields(HttpServletRequest request)
			throws Exception {
		return this.generateStringSortedSet("courseName", "shopName",
				"beginTime", "endTime", "userId");
	}

	@Override
	public Set<String> customSearchFields(HttpServletRequest request)
			throws Exception {
		return this.generateStringSortedSet("searchShop");
	}

	@Override
	public void customCreate(TeacherManager cmd, HttpServletRequest request)
			throws Exception {
		super.customCreate(cmd, request);
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(this.getAddDateFromRequest(request));
		cmd.setBeginTime(c.getTime());
		cmd.setEndTime(c.getTime());
	}

	@Override
	public TeacherManager customEdit(Long id, HttpServletRequest request)
			throws Exception {
		TeacherManager tm = this.getCrudService().findById(id);
		return tm;
	}

	@Override
	public TeacherManager customSaveCmd(TeacherManager cmd,
			HttpServletRequest request, Long id) throws Exception {
		cmd.setAuthSourceId(this.getAccount().getAccountId());
		cmd.setAuthSourceAlias(this.getAccount().getUserid());
		return cmd;
	}

	@Override
	public TeacherManager customSave(TeacherManager cmd, BindingResult result,
			HttpServletRequest request, ResponseObject response, boolean create)
			throws Exception {
		if (this.validate(cmd, result, request, create)) {
			if (create) {
				cmd.setCreateTime(new Date());
			}
		}
		return super.customSave(cmd, result, request, response, create);
	}

	private Long getAddDateFromRequest(HttpServletRequest request) {
		String date = request.getParameter("_date");
		if (StringUtils.hasText(date)) {
			return Long.parseLong(date);
		}
		return null;
	}

	private Long getShopIdRequest(HttpServletRequest request) {
		String shopId = request.getParameter("_shop");
		if (StringUtils.hasText(shopId)) {
			return Long.parseLong(shopId);
		}
		return null;
	}

	@Override
	public Set<String> customEditFields(HttpServletRequest request,
			boolean create) throws Exception {
		return this.generateStringSortedSet("shop", "course", "teacher",
				"beginTime", "endTime");
	}

	@DictionaryModel(header = true, headerLabel = "请选择")
	public Collection<Shop> _shops(HttpServletRequest request) {
		List<Shop> list = new ArrayList<Shop>();
		list.add(shopService.findById(getShopIdRequest(request)));
		return list;
	}

	@DictionaryModel(header = true, headerLabel = "请选择", label = "userid", val = "accountId")
	public List<Account> _teachers(HttpServletRequest request) {
		List<Account> teachers = accountService.listByRole(
				Constants.ROLE_TEACHER, -1, -1);
		return teachers;
	}

	public Collection<Shop> _searchShops(HttpServletRequest request) {
		return shopService.listAll(-1, -1);
	}

	@DictionaryModel(header = true, cascade = true, cascadeField = "shop", headerIsJustForSearch = true)
	public Collection<Course> _courses(HttpServletRequest request) {
		String id = this.getParameter(request, "__id");
		if (StringUtils.hasText(id)) {
			List<Course> courseList = courseService.listByShopId(Long
					.parseLong(id));
			if (courseList != null && courseList.size() > 0) {
				return courseList;
			}
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/select")
	public ResponseObject getTitle(HttpServletRequest request,
			HttpServletResponse response) {
		ResponseObject resp = new ResponseObject(false);
		Long shopId = this.getShopIdRequest(request);
		if (shopId != null) {
			List<TeacherManager> tmList = ((TeacherManagerService) this
					.getCrudService()).listByShopId(shopId);
			List<ResponseItem> items = resp.createListChildren("courses");
			for (TeacherManager tm : tmList) {
				ResponseItem item = new ResponseItem();
				item.put("title", tm.getCourseName());
				item.put("start", tm.getBeginTime());
				items.add(item);
			}
			resp.setSuccess(true);
		}
		return resp;
	}

	@DictionaryModel(val = "id", label = "name")
	public Collection<Access> _accessess(HttpServletRequest request) {
		List<Access> as = accessService.listAll(-1, -1);
		String isShop = this.getParameter(request, "_shop");
		if (StringUtils.hasText(isShop)) {
			List<Access> list = new ArrayList<Access>();
			for (Access access : as) {
				if (!Constants.ROLE_SUPER.equals(access.getId())) {
					list.add(access);
				}
			}
			return list;
		}
		return as;
	}

}
