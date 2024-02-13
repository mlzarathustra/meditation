package midi
import static midi.MlzMidi.toMidiNum

class Span {

    //  make a scale or arpeggio over a given range
    //  defined by the 'frets' in 'span'
    //
    static makeSpan(r,l,h,span) {
        def root = toMidiNum(r) - 12 
        def lowest = toMidiNum(l)
        def highest = toMidiNum(h)

        def rs=[]
        while (root + 12 < lowest) root += 12
        while (root <= highest) {
            rs << span.collect { degree -> root + degree }
                .findAll { note -> note >= lowest && note <= highest}
            root += 12
        }
        return rs.flatten()
    }

}
