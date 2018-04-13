package com.mashedtomatoes.shell.deserializer;

import java.util.HashSet;
import java.util.Set;

public class CharacterTalentIdSet {
    private Set<CharacterTalentId> characterTalentIds = new HashSet<>();

    public CharacterTalentIdSet() {
    }

    public Set<CharacterTalentId> getCharacterTalentIds() {
        return characterTalentIds;
    }
}