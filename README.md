### Technical requirements
- Java 11+
- String Boot, Spring Data.
- MongoDB.

Technical task itself consists of two main parts and one optional: 
1. Collect data.\
   You need to fetch cryptocurrency data prices from CEX.IO. For this task you should pull last prices for the following pairs: BTC/USD, ETH/USD and XRP/USD. This data should be stored in database, since you will use this data in the next two parts of the task. Feel free to store any additional information to database like 'createdAt' date etc.
2. Rest Endpoints.\
   You need to create a rest controller with the following endpoints 
     -  GET ```/cryptocurrencies/minprice?name=[currency_name]``` - should return record with the lowest price of selected cryptocurrency.
     -  GET ```/cryptocurrencies/maxprice?name=[currency_name]``` - should return record with the highest price of selected cryptocurrency
[currency_name] possible values: BTC, ETH or XRP. If some other value is provided then appropriate error message should be thrown.
     -  GET ```/cryptocurrencies?name=[currency_name]&page=[page_number]&size=[page_size]``` - should return a selected page with selected number of elements and default sorting should be by price from lowest to highest. For example, if page=0&size=10, then you should return first 10 elements from database, sorted by price from lowest to highest.
```[page_number]``` and ```[page_size]``` request parameters should be optional, so if they are missing then you should set them default values ```page=0, size=10```.
3. * Generate a CSV report.\
  You need to create an endpoint that will generate a CSV report saved into file.
	- GET ```/cryptocurrencies/csv```
	Report should contain the following fields: Cryptocurrency Name, Min Price, Max Price. So there should be only three records in that report, because we have three different cryptocurrencies. Feel free to use any available library for generating csv files.
 
