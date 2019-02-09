import midi.*

//patches = [*40..44,*48..54, *75..77,82,*89..95,97,119,122]
patches = [*48..54,*75..77,*89..85,*92..95,97]
lowPatches = [89,95,97]


low=[]
[0,7,12].each { low << 12+it; low << 24+it; 36+it }

mid = []
[0,4,7].each { mid << 48+it }

high= []
[0, 2, 4, 7, 11].each { high << 60+it; high << 72+it}

println ''+low + mid + high


//gervil=new Player('gervil') // the microsoft GS synth
gervil=new Player('828')
gervil.open()

rnd=new Random(new Date().getTime())

rndMin=10000
rndVar=7000
rndPause=7000

void playRandom(int chan, List pitchSet, List patchSet) {
    int note=64
    long t=1000
    try {
        Thread.sleep(200)
        
        for(;;) {
            it = patchSet[rnd.nextInt(patchSet.size())]
            print "$it >> "
            gervil.showInstrument(it)

            note=pitchSet[rnd.nextInt(pitchSet.size())]

            gervil.patch(chan,it)
            Thread.sleep(10)
            gervil.noteOn(chan,note,(96-note/2) as int)
            t=rnd.nextInt(rndMin)+rndVar
            Thread.sleep(t)
            gervil.noteOff(chan,note,0)
            Thread.sleep(20 + rnd.nextInt(rndPause))
        }
    }
    finally {
        //gervil.close() 
    }
}

threads=[]

for (int i=12; i<16; ++i) {
    threads<< Thread.start { playRandom(i, [12,24], lowPatches) } 
    Thread.sleep(rnd.nextInt(rndPause))
}
for (int i=0; i<5; ++i) {
    threads << Thread.start { playRandom(i, low, patches) } 
    Thread.sleep(rnd.nextInt(rndPause))
}
for (int i=5; i<9; ++i) {
    threads<< Thread.start { playRandom(i, mid, patches) } 
    Thread.sleep(rnd.nextInt(rndPause))
}

// channel 10 (9 here) is usually the drum channel
for (int i=10; i<12; ++i) { 
    threads<< Thread.start { playRandom(i, high, patches) } 
    Thread.sleep(rnd.nextInt(rndPause))
}

// TODO - exit gracefully 



