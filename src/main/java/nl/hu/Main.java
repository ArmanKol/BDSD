package nl.hu;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import persistence.TransactieDaoImpl;

public class Main {

	public static void main(String[] args) {
		TransactieDaoImpl transactions = new TransactieDaoImpl();
		Transactions tran = new Transactions();
		List<Transaction> list = transactions.findAll();
    	int counter = 0;
    	
    	for(Transaction item :list) {
    		tran.add(item);
    		counter++;
    		System.out.println(counter);
    	}
    	
    	Map<String, Integer> map = tran.getTransactionProductBoughtSameTime();    	
    	LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
    	
    	map.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
    	
    	for(Map.Entry<String, Integer> item : reverseSortedMap.entrySet()) {
    		System.out.println(item);
    	}
	}

}
