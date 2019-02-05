
# TO-DO list 




* Track which notes are playing in the midi.Player class in a NoteMap

* Add an `allNotesOff()` function to end gracefully 

* Give the caller visibility to the NoteMap, so that the compositional algorithm can make choices based on the current sonority. 

* Refactor channel to match midi standard (rather than the Java Sound origin 0)

* For the composer, support a structure like this:

```groovy
playerParams = [
    channel: 0,
    pitches: 'c1 g1 c2',
    patches: [95,97,98],
    timing: [
        min: 10000, var: 7000, pause: 5000
    ]
]
```




