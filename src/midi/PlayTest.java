package midi;

import javax.sound.midi.*;
import java.io.*;
import java.util.*;

import static java.lang.System.out;

public class PlayTest {
    public static void main(String[] args) {
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
        MidiDevice.Info[] infoList = MidiSystem.getMidiDeviceInfo();
        for (int i=0; i<infoList.length; ++i) {
            MidiDevice.Info info=infoList[i];
            out.println(""+i+": "+info);
        }
        out.print("enter a device number: ");
        int devNo=0;
        try {
            String ln = in.readLine();
            devNo = Integer.parseInt(ln);

            MidiDevice midiDev = MidiSystem.getMidiDevice(infoList[devNo]);
            out.println("device: "+midiDev);

            List<Transmitter> transList=midiDev.getTransmitters();
            for (Transmitter t : transList) {
                out.println(""+t);
            }




        }
        catch (Exception ex) {
            out.println(""+ex);
            System.exit(0);
        }
    }
}
