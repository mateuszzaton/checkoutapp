#### MARKET CHECK OUT APPLICAITON ###
1. run mysql server
2. mysql create database $dbname
3. mysql use $dbname
4. mysql create table item(id int auto_increment, name varchar(100), unit int, price bigint, discount_amount int, primary key(id));
5. setup mysql @ application.properties file
	e.g.
		spring.datasource.url=jdbc:mysql://localhost:3306/$dbname
		spring.datasource.username=root
		spring.datasource.password=admin

6. mvn clean install
7. java -jar target\checkoutapp-0.1.0.jar
8. localhost:8080 to load main page
9. localhost:8080/add to add 4 items to previously configured database

### REST CALLS ###
* /prices - get all items listed
* /prices?name="product name" - get details of specific product (e.g. /prices?name="a") (case insensitive);
* /order - use REST client to make an order (consumes json), front-end will need javascript to make the rest call with JSON included; Values need to be in double quotes 
\order format 
\	{
\		"name" : "units ordered",
\		"name2" : "units ordered"
\	}
\	
\example call
\(using POSTMAN, Google Chrome extension)
\POST localhost:8080/order
\Body type raw, JSON(application/json)
\{
\	"a" : "70",
\	"b" : "5",
\	"c" : "10",
\	"e" : "10"
\} 
\
\Response
\{
\    "a": "819.00 for 70 units. 10.0% discount for buying 70 or more.",
\    "b": "25.00 for 5 units.",
\    "c": "80.00 for 10 units.",
\    "e": "Could not find product in the catalog.",
\    "Total price": "924.00"
\}