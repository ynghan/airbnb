package jpabook.start.domain.house;

import jpabook.start.domain.booking.Book;
import jpabook.start.domain.day.Date;
import jpabook.start.domain.sale.Sale;

import javax.persistence.*;

public class DateHouse {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Book book;

  @ManyToOne(fetch = FetchType.LAZY)
  private House house;

  @OneToOne(fetch = FetchType.LAZY)
  private Date date;

  @OneToOne(fetch = FetchType.LAZY)
  private Sale sale;

}
