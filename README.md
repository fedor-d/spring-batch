# Spring Batch Order Parser
**Table of Contents**

[TOC]

## Spring Batch Order Parser
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
2) Go to the project folder, and mvn clean install
3) Run the app:
*java -jar order-parser.jar orders.csv orders.json* 
in the target directory.

## Remarks
Unit and Integration Tests not implemented.