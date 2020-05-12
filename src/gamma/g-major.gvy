package gamma

def faster = [
                hold: [ min: 222, var: 22 ],
                pause: [ min: 0, var: 0]
            ]

def slower = [
                hold: [ min: 3000, var: 500 ],
                pause: [ min: 0, var: 300]
            ]

def muchSlower = [
                hold: [ min: 4000, var: 1000 ],
                pause: [ min: 0, var: 300]
            ]



[
    fixedPause: 900,
    inherit: [
        passive:  [
            timing: [
                hold: [ min: 450, var: 44 ],
                pause: [ min: 0, var: 100]
            ]
        ]
    ]
    ,
    gamma: [
        [
            title: 'flute',
            channel: 4,
            engine: 'multiNote',
            pitches: 'g2 a2 d3 g3 a3 b3 d4 f#4',
            patch: 70,
            bankMSB: 2, 
            noteCount: 1,
            velocity: 60,
            timing: [
                hold: [ min: 4000, var: 1000 ],
                pause: [ min: 0, var: 3000]
            ],
            noRepeats: true,
            transpose: -12


        ],
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
        [   
            title: 'bass',
            channel: 3,
            engine: 'multiNote',
            pitches: 'c1 d1 e1 g1 g1 a1 c2 d2 g2',
            patch: 69,
            bankMSB: 2, 
            noteCount: 1,
            velocity: 108,
            timing: slower,
            noRepeats: true 
        ],     
        [   
            title: 'clock tower',
            channel: 1,
            engine: 'multiNote',
            pitches: 'e3 a3 d4 g4 c5',
            patch: 19,
            bankMSB: 1, 
            noteCount: 3,
            velocity: 96,
            timing: muchSlower,
            noRepeats: true 
        ],     
        [   
            title: 'tron mood',
            channel: 5,
            engine: 'multiNote',
            pitches: 'e2 g2 a2 c3 d3 g3 a3 c4 d4',
            patch: 56,
            bankMSB: 2, 
            noteCount: 5,
            velocity: 73,
            timing: slower 
        ],
        [   
            title: 'big sur (ocean)',
            channel: 6,
            engine: 'multiNote',
            pitches: 'd1 g1 c2 d2 e2 g2 c3',
            patch: 117,
            bankMSB: 3, 
            noteCount: 5,
            velocity: 115,
            timing: slower 
        ]     
    ]
]
