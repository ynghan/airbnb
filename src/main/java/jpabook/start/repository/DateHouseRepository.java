package jpabook.start.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class DateHouseRepository {

  private EntityManager em;


  public DateHouseRepository(EntityManager em) {
    this.em = em;
  }

//  public DateHouse findByDate(Integer date) {
//    return em.createQuery("select d from DateHouse d where d.dateNumber = :date", DateHouse.class)
//            .setParameter("date", date)
//            .getSingleResult();
//  }
}
