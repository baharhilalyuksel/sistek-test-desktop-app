package com.sistek.desktopapp;

public class TestDesktopApp {
	
	static RestClient client;
	
	public static void main(String s[]) {  
 
		RestClient restClient = new RestClient();
		FrameManager frameManager = new FrameManager(restClient);
		frameManager.startApplication();
		
    }
}
