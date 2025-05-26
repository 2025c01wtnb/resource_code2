// BookSearchSystem.java - メインクラス
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookSearchSystem {
    public static void main(String[] args) {
        // 書籍データの初期化
        BookRepository repository = new BookRepository();
        repository.initializeData();
        
        // 検索システム起動
        BookSearchService searchService = new BookSearchService(repository);
        UserInterface ui = new UserInterface(searchService);
        ui.start();
    }
}

// Book.java - 書籍クラス
class Book {
    private int id;
    private String title;
    private String author;
    private int publicationYear;
    
    public Book(int id, String title, String author, int publicationYear) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
    }
    
    public int getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public int getPublicationYear() {
        return publicationYear;
    }
    
    @Override
    public String toString() {
        return String.format("ID: %-3d | タイトル: %-20s | 著者: %-15s | 出版年: %d", 
                id, title, author, publicationYear);
    }
}

// BookRepository.java - 書籍データの管理クラス
class BookRepository {
    private List<Book> books = new ArrayList<>();
    
    public void initializeData() {
        // サンプル書籍データの追加
        books.add(new Book(1, "坊っちゃん", "夏目漱石", 1906));
        books.add(new Book(2, "こころ", "夏目漱石", 1914));
        books.add(new Book(3, "吾輩は猫である", "夏目漱石", 1905));
        books.add(new Book(4, "人間失格", "太宰治", 1948));
        books.add(new Book(5, "走れメロス", "太宰治", 1940));
        books.add(new Book(6, "蜘蛛の糸", "芥川龍之介", 1918));
        books.add(new Book(7, "羅生門", "芥川龍之介", 1915));
        books.add(new Book(8, "ノルウェイの森", "村上春樹", 1987));
        books.add(new Book(9, "海辺のカフカ", "村上春樹", 2002));
        books.add(new Book(10, "風の歌を聴け", "村上春樹", 1979));
        books.add(new Book(11, "雪国", "川端康成", 1937));
        books.add(new Book(12, "伊豆の踊子", "川端康成", 1926));
        books.add(new Book(13, "一九八四年", "ジョージ・オーウェル", 1949));
        books.add(new Book(14, "ホビットの冒険", "J.R.R.トールキン", 1937));
        books.add(new Book(15, "指輪物語", "J.R.R.トールキン", 1954));
    }
    
    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }
}

// BookSearchService.java - 検索ロジッククラス
class BookSearchService {
    private BookRepository repository;
    
    public BookSearchService(BookRepository repository) {
        this.repository = repository;
    }
    
    public List<Book> searchBooks(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return repository.getAllBooks();
        }
        
        keyword = keyword.toLowerCase();
        List<Book> results = new ArrayList<>();
        
        for (Book book : repository.getAllBooks()) {
            if (book.getTitle().toLowerCase().contains(keyword) || 
                book.getAuthor().toLowerCase().contains(keyword)) {
                results.add(book);
            }
        }
        
        return results;
    }
}

// UserInterface.java - ユーザーインターフェースクラス
class UserInterface {
    private BookSearchService searchService;
    private Scanner scanner;
    
    public UserInterface(BookSearchService searchService) {
        this.searchService = searchService;
        this.scanner = new Scanner(System.in);
    }
    
    public void start() {
        System.out.println("========== 書籍検索システム ==========");
        
        while (true) {
            System.out.println("\n1. 書籍を検索する");
            System.out.println("2. 全ての書籍を表示する");
            System.out.println("0. 終了");
            System.out.print("選択肢を入力してください: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    searchBooks();
                    break;
                case "2":
                    displayAllBooks();
                    break;
                case "0":
                    System.out.println("システムを終了します。");
                    scanner.close();
                    return;
                default:
                    System.out.println("無効な選択です。もう一度お試しください。");
            }
        }
    }
    
    private void searchBooks() {
        System.out.print("\nタイトルまたは著者名のキーワードを入力してください: ");
        String keyword = scanner.nextLine();
        
        List<Book> results = searchService.searchBooks(keyword);
        displayResults(results, keyword);
    }
    
    private void displayAllBooks() {
        List<Book> allBooks = searchService.searchBooks("");
        displayResults(allBooks, "");
    }
    
    private void displayResults(List<Book> books, String keyword) {
        System.out.println("\n===== 検索結果 =====");
        
        if (!keyword.isEmpty()) {
            System.out.println("キーワード: " + keyword);
        }
        
        if (books.isEmpty()) {
            System.out.println("検索条件に一致する書籍が見つかりませんでした。");
        } else {
            System.out.println("合計: " + books.size() + "件\n");
            System.out.println("------------------------------------------------------------");
            for (Book book : books) {
                System.out.println(book);
            }
            System.out.println("------------------------------------------------------------");
        }
    }
}