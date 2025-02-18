package com.gl.mdr.specificationsbuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.domain.Specification;

import com.gl.mdr.model.app.StatesInterpretationDb;
import com.gl.mdr.model.app.StolenLostModel;
import com.gl.mdr.model.constants.Datatype;
import com.gl.mdr.model.constants.SearchOperation;
import com.gl.mdr.model.generic.SearchCriteria;
import com.gl.mdr.util.DbFunctions;

public class GenericSpecificationBuilder<T> {
	private static final Logger logger = LogManager.getLogger(GenericSpecificationBuilder.class);

	private final List<SearchCriteria> params;
	private final List<SearchCriteria> orParams;
	private final List<SearchCriteria> searchParams;
	private final String dialect;
	private List<Specification<T>> specifications;

	public GenericSpecificationBuilder(String dialect) {
		params = new ArrayList<>();
		orParams = new ArrayList<>();
		searchParams = new ArrayList<>();
		specifications = new LinkedList<>();
		this.dialect = dialect;
	}

	public final GenericSpecificationBuilder<T> with(SearchCriteria criteria) { 
		params.add(criteria);
		return this;
	}
	
	public final GenericSpecificationBuilder<T> orSearch(SearchCriteria criteria) { 
		searchParams.add(criteria);
		return this;
	}
	
	public final GenericSpecificationBuilder<T> or(SearchCriteria criteria) { 
		orParams.add(criteria);
		return this;
	}

	public Specification<T> build() { 
		// convert each of SearchCriteria params to Specification and construct combined specification based on custom rules.
		int j = 0;
		Specification<T> finalSpecification   = null;
		Specification<T> searchSpecification  = null;
		Specification<T> orSpecification  = null;
		/** Specification added from addSpecification method**/
		if(!specifications.isEmpty()) {
			finalSpecification = Specification.where(specifications.get(0));
			for(int i = 1; i<specifications.size() ;i++) {
				finalSpecification = finalSpecification.and(specifications.get(i));
			}
		}
		/***If params list not empty***/
		specifications = createSpecifications( params );
		if(!specifications.isEmpty()) {
			if( finalSpecification != null ) {
				j = 0;
			}else {//If no call of addSpecification method
				j = 1;
				finalSpecification = Specification.where(specifications.get(0));
			}
			for(int i = j; i<specifications.size() ;i++) {
				finalSpecification = finalSpecification.and(specifications.get(i));
			}
		}
		/***If searchParams list not empty***/
		specifications = createSpecifications( searchParams );
		if( !specifications.isEmpty()) {
			searchSpecification = specifications.get(0);
			for(int i = 1; i<specifications.size() ;i++) {
				searchSpecification = searchSpecification.or(specifications.get(i));
			}
			if( finalSpecification != null ) {
				finalSpecification = finalSpecification.and( searchSpecification );
			}else {//If no call of addSpecification method and empty params 
				finalSpecification = Specification.where(searchSpecification);
			}
		}
		/***If orParams list not empty***/
		specifications = createSpecifications( orParams );
		if( !specifications.isEmpty()) {
			orSpecification = specifications.get(0);
			for(int i = 1; i<specifications.size() ;i++) {
				orSpecification = orSpecification.or(specifications.get(i));
			}
			if( finalSpecification != null ) {
				finalSpecification = finalSpecification.or( orSpecification );
			}else {//If no call of addSpecification method and empty params 
				finalSpecification = Specification.where(orSpecification);
			}
		}
		return finalSpecification;
	}

	public void addSpecification(Specification<T> specification) { 
		specifications.add(specification);
	}
	
