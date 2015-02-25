package main;

import java.util.ArrayList;
import java.util.List;

public class main {
	public List<String> check(List<String>nums,List<String> phone){
		List<String> temp = new ArrayList<String>();
		for( int i=0 ; i < phone.size() ; i++ ){
			for(int j=0;j<nums.size();j++){
				if( phone.get(i).startsWith(nums.get(j)) == true ){
					temp.add(phone.get(i));
					break;
				}
			}
		}
		return temp;
	}
	public static void main(String[] agrs){
		main a = new main();
		List<String> p = new ArrayList<String>();
		p.add("091");
		p.add("094");
		p.add("0123");
		p.add("0124");
		p.add("0125");
		p.add("0126");
		p.add("0127");
		p.add("0129");
		p.add("093");
		p.add("0120");
		p.add("0121");
		p.add("0122");
		// phone 
		List<String> phoneNumber = new ArrayList<String>();
		phoneNumber.add("01212199614"); // di dong 1
		phoneNumber.add("01265040909");// di dong 2
		phoneNumber.add("0723892052"); // ban 1
		phoneNumber.add("081251414");// ban 2
		phoneNumber.add("0916521979");// di dong 3
		phoneNumber.add("0946521973");// di dong 4
		phoneNumber.add("0936521978");// di dong 5
		a.check(p,phoneNumber);
		
	}
	
}
