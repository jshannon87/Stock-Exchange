package exercise.stockexchange;

public abstract class Stock 
{
	// Constants
	private final String STOCK_SYMBOL;
	
	// Instance variables
	Book book = new Book(this);
	
	protected double price;
	
	protected int    lastDividend;
	protected int    parValue;
	
	public Stock(String symbol, int lLastDividend, int lParValue)
	{
		STOCK_SYMBOL = symbol;
		lastDividend = lLastDividend;
		parValue = lParValue;
		setPrice(5.00); // default value
	}
	
	public String getSymbol()
	{
		return STOCK_SYMBOL;
	}
	
	private void setPrice(double lPrice)
	{
		price = lPrice;
	}
	
    public double getPrice()
    {
    	return price;
    }
    
	public void setAsk(AskOrder askOrder)
	{
		book.addAsk(askOrder);
	}
	
	public void setBid(BidOrder bidOrder)
	{
		book.addBid(bidOrder);
	}
	
	public void tradeExchanged(Order order)
	{
		setPrice(order.getPrice());
	}
	
	public double getPeRatio()
	{
		return price / (lastDividend / 100.00); // Divide by 100 to convert pennies to £
	}
	
	public double getPeRatio(double inputPrice)
	{
		return inputPrice / (lastDividend / 100.00); // Divide by 100 to convert pennies to £
	}
	
	public void getOfferAndBidLists()
	{
		book.getOfferAndBidLists();
	}
    
    public abstract double getDividendYield();
    
    public abstract double getDividendYield(double price);

}
