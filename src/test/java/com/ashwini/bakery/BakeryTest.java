package com.ashwini.bakery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Bakery test.
 */
public class BakeryTest
{
    /**
     * Test initialize.
     * Tests for improper bakery resource file.
     *
     * @throws IOException the io exception
     */
    @Test
    public void testInitialize()
            throws IOException
    {
        Bakery b = new Bakery();
        b.setBakeryResourceFileName("wrong_bakery_props.json");
        Assertions.assertThrows(IOException.class, b::initialize);
    }

    /**
     * Test missing bakery resource file.
     *
     * @throws IOException the io exception
     */
    @Test
    public void testMissingFile() throws IOException {
        Bakery b = new Bakery();
        Assertions.assertThrows(IOException.class, b::initialize);
    }

    /**
     * Test proper initialization.
     *
     * @throws IOException the io exception
     */
    @Test
    public void testProperInitialization() throws IOException {
        Bakery b = new Bakery();
        b.setBakeryResourceFileName("right_bakery_props.json");
        b.initialize();
        List<Product> expectedProductsOffered = new ArrayList<>();
        Packs[] vs5Packs = new Packs[2];
        vs5Packs[0] = new Packs(3, 6.99f);
        vs5Packs[1] = new Packs(5, 8.99f);
        expectedProductsOffered.add(new Product("Vegemite Scroll", "VS5", vs5Packs));
        Packs[] mb11Packs = new Packs[3];
        mb11Packs[0] = new Packs(2, 9.95f);
        mb11Packs[1] = new Packs(5, 16.95f);
        mb11Packs[2] = new Packs(8, 24.95f);
        expectedProductsOffered.add(new Product("Blueberry Muffin", "MB11", mb11Packs));
        Packs[] cfPacks = new Packs[3];
        cfPacks[0] = new Packs(3, 5.95f);
        cfPacks[1] = new Packs(5, 9.95f);
        cfPacks[2] = new Packs(9, 16.99f);
        expectedProductsOffered.add(new Product("Croissant", "CF", cfPacks));
        Assertions.assertEquals(expectedProductsOffered, Bakery.bakeryProducts);
    }
}
