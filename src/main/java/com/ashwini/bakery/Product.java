package com.ashwini.bakery;

import java.util.Arrays;

/**
 * The type Product. Contains the name, code and list of different types of packets available in bakery.
 */
public class Product
{
    private String name;
    private String code;

    private Packs[] packs;

    /**
     * Instantiates a new Product.
     */
    public Product() {

    }

    /**
     * Instantiates a new Product.
     *
     * @param code the code
     */
    public Product(String code) {
        this.code = code;
    }

    /**
     * Instantiates a new Product.
     * Sorts the packages in ascending order by items to help during processing.
     * Idea is to use a larger packet, if possible for the same weight so that number of packets reduce.
     *
     * @param name the name
     * @param code the code
     * @param packagePrices the package prices
     */
    public Product(String name, String code, Packs[] packagePrices)
    {
        this.name = name;
        this.code = code;
        this.packs = packagePrices;
        Arrays.sort(this.packs);
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode()
    {
        return code;
    }

    /**
     * Get packs packs [ ].
     *
     * @return the packs [ ]
     */
    public Packs[] getPacks()
    {
        return packs;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Product product = (Product) o;

        return code != null ? code.equals(product.code) : product.code == null;
    }

    @Override
    public int hashCode()
    {
        return code != null ? code.hashCode() : 0;
    }

    @Override
    public String toString()
    {
        return "Product{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", packs=" + Arrays.toString(packs) +
                '}';
    }
}
