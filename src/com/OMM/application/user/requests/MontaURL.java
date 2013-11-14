package com.OMM.application.user.requests;

public abstract class MontaURL {
	
	private static final String IP = "192.168.1.3";
	public  MontaURL(){
		
		
	}
	
	
	public static String montaURLCota(int idParlamentar){
		 
		String url = "http://"+IP+":8080/OlhaMinhaMesada/cota?id="+idParlamentar;
		
		return url;
	}
	
	public static String montaURLParlamentar (int idParlamentar){
		 
		String url = "http://"+IP+":8080/OlhaMinhaMesada/parlamentar?id="+idParlamentar;
		
		return url;
	
	}

}