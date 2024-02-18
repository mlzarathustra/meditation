
[
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

        [
            title: 'bali hai',
            channel: 2,
            engine: 'multiNote',
            pitches: 'Eb1 Bb1 Eb2 Eb3',
            patch: 55,
            bankMSB: 1,
            noteCount: 2,
            velocity: 96
        ],
        [
            title: 'afterglow',
            channel: 3,
            engine: 'multiNote',
            pitches: 'Eb0 Eb1 Bb1',
            patch: 83,
            bankMSB: 1,
            noteCount: 2,
            velocity: 64
        ],
        [
            title: 'glory aahs',
            channel: 4,
            engine: 'multiNote',
            pitches: 'g2 Bb2 eb3 f3 Bb3 d4 eb4',
            patch: 83,
            bankMSB: 1,
            noteCount: 4,
            velocity: 64
        ],
        [
            title: 'fretless lo',
            channel: 5,
            engine: 'multiNote',
            pitches: 'Eb0 bb0',
            patch: 44,
            bankMSB: 2,
            noteCount: 1,
            velocity: 64
        ],
        [
            title: 'sea',
            channel: 6,
            engine: 'ocean',

            spread: 10, 
            density: 5,
            gapMin: 500,
            gapVar: 500,

            velMin: 70,
            velVar: 26,
        ],        

    ] 
]