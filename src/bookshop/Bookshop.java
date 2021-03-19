package bookshop;

import java.util.List;
import java.util.Scanner;

public class Bookshop {

	public static void main(String[] args) {
	//	printAuthors();
		insertAuthor("유수진", "부자언니 부자노트");
		//updateAuthor(29L, "유수진", "부자언니 부자노트");
		//deleteAuthor(4L);
		//findAuthorById(3L);
		//findAuthorById(4L);
		//findAuthorById(5L);
		
		Scanner sc = new Scanner(System.in);
		System.out.print("검색어를 입력하세요: (");
		String s = sc.next();
		System.out.println(s + ") 로 검색합니다 \n 검색 결과는... ");
		printAuthors(s);
		
	}
	
	private static void findAuthorById(Long id) {
		AuthorDAO dao = new AuthorDAOImpl();
		AuthorVO authorVO = dao.get(id);
		
		if (authorVO == null) {
			System.out.println("작가를 찾지 못했습니다.");
		} else {
			System.out.printf("작가 %s를 찾았습니다.%n", authorVO);
		}
	}
	
	private static void deleteAuthor(Long id) {
		AuthorDAO dao = new AuthorDAOImpl();
		boolean success = dao.delete(id);
		
		if (success) {
			System.out.printf("작가 %d를 삭제하였습니다.%n", id);
		} else {
			System.out.printf("작가 %d를 삭제하지 못했습니다.%n", id);
		}
		printAuthors();
	}
	
	private static void updateAuthor(Long id, String name, String desc) {
		AuthorDAO dao = new AuthorDAOImpl();
		AuthorVO authorVO = new AuthorVO(id, name, desc);
		
		boolean success = dao.update(authorVO);
		
		if (success) {
			System.out.printf("저자 %s를 갱신하였습니다.%n", authorVO);
		} else {
			System.out.printf("저자 %s를 갱신하지 못했습니다.%n", authorVO);
		}
		printAuthors();
	}
	
	private static void insertAuthor(String name, String desc) {
		AuthorDAO dao = new AuthorDAOImpl();
		AuthorVO authorVO = new AuthorVO(null, 
				name, desc);
		
		boolean success = dao.insert(authorVO);
		
		if (success) {
			System.out.printf("저자 %s를 추가했습니다.%n", 
					authorVO);
		} else {
			System.out.printf("저자 %s를 추가하지 못했습니다.%n", 
					authorVO);
		}
		
		printAuthors();
	}
	
	private static void printAuthors() {
    AuthorDAO dao = new AuthorDAOImpl();
    List<AuthorVO> list = dao.getList();
    
    System.out.println("===== 저자 목록(start) =====");
    for (AuthorVO authorVO: list) {
      System.out.println(authorVO);
    }
    System.out.println("===== 저자 목록(end) =====");
  }
	
	private static void printAuthors(String s) {
    AuthorDAO dao = new AuthorDAOImpl();
    List<BookInfoVO> list = dao.getList(s);
    
    System.out.println("===== 저자 목록(start) =====");
    for (BookInfoVO bookInfoVO: list) {
      System.out.println(bookInfoVO);
    }
    System.out.println("===== 저자 목록(end) =====");
  }

}
