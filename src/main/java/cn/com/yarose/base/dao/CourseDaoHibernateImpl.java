package cn.com.yarose.base.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import cn.com.eduedu.jee.db.orm.hibernate.HibernateBaseDaoImpl;
import cn.com.eduedu.jee.order.OrderProperties;
import cn.com.eduedu.jee.order.OrderProperty;
import cn.com.yarose.base.Course;

public class CourseDaoHibernateImpl extends HibernateBaseDaoImpl<Course, Long>
		implements CourseDao {

	private void addCriteria(Criteria crit, Course example) {
		if (example.getShop() != null) {
			crit.add(Restrictions.eq("shop.id", example.getShop().getId()));
		}
		if (example.getDanceType() != null) {
			crit.add(Restrictions.eq("danceType.id", example.getDanceType()
					.getId()));
		}
	}

	@Override
	public Long countByExample(Course exampleInstance, boolean enableLike) {
		Session session = getSession(true);
		try {
			if (exampleInstance == null) {
				return 0l;
			}
			Example example = Example.create(exampleInstance);
			if (enableLike) {
				example.enableLike(MatchMode.ANYWHERE);
			} else {
				example.enableLike(MatchMode.EXACT);
			}
			Criteria crit = session.createCriteria(getPersistentClass());
			crit.setProjection(Projections.rowCount());
			crit.add(example);
			this.addCriteria(crit, exampleInstance);
			return super.countByExample(exampleInstance, enableLike);
		} catch (HibernateException ex) {
			throw convertHibernateAccessException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Course> findByExample(Course exampleInstance, int offset,
			int max, boolean enableLike, OrderProperties orders) {
		Session session = getSession(true);
		try {
			Example example = Example.create(exampleInstance);
			if (enableLike) {
				example.enableLike(MatchMode.ANYWHERE);
			} else {
				example.enableLike(MatchMode.EXACT);
			}
			Criteria crit = session.createCriteria(getPersistentClass());
			crit.add(example);
			this.addCriteria(crit, exampleInstance);
			if (orders != null) {
				List<OrderProperty> ops = orders.getProps();
				for (OrderProperty op : ops) {
					if (op.isDesc()) {
						crit.addOrder(Order.desc(op.getProperty()));
					} else {
						crit.addOrder(Order.asc(op.getProperty()));
					}
				}
			}
			if (offset > 0) {
				crit.setFirstResult(offset);
			}
			if (max > 0) {
				crit.setMaxResults(max);
			}
			return crit.list();
		} catch (HibernateException ex) {
			throw convertHibernateAccessException(ex);
		}
	}
}