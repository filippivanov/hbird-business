package eu.estcube.gsconnector.commands.radio;

import eu.estcube.domain.TelemetryCommand;
import eu.estcube.gsconnector.commands.HamlibCommandBuilder;

public class SetMemoryBankNr implements HamlibCommandBuilder {

    public StringBuilder createMessageString(TelemetryCommand command) {
        StringBuilder messageString = new StringBuilder();
        messageString.append("+B ");
        messageString.append(command.getParameter("Bank"));
        messageString.append("\n");
        return messageString;
    }
}
