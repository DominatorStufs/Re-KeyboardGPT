package tn.amin.keyboard_gpt.instruction.command;

import tn.amin.keyboard_gpt.ui.UiInteractor;

public class HelpCommand extends AbstractCommand {
    private static final String HELP_TEXT =
            "📖 KeyboardGPT Commands:\n\n" +
            "⚙️  *#settings#* — Open settings\n\n" +
            "🤖  $<prompt>$ — Send AI prompt\n\n" +
            "🔧  $$ — Configure AI model\n\n" +
            "✏️  %<prefix> <prompt>% — Custom prompt\n\n" +
            "📝  %% — Configure custom prompts\n\n" +
            "🔍  %s <text>% — Web search\n\n" +
            "𝘐  |<text>| — Italic text\n\n" +
            "𝗕  @<text>@ — Bold text\n\n" +
            "~~  ~<text>~ — Strikethrough\n\n" +
            "U  _<text>_ — Underline\n\n" +
            "❓  %?% — Show this help";

    @Override
    public void execute(String prompt) {
        UiInteractor.getInstance().getIMSController().commit(HELP_TEXT);
    }
}
