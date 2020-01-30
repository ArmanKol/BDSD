package kafka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Transactions {  
    public List<Transaction> allTransactionsWithoutDuplicates = new ArrayList<Transaction>();
    
    public Transactions() {

    }
    
    /**
     * Voegt alle transacties toe aan een lijst.
     * @param t transaction object
     */
    protected void add(Transaction t) {
    	if(!this.allTransactionsWithoutDuplicates.contains(t)) {
    		this.allTransactionsWithoutDuplicates.add(t);
    	}
    }
    
    
    /**
     * Geeft alle verschillende klanten terug die dezelfde producten hebben gekocht.
     * @param consumerID wordt gebruikt om de lijst te verkleinen naar alleen de klantid die wordt opgegeven.
     * @return HashMap dat bestaat uit klantparen die dezelfde producten hebben gekocht.  
     */
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
    
    
    /**
     * Geeft alle verschillende productparen terug die op dezelfde dag tegelijk zijn gekocht.
     * @return HashMap dat bestaat uit productparen en hoevaak ze tegelijk zijn gekocht.    
     */
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
