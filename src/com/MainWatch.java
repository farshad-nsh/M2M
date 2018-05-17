package com;

/**
 * Created by farshad2 on 5/5/17.
 */

import jssc.SerialPort;
import jssc.SerialPortException;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;

import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;



public class MainWatch {

    public String user,mac,value;
    public SerialPort serialPort ;
    public String path,xbeepath;
    public int count;

    public   void watchDirectoryPath(Path path) throws SerialPortException {
        // Sanity check - Check if path is a folder
        try {
            Boolean isFolder = (Boolean) Files.getAttribute(path,
                    "basic:isDirectory", NOFOLLOW_LINKS);
            if (!isFolder) {
                throw new IllegalArgumentException("Path: " + path + " is not a folder");
            }
        } catch (IOException ioe) {
            // Folder does not exists
            ioe.printStackTrace();
        }

        System.out.println("Watching path: " + path);

        // We obtain the file system of the Path
        FileSystem fs = path.getFileSystem ();

        // We create the new WatchService using the new try() block
        try(WatchService service = fs.newWatchService()) {

            // We register the path to the service
            // We watch for creation events
            path.register(service, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);

            // Start the infinite polling loop
            WatchKey key = null;
             while(true) {
            key = service.take();

            // Dequeueing events
            Kind<?> kind = null;
            for(WatchEvent<?> watchEvent : key.pollEvents()) {
                // Get the type of the event
                kind = watchEvent.kind();
                if (OVERFLOW == kind) {
                    continue; //loop
                } else if (ENTRY_CREATE == kind) {
                    // A new Path was created
                    Path newPath = ((WatchEvent<Path>) watchEvent).context();
                    // Output
                    System.out.println("New path created: " + newPath);

                }

                else if(ENTRY_MODIFY==kind){
                    System.out.println("farshad modified!");
                    // modified
                    Path newPath = ((WatchEvent<Path>) watchEvent)
                            .context();
                    // Output
                    System.out.println("New path modified: " + newPath);
                    System.out.println(xbeepath);
                    //read from file: xbeepath
                    File file = new File(xbeepath);
                    FileReader fr = new FileReader(file);
                    char [] a = new char[20];
                    count=0;
                    try {

                        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                            String line;
                            while ((line = br.readLine()) != null) {
                                count++;
                                switch (count){
                                    case 1: {System.out.println("user is:"+line);user=line;break;}
                                    case 2: {System.out.println("mac is :"+line);mac=line;break;}
                                    case 3: {System.out.println("value is:"+ line);value=line;break;}

                                }
                            }
                           //
                            //send digital output command to xbee
                            sendToXbee xbee=new sendToXbee(user,mac,value);
                            xbee.writeToSerial(serialPort);


                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
                else if(ENTRY_DELETE==kind){
                    System.out.println("farshad deleted!");
                    Path newPath = ((WatchEvent<Path>) watchEvent)
                            .context();
                    // Output
                    System.out.println("New path deleted: " + newPath);
                }

            }

                if(!key.reset()) {
                   break; //loop
                }

            }

        } catch(IOException ioe) {
            ioe.printStackTrace();
        } catch(InterruptedException ie) {
            ie.printStackTrace();
        }

    }

    public void setSerial(SerialPort se) {
        this.serialPort = se;
    }

    public void setPath(String path) {
        this.xbeepath = path+"/xbee.txt";
    }


}
