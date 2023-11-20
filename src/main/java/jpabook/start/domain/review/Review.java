package jpabook.start.domain.review;

import jpabook.start.domain.booking.Book;
import jpabook.start.domain.house.House;
import jpabook.start.domain.user.Guest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "REVIEW_ID")
  private Long id;

  @Enumerated(EnumType.STRING)
  private StarScore starScore;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "BOOK_ID")
  private Book book;

  private String contents;

}
