package com.example.vinod.locusassignment.utils;

public class JSONResponse {
  private String jsonResponse = "{ \"assignment\": [\n" + "  {\n" + "    \"type\": \"PHOTO\",\n"
      + "    \"id\": \"pic1\",\n" + "    \"title\": \"Photo 1\",\n" + "    \"dataMap\": {}\n"
      + "  },\n" + "  {\n" + "    \"id\": \"choice1\",\n" + "    \"type\": \"SINGLE_CHOICE\",\n"
      + "    \"title\": \"Photo 1 choice\",\n" + "    \"dataMap\": {\n" + "      \"options\": [\n"
      + "        \"Good\",\n" + "        \"OK\",\n" + "        \"BAD\"\n" + "      ]\n" + "    }\n"
      + "  },\n" + "  {\n" + "    \"type\": \"COMMENT\",\n" + "    \"id\": \"comment1\",\n"
      + "    \"title\": \"Photo 1 comments\",\n" + "    \"dataMap\": {}\n" + "  },\n" + "  {\n"
      + "    \"type\": \"PHOTO\",\n" + "    \"id\": \"pic1\",\n" + "    \"title\": \"Photo 1\",\n"
      + "    \"dataMap\": {}\n" + "  },\n" + "  {\n" + "    \"type\": \"PHOTO\",\n"
      + "    \"id\": \"pic2\",\n" + "    \"title\": \"Photo 2\",\n" + "    \"dataMap\": {}\n"
      + "  },\n" + "  {\n" + "    \"type\": \"SINGLE_CHOICE\",\n" + "    \"id\": \"choice2\",\n"
      + "    \"title\": \"Photo 2 choice\",\n" + "    \"dataMap\": {\n" + "      \"options\": [\n"
      + "        \"Good\",\n" + "        \"OK\",\n" + "        \"BAD\"\n" + "      ]\n" + "    }\n"
      + "  },\n" + "  {\n" + "    \"type\": \"COMMENT\",\n" + "    \"id\": \"comment2\",\n"
      + "    \"title\": \"Photo 2 comments\",\n" + "    \"dataMap\": {}\n" + "  }\n" + "]\n" + "}";

  public JSONResponse() {

  }

  public String getJsonResponse() {
    return jsonResponse;
  }

  public void setJsonResponse(String jsonResponse) {
    this.jsonResponse = jsonResponse;
  }
}
