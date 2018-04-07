package com.mashedtomatoes.shell.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mashedtomatoes.model.celebrity.Character;

import java.io.IOException;
import java.util.stream.StreamSupport;

public class CharacterTalentIdSetDeserializer extends StdDeserializer<CharacterTalentIdSet> {
    private static final int MaxCastMembers = 15;

    public CharacterTalentIdSetDeserializer() {
        this(null);
    }

    public CharacterTalentIdSetDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public CharacterTalentIdSet deserialize(JsonParser parser, DeserializationContext deserializer) {
        try {
            ObjectCodec codec = parser.getCodec();
            JsonNode rootNode = codec.readTree(parser);
            CharacterTalentIdSet set = new CharacterTalentIdSet();
            Iterable<JsonNode> nodeIterable = () -> rootNode.get("cast").elements();
            StreamSupport.stream(nodeIterable.spliterator(), false)
                    .limit(MaxCastMembers)
                    .forEach(node -> set.getCharacterTalentIds().add(deserializeCharacterTalentId(node)));

            return set;
        } catch (IOException ex) {
            System.err.println(ex);
            return null;
        }
    }

    public CharacterTalentId deserializeCharacterTalentId(JsonNode node) {
        CharacterTalentId characterTalentId = new CharacterTalentId();
        Character c = new Character();
        c.setName(node.get("character").asText());
        c.setCastOrder(node.get("order").asInt());
        characterTalentId.setCharacter(c);
        characterTalentId.setTalentId(node.get("id").asInt());
        return characterTalentId;
    }
}
