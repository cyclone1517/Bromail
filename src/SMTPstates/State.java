package SMTPstates;

import ServerImpl.ServerThread;

public abstract class State {
//    处理客户端命令行
    public abstract void handle();
//    处理客户端连接（非命令行）
    public void handle(ServerThread serverThread, String inStr){};
}
