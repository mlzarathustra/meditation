
# Structure for Gamma files 

The "Gamma files," found in the `gamma` directory 
direct the `morbleu` player which engines to use and 
what parameters to pass them.

They are interpreted as Groovy files, so they may include Groovy code. 
It's the return value that the application sees, namely the value of the last statement in the file.

There are examples in the gamma directory that illustrate the points explained below.


## Basic Structure

There are two possible structures one may use: List and Map.

### Simple structure (Map or List) 

Below  is an example of a single gamma, which is a Map:
(see [gamma/simple.gvy](src/gamma/simple.gvy))
```groovy
[
    // borrowed from d-minor.gvy

    title: 'chord',
    channel: 1..8,
    engine: 'multiPatch',

    pitches: 'd0 d1 d2 a2 d3 e3 f3 a3 bb3 d4 f4',
    patches: [49,50],

    timing: [
        hold: [ min: 10000, var: 7000 ],
        pause: [ min: 200, var: 200]
    ],
]
```
To run this example, say `morbleu simple`

---
You can also nest a collection of Gammas into a List:
(see [gamma/simple-list.gvy](src/gamma/simple-list.gvy))

```groovy
[
        [
            title: 'sea',
            channel: 9,
            engine: 'ocean',

            spread: 10,
            density: 5,
            gapMin: 500,
            gapVar: 500,
            velVar: 26,
        ],
        [
            title: 'low',
            channel: 5..8,
            engine: 'multiPatch',

            pitches: 'c1 c2 g1 g2 c2 c3',
            patches: [89,95,101],
            timing: [
                hold: [ min: 10000, var: 7000 ],
                pause: [ min: 200, var: 7000]
            ],
        ]
]



```

### Advanced structure (Map)

Here the list of `gammas` shown above is embedded in a Map that 
allows for setting various parameters at a global level.

An example of the advanced structure follows. 
Note that the returned object is a Map.

```groovy
[
    // optional: the amount of time to leave between starting engines, 
    // that is, after starting one engine before starting the next.
    //
    fixedPause: 1000, 


    //  this section is optional. It defines values that will be 
    //  inherited by all of the engines.     
    inherit: [   
        passive: [
            timing: [
                hold: [ min: 9000, var: 7000 ],
                pause: [ min: 200, var: 7000]
            ]
        ],
        add: [
            transpose: 0
        ]

    ],
    
    //  this section is required. It contains the list of gammas. 
    gamma: [
         [
            title: 'film score',
            channel: 1,
            engine: 'multiNote',
            pitches: 'f4 Bb4 d5 Eb5',
            patch: 53,
            bankMSB: 3,
            noteCount: 3,
            velocity: 64
        ],
        [ //  the next Gamma 
        ]
    ]
]
```



## Inherit section

This section is optional. It can help you avoid repeating settings
by defining values that pertain to (or can pertain to) all of the gammas.

The `inherit` section has three basic sub-sections:
* `passive` - these values are used if none other are defined
* `override` - use these values no matter what
* `add` - these values will be added to the values defined, 
mostly used for transposition. 

The logic can be found at the top of `engine/Morbleu.groovy`. (Note: which is different from `morbleu.gvy`) It does not perform any validity checking. You're perfectly free to define values that the engine will ignore. 
 


## Gamma section 

You can get a list of the currently available engines by using the -list command line argument:

```
$ morbleu -list
available engines:
  breathe
  intervals
  multiNote
  multiPatch
  ocean
```

Details of each engine's parameters and usage follow below.

### Common Parameters

**Timing**

 - hold: how long the note is sustained. 
 - pause: how long between the end of this note and the beginning of the next

Both have a range of variance defined by two sub-parameters: 
 - min: the minimum length
 - var: amount of random variation added to the minimum 

Sample timing block: 
```groovy

    timing: [
        hold: [ min: 10000, var: 7000 ], // range: 10000 - 17000
        pause: [ min: 200, var: 200]     // range: 200 - 400
    ],
```

### Note representation

Note names are case-insensitive. To specify a "flat," use a b (lower-case B). A "#" is a sharp, of course.

