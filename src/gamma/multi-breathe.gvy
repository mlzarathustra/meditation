package gamma

import static midi.Scale.*
import static midi.Arpeggio.*
/*

                For Halloween 2023  :^o



 */
[
    [
        title: 'breathing',
        engine: 'breathe',

        timing: [
            hold: [ min: 2000, var: 4000 ],
            pause: [ min: 2000, var: 6000 ],
        ],

        fixedPause: 900,

        gamma: [
            [
                title: 'afterglow',
                channel: 1,
                engine: 'intervals',
                pitches: makeScale('E', 'b4','g6', melodicMinor),
                patch: 83,
                bankMSB: 1, 
                interval: 7,
                velocity: 77,
                timing: [
                    hold: [ min: 1000, var: 6000 ],
                    pause: [ min: 5, var: 200]
                ],
                noRepeats: true

            ],
        ]
    ],    
    [
        title: 'harmonics',
        engine: 'breathe',

        timing: [
            hold: [ min: 2000, var: 6000 ],
            pause: [ min: 1000, var: 4000 ],
        ],

        fixedPause: 900,
        inherit: [
            passive:  [
                timing: [
                        hold: [ min: 555, var: 22 ],
                        pause: [ min: 0, var: 0 ]
                ]
            ]
        ],

        gamma: [
            [
                title: 'guitar harmonics',
                channel: 2,
                engine: 'multiNote',
                pitches: 'e1 c1 g2 b2 f#3 d#3 c3 b3 e4 g4 b4 d#5 f#5 b5',
                patch: 33,
                bankMSB: 2,
                noteCount: 3,
                velocity: 100,
                noRepeats: true,
            ],
        ]
    ],
    [
        title: 'tron mood',
        engine: 'breathe',

        timing: [
            hold: [ min: 4000, var: 6000 ],
            pause: [ min: 2000, var: 4000 ],
        ],

        fixedPause: 900,

        gamma: [
            [   
                title: 'tron mood',
                channel: 3,
                engine: 'multiNote',
                pitches: makeArpeggio('e', 'e1', 'f#3', minorMajor7),

                patch: 56,
                bankMSB: 2, 

                noteCount: 3,
                velocity: 33,
                timing:  [
                    hold: [ min: 500, var: 8000 ],
                    pause: [ min: 0, var: 7000]
                ] 
            ],        
        ]
    ],

    [   
        title: 'full ranks',
        channel: 4,
        engine: 'multiNote',
        pitches: 'c0 e0 f#0 a0 b0',

        patch: 25,
        bankMSB: 3, 

        noteCount: 1,
        velocity: 70,
        noRepeats: true,
        timing: [
            hold: [ min: 1500, var: 8000 ],
            pause: [ min: 0, var: 7000]
        ] 
    ],

    [
        title: 'slow strings',
        engine: 'breathe',

        timing: [
            hold: [ min: 1000, var: 2000 ],
            pause: [ min: 5000, var: 21000 ],
        ],

        fixedPause: 900,

        gamma: [
            [
                title: 'slow strings',
                channel: 5,
                engine: 'multiNote',
                pitches: 'g3 b3 d4 g4 b4 d5 g5 ',
                patch: 49,
                bankMSB: 4,
                noteCount: 4,
                velocity: 100,
                noRepeats: true,
                timing: [
                        hold: [ min: 1000, var: 2500 ],
                        pause: [ min: 0, var: 2000 ]
                ]                
            ],
       
        ]
    ],

    [
        title: 'breathing',
        engine: 'breathe',

        timing: [
            hold: [ min: 1000, var: 2000 ],
            pause: [ min: 5000, var: 21000 ],
        ],

        fixedPause: 900,

        gamma: [
            [
                title: 'timpani',
                channel: 6,
                engine: 'multiNote',
                pitches: 'c0 c#0 d0 d#0 e0 f0 ',
                patch: 47,
                bankMSB: 4,
                noteCount: 3,
                velocity: 100,
                noRepeats: true,
                timing: [
                        hold: [ min: 100, var: 22 ],
                        pause: [ min: 0, var: 0 ]
                ]                
            ],
       
        ]
    ],
    [
        title: 'seashore',
        channel: 7,
        engine: 'multiNote',
        pitches: 'g#-1 a-1 c0 d#0 ',
        patch: 122,
        bankMSB: 4,
        noteCount: 3,
        velocity: 100,
        noRepeats: true,
        timing: [
                hold: [ min: 5000, var: 5000 ],
                pause: [ min: 0, var: 2000 ]
        ]                
    ],
    [
        title: 'tubular bell',
        channel: 8,
        engine: 'multiNote',
        pitches: 'f#2 b2 e3 g3 d#4 g4 ',
        patch: 14,
        bankMSB: 4,
        noteCount: 1,
        velocity: 110,
        noRepeats: true,
        timing: [
                hold: [ min: 7000, var: 0 ],
                pause: [ min: 10000, var: 10000 ]
        ]                
    ],


    // another gamma here  

]