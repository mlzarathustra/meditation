import static midi.Scale.*
import static midi.Arpeggio.*

//  the c# major gamma, transposed down, 
//  with some things added
[
    inherit: [
        passive: [
            timing: [
                hold: [ min: 10000, var: 7000 ],
                pause: [ min: 200, var: 7000]
            ]
        ],
        add: [
            transpose: -2
        ],

    ],    
    
    gamma: [
        [
            title: 'wind orch',
            channel: 1,
            engine: 'multiNote',
            pitches: 'c#0 g#0 c#1',
            patch: 74,
            // TODO - accept '2:74' for bank:patch
            //   ? how to handle MSB/LSB? (maybe '2.:74'?), as 
            //   2 is the MSB ... how about with a real bank # that 
            //   needs to be split at 7 bits? 

            bankMSB: 2, 
            noteCount: 2,
            velocity: 96 
            // TODO - velocity scaling       

        ],
        [
            title: 'flute',
            channel: 2,
            engine: 'multiNote',
            pitches: 'c#1 g#1 c#2 e#2',
            patch: 70,
            bankMSB: 1, 
            noteCount: 3,
            velocity: 96        
        ],
        [
            title: 'obersphere',
            channel: 3,
            engine: 'multiNote',
            pitches: 'c#2 e#2 g#2 b#2 d#3 ', 
            patch: 56,
            bankMSB: 1,
            velocity: 64,
            noteCount: 3
        ],
        [
            title: 'pf ooh',
            channel: 4,
            engine: 'multiNote',
            pitches: 'c#2 d#2 e#2 g#2 b#2 c#3 d#2 ', 
            patch: 4,
            bankMSB: 3,
            velocity: 96,
            noteCount: 3
        ],
        [
            title: 'ooh',
            channel: 5,
            engine: 'multiNote',
            pitches: 'g#2 b#2 c#3 g#3', 
            patch: 80,
            bankMSB: 1,
            velocity: 96,
            noteCount: 4
        ],
        [
            title: 'glass bells',
            channel: 6,
            engine: 'multiNote',
            pitches: makeScale('C#','g#2','c#6', major), 
            patch: 17,
            bankMSB: 0,
            velocity: 64,
            noteCount: 1,
            noRepeats: true
        ],
        [
            title: 'vibe',
            channel: 7,
            engine: 'multiNote',
            pitches: makeArpeggio('C#','c#3','c#6', oneFive), 
            patch: 13,
            bankMSB: 1,
            velocity: 64,
            noteCount: 1,
            noRepeats: true
        ],
        [
            title: 'clock tower',
            channel: 8,
            engine: 'multiNote',
            pitches: 'd#3 g#3 c#4 d#4 g#4', 
            patch: 19,
            bankMSB: 1,
            velocity: 64,
            noteCount: 1,
            noRepeats: true
        ],


    ]
]