package com.mashedtomatoes.http;

import javax.validation.constraints.NotNull;

public class UserMediaListsRequest {
  private long id; // id of media
  private boolean add; // true = add, false = remove
  private UserMediaList list;

  public UserMediaListsRequest() {}

  @NotNull
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @NotNull
  public boolean isAdd() {
    return add;
  }

  public void setAdd(boolean add) {
    this.add = add;
  }

  @NotNull
  public UserMediaList getList() {
    return list;
  }

  public void setList(UserMediaList list) {
    this.list = list;
  }
}
