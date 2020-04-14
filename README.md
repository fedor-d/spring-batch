# Spring Batch Order Parser

**Spring Batch Order Parser** shows how  Spring Batch can efficiently operate batch jobs.
The module solves the simplest task:

## Input Data
*CSV or/and JSON file(-s)*

CSV line example - *1,100,USD,order payment*.
The columns are *orderId, amount, currency, comment*.

## Output Data
Console output *{"id":1,"amount":100,"comment":"order payment","filename":"orders.csv","line":1,"result":"OK"}* .

## Instruction
1) Clone the project
2) Go to the project folder, and run:
    -*mvn clean install*  
    -_cp src/main/resources/test_data/* target_
3) Run the app:
*java -jar orders-parser-0.0.1-SNAPSHOT.jar valid_data.csv valid_data.json invalid_data.csv invalid_data.json*
in the target directory.

## Remarks
Unit and Integration Tests not implemented.