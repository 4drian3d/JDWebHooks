package io.github._4drian3d.jdwebhooks.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

record HTTPMultiPartBodyImpl(byte[] bytes, String boundary) implements HTTPMultiPartBody {

  static final class Builder implements HTTPMultiPartBody.Builder {
    private final List<MultiPartRecord> parts = new ArrayList<>();

    public Builder addPart(String fieldName, MultiPartRecord.Content<?> content) {
      final MultiPartRecord part = new MultiPartRecord(fieldName, null, content);
      this.parts.add(part);
      return this;
    }

    public Builder addPart(String fieldName, String fileName, MultiPartRecord.Content.FileContent content) {
      final MultiPartRecord part = new MultiPartRecord(fieldName, fileName, content);
      this.parts.add(part);
      return this;
    }

    public HTTPMultiPartBodyImpl build() {
      final String boundary = Long.toString(System.currentTimeMillis());
      try (DataOutPut out = new DataOutPut(new ByteArrayOutputStream())) {
        for (final MultiPartRecord record : parts) {
          final StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("--")
              .append(boundary)
              .append("\r\n")
              .append("Content-Disposition: form-data; name=\"")
              .append(record.fieldName());
          if (record.filename() != null) {
            stringBuilder.append("\"; filename=\"")
                .append(record.filename());
          }
          out.write(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
          out.write(("\"\r\n").getBytes(StandardCharsets.UTF_8));
          switch (record.content()) {
            case MultiPartRecord.Content.StringContent string -> {
              out.write(("\r\n\r\n").getBytes(StandardCharsets.UTF_8));
              out.write(string.content().getBytes(StandardCharsets.UTF_8));
            }
            case MultiPartRecord.Content.FileContent fileContent -> {
              out.write(("Content-Type: application/octet-stream\r\n\r\n").getBytes(StandardCharsets.UTF_8));
              out.copyfrom(fileContent.content());
            }
            default -> throw new UnsupportedOperationException("Unsupported content");
          }
          out.write("\r\n".getBytes(StandardCharsets.UTF_8));
        }
        out.write(("--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8));

        return new HTTPMultiPartBodyImpl(out.dataStream.toByteArray(), boundary);
      }
    }

    private record DataOutPut(ByteArrayOutputStream dataStream) implements AutoCloseable {

      private void write(byte[] b) {
        try {
          dataStream.write(b);
        } catch (IOException e) {
          throw new UncheckedIOException(e);
        }
      }

      private void copyfrom(Path path) {
        try {
          Files.copy(path, dataStream);
        } catch (IOException e) {
          throw new UncheckedIOException(e);
        }
      }

      @Override
      public void close() {
        try {
          dataStream.close();
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }
    }
  }
}

