package jpabook.start.repository.userRepository;

import jpabook.start.domain.house.DateHouse;
import jpabook.start.domain.house.House;
import jpabook.start.domain.user.Host;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class HostRepository {

  private EntityManager em;

  public HostRepository(EntityManager em) {
    this.em = em;
  }

  public void save(Host host) {
    em.persist(host);
  }

  public DateHouse getDateHouseByDate(House house, int date) {
//  public List<DateHouse> getDateHouseByDate(int date) {
    return em.createQuery("select d from DateHouse d where d.houseDate = :date and d.house = :house", DateHouse.class)
            .setParameter("date", date)
            .setParameter("house", house)
            .getSingleResult();

//    DateHouse singleResult = em.createQuery("select d from DateHouse d join fetch d.house h where d.houseDate = :date and h = :house", DateHouse.class)
//            .setParameter("date", date)
//            .setParameter("house", house)
//            .getSingleResult();

  }
}
