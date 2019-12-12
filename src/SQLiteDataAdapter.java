import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDataAdapter implements IDataAdapter{

    public static final int PRODUCT_SAVED_OK = 0;
    public static final int PRODUCT_DUPLICATE_ERROR = 1;

    public static final int Customer_SAVED_OK = 0;
    public static final int Customer_DUPLICATE_ERROR = 1;

    public static final int PURCHASE_SAVED_OK = 0;
    public static final int PURCHASE_DUPLICATE_ERROR = 1;

    public static final int USER_SAVED_OK = 0;
    public static final int USER_DUPLICATE_ERROR = 1;

    Connection conn = null;

    public int connect(String dbfile) {
        try {
            // db parameters
            String url = "jdbc:sqlite:" + dbfile;
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return CONNECTION_OPEN_FAILED;
        }
        return CONNECTION_OPEN_OK;
    }

    @Override
    public int disconnect() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return CONNECTION_CLOSE_FAILED;
        }
        return CONNECTION_CLOSE_OK;
    }

    public ProductModel loadProduct(int productID) {
        ProductModel product = new ProductModel();

        try {
            String sql = "SELECT ProductId, Name, Price, Quantity FROM Products WHERE ProductId = " + productID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            product.mProductID = rs.getInt("ProductId");
            product.mName = rs.getString("Name");
            product.mPrice = rs.getDouble("Price");
            product.mQuantity = rs.getDouble("Quantity");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return product;
    }

    public CustomerModel loadCustomer(int CustomerID) {
        CustomerModel Customer = new CustomerModel();

        try {
            String sql = "SELECT CustomerId, Name, Address, Phone FROM Customer WHERE CustomerId = " + CustomerID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            Customer.mCustomerID = rs.getInt("CustomerId");
            Customer.mName = rs.getString("Name");
            Customer.mAddress = rs.getString("Address");
            Customer.mPhone = rs.getString("Phone");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Customer;
    }

    public PurchaseModel loadPurchase(int PurchaseID) {
        PurchaseModel Purchase = new PurchaseModel();
        try {
            String sql = "SELECT * FROM Purchases WHERE PurchaseID = " + PurchaseID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            Purchase.mPurchaseID = rs.getInt("PurchaseID");
            Purchase.mCustomerID = rs.getInt("CustomerID");
            Purchase.mProductID = rs.getInt("ProductID");
            Purchase.mDate = rs.getString("Date/Time");
            Purchase.mQuantity = rs.getDouble("Quantity");
            Purchase.mTotal = rs.getDouble("Total Cost");
            Purchase.mPrice = rs.getDouble("Price");
            Purchase.mCost = rs.getDouble("Cost");
            Purchase.mTax = rs.getDouble("Tax");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Purchase;
    }

    public int saveProduct(ProductModel product) {
        try {
            String sql = "INSERT OR REPLACE INTO Products(ProductId, Name, Price, Quantity) VALUES " + product;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return PRODUCT_DUPLICATE_ERROR;
        }

        return PRODUCT_SAVED_OK;
    }

    public int saveCustomer(CustomerModel Customer) {
        try {
            String sql = "INSERT OR REPLACE INTO Customer(CustomerId, Name, Address, Phone) VALUES " + Customer;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return Customer_DUPLICATE_ERROR;
        }

        return Customer_SAVED_OK;
    }

    public int savePurchase(PurchaseModel Purchase) {
        try {
            String sql = "INSERT OR REPLACE INTO Purchases(PurchaseID, CustomerID, ProductID, \"Date/Time\", Quantity, \"Total Cost\", Price, Cost, Tax) VALUES " + Purchase;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return PURCHASE_DUPLICATE_ERROR;
        }

        return PURCHASE_SAVED_OK;
    }

    @Override
    public PurchaseListModel loadPurchaseHistory(int id) {
        PurchaseListModel res = new PurchaseListModel();
        try {
            String sql;
            if (id == 0){
                sql = "SELECT * FROM Purchases";
            }
            else{
                sql = "SELECT * FROM Purchases WHERE CustomerID = " + id;
            }
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                PurchaseModel purchase = new PurchaseModel();
                purchase.mCustomerID = id;
                purchase.mPurchaseID = rs.getInt("PurchaseID");
                purchase.mProductID = rs.getInt("ProductID");
                purchase.mPrice = rs.getDouble("Price");
                purchase.mQuantity = rs.getDouble("Quantity");
                purchase.mCost = rs.getDouble("Cost");
                purchase.mTax = rs.getDouble("Tax");
                purchase.mTotal = rs.getDouble("Total Cost");
                purchase.mDate = rs.getString("Date/Time");

                res.purchases.add(purchase);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    @Override
    public ProductListModel searchProduct(String name, double minPrice, double maxPrice) {
        ProductListModel res = new ProductListModel();
        try {
            String sql;
            if (name.equals("ALL")){
                sql = "SELECT * FROM Products";
            }
            else if (name.isEmpty()){
                sql = "SELECT * FROM Products WHERE Price >= " + minPrice + " AND Price <= " + maxPrice;
            }
            else if (minPrice == 0 && maxPrice == 0){
                sql = "SELECT * FROM Products WHERE Name LIKE \'%" + name + "%\' ";
            }
            else {
                sql = "SELECT * FROM Products WHERE Name LIKE \'%" + name + "%\' "
                        + "AND Price >= " + minPrice + " AND Price <= " + maxPrice;
            }
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ProductModel product = new ProductModel();
                product.mProductID = rs.getInt("ProductID");
                product.mName = rs.getString("Name");
                product.mPrice = rs.getDouble("Price");
                product.mQuantity = rs.getDouble("Quantity");
                res.products.add(product);
            }

            System.out.println(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public UserModel loadUser(String username) {
        UserModel user = null;

        try {
            String sql = "SELECT * FROM Users WHERE Username = \"" + username + "\"";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                user = new UserModel();
                user.mUsername = username;
                user.mPassword = rs.getString("Password");
                user.mFullname = rs.getString("Fullname");
                user.mUserType = rs.getInt("Usertype");
                user.mCustomerID = rs.getInt("CustomerID");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public int saveUser(UserModel User) {
        try {
            String sql = "INSERT OR REPLACE INTO Users(Username, Password, Fullname, UserType, CustomerID) VALUES " + User;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return USER_DUPLICATE_ERROR;
        }

        return USER_SAVED_OK;
    }

    public int deleteUser(UserModel User) {
        try{
            String sql = "DELETE FROM Users WHERE Username = " + User.mUsername;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return USER_DUPLICATE_ERROR;
        }

        return USER_SAVED_OK;
    }

}

