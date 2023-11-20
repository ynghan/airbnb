package jpabook.start.domain.house;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.SATURDAY;

class HouseTest {

  @Test
  void printLocalDateTime() {
//    LocalDateTime registerDate = LocalDateTime.now();
//    Month month = registerDate.getMonth();
//    int year = registerDate.getYear();
//    System.out.println(year);
//    System.out.println(Month.of(12));
//

    LocalDateTime now = LocalDateTime.now();
    int lengthOfMonth = YearMonth.of(now.getYear(), now.getMonth()).lengthOfMonth();
    char[] calender = new char[lengthOfMonth];
    int i = 1;
    calender[1] = (char) i;
    System.out.println(calender[1]);

  }
}