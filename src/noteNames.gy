sharpNoteAry=['']
[0,2,4,5,7,9,11].eachWithIndex { n, idx ->
   sharpNoteAry[n]='CDEFGAB'[idx] 
   if (n<11) sharpNoteAry[n+1]=sharpNoteAry[n]+'#'
}

flatNoteAry=['']
[0,2,4,5,7,9,11].reverse().eachWithIndex { n, idx ->
   flatNoteAry[n]='CDEFGAB'.reverse()[idx] 
   if (n>0) flatNoteAry[n-1]=flatNoteAry[n]+'b'
}.reverse()

[sharpNoteAry,flatNoteAry].each {
    println it.collect { "'$it'" }
}