
## Technologies
- Java 17
- String Boot(Data, WEB, Test)
- MongoDB
- Lombok
- Maven
##  Rest Endpoints
- __GET__`:/cryptocurrencies/minprice?name=[currency_name]` - 
    returns record with the lowest price of selected cryptocurrency.
- __GET__`:/cryptocurrencies/maxprice?name=[currency_name]` - 
    returns record with the highest price of selected cryptocurrency.
- __GET__`:/cryptocurrencies?name=[currency_name]&page=[page_number]&size=[page_size]` -
    returns a selected page with selected number of elements and default sorting is by price from lowest to highest. 
    
    `[page_number]` and `[page_size]` request parameters are optional, so if they are missing then they will be set by default values `page = 0`, `size = 10`.
- __GET__`:/cryptocurrencies/csv` - generates a CSV report saved into file.
	Report contains the following fields: __`Cryptocurrency Name`__, __`Min Price`__, 
    __`Max Price`__.

-   `[currency_name]` possible values: __BTC__, __ETH__ or __XRP__. 

    If some other value is provided then appropriate error message would be thrown.
