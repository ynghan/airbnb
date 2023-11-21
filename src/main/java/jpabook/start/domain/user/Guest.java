package jpabook.start.domain.user;

import jpabook.start.domain.booking.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Guest extends BaseUser {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "GUEST_ID")
  private Long id;

  @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL)
  private List<Book> books = new ArrayList<>();


  public Guest(String name, int age) {
    super.setName(name);
    super.setAge(age);
  }
}
