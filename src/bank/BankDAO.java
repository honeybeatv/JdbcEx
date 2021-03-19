package bank;

import java.util.List;

public interface BankDAO {
	
	public List<BankInfoVO> getList();
	public boolean insert(int inMoney);
	public boolean delete(int outMoney);
	public List<BankInfoVO> getList(String date);
	public List<BankInfoVO> getList(String date1, String date2);

}
