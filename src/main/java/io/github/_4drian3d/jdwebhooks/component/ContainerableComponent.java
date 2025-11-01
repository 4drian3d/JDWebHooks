package io.github._4drian3d.jdwebhooks.component;

/**
 * A type of component that can be contained in a {@link ContainerComponent}
 */
public sealed interface ContainerableComponent
    permits FileComponent, MediaGalleryComponent, SectionComponent, SeparatorComponent, TextDisplayComponent {
}
