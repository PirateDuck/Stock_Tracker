import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FileGenerator {

	PrintWriter writer;
	String[][] data;
	
	ArrayList<Product> prod1 = new ArrayList<Product>();
	int subtotal1 = 0;
	int subtotal2 = 0;
	ArrayList<Product> prod2 = new ArrayList<Product>();
	
	FileGenerator(){
		
	}
	
	public void generateFile(String n, ArrayList<Product> p1, ArrayList<Product> p2){
		prod1 = p1;
		prod2 = p2;
		String name = n+".txt";
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();

		try {
			writer = new PrintWriter(name, "UTF-8");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.println("Vanzarile pe data de " + dateFormat.format(date));
		writer.println("");
		writer.println("(F.)");
		while(prod1.size() > 0){
			writer.println(createSaleDetailLine1());
		}
		writer.println("==========================================");
		writer.println("Subtotal F. = " + Integer.toString(subtotal1));
		writer.println("");
		writer.println("(F.F.)");
		while(prod2.size() > 0){
			writer.println(createSaleDetailLine2());
		}
		writer.println("==========================================");
		writer.println("Subtotal F.F. = " + Integer.toString(subtotal2));
		writer.println("");
		writer.println("==========================================");
		writer.println("Total = " + (Integer.toString(subtotal1+subtotal2)));
		writer.println("==========================================");
		writer.close();
	}
	
	private String createSaleDetailLine1(){
		String line;
		Product p = prod1.remove(0);
		subtotal1 += p.value;
		String name = p.name;
		String no = Integer.toString(p.quantity);
		String val = Integer.toString(p.value);
		String trace = "-";
		int traceLength = 39 - (name.length() + no.length() + val.length());
		for (int i = 0; i < traceLength; i++){
			trace = trace+"-";
		}
		line = no+"X "+name+trace+val;
		return line;
	}
	
	private String createSaleDetailLine2(){
		String line;
		Product p = prod2.remove(0);
		subtotal2 += p.value;
		String name = p.name;
		String no = Integer.toString(p.quantity);
		String val = Integer.toString(p.value);
		String trace = "-";
		int traceLength = 39 - (name.length() + no.length() + val.length());
		for (int i = 0; i < traceLength; i++){
			trace = trace+"-";
		}
		line = no+"X "+name+trace+val;
		return line;
	}
}
