public class TXTReceiptBuilder implements IReceiptBuilder {

    StringBuilder sb = new StringBuilder();

    @Override
    public void appendHeader(String header) {
        sb.append(header).append("\n");
    }

    @Override
    public void appendCustomer(CustomerModel customer) {
        sb.append("Customer ").append(customer.mCustomerID).append(": ").append(customer.mName).append("\n");
    }

    @Override
    public void appendProduct(ProductModel product) {
        sb.append("Product ").append(product.mProductID).append(": ").append(product.mName).append("\n");
        sb.append("Price: ").append(product.mPrice).append("\n");
    }

    @Override
    public void appendPurchase(PurchaseModel purchase) {
        sb.append("Quantity :").append(purchase.mQuantity).append("\n");
        sb.append("Cost: ").append(purchase.mCost).append("\n");
        sb.append("Tax: ").append(purchase.mTax).append("\n");
        sb.append("Total Cost: ").append(purchase.mTotal).append("\n");
    }

    @Override
    public void appendFooter(String footer) {
        sb.append(footer).append("\n");
    }

    public String toString() {
        return sb.toString();
    }
}
