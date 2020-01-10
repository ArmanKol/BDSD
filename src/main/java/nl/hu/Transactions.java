package nl.hu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by roelant on 19/12/2019.
 */
public class Transactions {
    private static Logger log = LoggerFactory.getLogger("Transactions");

    private Map<Integer, Set<Integer>> transactionsByProduct = new HashMap<Integer, Set<Integer>>();
    private Map<Integer, Set<Integer>> transactionsByCustomer  = new HashMap<Integer, Set<Integer>>();

    public Transactions() {
        this.transactionsByCustomer = new HashMap<Integer, Set<Integer>>();
    }

    protected void add(Transaction t) {
        log.info("Adding transaction %s"+ t);
        if (!transactionsByProduct.containsKey(t.getProductId())) {
            transactionsByProduct.put(t.getProductId(), new HashSet<Integer>());
        }
        ((Set) transactionsByProduct.get(t.getProductId())).add(t.customerId);

        if (!transactionsByCustomer.containsKey(t.getCustomerId())) {
            transactionsByCustomer.put(t.getCustomerId(), new HashSet<Integer>());
        }
        ((Set) transactionsByCustomer.get(t.getCustomerId())).add(t.productId);
    }

    /**
     * returns all customers that have bought productId.
     * @param productId
     * @return set of customers.
     */
    private Set<Integer> customersWithProduct(int productId) {
        // log.info("Looking up customers that have bought " + productId);
        return  transactionsByProduct.get(productId);
    }


    /**
     * returns all products that customerId has bought.
     * @param customerId
     * @return Set of products.
     */
    Set<Integer> productsByCustomer(int customerId) {
        // log.info("Looking up products that customer "+ customerId +" has bought");
        return  transactionsByCustomer.get(customerId);
    }

    /**
     * returns all customers that have bought any or more products in the set productIds.
     * @param productIds
     * @return Set of
     */
    Map<Integer, Integer> customersWithProduct(Set<Integer> productIds) {
        // log.info("CustomersWithProduct "+ productIds.toString());
        Map<Integer, Integer> customersProductMap = new HashMap<Integer, Integer>();
        for (int productId : productIds) {
            Set<Integer> customerIds = customersWithProduct(productId);
            for (int customerId : customerIds) {
                if (!customersProductMap.containsKey(customerId)) {
                    customersProductMap.put(customerId, 1);
                } else {
                    customersProductMap.put(customerId, customersProductMap.get(customerId) + 1);
                }
            }
        }
        return customersProductMap;
    }

}
