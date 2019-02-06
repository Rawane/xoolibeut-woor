package org.xoolibeut.scann.input;

public class Maintest {

	public static void main(String[] args) {
		int test = 0x0132;
		System.out.println(test);
		int MOD_NOREPEAT = 0x0002;
		test |= 4;
		System.out.println(test);
		System.out.println(MOD_NOREPEAT);

	}

}
