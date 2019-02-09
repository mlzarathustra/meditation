
import javax.sound.midi.*


def msg=new ShortMessage()
msg.setMessage(ShortMessage.NOTE_ON,0, 60, 93)
long timeStamp=-1
def rcvr=MidiSystem.getReceiver()
rcvr.send(msg, timeStamp)

Thread.sleep(2000)