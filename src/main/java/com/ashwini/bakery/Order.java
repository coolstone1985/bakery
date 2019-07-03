package com.ashwini.bakery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Order. Contains one or more order line items.
 * Takes the input provided and processes all line items to generate appropriate output.
 */
public class Order
{
    private final Logger LOGGER = LoggerFactory.getLogger(Order.class);

    private List<OrderLineItem> orderLineItems = new ArrayList<OrderLineItem>();

    /**
     * Gets order line items.
     * Used mainly for unit-tests
     *
     * @return the order line items
     */
    public List<OrderLineItem> getOrderLineItems()
    {
        return orderLineItems;
    }

    /**
     * Instantiates a new Order. Creates the list of order line items.
     *
     * @param inputLines the input lines provided by user
     * @throws UnknownProductException the unknown product exception
     * in case the input contains a product code not available in bakery
     */
    Order(List<String> inputLines)
            throws UnknownProductException
    {
        if (LOGGER.isDebugEnabled()) { LOGGER.debug("Initializing Order from input: " + inputLines); }
        for (String line : inputLines) {
            String[] productRequirements = line.split(" ");
            int numItems = Integer.parseInt(productRequirements[0].trim());
            String productCode = productRequirements[1].trim();
            boolean exists = false;
            for (Product p : Bakery.bakeryProducts) {
                if (p.equals(new Product(productCode))) {
                    exists = true;
                    orderLineItems.add(new OrderLineItem(productCode, numItems, p));
                    break;
                }
            }
            if (!exists) {
                throw new UnknownProductException(productCode);
            }
        }
        if (LOGGER.isDebugEnabled()) { LOGGER.debug("Order line items : " + orderLineItems); }
    }

    /**
     * Process order order. Processes each line item to determine output.
     *
     * @return the current order instance.
     */
    Order processOrder()
    {
        for (OrderLineItem orderLineItem : orderLineItems) {
            orderLineItem.processOrderLineItem();
        }
        return this;
    }

    /**
     * To String. Generates output from the line items in required format.
     *
     * @return the required output string
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (OrderLineItem oli : orderLineItems) { sb.append(oli); }
        return sb.toString();
    }
}
