package io.github._4drian3d.jdwebhooks.component;

import org.jetbrains.annotations.*;

import java.util.*;

@SuppressWarnings("unused")
public final class ContainerComponent extends Component {
    @NotNull
    private final List<@NotNull ContainerableComponent> components;
    private final Integer accentColor;
    private final Boolean spoiler;

    ContainerComponent(final int id, @NotNull final List<@NotNull ContainerableComponent> components, final Integer accentColor, final Boolean spoiler) {
        super(ComponentType.CONTAINER, id);

        this.components = List.copyOf(components);
        this.accentColor = accentColor;
        this.spoiler = spoiler;
    }

    @NotNull
    public List<@NotNull ContainerableComponent> getComponents() {
        return components;
    }

    public Integer getAccentColor() {
        return accentColor;
    }

    public Boolean getSpoiler() {
        return spoiler;
    }

    public static class Builder extends Component.Builder<Builder> {
        @NotNull
        private final List<@NotNull ContainerableComponent> components;
        private Integer accentColor;
        private Boolean spoiler;

        Builder() {
            super();
            this.components = new ArrayList<>();
            this.accentColor = null;
            this.spoiler = null;
        }

        public Builder component(@NotNull final ContainerableComponent component) {
            this.components.add(component);
            return this;
        }

        public Builder components(@NotNull final ContainerableComponent... components) {
            this.components.clear();
            Collections.addAll(this.components, components);
            return this;
        }

        public Builder components(@NotNull final List<@NotNull ContainerableComponent> components) {
            this.components.clear();
            this.components.addAll(components);
            return this;
        }

        public Builder accentColor(final Integer accentColor) {
            this.accentColor = accentColor;
            return this;
        }

        public Builder spoiler(final Boolean spoiler) {
            this.spoiler = spoiler;
            return this;
        }

        @Override
        public ContainerComponent build() {
            return new ContainerComponent(id, components, accentColor, spoiler);
        }
    }
}
