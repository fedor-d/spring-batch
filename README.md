# Spring Batch Order Parser

**Spring Batch Order Parser** shows how  Spring Batch can efficiently operate batch jobs.
The module solves the simplest task:

## Input Data
*CSV or/and JSON file(-s)*

CSV line example - *1,100,USD,order payment*.The columns are *orderId, amount, currency, comment*.  
JSON line example - *{"orderId":1,"amount":100,"currency":"USD","comment":"order payment"}*  

## Output Data
Parsing and converting the data to be performed in parallel in multiple threads.  
Console output *{"id":1,"amount":100,"comment":"order payment","filename":"orders.csv","line":1,"result":"OK"}* .

## Instruction
1) Clone the project
2) Go to the project folder, and run:  
    *mvn clean install*  
    _cp src/main/resources/test_data/* target_ - to copy test data files *valid_data.csv, valid_data.json, invalid_data.csv, invalid_data.json*
3) Run the app:
*java -jar orders-parser-0.0.1-SNAPSHOT.jar valid_data.csv valid_data.json invalid_data.csv invalid_data.json*
in the target directory.

## Remarks
Unit and Integration Tests not implemented.
