package com.ashwini.bakery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/**
 * The type Order line item.
 * Contains information regarding a single line in order.
 */
public class OrderLineItem
{
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderLineItem.class);

    private String itemCode;
    private int quantityOrdered;
    private Product product;
    private HashMap<Packs, Integer> packetsToSend;
    private float finalPrice;

    /**
     * Gets packets to send.
     *
     * @return the packets to send
     */
    public HashMap<Packs, Integer> getPacketsToSend()
    {
        return packetsToSend;
    }

    /**
     * Gets final price.
     *
     * @return the final price
     */
    public float getFinalPrice()
    {
        return finalPrice;
    }

    /**
     * Instantiates a new Order line item.
     *
     * @param itemCode the item code
     * @param quantityOrdered the quantity ordered
     * @param product the product
     */
    public OrderLineItem(String itemCode, int quantityOrdered, Product product)
    {
        this.itemCode = itemCode;
        this.quantityOrdered = quantityOrdered;
        this.product = product;
    }

    /**
     * Process order line item.
     * For each number between 0 and number of items requested, it tries to find the maximum sized package that can fit.
     * Uses DP to remove costly recursive operations.
     * For each number, it tries to find the maximum sized packet that can be used, which will automatically result in minimum number of packets.
     * It has been treated like a normal unbounded knapsack problem.
     * The only change is that price is not taken into account since we need to minimize the number of packets to send and not the price.
     */
    public void processOrderLineItem()
    {
        int[] dpArray = new int[quantityOrdered + 1];
        Packs[] packAtIndex = new Packs[quantityOrdered + 1];
        LOGGER.info(String.format("Will use DP to determine the minimum number of packets to use for %d items of %s", quantityOrdered, product.getName()));
        for (int i = 0; i < dpArray.length; i++) {
            Packs[] availablePackages = product.getPacks();
            for (int j = 0; j < availablePackages.length; j++) {
                if (i >= availablePackages[j].getItems()) {
                    if (dpArray[i] <= dpArray[i - availablePackages[j].getItems()] + availablePackages[j].getItems()) {
                        dpArray[i] = dpArray[i - availablePackages[j].getItems()] + availablePackages[j].getItems();
                        packAtIndex[i] = availablePackages[j];
                    }
                }
            }
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("DP result: " + Arrays.toString(dpArray));
            LOGGER.debug("Packets at weights: " + Arrays.toString(packAtIndex));
        }

        if (dpArray[quantityOrdered] != quantityOrdered) { packAtIndex = null; }
        populatePacksToSend(packAtIndex);
    }

    private void populatePacksToSend(Packs[] packAtIndex)
    {
        if (packAtIndex != null) {
            int index = quantityOrdered;
            packetsToSend = new HashMap<Packs, Integer>();
            while (index >= 0 && packAtIndex[index] != null) {
                Packs pack = packAtIndex[index];
                finalPrice += pack.getPrice();
                if (packetsToSend.containsKey(pack)) { packetsToSend.put(pack, packetsToSend.get(pack) + 1); }
                else { packetsToSend.put(pack, 1); }
                index -= packAtIndex[index].getItems();
            }
        }
    }

    /**
     * String representation of the processed order line item.
     * @return String representation of the output for that line item
     */
    public String toString()
    {
        if (packetsToSend == null) { return String.format("%d %s. Not possible to deliver this order", quantityOrdered, itemCode); }
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%d %s $%.2f", quantityOrdered, itemCode, finalPrice)).append("\n");
        for (Iterator<Packs> it = packetsToSend.keySet().iterator(); it.hasNext(); ) {
            Packs pack = it.next();
            int count = packetsToSend.get(pack);
            sb.append(String.format("%d X %d $%.2f", count, pack.getItems(), pack.getPrice())).append("\n");
        }
        return sb.toString();
    }
}
