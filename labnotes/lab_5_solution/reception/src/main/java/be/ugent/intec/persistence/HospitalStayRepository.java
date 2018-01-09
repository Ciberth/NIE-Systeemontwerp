package be.ugent.intec.persistence;

import org.springframework.data.repository.CrudRepository;

import be.ugent.intec.domainmodel.HospitalStay;

public interface HospitalStayRepository extends CrudRepository<HospitalStay, Long>{

}
