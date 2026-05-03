package tn.amin.keyboard_gpt.instruction.command;

public class HelpCommand extends AbstractCommand {

    @Override
    public String getCommandPrefix() {
        return "?";
    }
}
