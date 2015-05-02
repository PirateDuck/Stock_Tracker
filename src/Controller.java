import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;



public class Controller implements ActionListener {
	private GraphicalInterface view;
	private IModel model, modelF, modelFF;
	final JFileChooser fc;

	public Controller(GraphicalInterface v){ 
		view = v;
		model = new Model();
		modelF = new Model();
		modelFF = new Model();
		fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("save file","sav");
		fc.setFileFilter(filter);
		File workingDirectory = new File(System.getProperty("user.dir"));
		fc.setCurrentDirectory(workingDirectory);
	}

	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand() == "Elimina Produs"){
			
			System.out.println("pressed 'Elimina Produs'");
			int row;
			Product pr;
			row = view.table1.getSelectedRow();
			if(!model.isEmpty() && row != -1){
				pr = view.getProduct(row, 1);
				System.out.println(pr.name);
				model.removeProduct(pr);	
				refreshMainTable();
				view.totalField1.setText(Integer.toString(model.getTotalValue()));
			}
		}
		if(e.getActionCommand() == "Adauga Produs"){
			
			System.out.println("pressed 'Adauga Produs'");
			Product pr = new Product(view.getAddName(), Integer.parseInt(view.getAddPrice()), Integer.parseInt(view.getAddQuant()));
			model.addProduct(pr);	
			refreshMainTable();
		}
		if(e.getActionCommand() == "F."){
			System.out.println("pressed 'F.'");	
			addProductToTable(modelF);
			view.totalField1.setText(Integer.toString(model.getTotalValue()));
			view.totalField2.setText(Integer.toString(modelF.getTotalValue()));
			
		}
		if(e.getActionCommand() == "F.F."){
			System.out.println("pressed 'F.F.'");
			addProductToTable(modelFF);	
			view.totalField1.setText(Integer.toString(model.getTotalValue()));
			view.totalField3.setText(Integer.toString(modelFF.getTotalValue()));
		}	
		if(e.getActionCommand() == "<="){
			System.out.println("pressed '<='");
			if(view.table2.getRowSelectionAllowed()){
				returnProductToMainTable();
			}
			view.totalField1.setText(Integer.toString(model.getTotalValue()));
			view.totalField3.setText(Integer.toString(modelFF.getTotalValue()));
		}	
		if(e.getActionCommand() == "Salveaza stoc"){
			System.out.println("pressed 'Salveaza stoc'");
			String name = JOptionPane.showInputDialog(view, "Introduceti denumirea salvarii: ");
			while (!(name == null)){
				if(name.equals("")){
					name = JOptionPane.showInputDialog(view, "Introduceti denumirea salvarii: ");
				} else {
					saveModel(name, 1);
					name = null;
				}
			} 
		}
		if(e.getActionCommand() == "Deschide vanzari"){
			System.out.println("pressed 'Deschide vanzari'");
			modelF = loadModel(System.getProperty("user.dir")+"\\vanzari1.sav");
			modelFF = loadModel(System.getProperty("user.dir")+"\\vanzari2.sav");
			view.refreshTableF(generateArray(modelF.getAllProducts()));
			view.refreshTableFF(generateArray(modelFF.getAllProducts()));
		}
		
		if(e.getActionCommand() == "Salveaza vanzari"){
			System.out.println("pressed 'Salveaza vanzari'");
			saveModel("vanzari1", 2);
			saveModel("vanzari2", 3);
		}
		if(e.getActionCommand() == "Lista de stoc"){
			if (!model.isEmpty()){
				int n = JOptionPane.showConfirmDialog(view, "doriti sa salvati stocul curent?");
				if( n == JOptionPane.YES_OPTION){
					String name = JOptionPane.showInputDialog(view, "Introduceti denumirea salvarii: ");
					while (!(name == null)){
						if(name.equals("")){
							name = JOptionPane.showInputDialog(view, "Introduceti denumirea salvarii: ");
						} else {
							saveModel(name, 1);
							name = null;
						}
					} 
					
				}
			}
			File file = null;
			System.out.println("pressed 'Lista de stoc'");
			int returnVal = fc.showOpenDialog(view);
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
	            file = fc.getSelectedFile();
	            System.out.println("Opening: " + file.getName());
	        } else {
	        	System.out.println("Open command cancelled by user.");
	        }
			if(file != null){
				model = loadModel(file.getAbsolutePath());
				refreshMainTable();
			}
		}
		if(e.getActionCommand() == "Exporta vanzari"){
			FileGenerator generator = new FileGenerator();
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy, mm-HH");
			Date date = new Date();
			generator.generateFile(dateFormat.format(date), modelF.getAllProducts(), modelFF.getAllProducts());
		}
		
	}
	
	private String[][] generateArray(ArrayList<Product> prod){
		String[][] data = new String[prod.size()][];
		for(int i = 0; i<prod.size(); i++){
			Product p = prod.get(i);
			data[i] = new String[]{ p.getName(), Integer.toString(p.getPrice()), Integer.toString(p.getQuantity()), Integer.toString(p.getPrice()*p.getQuantity()) };
		}
		return data;
	}
	
	public void refreshMainTable(){
		
		view.refreshTableOne(generateArray(model.getAllProducts()));
	}
	
	private void returnProductToMainTable(){
		int tab = view.tabbedleft.getSelectedIndex();
		System.out.println(tab);
		int row;
		Product pr;
		if(tab == 0){
			row = view.table2.getSelectedRow();
			if(!modelF.isEmpty() && row != -1){
				pr = view.getProduct(row, 2);
				if(pr.quantity > 0){ 
					if (pr.quantity > 1){
					modelF.removeOneProductPiece(pr);
					view.refreshTableF(generateArray(modelF.getAllProducts()));
					view.table2.setRowSelectionInterval(row, row);	
					} else{
						modelF.removeProduct(pr);
						view.refreshTableF(generateArray(modelF.getAllProducts()));
					}
					pr.setQuantity(1);
					model.addProduct(pr); 
					view.refreshTableOne(generateArray(model.getAllProducts()));
						
				}
				view.totalField1.setText(Integer.toString(model.getTotalValue()));
				view.totalField2.setText(Integer.toString(modelF.getTotalValue()));
			} else Toolkit.getDefaultToolkit().beep();
		}
		if(tab == 1){
			row = view.table3.getSelectedRow();
			if(!modelFF.isEmpty() && row != -1){
				System.out.println("row: "+row);
				pr = view.getProduct(row, 3);
				if(pr.quantity > 0){ 
					if (pr.quantity > 1){
						modelFF.removeOneProductPiece(pr);
						view.refreshTableFF(generateArray(modelFF.getAllProducts()));
						view.table3.setRowSelectionInterval(row, row);
					} else { 
						modelFF.removeProduct(pr);
						view.refreshTableFF(generateArray(modelFF.getAllProducts()));
					}
					pr.setQuantity(1);
					model.addProduct(pr); 
					view.refreshTableOne(generateArray(model.getAllProducts()));	
				}
				view.totalField1.setText(Integer.toString(model.getTotalValue()));
				view.totalField2.setText(Integer.toString(modelF.getTotalValue()));
			} else Toolkit.getDefaultToolkit().beep();
		}
	}
	
	private void addProductToTable(IModel m){
		if(view.table1.getRowSelectionAllowed()){
			int row = view.table1.getSelectedRow();
			Product pr = view.getProduct(row, 1);
			if(pr.quantity > 0){ 
				model.removeOneProductPiece(pr);
				refreshMainTable();
				pr.setQuantity(1);
				m.addProduct(pr); 
				if(m == modelF){
					view.refreshTableF(generateArray(m.getAllProducts()));
					view.tabbedleft.setSelectedIndex(0);
				}
				if(m == modelFF){
					view.refreshTableFF(generateArray(m.getAllProducts()));	
					view.tabbedleft.setSelectedIndex(1);
				}
				view.table1.setRowSelectionInterval(row, row);
				refreshTotals();
			} else Toolkit.getDefaultToolkit().beep();
		} 
		
	}
	
	public void refreshTotals(){
		view.totalField1.setText(Integer.toString(model.getTotalValue()));
		view.totalField2.setText(Integer.toString(modelF.getTotalValue()));
		view.totalField3.setText(Integer.toString(modelFF.getTotalValue()));
	}
	
	public void refreshLargeTotal(){
		view.totalField0.setText(Integer.toString(model.getTotalValue()));
	}
	
	public void saveModel(String Name, int m){
		
		try {
			OutputStream file = new FileOutputStream(Name+".sav");
			OutputStream buffer = new BufferedOutputStream(file);	
			ObjectOutputStream stream = new ObjectOutputStream(buffer);
			if(m == 1) stream.writeObject(model);
			if(m == 2) stream.writeObject(modelF);
			if(m == 3) stream.writeObject(modelFF);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Model loadModel(String path){
		Model m = new Model();
		try{
			InputStream file = new FileInputStream(path);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInputStream stream = new ObjectInputStream(buffer);
			m = (Model) stream.readObject();
			stream.close();
		} catch (IOException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(view,
				    "Eggs are not supposed to be green.");
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}
		
		return m;
	}
	
	public boolean isStockEmpty(){
		return model.isEmpty();
	}
}
