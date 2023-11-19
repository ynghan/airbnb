package jpabook.start.domain.house;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.SATURDAY;

class HouseTest {

  @Test
  void printLocalDateTime() {
    LocalDateTime registerDate = LocalDateTime.now();
    int dayOfMonth = registerDate.getDayOfMonth();
    Month month = registerDate.getMonth();
    System.out.println(month);
    System.out.println(dayOfMonth);
  }
}