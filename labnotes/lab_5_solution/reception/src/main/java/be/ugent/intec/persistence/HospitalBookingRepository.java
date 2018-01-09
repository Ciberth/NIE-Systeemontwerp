package be.ugent.intec.persistence;

import org.springframework.data.repository.CrudRepository;

import be.ugent.intec.domainmodel.HospitalBooking;

public interface HospitalBookingRepository extends CrudRepository<HospitalBooking, Long> {

}
