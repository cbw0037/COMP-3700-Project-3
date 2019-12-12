import com.google.gson.Gson;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class ManagePurchaseUI {

    public JFrame view;

    public JButton btnLoad = new JButton("Load Purchase");
    public JButton btnSave = new JButton("Save Purchase");

    public JTextField txtPurchaseID = new JTextField(10);
    public JTextField txtCustomerID = new JTextField(10);
    public JTextField txtProductID = new JTextField(10);
    public JTextField txtQuantity = new JTextField(10);

    public JLabel labPrice = new JLabel("Product Price: ");
    public JLabel labDate = new JLabel("Date of Purchase: ");

    public JLabel labCustomerName = new JLabel("Customer Name: ");
    public JLabel labProductName = new JLabel("Product Name: ");

    public JLabel labCost = new JLabel("Cost: $0.00 ");
    public JLabel labTax = new JLabel("Tax: $0.00");
    public JLabel labTotalCost = new JLabel("Total Cost: $0.00");

    ProductModel product;
    PurchaseModel purchase;
    CustomerModel customer;


    public ManagePurchaseUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Update Product Information");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnLoad);
        panelButtons.add(btnSave);
        view.getContentPane().add(panelButtons);

        JPanel line = new JPanel(new FlowLayout());
        line.add(new JLabel("PurchaseID "));
        line.add(txtPurchaseID);
        line.add(labDate);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("CustomerID "));
        line.add(txtCustomerID);
        line.add(labCustomerName);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("ProductID "));
        line.add(txtProductID);
        line.add(labProductName);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("Quantity "));
        line.add(txtQuantity);
        line.add(labPrice);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(labCost);
        line.add(labTax);
        line.add(labTotalCost);
        view.getContentPane().add(line);

        txtProductID.addFocusListener(new ProductIDFocusListener());
        txtCustomerID.addFocusListener(new CustomerIDFocusListener());
        txtQuantity.getDocument().addDocumentListener(new QuantityChangeListener());

        btnLoad.addActionListener(new LoadButtonListerner());

        btnSave.addActionListener(new SaveButtonListener());
    }

    public void run() {
        purchase = new PurchaseModel();
        purchase.mDate = Calendar.getInstance().getTime().toString();
        labDate.setText("Date of purchase: " + purchase.mDate);
        view.setVisible(true);
    }

    class LoadButtonListerner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String id = txtPurchaseID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "PurchaseID cannot be null!");
                return;
            }

            try {
                purchase.mPurchaseID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "PurchaseID is invalid!");
                return;
            }

            // call data access!
            purchase = StoreManager.getInstance().getDataAdapter().loadPurchase(purchase.mPurchaseID);

            if (purchase == null) {
                JOptionPane.showMessageDialog(null, "Purchase NOT exists!");
            } else {
                product = StoreManager.getInstance().getDataAdapter().loadProduct(purchase.mProductID);
                customer = StoreManager.getInstance().getDataAdapter().loadCustomer(purchase.mCustomerID);

                txtPurchaseID.setText(Integer.toString(purchase.mPurchaseID));
                txtProductID.setText(Integer.toString(purchase.mProductID));
                txtCustomerID.setText(Integer.toString(purchase.mCustomerID));
                txtQuantity.setText(Double.toString(purchase.mQuantity));
                labDate.setText(purchase.mDate);
                labCustomerName.setText("Customer Name: " + customer.mName);
                labProductName.setText("Product Name: " + product.mName);
                labPrice.setText("Product Price: " + product.mPrice);
            }
        }
    }

    class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String id = txtPurchaseID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "PurchaseID cannot be null!");
                return;
            }

            try {
                purchase.mPurchaseID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "PurchaseID is invalid!");
                return;
            }


            switch (StoreManager.getInstance().getDataAdapter().savePurchase(purchase)) {
                case SQLiteDataAdapter.PURCHASE_DUPLICATE_ERROR:
                    JOptionPane.showMessageDialog(null, "Purchase NOT added successfully! Duplicate purchase ID!");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Purchase added successfully!" + purchase);
                    try {
                        String header = "                    Transaction " + purchase.mPurchaseID + "\n"
                                + "          " + purchase.mDate + "\n";
                        String footer = "\n                  Have a wonderful day!";
                        TXTReceiptBuilder receipt = new TXTReceiptBuilder();
                        receipt.appendHeader(header);
                        receipt.appendCustomer(customer);
                        receipt.appendProduct(product);
                        receipt.appendPurchase(purchase);
                        receipt.appendFooter(footer);
                        BufferedWriter writer = new BufferedWriter(new FileWriter("C:/Users/Carter/Documents/COMP3700/Receipt" + purchase.mPurchaseID + ".txt"));
                        writer.write(receipt.toString());
                        writer.close();
                    } catch (IOException ie) {
                        ie.printStackTrace();
                    }
            }
        }
    }

    private class ProductIDFocusListener implements FocusListener {
        @Override
        public void focusGained(FocusEvent focusEvent) {

        }

        @Override
        public void focusLost(FocusEvent focusEvent) {
            process();
        }

        private void process() {
            String s = txtProductID.getText();

            if (s.length() == 0) {
                labProductName.setText("Product Name: [not specified!]");
                return;
            }

            System.out.println("ProductID = " + s);

            try {
                purchase.mProductID = Integer.parseInt(s);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error: Invalid ProductID", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            product = StoreManager.getInstance().getDataAdapter().loadProduct(purchase.mProductID);

            if (product == null) {
                JOptionPane.showMessageDialog(null,
                        "Error: No product with id = " + purchase.mProductID + " in store!", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                labProductName.setText("Product Name: ");

                return;
            }

            labProductName.setText("Product Name: " + product.mName);
            purchase.mPrice = product.mPrice;
            labPrice.setText("Product Price: " + product.mPrice);

        }

    }

    private class CustomerIDFocusListener implements FocusListener {
        @Override
        public void focusGained(FocusEvent focusEvent) {

        }

        @Override
        public void focusLost(FocusEvent focusEvent) {
            process();
        }

        private void process() {
            String s = txtCustomerID.getText();

            if (s.length() == 0) {
                labCustomerName.setText("Customer Name: [not specified!]");
                return;
            }

            System.out.println("CustomerID = " + s);

            try {
                purchase.mCustomerID = Integer.parseInt(s);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error: Invalid CustomerID", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            customer = StoreManager.getInstance().getDataAdapter().loadCustomer(purchase.mCustomerID);

            if (customer == null) {
                JOptionPane.showMessageDialog(null,
                        "Error: No customer with id = " + purchase.mCustomerID + " in store!", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                labCustomerName.setText("Customer Name: ");

                return;
            }

            labCustomerName.setText("Customer Name: " + customer.mName);

        }

    }

    private class QuantityChangeListener implements DocumentListener {
        public void changedUpdate(DocumentEvent e) {
            process();
        }

        public void removeUpdate(DocumentEvent e) {
            process();
        }

        public void insertUpdate(DocumentEvent e) {
            process();
        }

        private void process() {
            String s = txtQuantity.getText();

            if (s.length() == 0) {
                //labCustomerName.setText("Customer Name: [not specified!]");
                return;
            }

            System.out.println("Quantity = " + s);

            try {
                purchase.mQuantity = Double.parseDouble(s);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error: Please enter an invalid quantity", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (purchase.mQuantity <= 0) {
                JOptionPane.showMessageDialog(null,
                        "Error: Please enter an invalid quantity", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (purchase.mQuantity > product.mQuantity) {
                JOptionPane.showMessageDialog(null,
                        "Not enough available products!", "Information",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            System.out.println("HI");

            purchase.mCost = purchase.mQuantity * product.mPrice;
            purchase.mTax = purchase.mCost * 0.07;
            purchase.mTotal = purchase.mCost + purchase.mTax;

            labCost.setText("Cost: $" + String.format("%8.2f", purchase.mCost).trim());
            labTax.setText("Tax: $" + String.format("%8.2f", purchase.mTax).trim());
            labTotalCost.setText("Total: $" + String.format("%8.2f", purchase.mTotal).trim());

        }
    }
}
