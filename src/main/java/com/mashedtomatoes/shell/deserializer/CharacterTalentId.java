package com.mashedtomatoes.shell.deserializer;

import com.mashedtomatoes.celebrity.Character;

public class CharacterTalentId {
    private Character character;
    private Integer talentId;

    public CharacterTalentId() {
    }

    public CharacterTalentId(Character character, Integer talentId) {
        this.character = character;
        this.talentId = talentId;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Integer getTalentId() {
        return talentId;
    }

    public void setTalentId(Integer talentId) {
        this.talentId = talentId;
    }
}