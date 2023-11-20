package jpabook.start.domain.review;

import lombok.Getter;

@Getter
public enum StarScore {
  ONE(1),
  TWO(2),
  THREE(3),
  FOUR(4),
  FIVE(5);

  private final double starPoint;

  StarScore(double starPoint) {
    this.starPoint = starPoint;
  }

}
