package midi
import javax.sound.midi.*
import static javax.sound.midi.ShortMessage.*

import static midi.MlzMidi.*

class Player {

    static final int BANK_MSB = 0
    static final int BANK_LSB = 32

    MidiDevice.Info info
    MidiDevice dev
    ShortMessage msg=new ShortMessage()
    Receiver recv
    Instrument[] instruments
    long timeStamp=-1 // dummy 
    def playing = [*1..17].collect { [] }

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

    // "Channel" means the display channel shown on MIDI instruments
    // The protocol subtracts 1 for internal use.
    // 
    void send(int midiMsg, int channel, int b1, int b2) {
        def internalChannel = channel - 1
        
        if (!dev || !recv ) {
            println "dev $dev - recv $recv - can't be null!"
            return
        }
        msg.setMessage(midiMsg, internalChannel, b1, b2)
        recv.send(msg,timeStamp)
    }
    synchronized void noteOn(int channel, int note, int vel) { 
        send(NOTE_ON,channel,note,vel) 
        playing[channel]<<note
    }
    synchronized void noteOff(int channel, int note, int vel=0) { 
        send(NOTE_OFF,channel,note,vel) 
        playing[channel] -= note
    }
    synchronized void patch(int channel, int patch) { 
        send(PROGRAM_CHANGE,channel, patch, 0) 
    }

    synchronized void bankMSB(int channel, int b) {
        send(CONTROL_CHANGE, channel, BANK_MSB, b)
    }
    synchronized void bankLSB(int channel, int b) {
        send(CONTROL_CHANGE, channel, BANK_LSB, b)
    }
    synchronized void control(int channel, b1, b2) { 
        send(CONTROL_CHANGE,channel,b1,b2) 
    }

    void allNotesOff() {
        println "allNotesOff: playing="+playing.collect { it.collect { midiNumToStr(it)} }
        playing.eachWithIndex { on, idx->
            while (on) {
                noteOff(idx, on[0])
                on.remove(0)
                 
            }    
        }
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