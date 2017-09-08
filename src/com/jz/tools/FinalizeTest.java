package com.jz.tools;

public class FinalizeTest {
	
	boolean checkOut = false;

	public FinalizeTest(boolean checkOut) {
	
		this.checkOut = checkOut;
	}
	
	protected void finallize() {
		if(checkOut) {
			System.out.println("Error: checked out ");
//			super.finalize();
		}else {
			System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeee");
		}
	}
	
	void checkIn() {
		checkOut = false;
	}
	
	
	public static void main(String[] args) {
		FinalizeTest novel = new FinalizeTest(true); 
		novel.checkIn();
		new FinalizeTest(true);
		System.gc();
	}
	

}
