package com.ashwini.bakery;

/**
 * The type Packs.
 * Contains information regarding the number of items and price of the package.
 */
public class Packs
        implements Comparable<Packs>{
    private Integer items;
    private float price;

    /**
     * Instantiates a new Packs.
     */
    public Packs() {

    }

    /**
     * Instantiates a new Packs.
     *
     * @param items the items
     * @param price the price
     */
    Packs(int items, float price)
    {
        this.items = items;
        this.price = price;
    }

    /**
     * Gets items.
     *
     * @return the items
     */
    public Integer getItems()
    {
        return items;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public float getPrice()
    {
        return price;
    }

    public int compareTo(Packs other)
    {
        return this.items.compareTo(other.items);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Packs packs = (Packs) o;

        if (Float.compare(packs.price, price) != 0) { return false; }
        return items.equals(packs.items);
    }

    @Override
    public int hashCode()
    {
        int result = items.hashCode();
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "Packs{" +
                "items=" + items +
                ", price=" + price +
                '}';
    }
}
