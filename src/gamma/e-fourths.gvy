package gamma

[
    [
        title: 'e fourth chords',
        engine: 'breathe',

        timing: [
            hold: [ min: 10_000, var: 4000 ],
            pause: [ min: 500, var: 500 ],
        ],

        fixedPause: 900,
        inherit: [
            passive:  [
                timing: [
                        hold: [ min: 1000, var: 1000 ],
                        pause: [ min: 500, var: 500 ]
                ]
            ]
        ],

        gamma: [
            [
                title: 'glory aahs',
                channel: 1,
                engine: 'multiNote',
                pitches: 'e2 a2 d3 g3  b2 a3 c#4',
                patch: 81,
                bankMSB: 2,
                noteCount: 3,
                velocity: 100,
            ],



        ]
    ],
    [
        title: 'e minor',
        engine: 'breathe',

        timing: [
            hold: [ min: 20_000, var: 4000 ],
            pause: [ min: 1000, var: 1000 ],
        ],

        fixedPause: 900,
        inherit: [
            passive:  [
                timing: [
                        hold: [ min: 1000, var: 1000 ],
                        pause: [ min: 500, var: 500 ]
                ]
            ]
        ],

        gamma: [
            [
                title: 'afterglow',
                channel: 2,
                engine: 'multiNote',
                pitches: 'e1 b1 e3 g3 b3 e4 g4',
                patch: 83,
                bankMSB: 1,
                noteCount: 4,
                velocity: 100,
            ],

        ]
    ],
    [
        title: 'bell',
        engine: 'breathe',

        timing: [
            hold: [ min: 6_000, var: 4000 ],
            pause: [ min: 10_000, var: 2000 ],
        ],

        fixedPause: 900,
        inherit: [
            passive:  [
                timing: [
                        hold: [ min: 2000, var: 5000 ],
                        pause: [ min: 500, var: 500 ]
                ]
            ]
        ],

        gamma: [
            [
                title: 'tacko bell',
                channel: 3,
                engine: 'multiNote',
                pitches: 'b2 f#3 b3 f#3 b4',
                patch: 19,
                bankMSB: 2,
                noteCount: 1,
                velocity: 54,
            ],

        ]
    ],    
    [
        title: 'outland',
        channel: 4,
        engine: 'multiNote',
        pitches: 'e0 c0 e1 b1 e2',
        patch: 87,
        bankMSB: 2,
        noteCount: 1,
        velocity: 100,
        timing: [
            hold: [ min: 3000, var: 1000 ],
            pause: [ min: 500, var: 3000 ]
        ]
    ],
    [
        title: 'med section',
        channel: 5,
        engine: 'multiNote',
        pitches: 'e0 b0 e1 b1',
        patch: 51,
        bankMSB: 1,
        noteCount: 2,
        velocity: 100,
        timing: [
            hold: [ min: 3000, var: 1000 ],
            pause: [ min: 200, var: 200 ]
        ]
    ],
    [
        title: 'sea',
        channel: 9,
        engine: 'ocean',

        spread: 10,
        density: 7,
        gapMin: 500,
        gapVar: 500,
        velVar: 40,
    ]

]