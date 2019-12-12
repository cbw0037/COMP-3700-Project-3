import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SystemConfigUI {

    public JFrame view;

    public JButton btnConfig = new JButton("Change Configuration");
    public JButton btnCancel = new JButton("Cancel");

    public JTextField txtConfigType = new JTextField(20);


    public SystemConfigUI() {
        this.view = new JFrame();

        view.setTitle("System Configuration");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        String[] labels = {"Username ", "Password ", "Fullname ", "Usertype "};

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("Configuaration "));
        line1.add(txtConfigType);
        view.getContentPane().add(line1);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnConfig);
        panelButtons.add(btnCancel);
        view.getContentPane().add(panelButtons);

        btnConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null, "You click on Config button!!!");
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
