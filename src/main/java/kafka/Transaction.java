package kafka;

public class Transaction {
    private int customerId;
    private int productId;
    private int filiaalID;
    private String datum;
    
    public Transaction(int customerId, int productId, String datum, int filiaalID) {
    	this.customerId = customerId;
        this.productId = productId;
        this.datum = datum;
        this.filiaalID = filiaalID;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getProductId() {
        return productId;
    }
    
    public int getFiliaalID() {
		return this.filiaalID;
	}
    
    public String getDateInString() {
    	return datum;
    }
    

    @Override
    public String toString() {
        return "Transaction{" +
                "customerId=" + this.customerId +
                ", productId=" + this.productId +
                ", datum=" + this.datum +
                ", filiaalID="+ this.getFiliaalID() + "}";
    }
}
