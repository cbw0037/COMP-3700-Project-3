import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SalesSummaryUI {

    public JFrame view;
    //public JList purchases;
    public JTable salesTable;

    public JLabel labRevenue = new JLabel("");

//    public JButton btnLoad = new JButton("Load Customer");
//    public JButton btnSave = new JButton("Save Customer");
//
//    public JTextField txtCustomerID = new JTextField(20);
//    public JTextField txtName = new JTextField(20);
//    public JTextField txtPhone = new JTextField(20);
//    public JTextField txtAddress = new JTextField(20);


    public SalesSummaryUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Sales Summary");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Sales Summary");

        title.setFont (title.getFont().deriveFont (24.0f));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        view.getContentPane().add(title);

        PurchaseListModel list = StoreManager.getInstance().getDataAdapter().loadPurchaseHistory(0);
//        DefaultListModel<String> data = new DefaultListModel<>();
        DefaultTableModel tableData = new DefaultTableModel();
//        String[] columnNames = {"PurchaseID", "ProductID", "Total"};
//        data.addElement(String.format("PurchaseId: %d, ProductId: %d, Total: %8.2f",
//                purchase.mPurchaseID,
//                purchase.mProductID,
//                purchase.mTotal));

        tableData.addColumn("PurchaseID");
        tableData.addColumn("Product Name");
        tableData.addColumn("Date/Time");
        tableData.addColumn("Revenue");

        double rev = 0;

        for (PurchaseModel purchase : list.purchases) {
            Object[] row = new Object[tableData.getColumnCount()];
            row[0] = purchase.mPurchaseID;
            ProductModel product = StoreManager.getInstance().getDataAdapter().loadProduct(purchase.mProductID);
            row[1] = product.mName;
            row[2] = purchase.mDate;
            row[3] = purchase.mCost;
            tableData.addRow(row);
            rev += purchase.mCost;
        }


//        purchases = new JList(data);

        salesTable = new JTable(tableData);

        JScrollPane scrollableList = new JScrollPane(salesTable);
        labRevenue.setText("Total Revenue: $" + rev);

        view.getContentPane().add(scrollableList);
        view.getContentPane().add(labRevenue);
    }
}