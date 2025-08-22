package io.github._4drian3d.jdwebhooks.component;

@SuppressWarnings("unused")
public class SeparatorComponent extends Component {
    private final Boolean divider;
    private final Spacing spacing;

    SeparatorComponent(final int id, final Boolean divider, final Spacing spacing) {
        super(ComponentType.SEPARATOR, id);
        this.divider = divider;
        this.spacing = spacing;
    }

    public enum Spacing {
        SMALL(1),
        LARGE(2);

        private final int value;

        Spacing(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public Boolean getDivider() {
        return divider;
    }

    public Spacing getSpacing() {
        return spacing;
    }

    public static class Builder extends Component.Builder<Builder> {
        private Boolean divider;
        private Spacing spacing;

        Builder() {
            super();
            this.divider = null;
            this.spacing = null;
        }

        public Builder divider(final Boolean divider) {
            this.divider = divider;
            return this;
        }

        public Builder spacing(final Spacing spacing) {
            this.spacing = spacing;
            return this;
        }

        @Override
        public SeparatorComponent build() {
            return new SeparatorComponent(id, this.divider, spacing);
        }
    }
}
