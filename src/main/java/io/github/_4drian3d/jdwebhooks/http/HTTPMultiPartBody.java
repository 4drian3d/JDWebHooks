package io.github._4drian3d.jdwebhooks.http;

public sealed interface HTTPMultiPartBody permits HTTPMultiPartBodyImpl {
  byte[] bytes();

  String boundary();

  default String contentType() {
    return "multipart/form-data; boundary=" + boundary();
  }

  static Builder builder() {
    return new HTTPMultiPartBodyImpl.Builder();
  }

  sealed interface Builder permits HTTPMultiPartBodyImpl.Builder {
    Builder addPart(String fieldName, MultiPartRecord.Content<?> content);

    Builder addPart(String fieldName, String fileName, MultiPartRecord.Content.FileContent content);

    HTTPMultiPartBody build();
  }
}
