
[
    [
        title: 'wind orch',
        channel: 0,
        engine: 'multiNote',
        pitches: 'c#0 g#0 c#1',
        timing: [
            hold: [ min: 10000, var: 7000 ],
            pause: [ min: 200, var: 7000]
        ],
        patch: 74,
        // TODO - accept '2:74' for bank:patch
        //   ? how to handle MSB/LSB? (maybe '2.:74'?), as 
        //   2 is the MSB ... how about with a real bank # that 
        //   needs to be split at 7 bits? 

        bankMSB: 2, 
        noteCount: 2,
        velocity: 96 
        // TODO - velocity scaling       

    ]
    ,
    [
        title: 'flute',
        channel: 1,
        engine: 'multiNote',
        pitches: 'c#1 g#1 c#2 e#2',
        timing: [
            hold: [ min: 10000, var: 7000 ],
            pause: [ min: 200, var: 7000]
        ],
        patch: 70,
        bankMSB: 1, 
        noteCount: 3,
        velocity: 96        

    ]
    ,
    [
        title: 'obersphere',
        channel: 2,
        engine: 'multiNote',
        pitches: 'c#2 e#2 g#2 b#2 d#3 ', 
        timing: [
            hold: [ min: 10000, var: 7000 ],
            pause: [ min: 200, var: 7000]
        ],
        patch: 56,
        bankMSB: 1,
        velocity: 64,
        noteCount: 3
    ]
    ,
    [
        title: 'pf ooh',
        channel: 3,
        engine: 'multiNote',
        pitches: 'c#2 d#2 e#2 g#2 b#2 c#3 d#2 ', 
        timing: [
            hold: [ min: 10000, var: 7000 ],
            pause: [ min: 200, var: 7000]
        ],
        patch: 4,
        bankMSB: 3,
        velocity: 96,
        noteCount: 3


    ]




]