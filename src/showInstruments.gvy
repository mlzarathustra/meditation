
import javax.sound.midi.*

def synth=MidiSystem.getSynthesizer()
println synth

def soundBank=synth.getDefaultSoundbank()
println soundBank

def instList=soundBank.getInstruments()
instList.each {
    println it
}

//println synth.getLoadedInstruments().size() // is 0
