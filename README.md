# oibsip_taskno4
I have created an ATM interface using Java and mySQL.

I have connected my project with sql database.

Here is the query for creating the database -
CREATE DATABASE ATM;

Then you have to create a table in which the data will be stored and can be fetched for username and pin number and whatever functions will be performed the balance will be updated in the table. Here is the query -

CREATE TABLE user_records (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(100), pin_number INT, total_balance BIGINT, PRIMARY KEY(id));
