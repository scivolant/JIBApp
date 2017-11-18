package gestion.data.quotation;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import gestion.compta.Placement;
import gestion.data.DataCenter;

public class QuotationUpdate extends JFrame {
	private QuotationUpdateThread t;
	private JProgressBar bar;
	private JButton cancel;
	private DataCenter dataCenter;
	private LinkedList<Placement> data;
	
	public QuotationUpdate() {
		this.setSize(300,120);
		this.setTitle("Quotation Update");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		// recherche des placements disponibles
		dataCenter = DataCenter.getInstance();
		data = dataCenter.getPlacementDAO().getAll();
		int n = data.size();
		
		t = new QuotationUpdateThread();
		bar = new JProgressBar();
		bar.setMinimum(0);
		bar.setMaximum(n);
		bar.setStringPainted(true);
		
		cancel = new JButton("Annuler");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				t.stopping();
			}
		});
		
		JLabel label = new JLabel("Veuillez patienter, mise à jour en cours...");
		
		this.getContentPane().add(label, BorderLayout.NORTH);
		this.getContentPane().add(bar ,BorderLayout.CENTER);
		this.getContentPane().add(cancel, BorderLayout.SOUTH);
		t.start();
		this.setVisible(true);
	}
	
	class QuotationUpdateThread extends Thread{
		protected volatile boolean running = true;
		
		public void run() {
			cancel.setEnabled(true);
			int val = 0;
			// Get quotation update, for all Placements.
			for (Placement place: data){
				if (running) {
					bar.setValue(val);
					dataCenter.dernierCoursMaJ(place);
					val++;
					bar.setValue(val);
				} else 
					break;
			}
			setVisible(false);
		}
		
		public void stopping() {
			running = false;
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, "Veuillez patienter, annulation en cours...", "Information", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
