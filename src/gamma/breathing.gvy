
[
    title: 'breathing',
    engine: 'breathe',

    timing: [
        hold: [ min: 1000, var: 2000 ],
        pause: [ min: 500, var: 500 ],
    ],

    fixedPause: 900,
    inherit: [
        passive:  [
            // this is where the gamma below gets its timing.
            timing: [
                    hold: [ min: 222, var: 22 ],
                    pause: [ min: 0, var: 0 ]
            ]
        ]
    ],

    gamma: [
        [
            title: 'guitar harmonics',
            channel: 2,
            engine: 'multiNote',
            pitches: 'g2 d2 g3 b3 d3 f#3 g4 a4 b4 c4 d4 f#4 g5',
            patch: 33,
            bankMSB: 2,
            noteCount: 3,
            velocity: 100,
        ],



    ]
]