package midi;

import javax.sound.midi.*;
import java.io.*;
import java.util.*;

import static java.lang.System.out;

public class PlayTest {
    public static void main(String[] args) {
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
        MidiDevice.Info[] infoList = MidiSystem.getMidiDeviceInfo();

        out.println("");
        for (int i=0; i<infoList.length; ++i) {
            MidiDevice.Info info=infoList[i];
            out.println(""+i+": "+info);
        }

        for (;;) {
            out.println("\n");
            out.print("Type a device number, or Enter to quit: ");
            int devNo=0;
            MidiDevice midiDev=null;
            try {
                String ln = in.readLine();
                if (ln.trim().length()==0) System.exit(0);
                devNo = Integer.parseInt(ln);
                out.println("");
                out.println(infoList[devNo]);

                midiDev = MidiSystem.getMidiDevice(infoList[devNo]);
                out.println("device: "+midiDev);
                midiDev.open();

                // unless you call getTransmitter(), getTransmitters() will
                // always return an empty list.
                try {
                    Transmitter trans = midiDev.getTransmitter();
                }
                catch (Exception ex) {
                    out.println("error getting transmitter");
                }
                out.println("Transmitter(s):");
                List<Transmitter> transList=midiDev.getTransmitters();
                for (Transmitter t : transList) {
                    out.println(""+t);
                }

                //  Same with getReceivers() - it will be empty unless you call
                //  getReceiver() first. 
                Receiver rcvr=null;
                try {
                    rcvr = midiDev.getReceiver();
                }
                catch (Exception ex) {
                    out.println("error getting rcvr");
                }
                out.println("Receiver(s):");
                List<Receiver> recvList=midiDev.getReceivers();
                for (Receiver r : recvList) {
                    out.println(""+r);
                }

                if (rcvr==null) {
                    out.println("No Receiver - can't play anything.");
                    continue;
                }

                //  play middle C
                ShortMessage msg=new ShortMessage();
                msg.setMessage(ShortMessage.NOTE_ON, 0, 60, 93);
                rcvr.send(msg, 0L);
                Thread.sleep(2000);
                msg.setMessage(ShortMessage.NOTE_OFF, 0, 60, 0);
            }
            catch (Exception ex) {
                out.println(""+ex);
                System.exit(0);
            }
            finally {
                midiDev.close();
            }
        }
    }
}
