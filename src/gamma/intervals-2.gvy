import static midi.Scale.getOvertone
import static midi.Scale.makeScale

[
    [
        title: 'strings',
        channel: 1,
        engine: 'intervals',
        pitches: makeScale('Eb', 'Eb1','c3', overtone),
        interval: 10,
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
            title: 'high strings',
            channel: 1,
            engine: 'intervals',
            pitches: makeScale('Eb', 'c3','eb5', overtone),
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
]

