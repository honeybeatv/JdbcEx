package bank;

public class BankInfoVO {

	private int seqId;
	private int deposit;
	private int withdraw;
	private String trDate;
	private int balance;
	
	public int getSeqId() {
		return seqId;
	}
	public void setSeqId(int seqId) {
		this.seqId = seqId;
	}
	public int getDeposit() {
		return deposit;
	}
	public void setDeposit(int deposit) {
		this.deposit = deposit;
	}
	public int getWithdraw() {
		return withdraw;
	}
	public void setWithdraw(int withdraw) {
		this.withdraw = withdraw;
	}
	public String getTrDate() {
		return trDate;
	}
	public void setTrDate(String trDate) {
		this.trDate = trDate;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	public BankInfoVO(int seqId, int deposit, int withdraw, String trDate, int balance) {
		super();
		this.seqId = seqId;
		this.deposit = deposit;
		this.withdraw = withdraw;
		this.trDate = trDate;
		this.balance = balance;
	}
	
	public BankInfoVO() {};
	
	@Override
	public String toString() {
		return seqId + " | deposit " + deposit + " | withdraw " + withdraw + " | date " + trDate + " | balance " + balance;
	}
	
	
	
}
