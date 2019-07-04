package com.siemo.notif.system.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.siemo.notif.system.model.Group;
import com.siemo.notif.system.model.MasterData;

public class UserSpecification implements Specification<Group> {
	 private SearchCriteria criteria;
	 
	    public UserSpecification(SearchCriteria criteria) {
		super();
		this.criteria = criteria;
	}

		@Override
	    public Predicate toPredicate
	      (Root<Group> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
	  
	        if (criteria.getOperation().equalsIgnoreCase(">")) {
	            return builder.greaterThanOrEqualTo(
	              root.<String> get(criteria.getKey()), criteria.getValue().toString());
	        } 
	        else if (criteria.getOperation().equalsIgnoreCase("<")) {
	            return builder.lessThanOrEqualTo(
	              root.<String> get(criteria.getKey()), criteria.getValue().toString());
	        } 
	        else if (criteria.getOperation().equalsIgnoreCase(":")) {
	            if (root.get(criteria.getKey()).getJavaType() == String.class) {
	                return builder.like(
	                  root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
	            } else {
	                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
	            }
	        }
	        return null;
	    }
}
