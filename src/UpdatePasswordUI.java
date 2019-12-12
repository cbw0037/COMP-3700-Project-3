import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdatePasswordUI {

    public JFrame view;

    public UserModel User;

    public JButton btnUpdate = new JButton("Update");

    public JTextField txtPassword = new JTextField(20);


    public UpdatePasswordUI(UserModel user) {
        this.view = new JFrame();

        User = user;

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Update Current Password");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("New Password "));
        line1.add(txtPassword);
        view.getContentPane().add(line1);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnUpdate);
        view.getContentPane().add(panelButtons);

        btnUpdate.addActionListener(new UpdateButtonListener());
    }

    class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String username = User.mUsername;

            if (username.length() == 0) {
                JOptionPane.showMessageDialog(null, "Username cannot be null!");
                return;
            }

            String password = txtPassword.getText();
            if (password.length() == 0) {
                JOptionPane.showMessageDialog(null, "Username name cannot be empty!");
                return;
            }

            User.mPassword = password;

            switch (StoreManager.getInstance().getDataAdapter().saveUser(User)) {
                case SQLiteDataAdapter.USER_DUPLICATE_ERROR:
                    JOptionPane.showMessageDialog(null, "User NOT added successfully! Duplicate Username!");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "User updated successfully!" + User);
            }
        }
    }
}