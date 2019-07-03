package com.ashwini.bakery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Order test.
 */
public class OrderTest
{
    /**
     * The Bakery.
     */
    static Bakery b;

    /**
     * Sets up the bakery object.
     *
     * @throws IOException the io exception
     */
    @BeforeAll
    public static void setup()
            throws IOException
    {
        b = new Bakery();
        b.setBakeryResourceFileName("right_bakery_props.json");
        b.initialize();
    }

    /**
     * Test valid input.
     *
     * @throws UnknownProductException the unknown product exception
     */
    @Test
    public void testValidInput()
            throws UnknownProductException
    {
        List<String> input = new ArrayList<>();
        input.add("10 VS5");
        input.add("14 MB11");
        input.add("13 CF");
        Order order = new Order(input);
        Assertions.assertEquals(order.getOrderLineItems().size(), 3);
    }

    /**
     * Test invalid input format.
     */
    @Test
    public void testInvalidInputFormat() {
        List<String> input = new ArrayList<>();
        input.add("10");
        input.add("14 MB11");
        input.add("13 CF");
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> new Order(input), "1");
    }

    /**
     * Test invalid product order.
     */
    @Test
    public void testInvalidProductOrder() {
        List<String> input = new ArrayList<>();
        input.add("10 VS");
        input.add("14 MB11");
        input.add("13 CF");
        Assertions.assertThrows(UnknownProductException.class, () -> new Order(input), "Unknown product code: VS");
    }
}
