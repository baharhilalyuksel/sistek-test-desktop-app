package com.sistek.desktopapp;

public class TestDesktopApp {
	
	static RestClient client;
	
	public static void main(String s[]) {  
 
		SerialPortConnection serialPortConnection = new SerialPortConnection();
		RestClient restClient = new RestClient();
		FrameManager frameManager = new FrameManager(restClient, serialPortConnection);
		frameManager.startApplication();
		
    }
}
