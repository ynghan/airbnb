package jpabook.start.repository.userRepository;

import jpabook.start.domain.user.Host;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;


@RequiredArgsConstructor
public class HostRepository {

  private final EntityManager em;

  public void save(Host host) {
    em.persist(host);
  }

}
