package com.elivoa.aliprint.common.dal;

/**
 * Limit
 * 
 * @author Bo Gao elivoa[AT]gamil.com, [Dec 4, 2011]
 */
public class Limit {

	private long start;
	private int n;
	
	private Limit(){
		this.start = 0;
		this.n = 0;
	}

	private Limit(long start, int n) {
		this.start = start;
		this.n = n;
	}

	public static Limit with(long start, int n) {
		return new Limit(start, n);
	}
	
	public static Limit empty(){
		return new Limit();
	}

	public static Limit with(int n) {
		return new Limit(0, n);
	}

	// accessors
	public long getStart() {
		return start;
	}

	public int getN() {
		return n;
	}

	public boolean isValid() {
		if(this.start < 0){
			return false;
		}
		
		if(this.n > 0){
			return true;
		}
		else{
			return false;
		}
	}

}
