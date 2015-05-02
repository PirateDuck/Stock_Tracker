import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.*;


public class Model implements IModel, Serializable {
	
	
	private static final long serialVersionUID = 1L;
	ArrayList<Product> products;
	String path;
	
	
	public Model(String filePath){
		path = filePath;
		try {
			products = openFile(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Model(){
		products = new ArrayList<Product>();
		
	}
	@Override
	public void setSourceFile(String path) {
		this.path = path;
		
	}

	@Override
	public boolean addProduct(Product prod) {
		boolean b = true;
		for(int i = 0; i<products.size(); i++){
			if(products.get(i).name.equals(prod.name) && products.get(i).price == prod.price){
				products.get(i).setQuantity(products.get(i).quantity + prod.quantity);
				i = products.size();
				b = false;
			}
		}
		if(b) {
			products.add(prod);
		} 
		return b;
		
	}

	@Override
	public void removeProductID(int id) {
		products.remove(id);
		
	}

	@Override
	public void removeProduct(Product prod) {
		for(int i = 0; i<products.size(); i++){
			if(products.get(i).name.equals(prod.name)){
				products.remove(i);
			}
		}
	}
	
	public void removeOneProductPiece(Product prod) {

		for(int i = 0; i<products.size(); i++){
			if(products.get(i).name.equals(prod.name) && products.get(i).price == prod.price){	
				products.get(i).setQuantity(products.get(i).quantity - 1);
				i = products.size();

			}
		}		
	}

	@Override
	public ArrayList<Product> getAllProducts() {
		return products;
	}



	private ArrayList<Product> openFile(String path) throws FileNotFoundException{
		
		 ArrayList<Product> products = new ArrayList<Product>();
		 File file = new File(path);
		 Scanner input = new Scanner(file);

		 while(input.hasNext()) {
		     String nextLine = input.nextLine();
		     Product prod = processLine(nextLine);	//may miss-fire;
		     products.add(prod);   
		 }

		 input.close();
		
		 return products;
		
	}
	
	private Product processLine(String line){
		Product prod;
		String name = null;
		int ints = 0;
		int price = 0;
		int quantity = 0;
		StringTokenizer tz = new StringTokenizer(line);
		while(tz.hasMoreTokens()){
			String token = tz.nextToken();
			if(isInteger(token)){
				if(ints == 0) price = Integer.parseInt(token);
				if(ints == 1) quantity = Integer.parseInt(token);
				ints ++;
			} else {
				if(name == null){
					name = token;
				} else name = name +" "+ token;
				
			}
		}
		prod = new Product(name, price, quantity);
		
		return prod;
	}
	
	private static boolean isInteger(String str) {
		if (str == null) {
			return false;
		}
		int length = str.length();
		if (length == 0) {
			return false;
		}
		int i = 0;
		if (str.charAt(0) == '-') {
			if (length == 1) {
				return false;
			}
			i = 1;
		}
		for (; i < length; i++) {
			char c = str.charAt(i);
			if (c <= '/' || c >= ':') {
				return false;
			}
		}
		return true;
	}
	
	public int getTotalValue(){
		int total = 0;
		for(int i = 0; i<products.size(); i++){
			total += products.get(i).value;
		}	
		return total;	
	}
	
	public boolean isEmpty(){
		if(products.size()<1){
			return true;
		}else return false;
	}
}
