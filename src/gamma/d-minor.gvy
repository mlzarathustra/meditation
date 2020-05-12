package gamma

[
    inherit: [
        passive: [
            timing: [
                hold: [ min: 10000, var: 7000 ],
                pause: [ min: 200, var: 200]
            ],
            bankMSB: 4

        ]
    ],
    gamma:
    [
        [
            title: 'chord',
            channel: 1..8,
            engine: 'multiPatch', 

            pitches: 'd0 d1 d2 a2 d3 e3 f3 a3 bb3 d4 f4',
            patches: [91,92]
        ],
        [
            title: '4ths',
            channel: 9,
            engine: 'multiPatch', 

            pitches: 'e2 a2 d3',
            patches: 89
        ],
        [
            title: 'bell',
            channel: 11,
            engine: 'multiPatch', 

            pitches: 'a4 e5 f5 a5 Bb5',
            patches: 112
        ],
        [
            title: '5ths',
            channel: 12,
            engine: 'multiPatch', 

            pitches: 'g2 d3 a3',
            patches: 5
        ],
        [
            title: 'bass',
            channel: 13,
            engine: 'multiPatch',
            pitches: 'd0 a0 Bb0 d1',
            patches: 35,
            timing: [
                hold: [ min: 10000, var: 7000 ],
                pause: [ min: 200, var: 5000]
            ]
        ],
        [
            title: 'sea',
            channel: 14,
            engine: 'ocean',

            spread: 10, 
            density: 5,
            gapMin: 500,
            gapVar: 500,
            velVar: 26,
        ]

    ]
]