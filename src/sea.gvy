import static midi.MlzMidi.*
import midi.Player

int patch = 122 // ocean
if (args) patch=args[0] as int

def p=new Player('gervil')
p.open()
Thread.sleep(200)
p.patch(0,patch)
Thread.sleep(200)

Random rnd=new Random()

spread=10
density=5
gapMin=500
gapVar=500

idx=0

note = toMidiNum('c-1')

for (;;) {

    int off = (idx+spread-density)%spread
    //println "$idx on  $off off"
    p.noteOn(0,note+idx,96)
    p.noteOff(0,note+off,0)
    Thread.sleep(gapMin+rnd.nextInt(gapVar))

    idx = ++idx % spread
    //System.console().readLine()
}














/*

//  N.B. gervil >> 'p'
[*toMidiNum('c-1')..toMidiNum('f#-1')].each { note->
    print "$note="+midiNumToStr(note)+' '
    [*1..10].each { d ->
        gervil.noteOn(0,note+d,96)
        Thread.sleep(500)
    }
    Thread.sleep(700)
    [*1..10].each { d-> 
        gervil.noteOff(0,note+d,0)
    }
}
*/
