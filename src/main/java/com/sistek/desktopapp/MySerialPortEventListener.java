package com.sistek.desktopapp;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;

public class MySerialPortEventListener implements SerialPortEventListener {
	
	RestClient restClient;
	SerialPort serialPort;

	public MySerialPortEventListener(SerialPort serialPort, RestClient restClient) {
		this.serialPort = serialPort;
		this.restClient = restClient;
	}

	@Override
	public void serialEvent(SerialPortEvent event) {
        if(event.isRXCHAR()){//If data is available
        	try {
				String data = serialPort.readString(event.getEventValue());
				System.out.println(data);
				restClient.sendBarcode(data);
			} catch (Exception e) {
				System.out.println(e);
			}
        }
    }

}
