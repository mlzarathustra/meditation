def faster = [
        hold: [ min: 222, var: 22 ],
        pause: [ min: 0, var: 0]
]

[
        title: 'breathing',
        engine: 'breathe',

        fixedPause: 900,
        inherit: [
                passive:  [
                        timing: [
                                hold: [ min: 450, var: 44 ],
                                pause: [ min: 0, var: 100]
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
                        timing: faster
                ],



        ]





]