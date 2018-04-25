package Utils;

import ServerImpl.POP3Server;
import ServerImpl.ServerManageUI;

import java.io.IOException;

public class Test {
        public static void main(String args[]) {
            new ServerManageUI().showUI();
//            try {
//                new POP3Server().setupPop3Sever(110);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
}
