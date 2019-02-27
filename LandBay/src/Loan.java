import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.opencsv.CSVReader;

public class Loan {
	public int loanId = 0;
	public int loanAmount = 0;
	public String product = "";
	public int term = 0;
	public String completedDate;
	public ArrayList<String> investors = new ArrayList<String>();
	public String status = "unfunded";
	public ArrayList<Integer> investmentAmounts = new ArrayList<Integer>();

	public Loan(String[] loanLine) throws IOException {
	      String[] nextLine = loanLine;
	         if (nextLine != null) {
	            loanId = Integer.parseInt(nextLine[0]);
	            loanAmount = Integer.parseInt(nextLine[1]);
	            product = nextLine[2];
	            term = Integer.parseInt(nextLine[3]);
	            completedDate = nextLine[4];
	       }

	}

}
