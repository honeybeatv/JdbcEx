package bank;

import java.util.List;
import java.util.Scanner;

public class Bank {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		BankDAO dao = new BankDAOImpl();
		
		boolean flag = true;
		
		while(flag) {
		System.out.println("업무를 선택하세요.");
		System.out.println("1.예금 2.출금 3.전체조회 4.특정일 거래조회 5.기간 거래조회 0.종료");
		int num = sc.nextInt();
		
		switch (num) {
		case 1: {
			System.out.print("입금액 : ");
			int inMoney = sc.nextInt();
			dao.insert(inMoney);
			break;
		}
		
		case 2 : {
			System.out.print("출금액 : ");
			int outMoney = sc.nextInt();
			dao.delete(outMoney);
			break;
		}
		
		case 3 : {
			List<BankInfoVO> list = dao.getList();
			for(BankInfoVO bankVo : list) {
				System.out.println(bankVo);
			}
			break;
		}
		
		case 4 : {
			System.out.print("검색일(YYYYMMDD) : ");
			String date = sc.next();
			List<BankInfoVO> list = dao.getList(date);
			for(BankInfoVO bankVo : list) {
				System.out.println(bankVo);
			}
			System.out.println();
			break;
			
		}
		
		case 5 : {
			System.out.print("시작일(YYYYMMDD) : ");
			String date1 = sc.next();
			
			System.out.print("종료일(YYYYMMDD) : ");
			String date2 = sc.next();
			
			List<BankInfoVO> list = dao.getList(date1, date2);
			
			for(BankInfoVO bankVO: list) {
				System.out.println(bankVO);
			}
			System.out.println();
			break;
		}
		case 0 : 
			flag = false;
			System.out.println("종료");
			break;
		}
	}
		sc.close();
	}

}
