package com.mashedtomatoes.shell.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.util.stream.StreamSupport;
import com.mashedtomatoes.celebrity.Character;

class MediaCreditsDeserializer extends StdDeserializer<MediaCredits> {

  private static final int MaxCastMembers = 15;

  private enum MovieDBKey {
    Cast("cast"),
    Crew("crew"),
    Character("character"),
    Order("order"),
    Job("job") ,
    Department("department"),
    TalentId("id");

    private String key;

    MovieDBKey(String key) {
      this.key = key;
    }

    public String toString() {
      return this.key;
    }
  }

  public MediaCreditsDeserializer() {
    this(null);
  }

  public MediaCreditsDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public MediaCredits deserialize(JsonParser parser, DeserializationContext ctxt) {
    try {
      ObjectCodec codec = parser.getCodec();
      JsonNode rootNode = codec.readTree(parser);
      MediaCredits mediaCredits = new MediaCredits();

      Iterable<JsonNode> castIterable = () -> rootNode.get(MovieDBKey.Cast.toString()).elements();
      StreamSupport.stream(castIterable.spliterator(), false)
          .limit(MaxCastMembers)
          .forEach(node -> mediaCredits.getCast().add(deserializeMediaCastMember(node)));

      Iterable<JsonNode> crewIterable = () -> rootNode.get(MovieDBKey.Crew.toString()).elements();
      StreamSupport.stream(crewIterable.spliterator(), false)
          .forEach(node -> mediaCredits.getCrew().add(deserializeMediaCrewMember(node)));

      return mediaCredits;
    } catch (IOException ex) {
      return null;
    }
  }

  public MediaCastMember deserializeMediaCastMember(JsonNode node) {
    MediaCastMember mediaCastMember = new MediaCastMember();
    Character character = new Character();
    character.setName(node.get(MovieDBKey.Character.toString()).asText());
    character.setCastOrder(node.get(MovieDBKey.Order.toString()).asInt());
    mediaCastMember.setCharacter(character);
    mediaCastMember.setTalentId(node.get(MovieDBKey.TalentId.toString()).asInt());
    return mediaCastMember;
  }

  public MediaCrewMember deserializeMediaCrewMember(JsonNode node) {
    MediaCrewMember mediaCrewMember = new MediaCrewMember();
    mediaCrewMember.setJob(node.get(MovieDBKey.Job.toString()).asText());
    mediaCrewMember.setDepartment(node.get(MovieDBKey.Department.toString()).asText());
    mediaCrewMember.setTalentId(node.get(MovieDBKey.TalentId.toString()).asInt());
    return mediaCrewMember;
  }
}
