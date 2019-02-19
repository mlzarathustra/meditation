import midi.*
import static midi.MlzMidi.*


import static midi.BasicEngines.*

  //##################################################################\\ 

    //          GAMMAS

  // \\ // \\   // \\ // \\   // \\ // \\   // \\ // \\   // \\ // \\ 


//patches = [*40..44,*48..54, *75..77,82,*89..95,97,119,122]
patches = [*49..54,*75..76,*88..89,*92..94,101]
lowPatches = [48,89,95,97,77]

gamma = [
    [
        title: 'sub',
        channel: 0..3,
        engine: multiPatch, 

        pitches: toMidiNumList('c0 c1'),
        patches: lowPatches,
        timing: [
            hold: [ min: 10000, var: 7000 ],
            pause: [ min: 200, var: 7000]
        ]
    ],
    [
        title: 'sea',
        channel: 8,
        engine: ocean,

        spread: 10, 
        density: 5,
        gapMin: 500,
        gapVar: 500,
        velVar: 26,
    ],
    [
        title: 'low',
        channel: 4..7,
        engine: multiPatch,

        pitches: toMidiNumList('c1 c2 g1 g2 c2 c3'),
        patches: [89,95,101],
        timing: [
            hold: [ min: 10000, var: 7000 ],
            pause: [ min: 200, var: 7000]
        ]
    ],
    [
        title: 'bells',
        channel: 13,
        engine: multiPatch,

        pitches: toMidiNumList('d2 f2 g2 c3 d3 g3 c4 d4'),
        patches: [14],
        timing: [
            hold: [ min: 10000, var: 7000 ],
            pause: [ min: 10000, var: 2000 ]
        ]
    ],
    [
        title: 'mid',
        channel: 10..12,
        engine: multiPatch,

        pitches: toMidiNumList('c1 e1 g1 c2 g2 c2 c3'),
        patches: patches,
        timing: [
            hold: [ min: 10000, var: 7000 ],
            pause: [ min: 200, var: 7000]
        ]
    ],
    [
        title: 'high',
        channel: 14..15,
        engine: multiPatch,

        pitches: toMidiNumList('c1 c2 g1 g2 c2 d2 b2 c3'),
        patches: patches,
        timing: [
            hold: [ min: 10000, var: 7000 ],
            pause: [ min: 200, var: 7000]
        ]
    ]

]

  //##################################################################\\ 

    //          MAIN

  // \\ // \\   // \\ // \\   // \\ // \\   // \\ // \\   // \\ // \\ 


// gamma should be able to contain or specify player

//player=new Player('gervil') // the microsoft GS synth
player=new Player('828')
player.open()


// the pause between the commencement of each track
rndPause=7000

threads=[]

Thread.start {
    gamma.each { g -> 
        g.channel.each { c->
            if (stop) return
            threads << Thread.start { g.engine(c, g, player) }
            Thread.sleep(rnd.nextInt(rndPause))
        }
    }
    if (!stop) println '>> ALL THREADS STARTED. <<'
}

println 'press Enter to stop'
System.console().readLine()
println '>> STOP REQUESTED [wait for threads to close] <<'
//stop=true

BasicEngines.stop=true  // TODO - too much surfce area.

threads.each { it.join() }

player.close()
