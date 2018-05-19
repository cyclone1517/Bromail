package ServerImpl;

import ServerInterface.ServiceManage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class ServerManageUI {

    public ServiceManageImpl myServer;

    public static void main(String args[]) {

        new ServerManageUI().showUI();
    }

//	by blackwhite97
    public void showUI(){
//		定义窗口
        JFrame frame = new JFrame(" brother server ~ version 1.0 ");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

//		设置布局
        frame.setLayout(new BorderLayout());

//		顶层菜单
        JPanel up = new JPanel();
        JMenuBar top_menu = new JMenuBar();
//		JMenu start = new JMenu("开始");
        JMenu help = new JMenu("帮助");
//		top_menu.add(start);
        top_menu.add(help);
        frame.add(top_menu, BorderLayout.NORTH);

//		总模块
        JTabbedPane module = new JTabbedPane(JTabbedPane.TOP);

//		服务器管理模块
        JPanel server_module = new JPanel(); // 服务器模块

        JPanel start_server = new JPanel(); //启动服务器
        start_server.setBorder(BorderFactory.createTitledBorder("启动服务器")); // 框名
        start_server.setPreferredSize(new Dimension(580, 80)); // 框大小
        JLabel server_port = new JLabel("请输入服务器端口号: ");// 提示文本
        JTextField port = new JTextField(5); // 输入文本大小
        JButton start_button = new JButton("点击启动服务器!"); // 点击按钮
        start_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionServer(port, start_button, frame);
            }
        });
        start_server.add(server_port);
        start_server.add(port);
        start_server.add(start_button);
        server_module.add(start_server);

        // pop
        JPanel pop = new JPanel(); // pop协议
        pop.setBorder(BorderFactory.createTitledBorder("POP3协议"));
        pop.setPreferredSize(new Dimension(580, 150)); // 框大小
        server_module.add(pop);

        // stmp
        JPanel stmp = new JPanel(); // pop协议
        stmp.setBorder(BorderFactory.createTitledBorder("STMP协议"));
        stmp.setPreferredSize(new Dimension(580, 150)); // 框大小
        server_module.add(stmp);

        module.add(server_module, "服务器管理");


//		用户管理模块
        JPanel user_module = new JPanel();
        module.add(user_module, "用户管理");




//		日志管理模块
        JPanel log_module = new JPanel();
        module.add(log_module, "日志管理");


//		加入JTabbedPane组件
        frame.add(module);


        frame.setVisible(true);
    }

    public void actionServer(JTextField port, JButton start_button, JFrame frame){
        if (myServer == null){
            int thisPort = Integer.parseInt(port.getText());
            myServer = new ServiceManageImpl(thisPort, ServiceManageImpl.ServerType.SMTP);
            myServer.start();
            start_button.setText("点击停止服务器!");
            frame.setTitle(" brother server ~ version 1.0 (运行中)");

            ServiceManageImpl sm = new ServiceManageImpl(thisPort+1, ServiceManageImpl.ServerType.POP);
            sm.start();
        }else if (myServer.isRun_state()){
            myServer.stopServer();
            myServer = null;
            start_button.setText("点击启动服务器!");
            frame.setTitle(" brother server ~ version 1.0 ");

        }
    }

}
