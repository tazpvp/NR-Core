package world.ntdi.nrcore.utils.command.simple.builder;

import lombok.Singular;
import lombok.experimental.SuperBuilder;
import world.ntdi.nrcore.utils.command.simple.Label;

import java.util.List;

@SuperBuilder
public class LabelBuilder {
    protected String name;
    protected String permission;
    protected String description;
    protected String usage;
    @Singular
    protected List<String> aliases;

    public Label make() {
        final Label label = new Label(name, permission, aliases);
        if (usage != null) {
            label.setUsage(usage);
        }

        if (description != null) {
            label.setDescription(description);
        }

        return label;
    }

    public static LabelBuilderBuilder<?, ?> of(String name) {
        return LabelBuilder.builder().name(name);
    }

    public static LabelBuilderBuilder<?, ?> of(String name, String permission) {
        return LabelBuilder.builder().name(name).permission(permission);
    }
}
