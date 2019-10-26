
# TO-DO list 

## Note Map
* Track which notes are playing in the midi.Player class in a NoteMap

* Add an `allNotesOff()` function to the midi.Player class 

* Give the caller visibility to the NoteMap, so that the compositional algorithm can make choices based on the current sonority. 

## Document
* Engine parameters
* Morbleu parameters

## Other 

* `stop` - make it an element of Gamma. That way, each Gamma can be started and stopped. 

* `breathe` - add to multiNote; how to handle? All voices off during the breath? Or just no new attacks? 

* `LFO` - add a module that can modulate other modules (e.g. timing scale)

* `multiNote` engine - if bank or patch are null, ignore (so you can have more than one on a channel)

* `rndPause` - the pause between each patch starting up - should this be in a separate envelope? It's not "per-engine"

* restructure to accommodate: 
    * `player` or `device` property
    * `transpose` property (int: half steps + or -; string: interval)
    * `inheritance` - false, true, or list of properties
    * `gammas` - sub-gammas
    * `duration` (should this live at a higher level? `Gammas` currently can go on forever)
    * `patches` - accommodate 'bank:patch' notation; how to deal with the MSB/LSB swapping? (e.g. alesis uses MSB as LSB) maybe 'bank.patch' vs. 'bank:patch' or something like that. 
    * accommodate nesting of `Gammas` with the option to inherit and override 

* Refactor channel to match midi standard (rather than the Java Sound origin 0)... or add a parameter `firstChannel={1|0}` (currently at the Player level)

* Velocity scaling

* fade 

* Markov chains! 

* Figure out the sound bank... is it using the 'deluxe' version that is in 
`C:\Program Files\Java\jdk1.8.0_191\lib\audio?`
See also: `F:\music\sound fonts\Java-Sound-Bank`


