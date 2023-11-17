package jpabook.start.repository.userRepository;

import jpabook.start.domain.user.Guest;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;


@RequiredArgsConstructor
public class GuestRepository {

  private final EntityManager em;

  public void save(Guest guest) {
    em.persist(guest);
  }

}
