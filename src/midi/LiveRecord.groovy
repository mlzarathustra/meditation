package midi

import javax.sound.midi.MidiEvent
import javax.sound.midi.MidiMessage
import javax.sound.midi.Sequence
import javax.sound.midi.ShortMessage
import javax.sound.midi.Track

import static javax.sound.midi.Sequence.*
import static midi.MlzMidi.*

class LiveRecord {

    /**
     * A sequence doesn't have a tempo - that's at the Sequencer layer.
     * 30 frames per second * 35 = 1050 ticks per second
     */
    float divisionType = SMPTE_30
    int resolution = 35
    double ticksPerSecond = 30 * resolution

    Sequence sequence
    Track[] tracks

    Long startTime = null

    LiveRecord() {
        // 16 tracks = 1 per channel
        sequence = new Sequence(divisionType, resolution, 16)
        tracks = sequence.getTracks()
    }

    void record(ShortMessage msg) {

        //  manually clone, to be sure the byte array gets copied
        byte[] msgData = msg.getMessage()
        byte[] msgDataCopy = Arrays.copyOf(msgData, msgData.length)
        MidiMessage copy = new ShortMessage()
        copy.setMessage(msgDataCopy, msgDataCopy.length)
        //

        if (startTime == null) startTime = System.nanoTime()
        long tick =
              ((double)((System.nanoTime() - startTime) / 1e9) // seconds
              * ticksPerSecond )

        println "\n  >>  record( $tick : ${ toString(copy) } )"

        tracks[copy.getChannel()].add(new MidiEvent(copy, tick))
    }

}
