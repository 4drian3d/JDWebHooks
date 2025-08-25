package io.github._4drian3d.jdwebhooks;

import org.jetbrains.annotations.*;

import java.io.*;

import static java.util.Objects.*;

public record FileAttachment(@NotNull File file, String filename, String description) {
    public FileAttachment {
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("File must exist and be a valid file");
        }
    }

    public String getFilename() {
        return filename != null ? filename : file.getName();
    }

    public static Builder builder(File file) {
        requireNonNull(file);
        return new Builder(file);
    }

    public static class Builder {
        @NotNull
        private final File file;
        private String filename;
        private String description;

        Builder(@NotNull File file) {
            this.file = file;
        }

        public Builder filename(String filename) {
            this.filename = filename;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public FileAttachment build() {
            return new FileAttachment(file, filename, description);
        }
    }
}
