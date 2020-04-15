package com.sistek.desktopapp;

import jssc.SerialPort;
import jssc.SerialPortException;

public class SerialPortConnection implements Runnable {
	
	private SerialPort serialPort;
	private RestClient restClient;

	public SerialPortConnection(RestClient restClient) {
		this.restClient = restClient;
	}

	@Override
	public void run() {
		serialPort = new SerialPort("/dev/pts/2");
		boolean portOpenUnsuccessful = true;
		while (portOpenUnsuccessful) {
			try {
				serialPort.openPort();
				serialPort.setParams(9600, 8, 1, 0);
				int mask = SerialPort.MASK_RXCHAR;
				serialPort.setEventsMask(mask);
				serialPort.addEventListener(new MySerialPortEventListener(serialPort, restClient));
				portOpenUnsuccessful = false;
			} catch (SerialPortException ex) {
//				System.out.println(ex);
			}
		}
	}
}
