package com.homeacc.repository.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.homeacc.classifier.MonthEnum;
import com.homeacc.dto.ReportDTO;
import com.homeacc.entity.Category;
import com.homeacc.repository.ReportRepository;
import com.homeacc.utils.DateUtils;

@Component
@Transactional
public class ReportRepositoryImpl implements ReportRepository {

	@Autowired
	private SessionFactory  sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<ReportDTO> getReportByDate(Date from, Date to, int monthNumber, long groupId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Category.class);
		criteria.createAlias("budgetRecords", "records");
		criteria.createAlias("records.budgetType", "type");
		criteria.add(Restrictions.eq("groups.id", groupId));

		if (from != null) {
			criteria.add(Restrictions.ge("records.created", from));
		}
		if (to != null) {
			criteria.add(Restrictions.le("records.created", to));
		}
		if (monthNumber != MonthEnum.UNDEFINED.getMonthNumber()) {
			criteria.add(Restrictions.ge("records.created", DateUtils.getStartOfMonth(monthNumber)));
			criteria.add(Restrictions.le("records.created", DateUtils.getEndOfMonth(monthNumber)));
		}
		if (from == null && to == null && monthNumber == 0) {
			criteria.add(Restrictions.ge("records.created", DateUtils.getStartOfMonthByDate(new Date())));
			criteria.add(Restrictions.le("records.created", DateUtils.getEndOfMonthByDate(new Date())));
		}

		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("name"), "categoryName")
				.add(Projections.groupProperty("type.code"), "budgetType")
				.add(Projections.sum("records.amount"), "amount"));

		criteria.setResultTransformer(new AliasToBeanResultTransformer(ReportDTO.class));
		return criteria.list();
	}

}
