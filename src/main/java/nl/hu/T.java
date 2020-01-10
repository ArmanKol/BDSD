package nl.hu;

public class T {
	int customerID;
	int filiaalID;
	
	public T(int customerID, int filiaalID) {
		this.customerID = customerID;
		this.filiaalID = filiaalID;
	}
	
	public int getCustomerID() {
		return this.customerID;
	}
	
	public int getFiliaalID() {
		return this.filiaalID;
	}
	
	@Override
	public String toString() {
		return "[customerID="+this.customerID+", filiaalID="+this.filiaalID+"]";
	}
}
