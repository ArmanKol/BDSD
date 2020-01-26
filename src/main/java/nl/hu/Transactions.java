package nl.hu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Transactions {
	private static Logger log = LoggerFactory.getLogger("Transactions");    
    public List<Transaction> allTransactionsWithoutDuplicates = new ArrayList<Transaction>();
    
    public Transactions() {

    }

    protected void add(Transaction t) {
    	if(!this.allTransactionsWithoutDuplicates.contains(t)) {
    		this.allTransactionsWithoutDuplicates.add(t);
    	}
    }
    
    public Map<Integer, Integer> getCustomerWithSameProduct(int customerID){
    	List<Transaction> list = this.allTransactionsWithoutDuplicates;
    	Map<Integer, Integer> mapper = new HashMap<Integer, Integer>();
    	
    	for(Transaction a : list) {
    		for(Transaction b: list) {
    			if((a.getCustomerId() != b.getCustomerId() && a.getProductId() == b.getProductId()) && a.getCustomerId() == customerID) {
    				if(mapper.containsKey(b.getCustomerId())) {
    					mapper.replace(b.getCustomerId(), mapper.get(b.getCustomerId())+1);
    				}else {
    					mapper.put(b.getCustomerId(), 1);
    				}
    			}
    		}
    	}
    	return mapper;
    }
    
    public Map<String, Integer> getTransactionProductBoughtSameTime(){
    	List<Transaction> list = this.allTransactionsWithoutDuplicates;
    	Map<String, Integer> returnMap = new HashMap<String, Integer>();
    	
    	for(Transaction transaction : list) {
    		for(Transaction transactionB: list) {
	    		if(transaction.getDateInString().equals(transactionB.getDateInString()) && transaction.getProductId() != transactionB.getProductId()
	    				&& transaction.getCustomerId() == transactionB.getCustomerId() && transaction.getFiliaalID() == transactionB.getFiliaalID()) {
	    			String productPair = transaction.getProductId()+":"+transactionB.getProductId();
	        		String[] split = productPair.split(":");
	        		String reversedProductPair = split[1]+":"+split[0];
	    			if(returnMap.containsKey(productPair)) {
	    				returnMap.replace(productPair, (returnMap.get(productPair))+1);
	    			}else if(!returnMap.containsKey(productPair) && !returnMap.containsKey(reversedProductPair)){
	    				returnMap.put(productPair, 1);
	    			}
	    		}
    		}
    	}
    	return returnMap;
    }

}
