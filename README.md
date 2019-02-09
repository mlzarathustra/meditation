
# ambient music generator

This application generates pseudo-random ambient music using
parameters passed to the engine. 

## early result
The eventual goal is to produce ambient soundscapes, but this happened along the way: https://youtu.be/qXlGXqxPX5k


## disclaimers
Some code tweaks may be required depending on your setup. It's in a state of flux. The directory is cluttered. Java Sound is finicky.

## requirements
Groovy should be on the path, meaning Java should be on the path. It uses the **javax.sound.midi** library. The scripts are  written to run in **CygWin**. They will probably work in Linux or on the Mac, but have not been tested. 

There are Java classes in the midi directory that need to be compiled using `javac`. The `build` bash script does that.  A glance at the verbosity of the Java files will give you some idea why I do this in Groovy.

It turns out that for Groovy to recognize a library, the script file must have a recognized extension, of which `.gy` is not but `.groovy` is. Now that I've renamed the files in the `midi` package, they should work a little better. (The previous approach was to compile them with groovyc first, which also works)


## usage
You can figure out what MIDI instruments Java thinks you have installed by running the **info** bash script. 

Even if you don't have an external synthesizer, you can still use the one Windows provides (if you're using Windows). It isn't the best, but it should work. 


Below is a sample: 
```
 -------------
Description: Internal software synthesizer
Name: Microsoft GS Wavetable Synth
Vendor: Unknown vendor
Version: 1.0
isOpen(): true
Maximum # of Transmitters: 0
Open Transmitters: 0
Maximum # of Receivers: -1
Open Receivers: 1
rcvr: com.sun.media.sound.MidiOutDevice$MidiOutReceiver@33b37288
is instanceof Synthesizer: false
is instanceof Sequencer: false
```

Notice that even though it's a synthesizer, it reports that it's not a synthesizer. The key is that Open Receivers is 1, and not 0.  That means it can receive playback messages via MIDI.

For accessing the above synth, I found it more functional to use "Gervill," which seems to be a JavaSound adaptor for it. 

Hopefully you have something better to play back on, but this should work for testing. 

## the Player object

You can write your own very easily.  Here's a script that plays middle C, holds it for 4 seconds, then closes (called `simple.gy`)

```groovy
import midi.Player

gervil=new Player('gervil') // the microsoft GS synth
gervil.open()

gervil.noteOn(0,60,93)
Thread.sleep(4000)
gervil.noteOff(0,60,0)

gervil.close() 
```

The **Player** constructor does a very wide search on the "name" field of the MIDI info for the device.

You can run the script by saying 

```
groovy simple.gy
```

To hear the ambient experiment try 

```
groovy morbleu.gy
```

NOTE: it does not automatically shut off notes if you are using an external synth, so be prepared to reset manually.  A note map is on the TODO list. 

