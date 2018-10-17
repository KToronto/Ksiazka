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
			"Data:  17 pa�dziernik 2018 r.\n";

	public static void main(String[] args) {

		new BookWindowApp();
	}

	private Book currentBook;

	// Font dla etykiet o sta�ej szeroko�ci znak�w
	Font font = new Font("MonoSpaced", Font.BOLD, 12);

	// Etykiety wy�wietlane na panelu w g��wnym oknie aplikacji
	JLabel titleLabel = new JLabel(" 	      Tytu�:	");
	JLabel authorLabel = new JLabel("	      Autor:	 ");
	JLabel yearLabel = new JLabel("		   Rok wyd.:	 ");
	JLabel bookTypeLabel = new JLabel(" 	Gatunek:	    ");

	// Pola tekstowe wy�wietlane na panelu w g��wnym oknie aplikacji
	JTextField titleField = new JTextField(10);
	JTextField authorField = new JTextField(10);
	JTextField yearField = new JTextField(10);
	JTextField bookTypeField = new JTextField(10);

	// Przyciski wy�wietlane na panelu w g��wnym oknie aplikacji
	JButton newButton = new JButton("Nowa ksiazka");
	JButton editButton = new JButton("Edycja danych");
	JButton saveButton = new JButton("Zapisz do pliku");
	JButton loadButton = new JButton("Wczytaj z pliku");
	JButton deleteButton = new JButton("Skasuj ksiazke");
	JButton infoButton = new JButton("O programie");
	JButton exitButton = new JButton("Zako�cz aplikacj�");

	/*
	 * Utworzenie i konfiguracja g��wnego okna apkikacji
	 */
	public BookWindowApp() {
		// Konfiguracja parametr�w g��wnego okna aplikacji
		setTitle("BookWindowApp");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(270, 270);
		setResizable(false);
		setLocationRelativeTo(null);

		// Zmiana domy�lnego fontu dla wszystkich etykiet
		// U�yto fontu o sta�ej szeroko�ci znak�w, by wyr�wna�
		// szeroko�� wszystkich etykiet.
		titleLabel.setFont(font);
		authorLabel.setFont(font);
		yearLabel.setFont(font);
		bookTypeLabel.setFont(font);

		// Zablokowanie mo�liwo�ci edycji tekst�w we wszystkich
		// polach tekstowych. (pola nieedytowalne)
		titleField.setEditable(false);
		authorField.setEditable(false);
		yearField.setEditable(false);
		bookTypeField.setEditable(false);

		// Dodanie s�uchaczy zdarze� do wszystkich przycisk�w.
		// UWAGA: s�uchaczem zdarze� b�dzie metoda actionPerformed
		// zaimplementowana w tej klasie i wywo�ana dla
		// bie��cej instancji okna aplikacji - referencja this
		newButton.addActionListener(this);
		editButton.addActionListener(this);
		saveButton.addActionListener(this);
		loadButton.addActionListener(this);
		deleteButton.addActionListener(this);
		infoButton.addActionListener(this);
		exitButton.addActionListener(this);

		// Utworzenie g��wnego panelu okna aplikacji.
		// Domy�lnym mened�erem rozd�adu dla panelu b�dzie
		// FlowLayout, kt�ry uk�ada wszystkie komponenty jeden za drugim.
		JPanel panel = new JPanel();
		panel.setBackground(Color.gray);
		// Dodanie i rozmieszczenie na panelu wszystkich
		// komponent�w GUI.
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

		// Umieszczenie Panelu w g��wnym oknie aplikacji.
		setContentPane(panel);

		// Wype�nienie p�l tekstowych danymi aktualnej osoby.
		showCurrentPerson();

		// Pokazanie na ekranie g��wnego okna aplikacji
		// UWAGA: T� instrukcj� nale�y wykona� jako ostatni�
		// po zainicjowaniu i rozmieszczeniu na panelu
		// wszystkich komponent�w GUI.
		// Od tego momentu aplikacja uruchamia g��wn� p�tl� zdarze�
		// kt�ra dzia�a w nowym w�tku niezale�nie od pozosta�ej cz�ci programu.
		setVisible(true);
	}

	/*
	 * Metoda wype�nia wszystkie pola tekstowe danymi aktualnej osoby.
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
	 * Metoda actionPerformrd bedzie automatycznie wywo�ywana do obs�ugi wszystkich
	 * zdarze� od obiekt�w, kt�rym jako s�uchacza zdarze� do��czono obiekt
	 * reprezentuj�cy bie��c� instancj� okna aplikacji (referencja this)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		// Odczytanie referencji do obiektu, kt�ry wygenerowa� zdarzenie.
		Object eventSource = event.getSource();

		try {
			if (eventSource == newButton) {
				currentBook = BookWindowDialog.createNewBook(this);
			}
			if (eventSource == deleteButton) {
				currentBook = null;
			}
			if (eventSource == saveButton) {
				String fileName = JOptionPane.showInputDialog("Podaj nazw� pliku");
				if (fileName == null || fileName.equals(""))
					return; // Cancel lub pusta nazwa pliku.
				Book.writeObject(fileName, currentBook);
			}
			if (eventSource == loadButton) {
				String fileName = JOptionPane.showInputDialog("Podaj nazw� pliku");
				if (fileName == null || fileName.equals(""))
					return; // Cancel lub pusta nazwa pliku.
				currentBook = Book.readObject(fileName, currentBook);
			}
			if (eventSource == editButton) {
				if (currentBook == null)
					throw new BookException("�adna ksi��ka nie zosta�a utworzona.");
				BookWindowDialog.changeBookData(this, currentBook);
			}
			if (eventSource == infoButton) {
				JOptionPane.showMessageDialog(this, GREETING_MESSAGE);
			}
			if (eventSource == exitButton) {
				System.exit(0);
			}
		} catch (BookException e) {
			// Tu s� wychwytywane wyj�tki zg�aszane przez metody klasy Book
			// gdy nie s� spe�nione ograniczenia na�o�one na dopuszczelne warto�ci
			// poszczeg�lnych atrybut�w.
			// Wy�wietlanie modalnego okna dialogowego
			// z komunikatem o b��dzie zg�oszonym za pomoc� wyj�tku BookException.
			JOptionPane.showMessageDialog(this, e.getMessage(), "B��d", JOptionPane.ERROR_MESSAGE);
		}

		// Aktualizacja zawarto�ci wszystkich p�l tekstowych.
		showCurrentPerson();
	}

} // koniec klasy BookWindowApp
