import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BookWindowApp extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static final String GREETING_MESSAGE =
			"Program Book - wersja okienkowa\n" +
			"Autor: Krzysztof Jopek\n" +
			"Data:  17 paŸdziernik 2018 r.\n";

	public static void main(String[] args) {

		new BookWindowApp();
	}

	private Book currentBook;

	// Font dla etykiet o sta³ej szerokoœci znaków
	Font font = new Font("MonoSpaced", Font.BOLD, 12);

	// Etykiety wyœwietlane na panelu w g³ównym oknie aplikacji
	JLabel titleLabel = new JLabel(" 	      Tytu³:	");
	JLabel authorLabel = new JLabel("	      Autor:	 ");
	JLabel yearLabel = new JLabel("		   Rok wyd.:	 ");
	JLabel bookTypeLabel = new JLabel(" 	Gatunek:	    ");

	// Pola tekstowe wyœwietlane na panelu w g³ównym oknie aplikacji
	JTextField titleField = new JTextField(10);
	JTextField authorField = new JTextField(10);
	JTextField yearField = new JTextField(10);
	JTextField bookTypeField = new JTextField(10);

	// Przyciski wyœwietlane na panelu w g³ównym oknie aplikacji
	JButton newButton = new JButton("Nowa ksiazka");
	JButton editButton = new JButton("Edycja danych");
	JButton saveButton = new JButton("Zapisz do pliku");
	JButton loadButton = new JButton("Wczytaj z pliku");
	JButton deleteButton = new JButton("Skasuj ksiazke");
	JButton infoButton = new JButton("O programie");
	JButton exitButton = new JButton("Zakoñcz aplikacjê");

	/*
	 * Utworzenie i konfiguracja g³ównego okna apkikacji
	 */
	public BookWindowApp() {
		// Konfiguracja parametrów g³ównego okna aplikacji
		setTitle("BookWindowApp");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(270, 270);
		setResizable(false);
		setLocationRelativeTo(null);

		// Zmiana domyœlnego fontu dla wszystkich etykiet
		// U¿yto fontu o sta³ej szerokoœci znaków, by wyrównaæ
		// szerokoœæ wszystkich etykiet.
		titleLabel.setFont(font);
		authorLabel.setFont(font);
		yearLabel.setFont(font);
		bookTypeLabel.setFont(font);

		// Zablokowanie mo¿liwoœci edycji tekstów we wszystkich
		// polach tekstowych. (pola nieedytowalne)
		titleField.setEditable(false);
		authorField.setEditable(false);
		yearField.setEditable(false);
		bookTypeField.setEditable(false);

		// Dodanie s³uchaczy zdarzeñ do wszystkich przycisków.
		// UWAGA: s³uchaczem zdarzeñ bêdzie metoda actionPerformed
		// zaimplementowana w tej klasie i wywo³ana dla
		// bie¿¹cej instancji okna aplikacji - referencja this
		newButton.addActionListener(this);
		editButton.addActionListener(this);
		saveButton.addActionListener(this);
		loadButton.addActionListener(this);
		deleteButton.addActionListener(this);
		infoButton.addActionListener(this);
		exitButton.addActionListener(this);

		// Utworzenie g³ównego panelu okna aplikacji.
		// Domyœlnym mened¿erem rozd³adu dla panelu bêdzie
		// FlowLayout, który uk³ada wszystkie komponenty jeden za drugim.
		JPanel panel = new JPanel();
		panel.setBackground(Color.gray);
		// Dodanie i rozmieszczenie na panelu wszystkich
		// komponentów GUI.
		panel.add(titleLabel);
		panel.add(titleField);

		panel.add(authorLabel);
		panel.add(authorField);

		panel.add(yearLabel);
		panel.add(yearField);

		panel.add(bookTypeLabel);
		panel.add(bookTypeField);

		panel.add(newButton);
		panel.add(deleteButton);
		panel.add(saveButton);
		panel.add(loadButton);
		panel.add(editButton);
		panel.add(infoButton);
		panel.add(exitButton);

		// Umieszczenie Panelu w g³ównym oknie aplikacji.
		setContentPane(panel);

		// Wype³nienie pól tekstowych danymi aktualnej osoby.
		showCurrentPerson();

		// Pokazanie na ekranie g³ównego okna aplikacji
		// UWAGA: T¹ instrukcjê nale¿y wykonaæ jako ostatni¹
		// po zainicjowaniu i rozmieszczeniu na panelu
		// wszystkich komponentów GUI.
		// Od tego momentu aplikacja uruchamia g³ówn¹ pêtlê zdarzeñ
		// która dzia³a w nowym w¹tku niezale¿nie od pozosta³ej czêœci programu.
		setVisible(true);
	}

	/*
	 * Metoda wype³nia wszystkie pola tekstowe danymi aktualnej osoby.
	 */
	void showCurrentPerson() {
		if (currentBook == null) {
			titleField.setText("");
			authorField.setText("");
			yearField.setText("");
			bookTypeField.setText("");
		} else {
			titleField.setText(currentBook.getTitle());
			authorField.setText(currentBook.getAuthor());
			yearField.setText("" + currentBook.getAnnounceYear());
			bookTypeField.setText("" + currentBook.getBookType());
		}
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
		Object eventSource = event.getSource();

		try {
			if (eventSource == newButton) {
				currentBook = BookWindowDialog.createNewBook(this);
			}
			if (eventSource == deleteButton) {
				currentBook = null;
			}
			if (eventSource == saveButton) {
				String fileName = JOptionPane.showInputDialog("Podaj nazwê pliku");
				if (fileName == null || fileName.equals(""))
					return; // Cancel lub pusta nazwa pliku.
				Book.writeObject(fileName, currentBook);
			}
			if (eventSource == loadButton) {
				String fileName = JOptionPane.showInputDialog("Podaj nazwê pliku");
				if (fileName == null || fileName.equals(""))
					return; // Cancel lub pusta nazwa pliku.
				currentBook = Book.readObject(fileName, currentBook);
			}
			if (eventSource == editButton) {
				if (currentBook == null)
					throw new BookException("¯adna ksi¹¿ka nie zosta³a utworzona.");
				BookWindowDialog.changeBookData(this, currentBook);
			}
			if (eventSource == infoButton) {
				JOptionPane.showMessageDialog(this, GREETING_MESSAGE);
			}
			if (eventSource == exitButton) {
				System.exit(0);
			}
		} catch (BookException e) {
			// Tu s¹ wychwytywane wyj¹tki zg³aszane przez metody klasy Book
			// gdy nie s¹ spe³nione ograniczenia na³o¿one na dopuszczelne wartoœci
			// poszczególnych atrybutów.
			// Wyœwietlanie modalnego okna dialogowego
			// z komunikatem o b³êdzie zg³oszonym za pomoc¹ wyj¹tku BookException.
			JOptionPane.showMessageDialog(this, e.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
		}

		// Aktualizacja zawartoœci wszystkich pól tekstowych.
		showCurrentPerson();
	}

} // koniec klasy BookWindowApp
