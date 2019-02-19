
# TO-DO list 

* `GammaTree` - in separate file, specified on the command line

* `rndPause` - the pause between each patch starting up - should this be in a separate envelope? It's not "per-engine"

* `stop` logic - shouldn't need to explicitly state the engine class; maybe register engines?

* restructure to accommodate: 
    * `player` or `device` property
    * `transpose` property (int: half steps + or -; string: interval)
    * `inheritance` - false, true, or list of properties
    * `gammas` - sub-gammas
    * `duration` (should this live at a higher level? `Gammas` currently can go on forever)
    * `patches` - accommodate 'bank:patch' notation; how to deal with the MSB/LSB swapping? (e.g. alesis uses MSB as LSB) maybe 'bank.patch' vs. 'bank:patch' or something like that. 
    * accommodate nesting of `Gammas` with the option to inherit and override 

* Refactor channel to match midi standard (rather than the Java Sound origin 0)... or add a parameter `firstChannel={1|0}` (currently at the Player level)

* Figure out the sound bank... is it using the 'deluxe' version that is in 
`C:\Program Files\Java\jdk1.8.0_191\lib\audio?`
See also: `F:\music\sound fonts\Java-Sound-Bank`

* Parse YAML? But it might be nice to allow Groovy closures (e.g. for `engine`)



## Note Map
* Track which notes are playing in the midi.Player class in a NoteMap

* Add an `allNotesOff()` function to the midi.Player class 

* Give the caller visibility to the NoteMap, so that the compositional algorithm can make choices based on the current sonority. 

## Sample Gamma

```
gammas = [
    [
        title: 'high',
        channel: 14..15,
        engine: multiPatch,

        pitches: toMidiNumList('c1 c2 g1 g2 c2 d2 b2 c3'),
        patches: patches,
        timing: [
            hold: [ min: 10000, var: 7000 ],
            pause: [ min: 200, var: 7000]
        ]
    ],
    ...
]
```



