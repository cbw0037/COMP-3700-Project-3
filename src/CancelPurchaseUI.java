import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CancelPurchaseUI {

    public JFrame view;

    public JButton btnDelete = new JButton("Delete Purchase");
    public JButton btnCancel = new JButton("Cancel");

    public JTextField txtPurchaseID = new JTextField(20);


    public CancelPurchaseUI(UserModel User) {
        this.view = new JFrame();

        view.setTitle("Cancel Purchase");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        String[] labels = {"Username ", "Password ", "Fullname ", "Usertype "};

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("PurchaseID "));
        line1.add(txtPurchaseID);
        view.getContentPane().add(line1);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnDelete);
        panelButtons.add(btnCancel);
        view.getContentPane().add(panelButtons);

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null, "You click on Delete button!!!");
            }
        });

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
}