	private List<Specification<T>> createSpecifications( List<SearchCriteria> criterias){
		List<Specification<T>> specifications = new ArrayList<Specification<T>>();
		try {
			for(SearchCriteria searchCriteria : criterias) {
				specifications.add((root, query, cb)-> {
					// Path<Tuple> tuple = root.<Tuple>get(searchCriteria);
					if(SearchOperation.GREATER_THAN.equals(searchCriteria.getSearchOperation())
							&& Datatype.STRING.equals(searchCriteria.getDatatype())) {
						return cb.greaterThan(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
					}
					else if(SearchOperation.LESS_THAN.equals(searchCriteria.getSearchOperation())
							&& Datatype.STRING.equals(searchCriteria.getDatatype())) {
						return cb.lessThan(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
					}
					else if(SearchOperation.EQUALITY.equals(searchCriteria.getSearchOperation())
							&& Datatype.STRING.equals(searchCriteria.getDatatype())) {
						return cb.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
					}
					else if(SearchOperation.EQUALITY_CASE_INSENSITIVE.equals(searchCriteria.getSearchOperation())
							&& Datatype.STRING.equals(searchCriteria.getDatatype())) {
						return cb.equal(cb.lower(root.get(searchCriteria.getKey())), searchCriteria.getValue().toString().toLowerCase());
					}
					else if(SearchOperation.EQUALITY.equals(searchCriteria.getSearchOperation())
							&& Datatype.INT.equals(searchCriteria.getDatatype())) {
						return cb.equal(root.get(searchCriteria.getKey()), (Integer)searchCriteria.getValue());
					}
					else if(SearchOperation.EQUALITY.equals(searchCriteria.getSearchOperation())
							&& Datatype.LONG.equals(searchCriteria.getDatatype())) {
						return cb.equal(root.get(searchCriteria.getKey()), (Long)searchCriteria.getValue());
					}
					else if(SearchOperation.GREATER_THAN.equals(searchCriteria.getSearchOperation())
							&& Datatype.DATE.equals(searchCriteria.getDatatype())){
						Expression<String> dateStringExpr = cb.function(DbFunctions.getDate(dialect), String.class,
								root.get(searchCriteria.getKey()), cb.literal(DbFunctions.getDateFormat(dialect)));
						return cb.greaterThanOrEqualTo(cb.lower(dateStringExpr), searchCriteria.getValue().toString());
					}
					else if(SearchOperation.LESS_THAN.equals(searchCriteria.getSearchOperation())
							&& Datatype.DATE.equals(searchCriteria.getDatatype())){
						Expression<String> dateStringExpr = cb.function(DbFunctions.getDate(dialect), String.class,
								root.get(searchCriteria.getKey()), cb.literal(DbFunctions.getDateFormat(dialect)));
						return cb.lessThanOrEqualTo(cb.lower(dateStringExpr), searchCriteria.getValue().toString());
					}
					else if(SearchOperation.EQUALITY.equals(searchCriteria.getSearchOperation())
							&& Datatype.DATE.equals(searchCriteria.getDatatype())){
						Expression<String> dateStringExpr = cb.function(DbFunctions.getDate(dialect), String.class,
								root.get(searchCriteria.getKey()), cb.literal(DbFunctions.getDateFormat(dialect)));
						return cb.equal(cb.lower(dateStringExpr), searchCriteria.getValue().toString());
					}
					else if(SearchOperation.NEGATION.equals(searchCriteria.getSearchOperation())
							&& Datatype.STRING.equals(searchCriteria.getDatatype())) {
						return cb.notEqual(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
					}
					else if(SearchOperation.NEGATION.equals(searchCriteria.getSearchOperation())
							&& Datatype.INT.equals(searchCriteria.getDatatype())) {
						return cb.notEqual(root.get(searchCriteria.getKey()), (Integer)searchCriteria.getValue());
					}else if(SearchOperation.NEGATION.equals(searchCriteria.getSearchOperation())
							&& Datatype.LONG.equals(searchCriteria.getDatatype())) {
						return cb.notEqual(root.get(searchCriteria.getKey()), (Long)searchCriteria.getValue());
					}else if(SearchOperation.LIKE.equals(searchCriteria.getSearchOperation())
							&& Datatype.STRING.equals(searchCriteria.getDatatype())) {
						if( searchCriteria.getKey().contains("-")) {
							//logger.info("Search Criteria join key:["+searchCriteria.getKey()+"]");
							String[] key = (searchCriteria.getKey()).split("-");
							if( key.length == 2 ) {
								return cb.like(cb.lower(root.join(key[0]).get(key[1]).as( String.class )),
										"%"+((String)searchCriteria.getValue()).toLowerCase()+"%");
							}else {
								return cb.like(cb.lower(root.join(key[0]).get(key[1]).get(key[2]).as( String.class )),
										"%"+((String)searchCriteria.getValue()).toLowerCase()+"%");
							}
						}else {
							return cb.like(cb.lower(root.get(searchCriteria.getKey()).as( String.class )), "%"+((String)searchCriteria.getValue()).toLowerCase()+"%");
						}
					}else {
						return null;
					}
				});
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return specifications;
	}
	
	public Specification<T> in(String key, List<Integer> status){
		return (root, query, cb) ->  cb.in(root.get(key)).value(status);
	}
	
	public Specification<T> inString(String key, List<String> values){
		return (root, query, cb) ->  cb.in(root.get(key)).value(values);
	}

	public Specification<T> notIn(String key, List<String> status){
		return (root, query, cb) -> cb.in(root.get(key)).value(status).not();
	}
	
	public Specification<T> joinOnColumn(String key, List<String> status){
		return (root, query, cb) -> cb.in(root.get(key)).value(status).not();
	}
	
	
	
	public <T> Specification joinWithMultiple(SearchCriteria searchCriteria) {
	    if (Objects.nonNull(searchCriteria.getValue())) {
	        return (root, query, cb) -> {
				Join<T, StatesInterpretationDb> join = root.join("statesInterpretationDb", JoinType.LEFT);
				return cb.and(
						cb.equal(join.get("state"), root.get("status")), // Join based on state and status columns
						cb.equal(join.get(searchCriteria.getKey()), searchCriteria.getValue()) // Condition for the state
				);
				/*
	            Join<T, StatesInterpretationDb> join = root.join("statesInterpretationDb".intern());
	            Predicate featureIdPredicate = cb.equal(join.get("featureId"), 91);
	            Predicate valuePredicate = cb.like(join.get(searchCriteria.getKey()), "%" + searchCriteria.getValue().toString() + "%");
	            return cb.and(featureIdPredicate, valuePredicate);*/
	        };
	    }
	    return null;
	}

	public Specification<StolenLostModel> getStatus(SearchCriteria searchCriteria) {
	    return new Specification<StolenLostModel>() {
	        @Override
	        public Predicate toPredicate(Root<StolenLostModel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
	           
	            Predicate condition2 = criteriaBuilder.equal(root.get("status"), "DONE");
	            Predicate condition3 = criteriaBuilder.equal(root.get("status"), "Done");
	            Predicate condition4 = criteriaBuilder.equal(root.get("status"), "PENDING_MOI");
	            Predicate condition5 = criteriaBuilder.equal(root.get("status"), "REJECT");
	            Predicate condition6 = criteriaBuilder.equal(root.get("status"), "VERIFY_MOI");
	            Predicate condition7 = criteriaBuilder.equal(root.get("status"), "INIT");
	            Predicate condition8 = criteriaBuilder.equal(root.get("status"), "Fail");
				Predicate createdByCondition = criteriaBuilder.notEqual(root.get("createdBy"), "End User");

				// Predicate for excluding records when status is "Fail" and createdBy is "End User"
				Predicate failAndEndUser = criteriaBuilder.and(
						criteriaBuilder.equal(root.get("status"), "Fail"),
						criteriaBuilder.equal(root.get("createdBy"), "End User")
				);
				// Combine the status conditions
				Predicate statusConditions = criteriaBuilder.or(condition2, condition3, condition4, condition5, condition6, condition7,condition8);
				// Exclude records where status is "Fail" and createdBy is "End User"
				return criteriaBuilder.and(
						statusConditions,  // Apply the status conditions
						criteriaBuilder.not(failAndEndUser) // Exclude "Fail" status with "End User" as createdBy
				);
	        }
	    };
	}

	
	
}
