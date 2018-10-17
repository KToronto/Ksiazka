import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
/* 
 *  Program: Operacje na obiektach klasy Book
 *     Plik: Book.java
 *           definicja typu wyliczeniowego BookType
 *           definicja klasy BookException
 *           definicja publicznej klasy Book
 *           
 *    Autor: Krzysztof Jopek 241406
 *     Data: 4 pazdziernik 2018 r.
 */

enum BookType {
	UNKNOWN("-------"), FANTASY("Fantastyka"), SCI_FI("Science Fiction"), CRIMINAL("Krymina³"), BIOGRAPHY("Biografia"),
	SCIENTIFIC("Naukowa");

	String bookTypeName;

	private BookType(String bookTypeName) {
		this.bookTypeName = bookTypeName;
	}

	@Override
	public String toString() {
		return bookTypeName;
	}

}

class BookException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BookException(String message) {
		super(message);
	}

}

public class Book implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String title;
	private String author;
	private int announceYear;
	private BookType bookType;

	public Book(String title, String author) throws BookException {
		setTitle(title);
		setAuthor(author);
		bookType = BookType.UNKNOWN;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) throws BookException {
		if ((title == null) || title.equals(""))
			throw new BookException("Pole <Tytu³> musi byæ wype³nione.");
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) throws BookException {
		if ((author == null) || author.equals(""))
			throw new BookException("Pole <Autor> musi byæ wype³nione.");
		this.author = author;
	}

	public int getAnnounceYear() {
		return announceYear;
	}

	public void setAnnounceYear(int announceYear) throws BookException {
		if ((announceYear != 0) && (announceYear < 1950 || announceYear > 2018))
			throw new BookException("Rok wydania ksi¹¿ki musi byæ w przedziale [1950 - 2018].");
		this.announceYear = announceYear;
	}

	public void setAnnounceYear(String announceYear) throws BookException {
		if (announceYear == null || announceYear.equals("")) {
			setAnnounceYear(0);
			return;
		}
		try {
			setAnnounceYear(Integer.parseInt(announceYear));
		} catch (NumberFormatException e) {
			throw new BookException("Rok wydania ksi¹¿ki musi byæ liczb¹ ca³kowit¹.");
		}
	}

	public BookType getBookType() {
		return bookType;
	}

	public void setBookType(BookType bookType) {
		this.bookType = bookType;
	}

	public void setBookType(String bookTypeName) throws BookException {
		if (bookTypeName == null || bookTypeName.equals("")) {
			this.bookType = BookType.UNKNOWN;
			return;
		}
		for (BookType bookType : BookType.values()) {
			if (bookType.bookTypeName.equals(bookTypeName)) {
				this.bookType = bookType;
				return;
			}
		}
		throw new BookException("Nie ma takiego gatunku.");
	}

	@Override
	public String toString() {
		return title + " " + author;
	}

	public static void writeObject(String fileName, Book book) throws BookException {
		try {
			FileOutputStream fos = new FileOutputStream(new File(fileName));
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(book);
			out.close();
		} catch (IOException e) {
			throw new BookException("Wyst¹pi³ b³¹d podczas odczytu danych z pliku");
		}
	}

	public static Book readObject( String fileName, Book book) throws BookException {
		try {
			FileInputStream fos = new FileInputStream(new File(fileName));
			ObjectInputStream in = new ObjectInputStream(fos);
			book = (Book) in.readObject();
			in.close();
		} catch (FileNotFoundException e) {
			throw new BookException("Nie odnaleziono pliku " + fileName);

		} catch (ClassNotFoundException e) {
			throw new BookException("Nie odnaleziono klasy");
		} catch (IOException e) {
			throw new BookException("Wyst¹pi³ b³¹d podczas odczytu danych z pliku");
		}

		return book;
	}
}

// koniec klasy Book
