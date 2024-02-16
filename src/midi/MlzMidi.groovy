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
        def m = note =~ /([a-gA-G])([#bx]?)(-?[0-8])?/
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

    /*
       ***************************************************************************
       *
       * From ONDES

     */
//    public static String[][] noteNames= {
//        {"C", "C#","D","D#","E","F","F#","G","G#","A","A#","B"},
//        {"C","Db","D","Eb","E","F","Gb","G","Ab","A","Bb","B"}
//    };
//
//    public static String midiNumToStr(int n) {
//        return midiNumToStr(n,false);
//    }
//    public static String midiNumToStr(int n, boolean isFlat) {
//        int octave = n/12;
//        return  noteNames[isFlat?1:0][n%12]+(octave-2);
//    }



    static String toString(MidiMessage msg) {
        String status="unknown";
        int s=msg.getStatus()>>4;
        switch (s) {
            case 0x8: status = "Note OFF"; break;
            case 0x9: status = "Note ON"; break;
            case 0xa: status = "Aftertouch"; break;
            case 0xb: status = "Controller"; break;
            case 0xc: status = "Program Change"; break;
            case 0xd: status = "Channel Pressure"; break;
            case 0xe: status = "Pitch Bend"; break;
            case 0xf: status = "System"; break;
        }
        StringBuilder sb=new StringBuilder(status);
        sb.append(String.format("%4s","["+(1+(msg.getStatus()&0xf)+"]")));
        sb.append(" - ");
        if (s<0xa) {
            sb.append(String.format("%4s ",midiNumToStr(msg.getMessage()[1])));
            sb.append(String.format(" vel=%3d ",msg.getMessage()[2]));
        }
        else {
            for (int i = 1; i < msg.getLength(); ++i) {
                sb.append(msg.getMessage()[i]);
                sb.append(" ");
            }
        }
        return sb.toString();
    }


    /*        *************************************************************************** */


    static boolean SHOW_TRACKS = false
    /**
        Assume midiFile is writable

     */
    static void save(Sequence s, File midiFile) {
        int[] types = MidiSystem.getMidiFileTypes(s)
        if (types.length == 0) {
            println "NO MIDI FILE TYPES AVAILABLE FOR SAVE!!"
            return
        }

        Track[] tracks = s.getTracks()
        for (int i = tracks.length - 1; i > 0; i--) {

            // println " Track[$i] : ${tracks[i].size() } events" // DEBUG

            if (tracks[i].size() < 2) s.deleteTrack(tracks[i])
            //  empty tracks still have one event in them.
        }

        if (SHOW_TRACKS) {
            for (int i = 0; i < tracks.length; ++i) {
                println "\nTrack $i -- "
                Track t = tracks[i]
                for (int j = 0; j < t.size(); ++j) {
                    MidiEvent evt = t.get(j)
                    println " $evt.tick : ${toString(evt.getMessage())}"
                }
            }
        }

        try {
            //   save .mid file
            MidiSystem.write(s, types[types.length - 1], midiFile);
            println "MIDI data written to $midiFile"
        }
        catch (Exception ex) { println "Error saving MIDI file: \n$ex" }
    }

}



