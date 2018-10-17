import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BookWindowDialog extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Book book;
	// Utworzenie i inicjalizacja komponentów do budowy
	// okienkowego interfejsu u¿ytkownika

	// Font dla etykiet o sta³ej szerokoœci znaków
	Font font = new Font("MonoSpaced", Font.BOLD, 12);

	// Etykiety wyœwietlane na panelu
	JLabel titleLabel = new JLabel("      Tytu³: ");
	JLabel authorLabel = new JLabel("  	  Autor: ");
	JLabel yearLabel = new JLabel("   Rok wyd.: ");
	JLabel bookTypeLabel = new JLabel("    Gatunek: ");

	// Pola tekstowe wyœwietlane na panelu
	JTextField titleField = new JTextField(10);
	JTextField authorField = new JTextField(10);
	JTextField yearField = new JTextField(10);
	JComboBox<BookType> bookTypeBox = new JComboBox<BookType>(BookType.values());

	// Przyciski wyœwietlane na panelu
	JButton OKButton = new JButton("  OK  ");
	JButton CancelButton = new JButton("Anuluj");

	/*
	 * Konstruktor klasy PersonWindowDialog. parent - referencja do okna aplikacji,
	 * z którego zosta³o wywo³ane to okno dialogowe. person - referencja do obiektu
	 * reprezentuj¹cego osobê, której dane maj¹ byæ modyfikowane. Jeœli person jest
	 * równe null to zostanie utworzony nowy obiekt klasy Person
	 */
	private BookWindowDialog(Window parent, Book book) {
		// Wywo³anie konstruktora klasy bazowej JDialog.
		// Ta instrukcja pododuje ustawienie jako rodzica nowego okna dialogowego
		// referencji do tego okna, z którego wywo³ano to okno dialogowe.
		// Drugi parametr powoduje ustawienie trybu modalnoœci nowego okna diakogowego
		// - DOCUMENT_MODAL oznacza, ¿e okno rodzica bêdzie blokowane.
		super(parent, Dialog.ModalityType.DOCUMENT_MODAL);

		// Konfiguracja parametrów tworzonego okna dialogowego
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(220, 200);
		setLocationRelativeTo(parent);

		// zapamiêtanie referencji do osoby, której dane bêd¹ modyfikowane.
		this.book = book;

		// Ustawienie tytu³u okna oraz wype³nienie zawartoœci pól tekstowych
		if (book == null) {
			setTitle("Nowa ksi¹¿ka");
		} else {
			setTitle(book.toString());
			titleField.setText(book.getTitle());
			authorField.setText(book.getAuthor());
			yearField.setText("" + book.getAnnounceYear());
			bookTypeBox.setSelectedItem(book.getBookType());
		}

		// Dodanie s³uchaczy zdarzeñ do przycisków.
		// UWAGA: s³uchaczem zdarzeñ bêdzie metoda actionPerformed
		// zaimplementowana w tej klasie i wywo³ana dla
		// bie¿¹cej instancji okna dialogowego - referencja this
		OKButton.addActionListener(this);
		CancelButton.addActionListener(this);

		// Utworzenie g³ównego panelu okna dialogowego.
		// Domyœlnym mened¿erem rozd³adu dla panelu bêdzie
		// FlowLayout, który uk³ada wszystkie komponenty jeden za drugim.
		JPanel panel = new JPanel();

		// Zmiana koloru t³a g³ównego panelu okna dialogowego
		panel.setBackground(Color.red);

		// Dodanie i rozmieszczenie na panelu wszystkich komponentów GUI.
		panel.add(titleLabel);
		panel.add(titleField);

		panel.add(authorLabel);
		panel.add(authorField);

		panel.add(yearLabel);
		panel.add(yearField);

		panel.add(bookTypeLabel);
		panel.add(bookTypeBox);

		panel.add(OKButton);
		panel.add(CancelButton);

		// Umieszczenie Panelu w oknie dialogowym.
		setContentPane(panel);

		// Pokazanie na ekranie okna dialogowego
		// UWAGA: T¹ instrukcjê nale¿y wykonaæ jako ostatni¹
		// po zainicjowaniu i rozmieszczeniu na panelu
		// wszystkich komponentów GUI.
		// Od tego momentu aplikacja wyœwietla nowe okno dialogowe
		// i bokuje g³ówne okno aplikacji, z którego wywo³ano okno dialogowe
		setVisible(true);
	}

	/*
	 * Implementacja interfejsu ActionListener.
	 * 
	 * Metoda actionPerformrd bedzie automatycznie wywo³ywana do obs³ugi wszystkich
	 * zdarzeñ od obiektów, którym jako s³uchacza zdarzeñ do³¹czono obiekt
	 * reprezentuj¹cy bie¿¹c¹ instancjê okna aplikacji (referencja this)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		// Odczytanie referencji do obiektu, który wygenerowa³ zdarzenie.
		Object source = event.getSource();

		if (source == OKButton) {
			try {
				if (book == null) { // Utworzenie nowej osoby
					book = new Book(titleField.getText(), authorField.getText());
				} else { // Aktualizacji imienia i nazwiska istniej¹cej osoby
					book.setTitle(titleField.getText());
					book.setAuthor(authorField.getText());
				}
				// Aktualizacja pozosta³ych danych osoby
				book.setAnnounceYear(yearField.getText());
				book.setBookType((BookType) bookTypeBox.getSelectedItem());

				// Zamkniêcie okna i zwolnienie wszystkich zasobów.
				dispose();
			} catch (BookException e) {
				// Tu s¹ wychwytywane wyj¹tki zg³aszane przez metody klasy Person
				// gdy nie s¹ spe³nione ograniczenia na³o¿one na dopuszczelne wartoœci
				// poszczególnych atrybutów.
				// Wyœwietlanie modalnego okna dialogowego
				// z komunikatem o b³êdzie zg³oszonym za pomoc¹ wyj¹tku PersonException.
				JOptionPane.showMessageDialog(this, e.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
			}
		}

		if (source == CancelButton) {
			// Zamkniêcie okna i zwolnienie wszystkich zasobów.
			dispose();
		}
	}

	/*
	 * Metoda tworzy pomocnicze okno dialogowe, które tworzy nowy obiekt klasy
	 * Person i umo¿liwia wprowadzenie danych dla nowo utworzonej osoby. Jako
	 * pierwszy parametr nale¿y przekazaæ referencjê do g³ównego okna aplikacji, z
	 * którego ta metoda jest wywo³ywana. G³ówne okno aplikacji zostanie zablokowane
	 * do momentu zamkniêcia okna dialogowego. Po zatwierdzeniu zmian przyciskiem OK
	 * odbywa siê walidacja poprawnoœci danych w konstruktorze i setterach klasy
	 * Person. Jeœli zostan¹ wykryte niepoprawne dane to zostanie przechwycony
	 * wyj¹tek PersonException i zostanie wyœwietlony komunikat o b³êdzie.
	 * 
	 * Po poprawnym wype³nieniu danych metoda zamyka okno dialogowe i zwraca
	 * referencjê do nowo utworzonego obiektu klasy Person.
	 */
	public static Book createNewBook(Window parent) {
		BookWindowDialog dialog = new BookWindowDialog(parent, null);
		return dialog.book;
	}

	/*
	 * Metoda tworzy pomocnicze okno dialogowe, które umo¿liwia modyfikacjê danych
	 * osoby reprezentowanej przez obiekt klasy Person, który zosta³ przekazany jako
	 * drugi parametr. Jako pierwszy parametr nale¿y przekazaæ referencjê do
	 * g³ównego okna aplikacji, z którego ta metoda jest wywo³ywana. G³ówne okno
	 * aplikacji zostanie zablokowane do momentu zamkniêcia okna dialogowego. Po
	 * zatwierdzeniu zmian przyciskiem OK odbywa siê walidacja poprawnoœci danych w
	 * konstruktorze i setterach klasy Person. Jeœli zostan¹ wykryte niepoprawne
	 * dane to zostanie przechwycony wyj¹tek PersonException i zostanie wyœwietlony
	 * komunikat o b³êdzie.
	 * 
	 * Po poprawnym wype³nieniu danych metoda aktualizuje dane w obiekcie person i
	 * zamyka okno dialogowe
	 */
	public static void changeBookData(Window parent, Book book) {
		new BookWindowDialog(parent, book);
	}

}
