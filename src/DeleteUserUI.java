import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class DeleteUserUI {

    public JFrame view;

    public JButton btnAdd = new JButton("Delete");
    public JButton btnCancel = new JButton("Cancel");

    public JTextField txtUsername = new JTextField(20);

    public JLabel labUsername = new JLabel("Username: ");
    public JLabel labPassword = new JLabel("Password: ");
    public JLabel labFullName = new JLabel("Name: ");
    public JLabel labUserType = new JLabel("UserType: ");

    UserModel User;


    public DeleteUserUI() {
        this.view = new JFrame();

        view.setTitle("Delete User");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        String[] labels = {"Username "};

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("Username "));
        line1.add(txtUsername);
        view.getContentPane().add(line1);

        line1 = new JPanel(new FlowLayout());
        line1.add(labUsername);
        line1.add(labPassword);
        line1.add(labFullName);
        line1.add(labUserType);
        view.getContentPane().add(line1);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnAdd);
        panelButtons.add(btnCancel);
        view.getContentPane().add(panelButtons);

        txtUsername.addFocusListener(new UsernameFocusListener());

        btnAdd.addActionListener(new DeleteButtonListerner());

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null, "You click on Cancel button!!!");
            }
        });

    }

    public void run() {
        view.setVisible(true);
    }

    private class UsernameFocusListener implements FocusListener {
        @Override
        public void focusGained(FocusEvent focusEvent) {

        }

        @Override
        public void focusLost(FocusEvent focusEvent) {
            process();
        }

        private void process() {
            String s = txtUsername.getText();

            if (s.length() == 0) {
                labUsername.setText("Username: [not specified!]");
                return;
            }

            System.out.println("Username = " + s);

            try {
                User.mUsername = s;

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error: Invalid Username", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            User = StoreManager.getInstance().getDataAdapter().loadUser(User.mUsername);

            if (User == null) {
                JOptionPane.showMessageDialog(null,
                        "Error: No user with username = " + User.mUsername + " in system!", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                labPassword.setText("Password: ");
                labFullName.setText("Name: ");
                labUserType.setText("UserType: ");

                return;
            }

            labPassword.setText("Password: " + User.mPassword);
            labFullName.setText("Name: " + User.mFullname);
            labUserType.setText("UserType: " + User.mUserType);

        }

    }

    class DeleteButtonListerner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            String username = txtUsername.getText();

            if (username.length() == 0) {
                JOptionPane.showMessageDialog(null, "Username cannot be null!");
                return;
            }

            try {
                User.mUsername = username;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Username is invalid!");
                return;
            }

            switch (StoreManager.getInstance().getDataAdapter().deleteUser(User)) {
                case SQLiteDataAdapter.PURCHASE_DUPLICATE_ERROR:
                    JOptionPane.showMessageDialog(null, "User NOT deleted successfully! No such User!");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "User deleted successfully!" + User);
            }
        }
    }
}
