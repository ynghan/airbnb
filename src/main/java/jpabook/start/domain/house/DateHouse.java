package jpabook.start.domain.house;

import jpabook.start.domain.booking.Book;
import jpabook.start.domain.day.Date;
import jpabook.start.domain.sale.Sale;

import javax.persistence.*;
@Entity
public class DateHouse {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "date_house_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "book_id")
  private Book book;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "house_id")
  private House house;

  @OneToOne(fetch = FetchType.LAZY)
  private Date date;

  @OneToOne(fetch = FetchType.LAZY)
  private Sale sale;

}
