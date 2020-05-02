package datastructure;

public class Account{
	private String name;
	private long number;
	

	public Account(String name, long number) {
		super();
		this.number = number;
		this.name = name;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (number != other.number)
			return false;
		return true;
	}

	
	
//	@Override
//	public int compareTo(Account o) {
//		if(this.number == o.number) {
//			return -1;
//		}else if(this.name.equals(o.name)) {
//			return 1;
//		}else {
//			return 0;
//		}
//	}
	

}
