package com.sistek.desktopapp;

import jssc.SerialPort;
import jssc.SerialPortException;

public class SerialPortConnection {
	private SerialPort serialPort;

	public void startConnection(RestClient restClient) {
		serialPort = new SerialPort("COM1");
		try {
			serialPort.openPort();// Open port
			serialPort.setParams(9600, 8, 1, 0);// Set params
			int mask = SerialPort.MASK_RXCHAR;// Prepare mask
			serialPort.setEventsMask(mask);// Set mask
			serialPort.addEventListener(new MySerialPortEventListener(serialPort, restClient));// Add SerialPortEventListener
		} catch (SerialPortException ex) {
			System.out.println(ex);
		}
	}
}
