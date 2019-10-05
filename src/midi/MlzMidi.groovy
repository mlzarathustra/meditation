package midi

import javax.sound.midi.*


class MlzMidi {
    // looks for midi device with name containing id
    // that has a receiver 
    static def getReceiver(id) {
        //println 'getReceiver'
        def infoList=MidiSystem.getMidiDeviceInfo()
        id=id.toLowerCase()

        infoList.find {
            if ( !it.name.toLowerCase().contains(id) ) return false
            //println "$it.name  - $id"

            def midiDev=MidiSystem.getMidiDevice(it)
            midiDev.open()
            try { def recv=midiDev.getReceiver() }
            catch (Exception ex) {
                midiDev.close() 
                //println 'no receiver'
                return false
            }
            midiDev.close()
            return true
        }
    }

    static def toMidiNum(int note) { return note } // for overloading

    //  TODO -- handle double flat (bb) 
    //
    static def toMidiNum(String note) {
        def offsets=[9,11,0,2,4,5,7] // a b c d e f g
        note=note.trim()
        def m = note =~ /([a-gA-G])([#bx]?)(-?[0-7])?/
        if (m.matches()) {
            def m0=m[0]
            def noteName=m0[1]
            def accidental=m0[2]
            def octave=m0[3]

            def rs = offsets[
                ((noteName.toLowerCase().charAt(0) as char) - 
                    ('a' as char)) 
                ] + 
                (accidental=='#'?1:0) + 
                (accidental=='b'?-1:0) +
                (accidental=='x'?2:0) +
                (octave == null ? 0 : (octave as int) + 2) * 12
            return rs<128? rs :-1
        }
        else return -1
    }

    // notes may be separated by spaces or commas
    // use lower-case b for flat 
    // samples: g#3 c0 Bb-1
    static def toMidiNumList(String noteStr) {
        def noteAry=noteStr.split(/[ ,]+/).collect { toMidiNum(it) }
    }

    static def midiNumToStr(int n, boolean isFlat=false) {
        def noteNames=[
            ['C', 'C#', 'D', 'D#', 'E', 'F', 'F#', 'G', 'G#', 'A', 'A#', 'B'],
            ['C', 'Db', 'D', 'Eb', 'E', 'F', 'Gb', 'G', 'Ab', 'A', 'Bb', 'B']
        ]
        int octave = n/12
        return  noteNames[isFlat?1:0][n%12]+(octave-2)

    } 

    static def midiNumListToStr(List n, boolean isFlat=false) {
        n.collect { midiNumToStr(it,isFlat) }
    }
    


}



