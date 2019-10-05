package midi

/*
        initially, just a set of notes
        eventually, with characteristics like 
        consonance, dissonance, and leading tones
*/
class Scale extends Span {
    static def major = [0,2,4,5,7,9,11]
    static def minor = [0,2,3,5,7,8,10]
    static def naturalMinor = minor
    static def melodicMinor = [0,2,3,5,7,8,11]
    static def overtone = [0,2,4,6,7,9,10]
    static def chromatic = [*0..11]

    static def makeScale(r, l, h, scale) {
        makeSpan(r,l,h,scale)
    }

}