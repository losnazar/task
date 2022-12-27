### Technologies
- Java 17
- String Boot(Data, WEB, Test)
- MongoDB
- Lombok
- Maven

### Rest Endpoints.\ 
     -  GET ```/cryptocurrencies/minprice?name=[currency_name]``` - returns record with the lowest price of selected cryptocurrency.
     -  GET ```/cryptocurrencies/maxprice?name=[currency_name]``` - returns record with the highest price of selected cryptocurrency.
[currency_name] possible values: BTC, ETH or XRP. If some other value is provided then appropriate error message wouldb be thrown.
     -  GET ```/cryptocurrencies?name=[currency_name]&page=[page_number]&size=[page_size]``` - returns a selected page with selected number of elements and default sorting is by price from lowest to highest. 
```[page_number]``` and ```[page_size]``` request parameters are optional, so if they are missing then they will be set by default values ```page=0, size=10```.
     -  GET ```/cryptocurrencies/csv``` - generates a CSV report saved into file.
	Report contains the following fields: `Cryptocurrency Name`, `Min Price`, `Max Price`.
