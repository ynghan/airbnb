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

  private String comments;

  @Enumerated(EnumType.STRING)
  private StarScore starScore;

  @OneToOne(mappedBy = "review", cascade = CascadeType.ALL)
  private Book book;


  public Review(Book book, StarScore starScore, String comments) {
    this.book = book;
    book.setReview(this);
    this.starScore = starScore;
    this.comments = comments;
  }

}
