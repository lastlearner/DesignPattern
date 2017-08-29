package com.itheima.bos.service.base.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.FixedAreaDao;
import com.itheima.bos.dao.base.SubareaDao;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.SubareaService;

@Service
@Transactional
public class SubareaServiceImpl implements SubareaService {
	@Autowired
	private SubareaDao dao;
	
	@Autowired
	private FixedAreaDao fixedAreaDao;

	public void save(SubArea model) {
		// model.setId(UUID.randomUUID().toString());
		dao.save(model);
	}

	public List<SubArea> findAll() {
		return dao.findAll();
	}

	public List<Object[]> showChart() {
		return dao.showChart();
	}


	@Override
	public List<Object[]> showChart_zhu() {
		List<Object[]> list = dao.showChart_zhu();
		return list;
	}

	@Override
	public List<SubArea> findAllSubAreaHasAssosiation(String fixedId) {
		return dao.findByFixedArea(fixedId);
	}

	@Override
	public List<SubArea> findAllSubAreaNoAssosiation() {
		return dao.findByFixedAreaIsNull();
	}

	@Override
	public void assignSubAreas2FixedArea(String fixedId, List<String> subAreaIds) {
		dao.updateFixedArea2Null(fixedId);

		// 查询出此定区id对象
		FixedArea fixedArea = fixedAreaDao.findOne(fixedId);
		// 将要关联的分区与此定区关联
		for (String subAreaId : subAreaIds) {
			SubArea subArea = dao.findOne(subAreaId);
			subArea.setFixedArea(fixedArea);
		}

	}

}
