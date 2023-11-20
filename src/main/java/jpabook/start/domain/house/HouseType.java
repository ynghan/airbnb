package jpabook.start.domain.house;

import lombok.Getter;

@Getter
public enum HouseType {

  ENTIRESPACE("전체 공간"),
  PARTSPACE("개인실");

  private final String space;

  HouseType(String space) {
    this.space = space;
  }
}
