import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageUserInfoUI {

    public JFrame view;

    public JButton btnUpdatePassword = new JButton("Update Password");
    public JButton btnUpdateName = new JButton("Update Name");

    public JLabel labFullname = new JLabel("Current User's Name: ");


    public ManageUserInfoUI(UserModel user) {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Manage User Information");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        labFullname.setText("Current User's Name: " + user.mFullname);
        view.getContentPane().add(labFullname);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnUpdatePassword);
        panelButtons.add(btnUpdateName);
        view.getContentPane().add(panelButtons);

        btnUpdatePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UpdatePasswordUI ui = new UpdatePasswordUI(user);
                ui.view.setVisible(true);
            }
        } );

        btnUpdateName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UpdateNameUI ui = new UpdateNameUI(user);
                ui.view.setVisible(true);
            }
        } );

    }
}
