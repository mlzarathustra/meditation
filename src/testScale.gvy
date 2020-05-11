
import static midi.Scale.*
import static midi.MlzMidi.*


def scale = makeScale('c#','c#1', 'g#2', overtone)
println scale
println midiNumListToStr(scale, true)