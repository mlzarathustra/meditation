
# Morbleu! An ambient music generator

This application generates pseudo-random ambient music using
parameters passed to various compositional engines. 

Hopefully, a side effect will be a convenient framework for working with MIDI in Groovy. 

## early result

At this point, if you `cd` to the `src` directory and say 
```
groovy morbleu c-major

# or

groovy morbleu d-minor
```

It should produce ambient music through the local machine's MIDI synthesizer. 

If you are running `bash`, the `morbleu` script will save you typing `groovy`


The aim is for the output to be highly adjustable. The eventual goal is to produce ambient soundscapes, but this happened along the way: https://youtu.be/qXlGXqxPX5k

## requirements
`Groovy` should be on the path, meaning `Java` should be on the path. It uses the **javax.sound.midi** library. The scripts are  written to run in **cygwin**. They will probably work in Linux or on the Mac, but have not been tested. They're not complex, so you can look at them and run the commands by hand.

There are Java classes in the midi directory that need to be compiled using `javac`. The `build` bash script does that.  A glance at the verbosity of the Java files will give you some idea why I do this in Groovy.

## usage
You can figure out what MIDI instruments Java thinks you have installed by running the `info` bash script. 

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

gervil.noteOn(1,60,93)
Thread.sleep(4000)
gervil.noteOff(1,60,0)

gervil.close() 
```

The **Player** constructor does a very wide search on the "name" field of the MIDI info for the device.

You can run the script by saying 

```
groovy simple.gvy
```

To hear the ambient experiment try 

```
groovy morbleu d-minor
```

NOTE: 
Morbleu listens to `Enter` from the keyboard as a signal to shut down its threads. It does not automatically shut off notes if you interrupt with ctl-C and are using an external synth. If you do so, be prepared to reset manually.
## windows 10 error

If you see this: 
```
java.util.prefs.WindowsPreferences <init>
WARNING: Could not open/create prefs root node Software\JavaSoft\Prefs at root 0x80000002. Windows RegCreateKeyEx(...) returned error code 5.
```
Try this: 

1. Go into your Start Menu and type regedit into the search field.
2. Navigate to path HKEY_LOCAL_MACHINE\Software\JavaSoft (Windows 10 seems to now have this here: HKEY_LOCAL_MACHINE\Software\WOW6432Node\JavaSoft)
3. Right click on the JavaSoft folder and click on New -> Key
4. Name the new Key Prefs and everything should work.

## disclaimers
Some code tweaks may be required depending on your setup. It's in a state of flux. The directory is cluttered. Java Sound is finicky.

See ./TODO.md to see what may eventually get done.
