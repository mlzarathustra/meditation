
import javax.sound.midi.*

def infoList=MidiSystem.getMidiDeviceInfo()
infoList.each {
    println it
}
