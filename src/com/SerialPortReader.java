package com;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;

/**
 * Created by Farshad Noravesh on 4/17/17.
 */
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.ByteArrayOutputStream;
class SerialPortReader extends AnchorPane implements SerialPortEventListener {
public  MainWatch W1=new MainWatch();

    @FXML
    ToggleGroup selectedUsbPort;
    @FXML
    RadioButton usb1,usb2;
    public String[] receivedByte;
    public String buffer,startByte,mac,sourceNetworkAddress,digitalMask,analogMask,data;
    public int IntegerOfData;
    public String user,password;
    public String myOS;
    public String myJson;
    public static SerialPort serialPort ;
    @FXML
    TextField UIPort = null;
    @FXML
    TextField result = null;
    public static String port;
    public String serverPort;
    //="/dev/cu.usbserial-A96PLNJR";
    //   /dev/cu.usbserial-A96PLNJR
// for linux-->   /dev/ttyUSB0
    public Stage editActuatorStage;
     public SerialPortReader(String username,String theOS,String server,String password){
         myOS=theOS;
         user=username;
         this.password=password;
         this.serverPort=server;
         System.out.println("user is now :"+user);
         //
        // System.out.println(System.setProperty("m","//home//farshad2//Documents"));
      //


         //




     }
@FXML
    public void add() throws SerialPortException {
        // UIPort.setText("/dev/cu.usbserial-A96PLNJR");


        selectedUsbPort.selectedToggleProperty().addListener(
                (ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
                    if (selectedUsbPort.getSelectedToggle() != null) {
                        System.out.println(selectedUsbPort.getSelectedToggle().selectedProperty().getBean().toString());

                        if (selectedUsbPort.getSelectedToggle().selectedProperty().getBean().toString()
                                .compareTo("RadioButton[id=usb1, styleClass=radio-button]'usb1'") == 0) {


                            try {
                                System.out.println("usb1");
                                if (this.myOS.compareTo("linux")==0){
                                    UIPort.setText("/dev/ttyUSB0");
                                }else if (this.myOS.compareTo("mac")==0){
                                    UIPort.setText("/dev/cu.usbserial-A96PLNJR");
                                }
                                serialPort = new SerialPort(UIPort.getCharacters().toString());
                                serialPort.openPort();//Open serial port
                                serialPort.setParams(SerialPort.BAUDRATE_9600, serialPort.DATABITS_8, serialPort.STOPBITS_1, serialPort.PARITY_NONE);

                                int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;//Prepare mask
                                serialPort.setEventsMask(mask);//Set mask
                                serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
                                serialPort.addEventListener(new SerialPortReader(this.user,this.myOS,this.serverPort,this.password), SerialPort.MASK_RXCHAR);






                                System.setProperty("m","//home//lcdfarshad//Documents//myJavascriptCodes//javaserver//ver3-writfile");

                                Path folder = Paths.get(System.getProperty("m"));
                                W1.setPath(folder.toString());
                                W1.setSerial(serialPort);
                                Thread T1=new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            W1.watchDirectoryPath(folder);
                                        } catch (SerialPortException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                T1.start();
                                System.out.println("after");





                                port = UIPort.getCharacters().toString();
                                System.out.println(UIPort.getCharacters().toString());
                                result.setText("usb port : " + UIPort.getCharacters().toString() + "opened successfully !");


                            } catch (Exception e) {

                            }

                        }
                    }
                });
    }



    @Override
    public void serialEvent(SerialPortEvent event) {

        if (event.isRXCHAR()) {//If data is available
            if (event.getEventValue() ==22) {//Check bytes count in the input buffer
                //Read data, if 22 bytes available
                try {
                    mac=""; sourceNetworkAddress="";digitalMask="";analogMask="";data="";
                    buffer= serialPort.readHexString(22);
                    System.out.println("buffer="+buffer);
                    int count=0;//number of bytes received (byteID! we need 22bytes)
                    for (String p : buffer.split("\\s")) {
                        count++;
                        receivedByte = p.split("");
                        if (count == 1) {
                            startByte = receivedByte[0] + receivedByte[1];
                            System.out.println("startByte=" + startByte);
                        }
                        if ((count >= 5) && (count <= 12)) {
                            mac += receivedByte[0] + receivedByte[1];
                            if (count == 12) {
                                System.out.println("mac=" + mac);
                            }
                        }
                        if ((count >= 13) && (count <= 14)) {
                            sourceNetworkAddress += receivedByte[0] + receivedByte[1];
                            if (count == 14) {
                                System.out.println("sourceNetworkAddress=" + sourceNetworkAddress);
                            }
                        }
                        if ((count >= 17) && (count <= 18)) {
                            digitalMask += receivedByte[0] + receivedByte[1];
                            if (count == 18) {
                                System.out.println("digitalMask=" + digitalMask);
                            }
                        }
                        if (count == 19) {
                            analogMask += receivedByte[0] + receivedByte[1];
                            System.out.println("analogMask=" + analogMask);
                        }
                        if ((count >= 20)&&(count<=21)) {
                            data += receivedByte[0] + receivedByte[1];
                            if (count == 21) {

                                System.out.println("data=" + data);
                            }
                        }
                    }
                    createFile File1=new createFile();
                    File1.openFile();
                    mac = mac.replaceAll("^0+", "");
                    data=data.replaceAll("^0+", "");
                    System.out.println("now:"+data);
                    if (data.matches("")){
                        IntegerOfData=0;
                    }else{
                        IntegerOfData = Integer.parseInt(data, 16);
                    }


                    if (analogMask.matches("08")){
                        System.out.println("We are receiving analog data !");
                        myJson="{message:{mac:"+mac+",value:"+IntegerOfData+"}}";
                        IntegerOfData= (int) (IntegerOfData*212.77-68.085)/1023;
                    }else {
                        System.out.println("We are receiving digital data !");
                       // myJson="{message:{mac:"+mac+",value:"+IntegerOfData+"}}";

                        myJson="{\"message\""+":"+
                                "{"+"\"mac\""+":"+"\""+mac+"\""+","+"\"value\""+":"
                                +IntegerOfData+","+"\"user\""+":"+"\""+user+"\""+"}}";
                    }
                    File1.addRequest(myJson);
                    File1.closeFile();
                    //Now we post this file as a http post request


                    try {
                       // serverPort="4201";

                        if (HttpURLConnectionExample.sendingPostRequestToLogin(this.user,this.password,this.serverPort).compareTo("fine")==0){
                            HttpURLConnectionExample.sendingPostRequestToCurlDevice(mac,IntegerOfData,user,this.serverPort);


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    System.out.println("GET DONE");
                    //end posting
                } catch (SerialPortException ex) {
                    System.out.println(ex);
                }
            }
        } else if (event.isCTS()) {//If CTS line has changed state
            if (event.getEventValue() == 1) {//If line is ON
                System.out.println("CTS - ON");
            } else {
                System.out.println("CTS - OFF");
            }
        } else if (event.isDSR()) {///If DSR line has changed state
            if (event.getEventValue() == 1) {//If line is ON
                System.out.println("DSR - ON");
            } else {
                System.out.println("DSR - OFF");
            }
        }
    }







}
