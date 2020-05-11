
# Morbleu! An ambient music generator

This application generates pseudo-random ambient music 
using parameters passed to various compositional engines. 

## Requirements
`Groovy` should be on the path, meaning `Java` should be on the path. 
It uses the **javax.sound.midi** library. The scripts are  written to run in **cygwin**. 
They will probably work in Linux or on the Mac, but have not been tested. 
They're not complex, so you can look at them and run the commands by hand.

There are Java classes in the midi directory that need to be compiled using `javac`. 
The `build` bash script does that. Groovy is an interpretive language, 
so no compilation step is needed when modifying Groovy scripts.


## Quick Start

* `cd` to the `src` directory
* `groovy morbleu c-major` 

This should produce ambient music through the local machine's MIDI synthesizer.

Hit <kbd>Enter</kbd> to quit.  

If you are running `bash`, the `morbleu` script will save you typing `groovy`, especially if `.` is on your path.

There are also some other scripts that will create MIDI music using a similar framework. 
For example, this happened along the way: https://youtu.be/qXlGXqxPX5k

## Creating your own Music
The scripts (written in Groovy) that determine what music `morbleu` will play are 
loosely called `gammas`,  
or more accurately, they contain a list of `gammas` 
To learn how that works, please see 
[Gamma.md](./Gamma.md)

Below you'll find more low-level help, including some ideas in case you can't 
get any sound to play.


## MIDI Instrument(s)
You can figure out what MIDI instruments Java thinks you have installed by 
running the `info` bash script. 

By default, it will use "Gervil," which is Microsoft's wavetable synth. You can override this setting 
by renaming `morbleu-settings.gvy.example` to `morbleu-settings.gvy` and editing the value of the 
'player' property. It does a case-insensitive 'contains' match on the `Name` field 
as returned by the `info` command, so you only need to spell out enough of the name 
to be distinct.

Below is a sample of the `info` output: 
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

Hopefully you have something better to play back on. 

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
## Windows 10 error

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

## Disclaimers
Some code tweaks may be required depending on your setup. It's in a state of flux. The directory is cluttered. Java Sound is finicky.

See ./TODO.md to see what may eventually get done.
