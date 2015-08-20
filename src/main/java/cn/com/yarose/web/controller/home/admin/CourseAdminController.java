package cn.com.yarose.web.controller.home.admin;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.eduedu.jee.mvc.controller.DictionaryModel;
import cn.com.yarose.base.Course;
import cn.com.yarose.base.CourseService;
import cn.com.yarose.base.Dictionary;
import cn.com.yarose.base.DictionaryService;
import cn.com.yarose.base.Shop;
import cn.com.yarose.base.ShopService;
import cn.com.yarose.utils.Constants;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/admin/courses")
@CRUDControllerMeta(title = "课程管理", service = CourseService.class, listable = true, createable = true, editable = true, deleteable = true, viewable = true, searchable = true)
public class CourseAdminController extends BaseCRUDControllerExt<Course, Long> {

	@Resource(name = "dictionaryService")
	private DictionaryService dictionaryService;

	@Resource(name = "shopService")
	private ShopService shopService;

	@Override
	public Set<String> customListFields(HttpServletRequest request)
			throws Exception {
		return this
				.generateStringSortedSet("name", "shopName", "danceTypeName");
	}

	@Override
	public Set<String> customEditFields(HttpServletRequest request,
			boolean create) throws Exception {
		return this
				.generateStringSortedSet("name", "shop", "danceType", "desc");
	}

	@Override
	public Set<String> customViewFields(HttpServletRequest request)
			throws Exception {
		return this.generateStringSortedSet("name", "shopName",
				"danceTypeName", "desc");
	}

	@Override
	public Set<String> customSearchFields(HttpServletRequest request)
			throws Exception {
		return this.generateStringSortedSet("name", "shop", "danceType");
	}

	@DictionaryModel(label = "name", val = "id", header = true, headerLabel = "请选择", headerIsJustForSearch = true)
	public List<Shop> _shops(HttpServletRequest request) {
		return shopService.listAll(-1, -1);
	}

	@DictionaryModel(label = "name", val = "id", header = true, headerLabel = "请选择", headerIsJustForSearch = true)
	public List<Dictionary> _danceTypes(HttpServletRequest request) {
		return dictionaryService.listByTypeCode(Constants.DICT_TYPE_DANCE, -1,
				-1);
	}
}
