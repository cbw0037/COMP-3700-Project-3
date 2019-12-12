import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ProductSearchUI {

        public JFrame view;
        public JTable productTable;
        public UserModel user = null;

    public JButton btnSearch = new JButton("Search");
    public JButton btnReset = new JButton("Reset");

    public JTextField txtName = new JTextField(20);
    public JTextField txtMin = new JTextField(20);
    public JTextField txtMax = new JTextField(20);


        public ProductSearchUI(UserModel user) {
            this.user = user;

            this.view = new JFrame();

            view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            view.setTitle("Search Product");
            view.setSize(1000, 600);
            view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

            JLabel title = new JLabel("Search results for " + user.mFullname);

            title.setFont (title.getFont().deriveFont (24.0f));
            title.setHorizontalAlignment(SwingConstants.CENTER);
            view.getContentPane().add(title);

            ProductListModel list = StoreManager.getInstance().getDataAdapter().searchProduct("ALL", 0, 10000);
            DefaultTableModel tableData = new DefaultTableModel();

            tableData.addColumn("ProductID");
            tableData.addColumn("Product Name");
            tableData.addColumn("Price");
            tableData.addColumn("Quantity");

            for (ProductModel p : list.products) {
                Object[] row = new Object[tableData.getColumnCount()];

                row[0] = p.mProductID;
                row[1] = p.mName;
                row[2] = p.mPrice;
                row[3] = p.mQuantity;
                tableData.addRow(row);
            }

//        purchases = new JList(data);

            productTable = new JTable(tableData);

            JScrollPane scrollableList = new JScrollPane(productTable);

            view.getContentPane().add(scrollableList);

            JPanel line = new JPanel(new FlowLayout());
            line.add(new JLabel("Product Name "));
            line.add(txtName);
            line.add(new JLabel("Minimum Price "));
            line.add(txtMin);
            line.add(new JLabel("Maximum Price "));
            line.add(txtMax);
            view.getContentPane().add(line);

            JPanel panelButtons = new JPanel(new FlowLayout());
            panelButtons.add(btnSearch);
            panelButtons.add(btnReset);
            view.getContentPane().add(panelButtons);

            btnSearch.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    String name = txtName.getText();
                    String min = txtMin.getText();
                    String max = txtMax.getText();
                    ProductListModel list = StoreManager.getInstance().getDataAdapter().searchProduct(name, Double.parseDouble(min), Double.parseDouble(max));

                    DefaultTableModel tableData = new DefaultTableModel();

                    tableData.addColumn("ProductID");
                    tableData.addColumn("Product Name");
                    tableData.addColumn("Price");
                    tableData.addColumn("Quantity");

                    for (ProductModel p : list.products) {
                        Object[] row = new Object[tableData.getColumnCount()];

                        row[0] = p.mProductID;
                        row[1] = p.mName;
                        row[2] = p.mPrice;
                        row[3] = p.mQuantity;
                        tableData.addRow(row);
                    }

                    productTable.setModel(tableData);
                }
            });

            btnReset.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    ProductListModel list = StoreManager.getInstance().getDataAdapter().searchProduct("ALL", 0, 10000);

                    DefaultTableModel tableData = new DefaultTableModel();

                    tableData.addColumn("ProductID");
                    tableData.addColumn("Product Name");
                    tableData.addColumn("Price");
                    tableData.addColumn("Quantity");

                    for (ProductModel p : list.products) {
                        Object[] row = new Object[tableData.getColumnCount()];

                        row[0] = p.mProductID;
                        row[1] = p.mName;
                        row[2] = p.mPrice;
                        row[3] = p.mQuantity;
                        tableData.addRow(row);
                    }

                    productTable.setModel(tableData);
                }
            });
        }
    }

