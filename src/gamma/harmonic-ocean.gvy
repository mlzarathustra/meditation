package gamma

def slower = [
    hold: [ min: 3000, var: 500 ],
    pause: [ min: 0, var: 300]
]

[

    [
        title: 'sea',
        channel: 4,
        engine: 'ocean',

        spread: 10,
        density: 5,
        gapMin: 500,
        gapVar: 500,
        velVar: 26,
    ],
    [
        title: 'les cloches',
        engine: 'breathe',

        timing: [
            hold: [ min: 1000, var: 2000 ],
            pause: [ min: 2000, var: 7000 ],
        ],

        fixedPause: 2000,

        gamma: [
            [
                title: 'clock tower',
                channel: 2,
                engine: 'multiNote',
                pitches: 'e3 a3 d4 g4 c5',
                patch: 19,
                bankMSB: 1,
                noteCount: 3,
                velocity: 96,
                timing:     [
                    hold: [ min: 700, var: 1000 ],
                    pause: [ min: 0, var: 0],
                ],
                noRepeats: true
            ],
        ]
    ],
    [
        title: 'flute',
        engine: 'breathe',

        timing: [
            hold: [ min: 5000, var: 10_000 ],
            pause: [ min: 500, var: 10_000 ],
        ],

        gamma: [
            [
                title: 'flute',
                channel: 3,
                engine: 'multiNote',
                pitches: 'g2 a2 d3 g3 a3 b3 d4 f#4',
                patch: 70,
                bankMSB: 2,
                noteCount: 2,
                velocity: 60,
                timing: [
                    hold: [ min: 4000, var: 1000 ],
                    pause: [ min: 0, var: 3000]
                ],
                noRepeats: true,
                transpose: -12


            ],
        ]
    ],

    [
        title: 'harmonics',
        engine: 'breathe',

        timing: [
            hold: [ min: 3000, var: 7000 ],
            pause: [ min: 500, var: 10_000 ],
        ],

        fixedPause: 900,
        inherit: [
            passive:  [
                timing: [
                    hold: [ min: 600, var: 100 ],
                    pause: [ min: 0, var: 0 ]
                ]
            ]
        ],

        gamma: [
            [
                title: 'guitar harmonics',
                channel: 1,
                engine: 'multiNote',
                pitches: 'g2 d2 g3 b3 d3 f#3 g4 a4 b4 c4 d4 f#4 g5',
                patch: 33,
                bankMSB: 2,
                noteCount: 3,
                velocity: 100,
            ],
        ]
    ],
    [

        title: 'tron',
        engine: 'breathe',

        timing: [
            hold: [ min: 10_000, var: 10_000 ],
            pause: [ min: 5000, var: 5000 ],
        ],

        gamma:
            [
                [
                    title: 'tron mood',
                    channel: 5,
                    engine: 'multiNote',
                    pitches: 'e2 g2 a2 c3 d3 g3 a3 c4 d4',
                    patch: 56,
                    bankMSB: 2,
                    noteCount: 3,
                    noRepeats: true,
                    velocity: 24,
                    timing: slower
                ],
            ],
    ],
    [
        title: 'wind orch',
        channel: 6,
        engine: 'multiNote',
        pitches: 'g0 d0 g1',
        patch: 74,
        bankMSB: 2,
        noteCount: 2,
        velocity: 64,
        timing: [
            hold: [ min: 10000, var: 7000 ],
            pause: [ min: 200, var: 7000]
        ]


    ]

]