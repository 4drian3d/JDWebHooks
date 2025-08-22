package io.github._4drian3d.jdwebhooks.component;

import org.jetbrains.annotations.*;

import java.util.*;

import static java.util.Objects.*;

public final class SectionComponent extends Component {
    @NotNull
    private final List<@NotNull TextDisplayComponent> components;
    @NotNull
    private final AccessoryComponent accessory;

    SectionComponent(int id, @NotNull final List<@NotNull TextDisplayComponent> components, @NotNull final AccessoryComponent accessory) {
        super(ComponentType.SECTION, id);

        if (components.isEmpty() || components.size() > 3) {
            throw new IllegalArgumentException("Section component must have between 1 and 3 text display components.");
        }
        this.components = List.copyOf(components);
        this.accessory = accessory;
    }

    @NotNull
    public List<@NotNull TextDisplayComponent> getComponents() {
        return components;
    }

    @NotNull
    public AccessoryComponent getAccessory() {
        return accessory;
    }

    @SuppressWarnings("unused")
    public static final class Builder extends Component.Builder<Builder> {
        @NotNull
        private final List<@NotNull TextDisplayComponent> components;
        private AccessoryComponent accessory;

        Builder() {
            super();
            this.components = new ArrayList<>();
        }

        public Builder component(@NotNull final TextDisplayComponent component) {
            this.components.add(component);
            return this;
        }

        public Builder components(@NotNull final TextDisplayComponent... components) {
            this.components.clear();
            Collections.addAll(this.components, components);
            return this;
        }

        public Builder components(@NotNull final List<@NotNull TextDisplayComponent> components) {
            this.components.clear();
            this.components.addAll(components);
            return this;
        }

        public Builder accessory(@NotNull final AccessoryComponent accessory) {
            this.accessory = accessory;
            return this;
        }


        @Override
        public SectionComponent build() {
            requireNonNull(accessory, "Accessory component must be provided.");
            return new SectionComponent(id, components, accessory);
        }
    }
}
