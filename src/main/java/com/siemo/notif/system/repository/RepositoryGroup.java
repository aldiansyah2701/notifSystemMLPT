package com.siemo.notif.system.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.siemo.notif.system.model.Group;

@Repository
public interface RepositoryGroup extends CrudRepository<Group, String>, JpaSpecificationExecutor<Group> {
	Group findByCategoryAndDetail(String category, String detail);
}
