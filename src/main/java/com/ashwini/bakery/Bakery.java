package com.ashwini.bakery;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * The type Bakery.
 * Main class that runs the bakery.
 */
public class Bakery
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Bakery.class);
    private static String BAKERY_RESOURCE_FILE_NAME = "bakery.json";
    /**
     * The Bakery products that are offered by bakery.
     */
    public static List<Product> bakeryProducts;

    /**
     * Sets bakery resource file name.
     * This is a json file that contains information about products that are offered.
     *
     * @param bakeryResourceFileName the bakery resource file name
     */
    public void setBakeryResourceFileName(String bakeryResourceFileName)
    {
        BAKERY_RESOURCE_FILE_NAME = bakeryResourceFileName;
    }

    /**
     * Initialize the bakery.
     *
     * @throws IOException the io exception in case bakery resource file is not readable.
     */
    public void initialize()
            throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        bakeryProducts = mapper.readValue(Bakery.class.getClassLoader().getResource(BAKERY_RESOURCE_FILE_NAME), new TypeReference<List<Product>>() {});
        if (LOGGER.isDebugEnabled()) { LOGGER.debug("Bakery Products available: " + bakeryProducts); }
    }

    private static String usage()
    {
        return "Usage: java -jar bakery-1.0-SNAPSHOT.jar [input_file_name]";
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args)
    {
        if (args.length == 0) {
            LOGGER.error(usage());
            System.exit(-1);
        }
        Bakery b = new Bakery();
        try {
            b.initialize();
        }
        catch (IOException e) {
            LOGGER.error("Unable to initialize bakery. Please check the configurations resource file:" + BAKERY_RESOURCE_FILE_NAME, e);
            System.exit(-1);
        }
        String inputFileName = args[0];
        try {
            List<String> lines = FileUtils.readLines(new File(inputFileName), Charset.defaultCharset());
            Order order = new Order(lines).processOrder();
            LOGGER.info(order.toString());
        }
        catch (IOException e) {
            LOGGER.error("Unable to parse input file. Kindly provide a file that exists", e);
        }
        catch (UnknownProductException e) {
            LOGGER.error("Unknown product code in input file.", e);
        }
    }
}
