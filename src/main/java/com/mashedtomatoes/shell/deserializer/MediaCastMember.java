package com.mashedtomatoes.shell.deserializer;

import com.mashedtomatoes.celebrity.Character;

public class MediaCastMember {

  private Character character;
  private Integer talentId;

  public MediaCastMember() {}

  public Character getCharacter() {
    return character;
  }

  public Integer getTalentId() {
    return talentId;
  }

  public void setCharacter(Character character) {
    this.character = character;
  }

  public void setTalentId(Integer talentId) {
    this.talentId = talentId;
  }
}
