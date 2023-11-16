package jpabook.start.domain.booking;

import jpabook.start.domain.house.DateHouse;
import jpabook.start.domain.user.Guest;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Guest guest;

  @OneToMany(mappedBy = "book")
  private List<DateHouse> dateHouse = new ArrayList<>();


}
