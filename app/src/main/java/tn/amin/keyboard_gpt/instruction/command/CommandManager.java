package tn.amin.keyboard_gpt.instruction.command;

import android.os.Bundle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tn.amin.keyboard_gpt.SPManager;
import tn.amin.keyboard_gpt.llm.LanguageModelField;
import tn.amin.keyboard_gpt.ui.UiInteractor;
import tn.amin.keyboard_gpt.listener.ConfigChangeListener;
import tn.amin.keyboard_gpt.llm.LanguageModel;

public class CommandManager implements ConfigChangeListener {
    private static final String HELP_TEXT =
            "📖 KeyboardGPT Commands:\n\n" +
            "⚙️  *#settings#* — Open settings\n\n" +
            "🤖  $<prompt>$ — Send AI prompt\n\n" +
            "🔧  $$ — Configure AI model\n\n" +
            "✏️  %<prefix> <prompt>% — Custom prompt\n\n" +
            "📝  %% — Configure custom prompts\n\n" +
            "🔍  %s <text>% — Web search\n\n" +
            "🌐  %tr <text>% — Translate to English\n\n" +
            "𝘐  |<text>| — Italic text\n\n" +
            "𝗕  @<text>@ — Bold text\n\n" +
            "~~  ~<text>~ — Strikethrough\n\n" +
            "U  _<text>_ — Underline\n\n" +
            "❓  %?% — Show this help";

    private final static Map<String, AbstractCommand> STATIC_COMMAND_MAP = Map.of(
            "s", new WebSearchCommand(),
            "?", new HelpCommand(),
            "tr", new TranslateCommand()
    );

    private Map<String, AbstractCommand> commandMap;

    public CommandManager() {
        UiInteractor.getInstance().registerConfigChangeListener(this);
        List<GenerativeAICommand> aiCommands = SPManager.getInstance().getGenerativeAICommands();
        updateCommandMap(aiCommands);
    }

    private void updateCommandMap(List<GenerativeAICommand> aiCommands) {
        commandMap = new HashMap<>(STATIC_COMMAND_MAP);
        for (GenerativeAICommand command: aiCommands) {
            commandMap.put(command.getCommandPrefix(), command);
        }
    }

    public AbstractCommand get(String prefix) {
        return commandMap.get(prefix);
    }

    public String getHelpText() {
        return HELP_TEXT;
    }

    @Override
    public void onLanguageModelChange(LanguageModel model) {}

    @Override
    public void onLanguageModelFieldChange(LanguageModel model, LanguageModelField field, String value) {}

    @Override
    public void onCommandsChange(String commandsRaw) {
        updateCommandMap(Commands.decodeCommands(commandsRaw));
    }

    @Override
    public void onPatternsChange(String patternsRaw) {}

    @Override
    public void onOtherSettingsChange(Bundle otherSettings) {}
}
