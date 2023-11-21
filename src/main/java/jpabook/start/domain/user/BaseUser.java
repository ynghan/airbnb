package jpabook.start.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@Setter
@Getter
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseUser {
  private String name;
  private int age;

}
