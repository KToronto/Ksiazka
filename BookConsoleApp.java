import java.util.Arrays;
/*
 * Program: Aplikacja dzia�aj�ca w oknie konsoli, kt�ra umo�liwia testowanie 
 *          operacji wykonywanych na obiektach klasy Book.
 *    Plik: BookConsoleApp.java
 *          
 *   Autor: Krzysztof Jopek 241406
 *    Data: 4 pazdziernik 2018 r.
 */


public class BookConsoleApp {

	private static final String GREETING_MESSAGE =
			"Program Book - wersja konsolowa\n"+
			"Autor: Krzysztof Jopek\n" +
			"Data: 4 pa�dziernik 2018 r.\n";
	
	private static final String MENU =
			"       MENU G��WNE         \n" +
			"1 - Podaj dane ksi��ki     \n" +
			"2 - Usu� dane ksi��ki      \n" +
			"3 - Modyfikuj dane ksi��ki \n" +
			"4 - Wczytaj dane z pliku   \n" +
			"5 - Zapisz dane do pliku   \n" +
			"0 - Zako�cz program        \n";	

	private static final String CHANGE_MENU = 
			"   Co zmieni�?     \n" + 
	        "1 - Tytu�          \n" + 
			"2 - Autor          \n" + 
	        "3 - Rok wydania    \n" + 
			"4 - Gatunek        \n" +
	        "0 - Powr�t do menu g��wnego\n";
	
	private static ConsoleUserDialog UI = new ConsoleUserDialog();
	
	
	public static void main(String[] args) {
	
		BookConsoleApp application = new BookConsoleApp();
		application.runMainLoop();
	} 
	
	
	
	private Book currentBook = null;
	
	public void runMainLoop() {
		UI.printMessage(GREETING_MESSAGE);

		while (true) {
			UI.clearConsole();
			showCurrentBook();

			try {
				switch (UI.enterInt(MENU + "==>> ")) {
				case 1:
					
					currentBook = createNewBook();
					break;
				case 2:
					
					currentBook = null;
					UI.printInfoMessage("Dane aktualnej ksi��ki zosta�y usuni�te");
					break;
				case 3:
					
					if (currentBook == null) throw new BookException("�adna ksi��ka nie zosta�a utworzona.");
					changeBookData(currentBook);
					break;
				case 4: {
					
					String fileName = UI.enterString("Podaj nazw� pliku: ");
					currentBook = Book.readObject(fileName, currentBook);
					UI.printInfoMessage("Dane aktualnej ksi��ki zosta�y wczytane z pliku " + fileName);
				}
					break;
				case 5: {
					
					String file_name = UI.enterString("Podaj nazw� pliku: ");
					Book.writeObject(file_name, currentBook);
					UI.printInfoMessage("Dane aktualnej ksi��ki zosta�y zapisane do pliku " + file_name);
				}

					break;
				case 0:
					
					UI.printInfoMessage("\nProgram zako�czy� dzia�anie!");
					System.exit(0);
				} 
			} catch (BookException e) { 
				
				UI.printErrorMessage(e.getMessage());
			}
		} 
	}
	
	
	
	void showCurrentBook() {
		showBook(currentBook);
	} 

	
	static void showBook(Book book) {
		StringBuilder sb = new StringBuilder();
		
		if (book != null) {
			sb.append( "      Aktualna ksi��ka: \n");
			sb.append( "      Tytu�: " + book.getTitle() + "\n" );
			sb.append( "      Autor: " + book.getAuthor() + "\n" );
			sb.append( "      Rok wyd.: " + book.getAnnounceYear() + "\n" );
			sb.append( "      Gatunek: " + book.getBookType() + "\n");
		} else
			sb.append( "Brak danych ksi��ki" + "\n" );
		UI.printMessage( sb.toString() );
	}

	
	
	static Book createNewBook(){
		String title = UI.enterString("Podaj tytu�: ");
		String author = UI.enterString("Podaj autora: ");
		String announce_year = UI.enterString("Podaj rok wyd.: ");
		UI.printMessage("Dozwolone gatunki:" + Arrays.deepToString(BookType.values()));
		String book_type = UI.enterString("Podaj gatunek: ");
		Book book;
		try { 
			
			book = new Book(title, author);
			book.setAnnounceYear(announce_year);
			book.setBookType(book_type);
		} catch (BookException e) {    
			UI.printErrorMessage(e.getMessage());
			return null;
		}
		return book;
	}
	
	
	
	static void changeBookData(Book book)
	{
		while (true) {
			UI.clearConsole();
			showBook(book);

			try {		
				switch (UI.enterInt(CHANGE_MENU + "==>> ")) {
				case 1:
					book.setTitle(UI.enterString("Podaj tytu�: "));
					break;
				case 2:
					book.setAuthor(UI.enterString("Podaj autora: "));
					break;
				case 3:
					book.setAnnounceYear(UI.enterString("Podaj rok wyd.: "));
					break;
				case 4:
					UI.printMessage("Dozwolone gatunki:" + Arrays.deepToString(BookType.values()));
					book.setBookType(UI.enterString("Podaj gatunek: "));
					break;
				case 0: return;
				}  
			} catch (BookException e) {     
				
				UI.printErrorMessage(e.getMessage());
			}
		}
	}
	
	
}  // koniec klasy BookConsoleApp
	
	
	
	
	
	
	
	
	
	
	
	