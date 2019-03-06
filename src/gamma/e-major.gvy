
def slowTiming =  [
                hold: [ min: 10000, var: 1000 ],
                pause: [ min: 200, var: 1000]
            ]

[
    inherit: [
        passive:  [
            timing: [
                hold: [ min: 3000, var: 1000 ],
                pause: [ min: 200, var: 1000]
            ]
        ]
    ]
    ,
    gamma: [
        [
            title: 'soft organ',
            channel: 1,
            engine: 'multiNote',
            pitches: 'e2 f#2 a2 b2 d#3 f#3 g#3 a3 b3 c#3',
            patch: 26,
            bankMSB: 2, 
            noteCount: 5,
            velocity: 96 
        ]
        ,
        [
            title: 'grit',
            channel: 2,
            engine: 'multiNote',
            pitches: 'e3 b3 d#4 e4 f#4',
            patch: 22,
            bankMSB: 1, 
            noteCount: 2,
            velocity: 96 
        ]
        ,
        [
            title: 'vox',
            channel: 3,
            engine: 'multiNote',
            pitches: 'c#4 f#4 a4 b4',
            patch: 52,
            bankMSB: 4, 
            noteCount: 2,
            velocity: 96 
        ]
        ,
        [
            title: 'bass',
            channel: 4,
            engine: 'multiNote',
            pitches: 'b0 c#1 f#1 a1',
            patch: 40,
            bankMSB: 1, 
            noteCount: 1,
            velocity: 96,
            timing: slowTiming
 
        ]
        ,
        [
            title: 'tub bell',
            channel: 5,
            engine: 'multiNote',
            pitches: 'c#4 f#4 a4 b4',
            patch: 14,
            bankMSB: 4, 
            noteCount: 2,
            velocity: 96,
            timing: slowTiming
 

        ]



    ]


]