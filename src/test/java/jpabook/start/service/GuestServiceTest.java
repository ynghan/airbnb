package jpabook.start.service;

import jpabook.start.domain.house.House;
import jpabook.start.repository.HouseRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuestServiceTest {
  @Test
  void readByCapacity() {

    HouseRepository houseRepository = new HouseRepository();

    List<House> byCapacity = houseRepository.findByCapacity(6);

    for (House house : byCapacity) {
      System.out.println(house.getName());
    }
  }

}