
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

	
	public class GraphicalInterface extends JFrame{
		
		public JTextField nameProd, priceProd, quantProd;
		public JTable table1, table2, table3;
		public JTextField totalField0, totalField1, totalField2, totalField3; 
		JTabbedPane tabbedleft, tabbedright;
		
		private static final long serialVersionUID = 1L;
		public Controller control; 
		DefaultTableModel dtm;
		Border border = BorderFactory.createLineBorder(Color.black);
		String[] header = {"Nume Produs", "Pret", "Cantitate", "Total"};
		
		public GraphicalInterface(){
			
			control = new Controller(this);
			initGUI();
			control.refreshLargeTotal();
			control.refreshTotals();
			
		}
		
			
		
		private void initGUI() {
			setTitle("Stoc Produse");
			
			JPanel panel = new JPanel(new GridBagLayout());
			this.getContentPane().add(panel);
			GridBagConstraints gbc = new GridBagConstraints();
			nameProd = new JTextField(8);
			priceProd = new JTextField(8);
			quantProd = new JTextField(8);
			
			/*menu
			JMenuBar menubar = new JMenuBar();
			setJMenuBar(menubar);
			JMenuItem item;
			JMenu file = new JMenu("File");
			menubar.add(file);
			item = new JMenuItem("Open ");
			file.add(item);
			*/
		
			//top panel 
			JPanel top = new JPanel();
			top.setLayout(new FlowLayout());
			top.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			JButton elim = new JButton("Elimina Produs");
			elim.addActionListener(control);
			top.add(elim);
			JLabel label;	
			label = new JLabel("NumeProdus");
			top.add(label);
			top.add(nameProd);
			label = new JLabel("Pret");
			top.add(label);
			top.add(priceProd);
			label = new JLabel("Cantitate");
			top.add(label);
			top.add(quantProd);
			JButton add = new JButton("Adauga Produs");
			add.addActionListener(control);
			top.add(add);
			//
	
			//Middle panels 
			
			dtm = createTableModel();
			dtm.setColumnIdentifiers(header);
			table1 = new JTable(dtm);
			table2 = new JTable(dtm);
			table3 = new JTable(dtm);
			totalField0 = new JTextField(8);
			totalField1 = new JTextField(8);
			totalField2 = new JTextField(8);
			totalField3 = new JTextField(8);
				tabbedleft = new JTabbedPane();
				tabbedright = new JTabbedPane();
				JPanel tab0 = new JPanel(new GridBagLayout());
				tab0.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				JPanel tab1 = new JPanel(new GridBagLayout());
				tab1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				JPanel tab2 = new JPanel(new GridBagLayout());
				tab2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				JScrollPane scroll = new JScrollPane(table1);
				scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				gbc.gridx = 0;
				gbc.gridy = 0;	
				gbc.gridwidth = 4;
				tab0.add(scroll, gbc);	
				gbc.gridx = 2;
				gbc.gridy = 1;
				gbc.gridwidth = 1;
				gbc.anchor = GridBagConstraints.LINE_END;
				tab0.add(new JLabel("  Valoare Totala: "), gbc);
				gbc.gridx = 3;
				gbc.gridy = 1;	
				gbc.gridwidth = 1; 
				gbc.anchor = GridBagConstraints.LINE_START;
				tab0.add(totalField1, gbc);	
				tabbedright.addTab("Stoc", tab0);
				scroll = new JScrollPane(table2);
				scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.gridwidth = 2; 
				tab1.add(scroll, gbc);
				gbc.gridx = 0;
				gbc.gridy = 1;	
				gbc.gridwidth = 1; 
				tab1.add(new JLabel("  Valoare Totala: "), gbc);
				gbc.gridx =	1;
				gbc.gridy = 1;	
				tab1.add(totalField2, gbc);
				tabbedleft.addTab("F.", tab1);
				gbc.gridx = 0;
				gbc.gridy = 0;	
				gbc.gridwidth = 2; 
				scroll = new JScrollPane(table3);
				scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				tab2.add(scroll, gbc);
				gbc.gridx = 0;
				gbc.gridy = 1;	
				gbc.gridwidth = 1;
				tab2.add(new JLabel("  Valoare Totala: "), gbc);
				gbc.gridx = 1;
				gbc.gridy = 1;	
				gbc.gridwidth = 1;
				tab2.add(totalField3, gbc);
				tabbedleft.addTab("F.F.", tab2);
				//
			
				//center panel
				JPanel center = new JPanel();
				center.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				center.setLayout(new FlowLayout());
				center.setPreferredSize(new Dimension(70, 200));
				JButton F = new JButton("F.");
				F.setPreferredSize(new Dimension(60, 60));
				F.addActionListener(control);
				JButton FF = new JButton("F.F.");
				FF.setPreferredSize(new Dimension(60, 60));
				FF.addActionListener(control);
				JButton restore = new JButton("<=");
				restore.setPreferredSize(new Dimension(60, 60));
				restore.addActionListener(control);
				center.add(F);
				center.add(FF);
				center.add(restore);
				//
			
			//Bottom panel
			JPanel bottom = new JPanel();
			bottom.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			bottom.setLayout(new FlowLayout());
			JButton btn = new JButton("Lista de stoc");
			btn.addActionListener(control);
			bottom.add(btn, gbc);
			btn = new JButton("Salveaza stoc");
			btn.addActionListener(control);
			bottom.add(btn, gbc);
			btn = new JButton("Deschide vanzari");
			btn.addActionListener(control);
			bottom.add(btn, gbc);
			btn = new JButton("Salveaza vanzari");
			btn.addActionListener(control);
			bottom.add(btn, gbc);
			btn = new JButton("Exporta vanzari");
			btn.addActionListener(control);
			bottom.add(btn, gbc);
			//
			
			gbc.gridx = 0;
			gbc.gridy = 0;	
			gbc.gridwidth = 3;
			gbc.fill =  GridBagConstraints.HORIZONTAL;
			panel.add(top, gbc);	
			
			gbc.gridx = 0;
			gbc.gridy = 1;	
			gbc.gridwidth = 1;
			gbc.gridheight = 2;
			panel.add(tabbedright, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 1;
			gbc.gridwidth = 1;
			gbc.gridheight = 2;
			panel.add(center, gbc);
			
			gbc.gridx = 2;
			gbc.gridy = 1;	
			gbc.gridwidth = 1;
			gbc.gridheight = 2;
			panel.add(tabbedleft, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = GridBagConstraints.RELATIVE;
			gbc.gridwidth = 3;
			gbc.fill =  GridBagConstraints.HORIZONTAL;
			panel.add(bottom, gbc);
			
		
			
			this.pack();
			this.setVisible(true);
			this.setResizable(false);
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			
		}

		private DefaultTableModel createTableModel() {
			DefaultTableModel model = new DefaultTableModel() {

				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			return model;

		}
		
		
		public String getAddName(){
			return nameProd.getText();
		}
		
		public String getAddPrice(){
			return priceProd.getText();
		}

		public String getAddQuant(){
			return quantProd.getText();
		}
		
		public void refreshTableOne(Object[][] data){
			dtm = new DefaultTableModel(data, header);
			table1.setModel(dtm);
			
		}
		
		public void refreshTableF(Object[][] data){
			dtm = new DefaultTableModel(data, header);
			table2.setModel(dtm);
			
		}
		
		public void refreshTableFF(Object[][] data){
	
			dtm = new DefaultTableModel(data, header);
			table3.setModel(dtm);
			
		}

		public Product getProduct(int row, int table){
			String name, price, quant;
			Product pr = null;
		
			if(table == 1){
				name = (String) table1.getModel().getValueAt(row, 0);
				price = (String) table1.getModel().getValueAt(row, 1);
				quant = (String) table1.getModel().getValueAt(row, 2);
				pr = new Product(name, Integer.parseInt(price), Integer.parseInt(quant));
			}
			if(table == 2){
				name = (String) table2.getModel().getValueAt(row, 0);
				price = (String) table2.getModel().getValueAt(row, 1);
				quant = (String) table2.getModel().getValueAt(row, 2);
				pr = new Product(name, Integer.parseInt(price), Integer.parseInt(quant));
			}
			if(table == 3){
				name = (String) table3.getModel().getValueAt(row, 0);
				price = (String) table3.getModel().getValueAt(row, 1);
				quant = (String) table3.getModel().getValueAt(row, 2);
				pr = new Product(name, Integer.parseInt(price), Integer.parseInt(quant));
			}
			return pr;
		}
		
	}
	
