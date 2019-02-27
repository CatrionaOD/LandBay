import java.io.IOException;
import java.util.Arrays;

public class InvestmentRequest {
	public String investor = "";
	public int investment = 0;
	public String productType = "";
	public int term = 0;

	public InvestmentRequest(String[] investmentLine) throws IOException {	       
	      //Read CSV line by line and use the string array as you want
	      String[] nextLine = investmentLine;
	         if (nextLine != null) {

	            investor = nextLine[0];
	            investment = Integer.parseInt(nextLine[1]);
	            productType = nextLine[2];
	            term = Integer.parseInt(nextLine[3]);
	       }

	}

}
