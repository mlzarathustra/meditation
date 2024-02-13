
# Structure for Gamma files 

The "Gamma files," found in the `gamma` directory 
direct the `morbleu` player which engines to use and 
what parameters to pass them.

They are interpreted as Groovy files, so they may include Groovy code. 
It's the return value that the application sees, namely the value of the 
last statement in the file. 

## Basic Structure

There are two possible structures one may use: List and Map.

### Simple structure (Map or List) 

Below  is an example of a single gamma, which is a Map:
(see [gamma/simple.gvy](gamma/simple.gvy))
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
(see [gamma/simple-list.gvy](gamma/simple-list.gvy))

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

The usage of each will follow in a subesequent revision. 

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




### breathe

### intervals

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
        ],

```

---
### multiPatch

Plays one note per channel. On each note change, choosing a random and note and patch from the `pitches` and `patches` parameters given.

Example:
```groovy

    title: 'chord',
    channel: 1..8,
    engine: 'multiPatch',

    pitches: 'd0 d1 d2 a2 d3 e3 f3 a3 bb3 d4 f4',
    patches: [49,50],

    timing: [
        hold: [ min: 10000, var: 7000 ],
        pause: [ min: 200, var: 200]
    ],

```
---

### ocean




 




