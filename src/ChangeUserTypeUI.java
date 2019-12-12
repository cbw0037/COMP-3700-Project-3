import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeUserTypeUI {

    public JFrame view;

    public JButton btnUpdate = new JButton("Update");
    public JButton btnLoad = new JButton("Load");

    public JTextField txtUsername = new JTextField(20);
    public JTextField txtUserType = new JTextField(20);

    public JLabel labFullName = new JLabel("User Name: ");

    UserModel User;


    public ChangeUserTypeUI() {
        this.view = new JFrame();

        view.setTitle("Update User Type");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        String[] labels = {"Username ", "Password ", "Fullname ", "Usertype "};

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("Username "));
        line1.add(txtUsername);
        line1.add(labFullName);
        view.getContentPane().add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("Usertype "));
        line2.add(txtUserType);
        view.getContentPane().add(line2);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnLoad);
        panelButtons.add(btnUpdate);
        view.getContentPane().add(panelButtons);

        btnLoad.addActionListener(new LoadButtonListerner());

        btnUpdate.addActionListener(new UpdateButtonListerner());

    }

    public void run() {
        User = new UserModel();
        view.setVisible(true);
    }

    class LoadButtonListerner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String username = txtUsername.getText();
            System.out.println(username);
            if (username.length() == 0) {
                JOptionPane.showMessageDialog(null, "PurchaseID cannot be null!");
                return;
            }

            try {
                User.mUsername = username;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Username is invalid!");
                return;
            }

            User = StoreManager.getInstance().getDataAdapter().loadUser(User.mUsername);

            if (User == null) {
                JOptionPane.showMessageDialog(null, "User NOT exists!");
            } else {
                txtUserType.setText(Integer.toString(User.mUserType));
                labFullName.setText("User Name: " + User.mFullname);
            }
        }
    }

    class UpdateButtonListerner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            String usertype = txtUserType.getText();
            try {
                User.mUserType = Integer.parseInt(usertype);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Usertype is invalid!");
                return;
            }

            switch (StoreManager.getInstance().getDataAdapter().saveUser(User)) {
                case SQLiteDataAdapter.USER_DUPLICATE_ERROR:
                    JOptionPane.showMessageDialog(null, "User NOT updated successfully! Duplicate User ID!");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "User updated successfully!" + User);
            }
        }
    }
}