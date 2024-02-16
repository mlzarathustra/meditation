import static midi.Scale.*

[
    [
        title: 'low string 10ths',
        channel: 1,
        engine: 'intervals',
        pitches: makeScale('Eb', 'Eb0','c3', major),
        interval: 10,
        maxLeap: 3,
        repeat: 'hold',  // possible values: 'never', 'hold', 're-attack'

        patch: 56,
        bankMSB: 2,
        velocity: 48,
        timing: [
            hold: [ min: 2000, var: 2000 ],
            pause: [ min: 0, var: 0 ]
        ],
    ],
    [
            title: 'high strings 6ths',
            channel: 2,
            engine: 'intervals',
            pitches: makeScale('Eb', 'c3','eb5', major),
            interval: 6,
            maxLeap: 3,
            repeat: 'hold',  // possible values: 'never', 'hold', 're-attack'

            patch: 56,
            bankMSB: 2,
            velocity: 48,
            timing: [
                hold: [ min: 1000, var: 1000 ],
                pause: [ min: 0, var: 0 ]
            ],
    ],
    [
            title: 'vibes in 3rds',
            channel: 3,
            engine: 'intervals',
            pitches: makeScale('Eb', 'c3','eb5', major),
            interval: 3,
            maxLeap: 8,
            repeat: 'never',  // possible values: 'never', 'hold', 're-attack'

            patch: 13,
            bankMSB: 1,
            velocity: 33,
            timing: [
                hold: [ min: 300, var: 0 ],
                pause: [ min: 0, var: 0 ]
            ],
    ],
    [
            title: 'bells in 4ths',
            channel: 4,
            engine: 'intervals',
            pitches: makeScale('Eb', 'c3','eb6', major),
            interval: 4,
            maxLeap: 8,
            repeat: 'hold',  // possible values: 'never', 'hold', 're-attack'

            patch: 18,
            bankMSB: 1,
            velocity: 55,
            timing: [
                hold: [ min: 200, var: 0 ],
                pause: [ min: 0, var: 0 ]
            ],
    ],
]

