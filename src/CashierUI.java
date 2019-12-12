import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CashierUI {
    public JFrame view;

    public JButton btnManagePurchase = new JButton("Manage Purchases");
    public JButton btnManageCustomer = new JButton("Manage Customer");
    public JButton btnManageUserInfo = new JButton("Manage Current User Info");

    public CashierUI(UserModel user) {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setTitle("Store Management System - Cashier View");
        view.setSize(1000, 600);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Store Management System");

        title.setFont (title.getFont ().deriveFont (24.0f));
        view.getContentPane().add(title);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnManagePurchase);
        panelButtons.add(btnManageCustomer);

        view.getContentPane().add(panelButtons);

        JPanel userButtons = new JPanel(new FlowLayout());
        userButtons.add(btnManageUserInfo);

        view.getContentPane().add(userButtons);


        btnManagePurchase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ManagePurchaseUI ap = new ManagePurchaseUI();
                ap.run();
            }
        });

        btnManageCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ManageCustomerUI ap = new ManageCustomerUI();
                ap.run();
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