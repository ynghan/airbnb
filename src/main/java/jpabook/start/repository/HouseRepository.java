package jpabook.start.repository;

import jpabook.start.domain.booking.Book;
import jpabook.start.domain.house.DateHouse;
import jpabook.start.domain.house.House;
import jpabook.start.domain.house.HouseType;
import jpabook.start.domain.review.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class HouseRepository {

  public HouseRepository(EntityManager em) {
    this.em = em;
  }

  private EntityManager em;

  public void save(House house) { em.persist(house); }


  public House findOne(Long id) {
    return em.find(House.class, id);
  }

  public List<House> findByCapacity(int capacity) {
    return em.createQuery("select h from House h where h.capacity >= :capacity", House.class)
            .setParameter("capacity", capacity).getResultList();
  }

  public House findByName(String name) {
    try {
      return em.createQuery("select h from House h where h.name = :name", House.class)
              .setParameter("name", name)
              .getSingleResult();
    } catch (Exception e) {
      // 결과가 없는 경우 처리
      System.out.println("결과가 없는 경우");
    }
    return new House();
  }


  public List<House> findByType(HouseType houseType) {
    return em.createQuery("select h from House h where h.houseType = :houseType", House.class)
            .setParameter("houseType", houseType)
            .getResultList();
  }

  public List<House> findByDateRange(int checkIn, int checkOut) {
//    List<House> houses = em.createQuery("select h from House h join fetch h.dateHouses d where d.houseDate >= :checkIn and d.houseDate <= :checkOut", House.class)
//            .setParameter("checkIn", checkIn)
//            .setParameter("checkOut", checkOut)
//            .getResultList();
    List<House> houses = em.createQuery("select h from House h", House.class).getResultList();
    int count;
    List<House> returnHouse = new ArrayList<>();

    for (House house : houses) {
      count = 0;
      List<DateHouse> dateHouses = house.getDateHouses();
      for (DateHouse dateHouse : dateHouses) {
        if(dateHouse.getHouseDate() >= checkIn && dateHouse.getHouseDate() <= checkOut) {
          count++;
        }
      }
      if((checkOut - checkIn + 1) == count) {
        returnHouse.add(house);
      }
    }

    return houses;
  }

  public void getAllStarPoint(House house) {
    Set<Review> reviews = getAllReviews(house);

    for (Review review : reviews) {
      System.out.print(review.getBook().getGuest().getName() + " : ");
      System.out.println(review.getStarScore().getStarPoint());
    }
  }

  public void getAllContents(House house) {
    Set<Review> reviews = getAllReviews(house);

    for (Review review : reviews) {
      System.out.print(review.getBook().getGuest().getName() + " : ");
      System.out.println(review.getComments());
    }
  }

  private static Set<Review> getAllReviews(House house) {
    List<DateHouse> dateHouses = house.getDateHouses();
    Set<Review> reviews = new HashSet<>();
    for (DateHouse dateHouse : dateHouses) {
      Book book = dateHouse.getBook();
      if(book != null) {
        Review review = book.getReview();
        if(review != null) {
          reviews.add(review);
        }
      }
    }
    return reviews;
  }




}
