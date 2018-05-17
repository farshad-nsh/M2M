package com;

/**
 * Created by farshad2 on 5/9/17.
 */
import jssc.SerialPort;
import jssc.SerialPortException;

import java.util.ArrayList;

public  class  sendToXbee {
    String user,value;
    public SerialPort serialPort;
    private String[] macArray=new String[7];
    sendToXbee(String user,String mac,String value){
        this.user=user;
        this.value=value;
        System.out.println("sendtoXbee:user="+user);
        System.out.println("sendtoXbee:mac="+mac);
        System.out.println("sendtoXbee:value="+value);
        macArray[0]=mac.substring(0,2);
        macArray[1]=mac.substring(2,4);
        macArray[2]=mac.substring(4,6);
        macArray[3]=mac.substring(6,8);
        macArray[4]=mac.substring(8,10);
        macArray[5]=mac.substring(10,12);
        macArray[6]=mac.substring(12,14);
      //  macArray[7]=mac.substring(14,16);

        System.out.println(macArray[0]);
        System.out.println(macArray[1]);
        System.out.println(macArray[2]);
        System.out.println(macArray[3]);
        System.out.println(macArray[4]);
        System.out.println(macArray[5]);
        System.out.println(macArray[6]);
       // System.out.println(macArray[7]);



    }
    public void writeToSerial(SerialPort ser){
        serialPort=ser;
        try {
            serialPort.writeByte((byte) (Integer.parseInt("7E",16) & 0xff));//start byte
            serialPort.writeByte((byte) (Integer.parseInt("00",16) & 0xff)); //high byte length
            serialPort.writeByte((byte)(Integer.parseInt("10",16) & 0xff)); //low byte length  16bytes
            serialPort.writeByte((byte)(Integer.parseInt("17",16) & 0xff)); //AT command request
            serialPort.writeByte((byte)(Integer.parseInt("00",16) & 0xff)); //we don't need acknow(frame ID)
            serialPort.writeByte((byte)(Integer.parseInt("00",16) & 0xff));    //destination address of xbee
            serialPort.writeByte((byte)(Integer.parseInt(macArray[0],16) & 0xff));//destination address of xbee
            serialPort.writeByte((byte)(Integer.parseInt(macArray[1],16) & 0xff));//destination address of xbee
            serialPort.writeByte((byte)(Integer.parseInt(macArray[2],16) & 0xff));//destination address of xbee
            serialPort.writeByte((byte)(Integer.parseInt(macArray[3],16) & 0xff));//destination address of xbee
            serialPort.writeByte((byte)(Integer.parseInt(macArray[4],16) & 0xff));//destination address of xbee
            serialPort.writeByte((byte)(Integer.parseInt(macArray[5],16) & 0xff));//destination address of xbee
            serialPort.writeByte((byte)(Integer.parseInt(macArray[6],16) & 0xff));//destination address of xbee
           // serialPort.writeByte((byte)(Integer.parseInt(macArray[7],16) & 0xff));//destination address of xbee
            //serialPort.writeBytes(hexStringToByteArray("FF"));//destination network address(under panID)
            //serialPort.writeBytes(hexStringToByteArray("FE"));//destination network address(under panID)
            serialPort.writeByte((byte)(Integer.parseInt("FF",16) & 0xff));//destination network address(under panID)
            serialPort.writeByte((byte)(Integer.parseInt("FE",16) & 0xff));//destination network address(under panID)
            serialPort.writeByte((byte)(Integer.parseInt("02",16) & 0xff));//remote command option
            serialPort.writeByte((byte)(Integer.parseInt("44",16) & 0xff));//pin number ascii for D
            serialPort.writeByte((byte)(Integer.parseInt("33",16) & 0xff));//pin number ascii for 3
            serialPort.writeByte((byte)(Integer.parseInt("0"+this.value,16) & 0xff));//value  05=high 04=low 02=ADC 03=digital in
           // long sum=0x17+0x13+0xA2+0x41+0x47+0x2E+0x68+0xFF+0xFE+0x02+0x44+0x33+0x05;

            long sum=0x17+Long.decode("0x"+macArray[0])+Long.decode("0x"+macArray[1])+
                    Long.decode("0x"+macArray[2])+Long.decode("0x"+macArray[3])+
                    Long.decode("0x"+macArray[4])+Long.decode("0x"+macArray[5])+
                    Long.decode("0x"+macArray[6])+
                    //Long.decode("0x"+macArray[7])+
                    0xFF+0xFE+0x02+0x44+0x33+Long.decode("0x0"+this.value);

            long checksum=0xFF-(sum&0xFF);
            // System.out.println("checksum"+checksum);
            System.out.println("checksum="+Long.toHexString(checksum));
            serialPort.writeByte((byte)(Integer.parseInt(Long.toHexString(checksum),16) & 0xff));//checksum
            // System.out.println("hexStringToByteArray()="+hexStringToByteArray("0F"));
            //end send digital output command to xbee

            //
        } catch (SerialPortException e) {
            e.printStackTrace();
        }


    }

}
