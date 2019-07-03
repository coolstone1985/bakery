package com.ashwini.bakery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Order line item test.
 */
public class OrderLineItemTest
{
    /**
     * Test impossible scenario when we cannot fulfil the order using the packets we have.
     */
    @Test
    public void testImpossibleScenario() {
        Packs[] vs5Packs = new Packs[2];
        vs5Packs[0] = new Packs(4, 6.99f);
        vs5Packs[1] = new Packs(5, 8.99f);
        Product p = new Product("Vegemite Scroll", "VS5", vs5Packs);
        OrderLineItem oli = new OrderLineItem("VS5", 11, p);
        oli.processOrderLineItem();
        Assertions.assertEquals(oli.getFinalPrice(), 0);
        Assertions.assertNull(oli.getPacketsToSend());

    }

    /**
     * Test min packets supported.
     * Given two ways of fulfilling the order, the way with minimum packets should be chosen.
     */
    @Test
    public void testMinPacketsSupported() {
        Packs[] vs5Packs = new Packs[3];
        vs5Packs[0] = new Packs(3, 6.99f);
        vs5Packs[1] = new Packs(5, 8.99f);
        vs5Packs[2] = new Packs(9, 16.99f);
        Product p = new Product("Vegemite Scroll", "VS5", vs5Packs);
        OrderLineItem oli = new OrderLineItem("VS5", 12, p);
        oli.processOrderLineItem();
        Assertions.assertEquals(23.98, oli.getFinalPrice(), 0.0001);
        Map<Packs, Integer> expectedPacks = new HashMap<>();
        expectedPacks.put(vs5Packs[2], 1);
        expectedPacks.put(vs5Packs[0], 1);
        Assertions.assertEquals(expectedPacks, oli.getPacketsToSend());
    }

    /**
     * Test disregard price.
     * Minimize on space (number of packets) rather than on price.
     */
    @Test
    public void testDisRegardPrice() {
        Packs[] vs5Packs = new Packs[3];
        vs5Packs[0] = new Packs(3, 1f);
        vs5Packs[1] = new Packs(5, 2f);
        vs5Packs[2] = new Packs(9, 116f);
        Product p = new Product("Vegemite Scroll", "VS5", vs5Packs);
        OrderLineItem oli = new OrderLineItem("VS5", 12, p);
        oli.processOrderLineItem();
        Assertions.assertEquals(117, oli.getFinalPrice(), 0.0001);
        Map<Packs, Integer> expectedPacks = new HashMap<>();
        expectedPacks.put(vs5Packs[2], 1);
        expectedPacks.put(vs5Packs[0], 1);
        Assertions.assertEquals(expectedPacks, oli.getPacketsToSend());
    }
}
