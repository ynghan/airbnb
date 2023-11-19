package jpabook.start.service;

import jpabook.start.domain.user.Host;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;


@SpringBootTest
@Transactional
class HostServiceTest {
  HostService hostService;
  @Test
  void persistHouse() {
    Host host = new Host();
    hostService.save(host);
//    hostService.registHouse(host, "금오호텔", 8, 3, 2, HouseType.PARTSPACE, "넓고 쾌적합니다.");



  }
}