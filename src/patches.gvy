import midi.*

first=0
if (args) first=args[0] as int


fixed=true

def msPatches() {
    def gervil=new Player('gervil')
    Random rnd=new Random(new Date().getTime())
    int note=60
    long t=1000
    try {
        gervil.open()
        Thread.sleep(200)
        
        [*first..127].each {
            print "$it >> "
            gervil.showInstrument(it)

            if (!fixed) note=rnd.nextInt(36)+36

            gervil.patch(1,it)
            // gervil.loadInstrument(it) // no apparent effect
            gervil.noteOn(1,note,96)
            if (!fixed) t=rnd.nextInt(1000)+200
            Thread.sleep(t)
            gervil.noteOff(1,note,0)
        }
    }
    finally {
        gervil.close() 
    }
}
msPatches()

def playBoth() {
    def motu=new Player('828')
    def msft=new Player('microsoft gs')

    try {
        motu.open()
        msft.open()

        motu.noteOn(1, 60, 64)
        Thread.sleep(2000)
        motu.noteOff(1, 60, 0)

        msft.control(1,7,127)
        msft.noteOn(1, 72, 127)
        Thread.sleep(2000)
        msft.noteOff(1, 72, 0)

    }
    finally {
        motu.close()
        msft.close()
    }
}
//playBoth()



