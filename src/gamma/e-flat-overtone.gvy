import static midi.Scale.*

def slower = [
                hold: [ min: 3000, var: 500 ],
                pause: [ min: 0, var: 300]
            ]


[
    fixedPause: 1000,
    gamma: [
        [
            title: 'pf morph',
            channel: 1,
            engine: 'intervals',
            pitches: makeScale('Eb', 'c2','eb5', overtone),
            patch: 3,
            bankMSB: 1, 
            interval: 3,
            velocity: 96,
            timing: [
                hold: [ min: 1000, var: 700 ],
                pause: [ min: 0, var: 100]
            ],
            noRepeats: true

        ],
        [
            title: 'harp',
            channel: 2,
            engine: 'intervals',
            pitches: makeScale('Eb', 'c2','eb5', overtone),
            interval: 6,
            maxLeap: 2,
            noRepeats: true,

            patch: 59,
            bankMSB: 1, 
            velocity: 96,
            timing: [
                hold: [ min: 3000, var: 700 ],
                pause: [ min: 0, var: 0]
            ],
            noRepeats: true

        ],
        [   
            title: 'tron mood',
            channel: 3,
            engine: 'multiNote',
            pitches: 'f1 c2 eb2 f2 a2',

            patch: 56,
            bankMSB: 2, 

            noteCount: 3,
            velocity: 70,
            timing: slower 
        ],       
        [
            title: 'pan flute',
            channel: 4,
            engine: 'intervals',
            pitches: makeScale('Eb', 'c2','eb5', overtone),
            interval: 5,
            maxLeap: 1,
            noRepeats: true,

            patch: 79,
            bankMSB: 2, 
            velocity: 48,
            timing: [
                hold: [ min: 2000, var: 2 ],
                pause: [ min: 0, var: 30]
            ],
            noRepeats: true,
            breathe: [
                freq: [ min: 20, var: 100],
                pause: [ min: 2000, var: 7000 ]
            ]


        ],
        [   
            title: 'bell',
            channel: 5,
            engine: 'multiNote',
            pitches: makeScale('Eb', 'c2','eb5', overtone),

            patch: 98,
            bankMSB: 1, 

            noteCount: 3,
            velocity: 70,
            timing: [
                hold: [ min: 500, var: 25 ],
                pause: [ min: 0, var: 0]
            ],
            noRepeats: true,
        ],       


    ]



]