package exercise.stockexchange;

public class PreferredStock extends Stock 
{
	
	private double fixedDividend;

	public PreferredStock(String symbol, int lastDividend, int parValue, double fixedDividend) 
	{
		super(symbol, lastDividend, parValue);
		
		this.fixedDividend = fixedDividend;
	}
	
	public double getDividendYield()
	{
		return (fixedDividend * parValue) / price;
	}
	
	public double getDividendYield(double inputPrice)
	{
		return (fixedDividend * parValue) / inputPrice;
	}

}
