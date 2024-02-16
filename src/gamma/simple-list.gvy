
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