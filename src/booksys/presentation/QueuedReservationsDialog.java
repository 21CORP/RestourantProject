package booksys.presentation;
import java.awt.* ;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;


import booksys.application.persistency.PersistentQueuedReservation;

public class QueuedReservationsDialog extends Dialog {

//	private class EntryLayout extends Component{
//		
//		public EntryLayout(String phonenumber, String name, String cover)
//		{
//			
//		}
//	}
	private PersistentQueuedReservation acceptee;
	private Vector<PersistentQueuedReservation> entries;
	public QueuedReservationsDialog(Dialog owner, Vector<PersistentQueuedReservation> entries) {
		super(owner, "Please ");
		this.entries = entries;
		setModal(true);
		Setup();
		
		
	}
	
	private void Setup()
	{
		this.setLayout(new GridLayout(entries.size(), 4));
		for(int i=0;i<entries.size();++i)
		{
			PersistentQueuedReservation current = entries.get(i);
			Label phone = new Label((current.getCustomer().getPhoneNumber()));
			Label name = new Label(current.getCustomer().getName());
			Label cover = new Label(Integer.toString(current.getCovers()));
			Button button = new Button("Accepted");
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					acceptee = current;
					QueuedReservationsDialog.this.setVisible(false);
				}
				
			});
			this.add(phone);
			this.add(name);
			this.add(cover);
			this.add(button);
		}
		pack();
	}
	public PersistentQueuedReservation getAcceptee()
	{
		return acceptee;
	}
	

}
