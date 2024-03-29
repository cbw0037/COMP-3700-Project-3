public class OracleDataAdapter implements IDataAdapter {
    public int connect(String dbfile) {
        //...
        return CONNECTION_OPEN_OK;
    }

    public int disconnect() {
        // ...
        return CONNECTION_CLOSE_OK;

    }

    public ProductModel loadProduct(int id) {
        return null;
    }
    public int saveProduct(ProductModel model) {
        return PRODUCT_SAVED_OK;
    }

    public CustomerModel loadCustomer(int id) {
        return null;
    }
    public int saveCustomer(CustomerModel model) {
        return CUSTOMER_SAVED_OK;
    }

    public PurchaseModel loadPurchase(int id, int id1, int id2) {
        return null;
    }
    public int savePurchase(PurchaseModel model) {
        return PURCHASE_SAVED_OK;
    }

    @Override
    public PurchaseModel loadPurchase(int id){
        PurchaseModel purchase = new PurchaseModel();
        return purchase;
    }

    @Override
    public PurchaseListModel loadPurchaseHistory(int customerID) {
        return null;
    }

    @Override
    public ProductListModel searchProduct(String name, double minPrice, double maxPrice) {
        return null;
    }

    @Override
    public UserModel loadUser(String username) {
        return null;
    }

    @Override
    public int deleteUser(UserModel User) {
        return USER_SAVED_OK;
    }

    @Override
    public int saveUser(UserModel User) {
        return USER_SAVED_OK;
    }
}
