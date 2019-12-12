import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddUserUI {

    public JFrame view;

    public JButton btnAdd = new JButton("Add");
    public JButton btnCancel = new JButton("Cancel");

    public JTextField txtUsername = new JTextField(20);
    public JTextField txtPassword = new JTextField(20);
    public JTextField txtFullname = new JTextField(20);
    public JTextField txtUserType = new JTextField(20);
    public JTextField txtCustomerID = new JTextField(20);


    public AddUserUI() {
        this.view = new JFrame();

        view.setTitle("Add User");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        String[] labels = {"Username ", "Password ", "Fullname ", "Usertype "};

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("Username "));
        line1.add(txtUsername);
        view.getContentPane().add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("Password "));
        line2.add(txtPassword);
        view.getContentPane().add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("Fullname "));
        line3.add(txtFullname);
        view.getContentPane().add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("Usertype "));
        line4.add(txtUserType);
        view.getContentPane().add(line4);

        JPanel line5 = new JPanel(new FlowLayout());
        line5.add(new JLabel("Enter 0 for Non-Customers:"));
        view.getContentPane().add(line5);

        JPanel line6 = new JPanel(new FlowLayout());
        line6.add(new JLabel("CustomerID "));
        line6.add(txtCustomerID);
        view.getContentPane().add(line6);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnAdd);
        panelButtons.add(btnCancel);
        view.getContentPane().add(panelButtons);

        btnAdd.addActionListener(new AddButtonListerner());

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

    class AddButtonListerner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            UserModel User = new UserModel();

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

            String password = txtPassword.getText();
            if (password.length() == 0) {
                JOptionPane.showMessageDialog(null, "User name cannot be empty!");
                return;
            }

            User.mPassword = password;

            String Fullname = txtFullname.getText();
            try {
                User.mFullname = Fullname;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Fullname is invalid!");
                return;
            }

            String usertype = txtUserType.getText();
            try {
                User.mUserType = Integer.parseInt(usertype);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Usertype is invalid!");
                return;
            }

            String customerID = txtCustomerID.getText();
            try {
                User.mCustomerID = Integer.parseInt(customerID);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "CustomerID is invalid!");
                return;
            }

            switch (StoreManager.getInstance().getDataAdapter().saveUser(User)) {
                case SQLiteDataAdapter.USER_DUPLICATE_ERROR:
                    JOptionPane.showMessageDialog(null, "User NOT added successfully! Duplicate User ID!");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "User added successfully!" + User);
            }
        }
    }
}
