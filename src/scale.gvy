import static midi.MlzMidi.*
import midi.Player

int patch = 14
int chan=1
if (args) patch=args[0] as int


def gervil=new Player('gervil')
gervil.open()
Thread.sleep(200)
gervil.patch(chan,patch)
Thread.sleep(200)

[*toMidiNum('c3')..toMidiNum('c5')].each { note->
    print "$note="+midiNumToStr(note)+' '
    gervil.noteOn(chan,note,96)
    Thread.sleep(1000)
    gervil.noteOff(chan,note,0)
}
