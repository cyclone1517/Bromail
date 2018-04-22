package ServerImpl;

import SMTPstates.SENDState;
import SMTPstates.State;

import java.io.IOException;

/*
对命令行命令处理的类
 */
public class SMTPReceiver {
    State state;
    private String[] commands = {"helo", "auth", "mail", "rcpt", "data", "quit"};

    public void handleInput(ServerThread server, String inStr) {
        if ((state instanceof SENDState)) {
            this.state.handle(server, inStr);
            return ;
        }
        if (!checkCommand(inStr)) {
            server.sendMsgToMe("500 Invalid command");
            return;
        }
        String com = this.getCommand(inStr);
        String arg = this.getArgument(inStr);

        if ("quit".equals(com)) {
            server.setFlag(false);
            return;
        }
    }
    private String getCommand(String inStr) {
        int bPos = inStr.indexOf(" ");
        if (bPos == -1)
            return inStr.toLowerCase();
        return inStr.substring(0, bPos).toLowerCase();
    }
    private String getArgument(String inStr) {
        int bPos = inStr.indexOf(" ");
        if (bPos == -1)
            return "";
        return inStr.substring(bPos + 1, inStr.length());
    }
    private boolean checkCommand(String inStr) {
        if ("".equals(inStr))
            return false;

        String com = getCommand(inStr);

        for (int i = 0; i < commands.length; i++)
            if (commands[i].endsWith(com))
                return true;
        return false;
    }
}
