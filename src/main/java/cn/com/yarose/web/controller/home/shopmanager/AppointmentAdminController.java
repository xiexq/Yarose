package cn.com.yarose.web.controller.home.shopmanager;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.eduedu.jee.mvc.controller.CRUDControllerMeta;
import cn.com.yarose.card.Appointment;
import cn.com.yarose.card.AppointmentService;
import cn.com.yarose.web.controller.BaseCRUDControllerExt;

@Controller
@RequestMapping("/home/admin/courses")
@CRUDControllerMeta(title = "课程管理", service = AppointmentService.class, listable = true, createable = true, editable = true, deleteable = true, viewable = true, searchable = true)
public class AppointmentAdminController extends BaseCRUDControllerExt<Appointment, Long> {

	@Override
	public Set<String> customListFields(HttpServletRequest arg0) throws Exception {
		return this.generateStringSortedSet("code","courseName","shopName","teacherName");
	}
	
	@Override
	public Set<String> customEditFields(HttpServletRequest request, Appointment entity) throws Exception {
		return this.generateStringSortedSet("");
	}

}
