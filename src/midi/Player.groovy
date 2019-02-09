package midi
import javax.sound.midi.*
import static javax.sound.midi.ShortMessage.*

import static midi.MlzMidi.*

class Player {

    MidiDevice.Info info
    MidiDevice dev
    ShortMessage msg=new ShortMessage()
    Receiver recv
    Instrument[] instruments
    long timeStamp=-1 // dummy 

    Player(id) {
        info=getReceiver(id)
        if (info) dev=MidiSystem.getMidiDevice(info)
    }

    void open() { 
        dev.open()
        recv=dev.getReceiver()
    }
    void close() { 
        recv=null
        dev.close()
    }

    void send(int midiMsg, int channel, int b1, int b2) {
        if (!dev || !recv ) {
            println "dev $dev - recv $recv - can't be null!"
            return
        }
        msg.setMessage(midiMsg, channel, b1, b2)
        recv.send(msg,timeStamp)
    }
    synchronized void noteOn(int channel, int note, int vel) { 
        send(NOTE_ON,channel,note,vel) 
    }
    synchronized void noteOff(int channel, int note, int vel) { 
        send(NOTE_OFF,channel,note,vel) 
    }
    synchronized void patch(int channel, int patch) { 
        send(PROGRAM_CHANGE,channel, patch, 0) 
    }
    synchronized void control(int channel, b1, b2) { 
        send(CONTROL_CHANGE,channel,b1,b2) 
    }

    Instrument[] getInstruments() {
        if (dev instanceof Synthesizer && instruments==null) {
            instruments=dev.getLoadedInstruments()
        }
        instruments
    }
    void showInstrument(int n) {
        if ( dev instanceof Synthesizer ) println getInstruments()[n]
    }

    void loadInstrument(int n) {
        if (dev instanceof Synthesizer) dev.loadInstrument(getInstruments()[n])
    }

    String toString() { "Player for $info"}
}