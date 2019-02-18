
# TO-DO list 

* `GammaTree` - in separate file, specified on the command line

* add  a 'transpose' feature

* Use YAML instead? But it might be nice to allow closures (e.g. for `engine`)

* restructure to accommodate: 
    * `engine` property (call these 'multi-patch' and 'ocean')
    * `device` property
    * `transpose` property (int: half steps + or -; string: interval)
    * `GammaBase` type (for defaults)... or set inheritence as a property? 
    * `duration` (should this live at a higher level? `Gammas` currently can go on forever)
    * `patches` - accommodate 'bank:patch' notation; how to deal with the MSB/LSB swapping? (e.g. alesis uses MSB as LSB) maybe 'bank.patch' vs. 'bank:patch' or something like that. 
    * accommodate nesting of `Gammas` with the option to inherit and override 

* Track which notes are playing in the midi.Player class in a NoteMap

* Add an `allNotesOff()` function to end gracefully 

* Give the caller visibility to the NoteMap, so that the compositional algorithm can make choices based on the current sonority. 

* Refactor channel to match midi standard (rather than the Java Sound origin 0)... or add a parameter `firstChannel={1|0}` (currently at the Player level)

* Figure out the sound bank... is it using the 'deluxe' version that is in 
`C:\Program Files\Java\jdk1.8.0_191\lib\audio?`
See also: `F:\music\sound fonts\Java-Sound-Bank`

## Sample Gamma

```
gamma = [
    [
        title: 'sub',
        channel: 0..3,
        pitches: toMidiNumList('c0 c1'),
        patches: lowPatches,
        timing: [
            hold: [ min: 10000, var: 7000 ],
            pause: [ min: 200, var: 7000]
        ]
    ],
    ...
]
```



