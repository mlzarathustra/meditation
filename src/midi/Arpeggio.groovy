package midi

/*
    So far, the only difference between
    arpeggio and scale are the notes in it

*/

class Arpeggio extends Span {

    static def major = [0,4,7]
    static def minor = [0,3,7]
    static def augmented = [0,4,8]
    static def diminished = [0,3,6]

    static def major7 = [0,4,7,11]
    static def minor7 = [0,3,7,10]

    static def halfDim = [0,3,6,10]
    static def fullDim = [0,3,6,9]

    static def minorMajor7 = [0,3,7,11]

    static def oneFive = [0,2,7,11]

    static def makeArpeggio(r,l,h,chord) { 
        makeSpan(r,l,h,chord)
    }
}