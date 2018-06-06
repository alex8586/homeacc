package com.homeacc.repository.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.homeacc.dto.MonthSumDTO;
import com.homeacc.repository.TableReportRepository;

@Component
@Transactional
public class TableReportRepositoryImpl implements TableReportRepository {

	@Autowired
	private SessionFactory  sessionFactory;

	private final static String TABLE_REPORT_QUERY = "select months.month_num as monthNum, "
			+ "c.name as categoryName, "
			+ "sum(br.amount) as sum "
			+ "from (select level as month_num from dual connect by level < 13)months "
			+ "left join budget_record br on months.month_num = to_number(to_char(br.created, 'MM')) "
			+ "left join category c on br.category_id = c.id "
			+ "where br.created < sysdate "
			+ "and br.created > add_months(trunc(sysdate), -11) "
			+ "and br.group_id = :groupId "
			+ "and br.budget_type_id = :budgetTypeId "
			+ "group by months.month_num, c.name "
			+ "order by months.month_num asc ";


	@SuppressWarnings("unchecked")
	public List<MonthSumDTO> getDataForTableReport(long groupId, long budgetTypeId) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(TABLE_REPORT_QUERY)
				.addScalar("monthNum", StandardBasicTypes.LONG)
				.addScalar("categoryName", StandardBasicTypes.STRING)
				.addScalar("sum", StandardBasicTypes.BIG_DECIMAL);

		query.setLong("groupId", groupId);
		query.setLong("budgetTypeId", budgetTypeId);
		query.setResultTransformer(Transformers.aliasToBean(MonthSumDTO.class));
		return query.list();
	}
}
