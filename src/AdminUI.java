import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUI {

    public JFrame view;

    public JButton btnSystemConfig = new JButton("System Configuration");
    public JButton btnAddUser = new JButton("Add New User");
    public JButton btnDeleteUser = new JButton("Delete User");
    public JButton btnChangeUserType = new JButton("Change User Type");
    public JButton btnManageUserInfo = new JButton("Manage Current User Info");


    public AdminUI(UserModel user) {

        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setTitle("Store Management System - Admin View");
        view.setSize(1000, 600);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Store Management System");

        title.setFont(title.getFont().deriveFont(24.0f));
        view.getContentPane().add(title);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnSystemConfig);
        panelButtons.add(btnAddUser);
        panelButtons.add(btnDeleteUser);
        panelButtons.add(btnChangeUserType);

        view.getContentPane().add(panelButtons);

        JPanel userButtons = new JPanel(new FlowLayout());
        userButtons.add(btnManageUserInfo);

        view.getContentPane().add(userButtons);

        btnSystemConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SystemConfigUI ui = new SystemConfigUI();
                ui.run();
            }
        });

        btnAddUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddUserUI ui = new AddUserUI();
                ui.run();
            }
        });

        btnDeleteUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DeleteUserUI ui = new DeleteUserUI();
                ui.run();
            }
        });

        btnChangeUserType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ChangeUserTypeUI ui = new ChangeUserTypeUI();
                ui.run();
            }
        });

        btnManageUserInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ManageUserInfoUI ui = new ManageUserInfoUI(user);
                ui.view.setVisible(true);
            }
        } );
    }
}
