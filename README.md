# Bakery

## Algorithm
Use DP:
dpArray[0] = 0
dpArray[i] = max(dpArray[i], dpArray[i - packs[j]] + packs[j])

where, 
i <- 0 to number of items required.

j <- 0 to different types of packets available.

packs = array of different types of packets available for the product.

dpArray = the array for maintaining number of items we can send at each index.

## Build
mvn clean package

## Run
java -jar target/bakery-1.0-SNAPSHOT.jar [input file]

## Notes

The input can be changed by changing the input file. Sample file provided at [sample](./src/main/resources/input.txt)

The bakery resource config is located at [config_json](./src/main/resources/bakery.json) and can be replaced with other valid json file.