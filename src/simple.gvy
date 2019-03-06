
import midi.Player

gervil=new Player('gervil') // the microsoft GS synth
gervil.open()

gervil.noteOn(1,60,93)
Thread.sleep(4000)
gervil.noteOff(1,60,0)

gervil.close() 