The octave is given by the number following, according to the MIDI standard. C3 is middle C. 

For example, "Bb3" is the B-flat on the middle line of the treble clef.


### Helper functions

When you want to specify a scale or a chord, a couple of methods are available to help:

- makeScale()
- makeArpeggio()

In both cases, their output replaces a manually typed string containing a list of note names separated by space (e.g. `'c3 e3 g3'`)

---
#### makeScale( root, lowest, highest, scale ) 
Example:
```groovy

import static midi.Scale.*
 // ...
     pitches: makeScale('Eb', 'c2','eb5', overtone)
```

This will generate an overtone scale based on E-flat, with a range from c2 to E-flat 5.

The types of scale available are: 
 - major
 - minor
 - naturalMinor (= minor)
 - melodicMinor (ascending) 
 - overtone
 - chromatic

---

#### makeArpeggio( root, lowest, highest, chord )
Example:
```groovy
import static midi.Scale.*
 // ...
    pitches: makeArpeggio('G#','g#2','c#6', major7),
```

This will generate an arpeggio on a major 7th chord based on G#, from G#2 to C#6. 


The types of chord available are: 
 - major
 - minor
 - augmented
 - diminished
 - major7
 - minor7
 - halfDim
 - fullDim
 - minorMajor7
 - oneFive


## Engines

### multiNote

Plays multiple notes on a single channel, from the `pitches` given. The number of notes is determined by the `noteCount` parameter.

Example: 
```groovy
    [
        title: 'flute',
        channel: 2,
        engine: 'multiNote',
        pitches: 'c#1 g#1 c#2 e#2',
        patch: 70,
        bankMSB: 1, 
        noteCount: 3,
        velocity: 96,
        noRepeats: true        
    ]

```

---
### multiPatch

Plays one note per channel. On each note change, choosing a random and note and patch from the `pitches` and `patches` parameters given.

Example:
```groovy
    [
        title: 'chord',
        channel: 1..8,
        engine: 'multiPatch',
    
        pitches: 'd0 d1 d2 a2 d3 e3 f3 a3 bb3 d4 f4',
        patches: [49,50],
    
        timing: [
            hold: [ min: 10000, var: 7000 ],
            pause: [ min: 200, var: 200]
        ],
    ]
```
---

### intervals

Plays a series of the interval specified, when supplied with a scale.  

For example: 
```groovy
import static midi.Scale.*

[
    title: 'strings',
    channel: 1,
    engine: 'intervals',
    pitches: makeScale('Eb', 'c1','eb4', overtone),
    interval: 10,
    maxLeap: 3,
    repeat: 'hold',  // possible values: 'never', 'hold', 're-attack'

    patch: 56,
    bankMSB: 2,
    velocity: 48,
    timing: [
        hold: [ min: 1000, var: 1000 ],
        pause: [ min: 0, var: 0 ]
    ],
]
```

The above will play 10ths in the E-flat overtone scale. 

When it switches notes, it never moves farther than `maxLeap` (also expressed in origin 1, as an interval). Above, it will either repeat or step up or down a 2nd or 3rd.  

The `repeat` parameter dictates how to handle repeated notes:
 * `never` - Never repeat notes. Always ensure that the next note is different from the last. If that's impossible given this set of notes and the interval, it will be changed to `hold`
 * `hold` - When the next randomly generated note is the same as the last, simply hold the note
 * `re-attack` - when the next note is the same as the last, transmit a note-off and a note-on to effect a re-attack.

Timing is as described above in the **Common Parameters** section.

The Gammas `intervals.gvy` and `intervals-2.gvy` illustrate single and multiple instances of the intervals engine, respectively. 

Note that if the set of notes given is a chord (for example) rather than a scale, the algorithm will ignore the gaps between the notes in the set, and instead treat the distance between notes as if they were on a scale.

If the set of notes given is "C E G B" and the interval given is `3`, the possible intervals will be C-G and E-B, i.e. the endpoints of a span of three sequential notes in the set.  

In a diatonic scale, that's simply a third. In a chromatic scale, however, it will be a major 2nd. 


### ocean

    TODO - write description





### breathe

    TODO - write description






