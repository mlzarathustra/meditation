import midi.*
import static midi.MlzMidi.*

//patches = [*40..44,*48..54, *75..77,82,*89..95,97,119,122]
patches = [*49..54,*75..76,*88..89,*92..94,101]
lowPatches = [48,89,95,97,77]

// the pause between the commencement of each track
rndPause=7000

gamma = [
    [
        title: 'sub',
        channel: 0..3,
        pitches: toMidiNumList('c0 c1'),
        patches: lowPatches,
        timing: [
            hold: [ min: 10000, var: 7000 ],
            pause: [ min: 200, var: 7000]
        ]
    ],
    [
        title: 'low',
        channel: 4..7,
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
        pitches: toMidiNumList('c1 c2 g1 g2 c2 d2 b2 c3'),
        patches: patches,
        timing: [
            hold: [ min: 10000, var: 7000 ],
            pause: [ min: 200, var: 7000]
        ]
    ]

]



gervil=new Player('gervil') // the microsoft GS synth
//gervil=new Player('828')
gervil.open()

rnd=new Random(new Date().getTime())


boolean stop=false 

def playRandom = { c, g ->
    int chan=c
    List pitchSet = (g.pitches instanceof String) ? 
            toMidiNumList(g.pitches) :
            g.pitches

    List patchSet = g.patches
    def hold = g.timing.hold
    def pause = g.timing.pause

    int note=64
    long t=1000
    Thread.sleep(200)
    
    for(;;) {
        it = patchSet[rnd.nextInt(patchSet.size())]
        print "$g.title [${c+1}] >> "

        note=pitchSet[rnd.nextInt(pitchSet.size())]
        print "{${midiNumToStr(note)}} "
        gervil.showInstrument(it)

        gervil.patch(chan,it)
        Thread.sleep(10)

        gervil.noteOn(chan,note,(96-note/2) as int)
        t=rnd.nextInt(hold.var)+hold.min
        Thread.sleep(t)

        gervil.noteOff(chan,note,0)
        if (stop) return
        Thread.sleep(rnd.nextInt(pause.var)+pause.min)
    }
}

threads=[]

Thread.start {
    gamma.each { g -> 
        def c
        if (g.channel instanceof IntRange) c=[*g.channel] 
        else if (g.channel instanceof List) c=g.channel
        else if (g.channel instanceof Integer) c=[g.channel]

        g.channel.each { i->
            if (stop) return
            threads << Thread.start { playRandom(i, g) }
            Thread.sleep(rnd.nextInt(rndPause))
        }
    }
    if (!stop) println '>> ALL THREADS STARTED. <<'
}

threads << Thread.start { // ocean effect
    spread=10
    density=5
    gapMin=500
    gapVar=500
    velVar=26
    chan=8

    gervil.patch(chan,122)
    Thread.sleep(200)

    idx=0
    note = toMidiNum('c-1')
    for (;;) {
        if (stop) break

        int off = (idx+spread-density)%spread
        //println "$idx on  $off off"
        gervil.noteOn(chan,note+idx,70+rnd.nextInt(velVar))
        gervil.noteOff(chan,note+off,0)
        Thread.sleep(gapMin+rnd.nextInt(gapVar))

        idx = ++idx % spread
        //System.console().readLine()
    }

    // todo - send note-offs to all 
}




println 'press Enter to stop'
System.console().readLine()
println '>> STOP REQUESTED [wait for threads to close] <<'
stop=true
threads.each { it.join() }

gervil.close()
