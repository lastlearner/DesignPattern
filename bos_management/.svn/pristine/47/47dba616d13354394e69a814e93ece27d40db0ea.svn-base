package com.itheima.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.domain.system.Role;

public interface RoleDao extends JpaRepository<Role, Integer> {

	@Query("select p from Permission p join p.roles r where r.id = ? ")
	List<Permission> findPermissionsById(Integer id);

	@Query("select m from Menu m join m.roles r where r.id = ? ")
	List<Menu> findMenusById(Integer id);

}
