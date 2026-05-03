package tn.amin.keyboard_gpt.instruction.command;

public class TranslateCommand extends AbstractCommand {
    @Override
    public String getCommandPrefix() {
        return "tr";
    }

    public String getSystemMessage() {
        return "You are a translator. Translate the given text to English. " +
               "Return ONLY the translated text, nothing else. No explanations.";
    }
}
