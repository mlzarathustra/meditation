import static midi.Scale.getOvertone
import static midi.Scale.makeScale

[
    title: 'strings',
    channel: 1,
    engine: 'intervals',
    pitches: makeScale('Eb', 'c2','eb5', overtone),
    interval: 5,
    maxLeap: 1,
    noRepeats: true,

    patch: 56,
    bankMSB: 2,
    velocity: 48,
    timing: [
        hold: [ min: 2000, var: 2 ],
        pause: [ min: 0, var: 30]
    ],
    breathe: [
        freq: [ min: 20, var: 100],
        pause: [ min: 2000, var: 7000 ]
    ]


]
