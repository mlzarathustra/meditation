
import midi.*
import static midi.MlzMidi.*


// define the two intervals, from 0
def maj=[[4,7],[3,8],[5,9]]  // root, 1st, 2nd inversion
def min=[[3,7],[4,9],[5,8]]
def odd=[[4,8],[3,6]] // aug, dim
def triads=[*maj,*min, *odd]

// [maj,min].each { 
//     it.each {
//         println midiNumListToStr(it,true)
//     }
// }

def getSeed() {
    long t=new Date().getTime()
    def lo=t%1000
    t /= 1000
    def hi=t%1000
    lo**6 + 3*hi**2
}

Random r=new Random(getSeed())
nextRnd = { c->
    def dir=r.nextInt(2)
    [*0..2].each { 
        if (dir) c[it]++
        else c[it]--
     }
}
nextUp = { c-> [*0..2].each {c[it]++ } }
nextDn = { c-> [*0..2].each {c[it]-- } }

shift = { c-> 
    def nxt=triads[r.nextInt(triads.size())]
    switch (r.nextInt(3)) {
        case 0: // bottom note in common
            c[1]=c[0]+nxt[0]
            c[2]=c[0]+nxt[1]
            break
        case 1: // middle note in common
            c[0]=c[1]-nxt[0]
            c[2]=c[0]+nxt[1]
            break
        case 2:  // top note in common 
            c[0]=c[2]-nxt[1]
            c[1]=c[0]+nxt[0]
            break
        default: println 'ERROR!'
    }
}

// \\ // \\ // \\ // \\ // \\ // \\ // \\ // \\ // \\ // \\ 

boolean stop=false

def freneticBells = {
    def chord = [0,4,7].collect { 64+it }  // E major
    println midiNumListToStr(chord)
    def chan=0


    def p=new Player('gervil') // M$GS
    p.open()
    p.patch(chan,9) // glock
    //p.patch(chan,24) // nylon guit

    Thread.sleep(100) // let the patch settle  :^)

    def vel=100
    try {
        for (;;) {

            shift(chord)
            [*1..(r.nextInt(24))].each {
                if (chord[0]<0 || chord[-1]>127) return
                chord.each { p.noteOn(chan, it, vel) }
                Thread.sleep(100)
                chord.each { p.noteOff(chan, it, 0) }
                nextUp(chord)
            }
            shift(chord)
            [*1..(r.nextInt(24))].each {
                if (chord[0]<0 || chord[-1]>127) return 
                chord.each { p.noteOn(chan, it, vel) }
                Thread.sleep(100)
                chord.each { p.noteOff(chan, it, 0) }
                nextDn(chord)
            }
            if (stop) return

        }
    }
    finally {
        p.close() 
    }
}

def slowShiftingChords = {
    def chord = [0,4,7].collect { 64+it }  // E major
    def chan=0

    def p=new Player('gervil') // M$GS
    p.open()
    p.patch(chan,50) // syn strings
    p.patch(chan,95) 

    p.patch(chan+1,11)


    for (;;) {
        chord.each { p.noteOn(chan, it, 80) }
        Thread.sleep(1000)
        chord.each { p.noteOn(chan+1,it,100); Thread.sleep(30) }
        Thread.sleep(2200)
        chord.each { p.noteOff(chan, it, 0); p.noteOff(chan+1,it,0) }
        
        shift(chord)
        if (stop) return

    }
}



def fastDblShiftingChords = {
    def chord = [0,4,7].collect { 64+it }  // E major
    def chan=0

    def p=new Player('gervil') // M$GS
    p.open()
    p.patch(chan,18) 

    p.patch(chan+1,21)


    for (;;) {
        chord.each { p.noteOn(chan, it, 96) }
        Thread.sleep(200)
        chord.each { p.noteOn(chan+1,it,100); Thread.sleep(30) }
        Thread.sleep(200)
        chord.each { p.noteOff(chan, it, 0); p.noteOff(chan+1,it,0) }
        
        shift(chord)
        if (stop) return
    }
}

def slowWindOrchChords = {
    def chord = [0,4,7].collect { 64+it }  // E major
    def chan=0

    def p=new Player('828') 
    //def g=new Player('gervil')

    p.open()
    p.patch(chan,74) // wind orch
    p.control(chan, 0, 2) // to bank 2
    Thread.sleep(200) 
    

    for (;;) {
        chord.each { p.noteOn(chan, it, 80) }
        Thread.sleep(3000)
        if (!r.nextInt(6)) Thread.sleep(r.nextInt(500))
        chord.each { p.noteOff(chan, it, 0); p.noteOff(chan+1,it,0) }
        if (stop) return

        old=[*chord]
        while (old == chord) {
            old=[*chord]
            shift(chord)
        }

    }
}


t=[]
//freneticBells()  // or guitar
//fastDblShiftingChords()
//slowShiftingChords()

t<<Thread.start { slowWindOrchChords() }
Thread.sleep(200)
t<<Thread.start { freneticBells() }



println 'press Enter to stop'
System.console().readLine()
stop=true
t.each { it.join() }




