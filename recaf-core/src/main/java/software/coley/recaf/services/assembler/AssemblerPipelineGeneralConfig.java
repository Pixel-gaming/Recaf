package software.coley.recaf.services.assembler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import software.coley.observables.ObservableInteger;
import software.coley.observables.ObservableString;
import software.coley.recaf.config.BasicConfigContainer;
import software.coley.recaf.config.BasicConfigValue;
import software.coley.recaf.config.ConfigGroups;
import software.coley.recaf.services.ServiceConfig;

@ApplicationScoped
public class AssemblerPipelineGeneralConfig extends BasicConfigContainer implements ServiceConfig {

    private final ObservableString disassemblyIndent = new ObservableString("    ");
    private final ObservableInteger disassemblyAstParseDelay = new ObservableInteger(100);

    @Inject
    public AssemblerPipelineGeneralConfig() {
        super(ConfigGroups.SERVICE_ASSEMBLER, AssemblerPipelineManager.SERVICE_ID + ConfigGroups.PACKAGE_SPLIT
                + "general" + CONFIG_SUFFIX);

        addValue(new BasicConfigValue<>("disassembly_indent", String.class, disassemblyIndent));
        addValue(new BasicConfigValue<>("disassembly_ast_parse_delay", Integer.class, disassemblyAstParseDelay));
    }

    public ObservableString getDisassemblyIndent() {
        return disassemblyIndent;
    }

    public ObservableInteger getDisassemblyAstParseDelay() {
        return disassemblyAstParseDelay;
    }
}
