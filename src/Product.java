import java.io.Serializable;


public class Product implements Serializable{
	
	private static final long serialVersionUID = 1L;
	int price; 
	int quantity;
	int value;
	String name;
	
	public Product(String n, int p, int q){
		name = n;
		price = p;
		quantity = q;
		value = price*quantity;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
		value = price*quantity;
	}
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
		refreshValue();
	}
	
	public int getValue(){
		return value;
	}
	
	private void refreshValue(){
		value = price*quantity;
	}
	
	

	
}
