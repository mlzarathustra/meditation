import javax.sound.midi.*
import static javax.sound.midi.ShortMessage.*

//  plays notes via MOTU 828 interface
//  there are two: one for receiving, the other for transmitting
//  so first we have to find the receiver
//
def infoList=MidiSystem.getMidiDeviceInfo()
def midiDevInfo = infoList.find {
    if ( !it.name.startsWith('828') ) return false

    def midiDev=MidiSystem.getMidiDevice(it)
    midiDev.open()
    try { recv=midiDev.getReceiver() }
    catch (Exception ex) {
        midiDev.close() 
        return false
        //println 'no receiver'
    }
    midiDev.close()
    return true
}
println midiDevInfo

def midiDev=MidiSystem.getMidiDevice(midiDevInfo)
println midiDev.getClass()

midiDev.open()
def recv=midiDev.getReceiver()
println recv.getClass()

def msg=new ShortMessage()
long timeStamp=-1

msg.setMessage(CONTROL_CHANGE,0,0,2)
recv.send(msg,timeStamp)


// the last arg is ignored, but is needed for the fn overload
msg.setMessage(PROGRAM_CHANGE,0,74,0) 
recv.send(msg,timeStamp)

seq=[60,62,67,71,28]

seq.each { note -> 
    msg.setMessage(NOTE_ON,0,note,44)
    recv.send(msg,timeStamp)
    Thread.sleep(1000)
}

Thread.sleep(4000)

seq.each { note->
    msg.setMessage(NOTE_OFF,0,note,0)
    recv.send(msg,timeStamp)
}

recv.send(msg,timeStamp)

midiDev.close()
//System.exit(0)




