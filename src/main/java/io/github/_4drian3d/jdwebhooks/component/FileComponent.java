package io.github._4drian3d.jdwebhooks.component;

import org.jetbrains.annotations.*;

public final class FileComponent extends Component implements ContainerableComponent {
    @NotNull
    private final String file;
    private final Boolean spoiler;

    FileComponent(final int id, @NotNull final String file, final Boolean spoiler) {
        super(ComponentType.FILE, id);
        this.file = "attachment://" + file;
        this.spoiler = spoiler;
    }

    @NotNull
    public String getFile() {
        return file;
    }

    public Boolean getSpoiler() {
        return spoiler;
    }

    public static class Builder extends Component.Builder<Builder> {
        @NotNull
        private String file;
        private Boolean spoiler;

        Builder(@NotNull final String file) {
            super();
            this.file = file;
            this.spoiler = null;
        }

        public Builder file(@NotNull final String file) {
            this.file = file;
            return this;
        }

        public Builder spoiler(final Boolean spoiler) {
            this.spoiler = spoiler;
            return this;
        }

        @Override
        public FileComponent build() {
            return new FileComponent(id, file, spoiler);
        }
    }
}
