package com.itheima.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.SubArea;

public interface SubareaDao extends JpaRepository<SubArea, String> {
	//@Query(value="select a.c_province, count(*)  from t_sub_area s inner join t_area a  on s.c_area_id = a.c_id group by a.c_province",nativeQuery=true)
	@Query("select a.province ,count(*) from SubArea s join s.area a group by a.province")
	public List<Object[]> showChart();

	
	@Query(value="select a.c_province, count(*)  from t_sub_area s inner join t_area a  on s.c_area_id = a.c_id group by a.c_province",nativeQuery=true)
	List<Object[]> showChart_zhu();

	/**
	 * 查询所有未与定区关联的分区
	 * @return
	 */
	List<SubArea> findByFixedAreaIsNull();

	/**
	 * 查询与此id的定区关联的分区
	 * @param id
	 * @return
	 */
	@Query("from SubArea where fixedArea.id = ?")
	List<SubArea> findByFixedArea(String id);

	/**
	 * 将分区与定区取消关联
	 * @param id
	 */
	@Query("update SubArea set fixedArea = null where fixedArea.id=?")
	@Modifying
	void updateFixedArea2Null(String fixedId);

}
