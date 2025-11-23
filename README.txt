README.txt Write up will be an analysis of your experiments.
What kinds of file lead to lots of compressions.
- Files in the Calgary folder lead to lots of compressions, more than the others, with 43.239% compression.
- Things these files had in common were that they were text documents with common characters like spaces, vowels,
and punctuation. These had lots of compressions because the Huffman codes gave shorter codes to these common
characters. In summary, files with plain text compressed the most because the repetitivity of the letters from
the alphabet and repeated words and patterns.

What kind of files had little or no compression?
- .tif images had little to no compression.

What happens when you try and compress a huffman code file?
- The returned "compressed" file would actually be larger, not smaller.
- This is because the data is already compressed in a .hf file, and when you try to compress it again,
  the compressor writes an additional header to the old header and already-compressed bits.

  CALGARY
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/calgary/paper6.hf
  paper6 from	 38105 to	 25057 in	 0.045
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/calgary/paper1.hf
  paper1 from	 53161 to	 34371 in	 0.038
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/calgary/obj1.hf
  obj1 from	 21504 to	 17085 in	 0.018
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/calgary/geo.hf
  geo from	 102400 to	 73592 in	 0.069
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/calgary/progc.hf
  progc from	 39611 to	 26948 in	 0.025
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/calgary/progl.hf
  progl from	 71646 to	 44017 in	 0.044
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/calgary/book1.hf
  book1 from	 768771 to	 439409 in	 0.378
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/calgary/progp.hf
  progp from	 49379 to	 31248 in	 0.028
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/calgary/pic.hf
  pic from	 513216 to	 107586 in	 0.094
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/calgary/news.hf
  news from	 377109 to	 247428 in	 0.209
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/calgary/paper4.hf
  paper4 from	 13286 to	 8894 in	 0.010
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/calgary/bib.hf
  bib from	 111261 to	 73795 in	 0.065
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/calgary/paper3.hf
  paper3 from	 46526 to	 28309 in	 0.026
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/calgary/paper2.hf
  paper2 from	 82199 to	 48649 in	 0.043
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/calgary/paper5.hf
  paper5 from	 11954 to	 8465 in	 0.009
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/calgary/obj2.hf
  obj2 from	 246814 to	 195131 in	 0.171
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/calgary/trans.hf
  trans from	 93695 to	 66252 in	 0.058
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/calgary/book2.hf
  book2 from	 610856 to	 369335 in	 0.315
  --------
  total bytes read: 3251493
  total compressed bytes 1845571
  total percent compression 43.239
  compression time: 1.645


  WATERLOO
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/waterloo/sail.tif.hf
  sail.tif from	 1179784 to	 1085501 in	 0.960
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/waterloo/monarch.tif.hf
  monarch.tif from	 1179784 to	 1109973 in	 1.019
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/waterloo/clegg.tif.hf
  clegg.tif from	 2149096 to	 2034595 in	 1.755
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/waterloo/lena.tif.hf
  lena.tif from	 786568 to	 766146 in	 0.648
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/waterloo/serrano.tif.hf
  serrano.tif from	 1498414 to	 1127645 in	 0.960
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/waterloo/peppers.tif.hf
  peppers.tif from	 786568 to	 756968 in	 0.648
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/waterloo/tulips.tif.hf
  tulips.tif from	 1179784 to	 1135861 in	 0.967
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/waterloo/frymire.tif.hf
  frymire.tif from	 3706306 to	 2188593 in	 1.843
  --------
  total bytes read: 12466304
  total compressed bytes 10205282
  total percent compression 18.137
  compression time: 8.800


  BOOKSANDHTML
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/BooksAndHTML/melville.txt.hf
  melville.txt from	 82140 to	 47364 in	 0.072
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/BooksAndHTML/A7_Recursion.html.hf
  A7_Recursion.html from	 41163 to	 26189 in	 0.028
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/BooksAndHTML/jnglb10.txt.hf
  jnglb10.txt from	 292059 to	 168618 in	 0.151
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/BooksAndHTML/ThroughTheLookingGlass.txt.hf
  ThroughTheLookingGlass.txt from	 188199 to	 110293 in	 0.099
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/BooksAndHTML/syllabus.htm.hf
  syllabus.htm from	 33273 to	 21342 in	 0.021
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/BooksAndHTML/revDictionary.txt.hf
  revDictionary.txt from	 1130523 to	 611618 in	 0.537
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/BooksAndHTML/CiaFactBook2000.txt.hf
  CiaFactBook2000.txt from	 3497369 to	 2260664 in	 2.064
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/BooksAndHTML/kjv10.txt.hf
  kjv10.txt from	 4345020 to	 2489768 in	 2.164
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/BooksAndHTML/rawMovieGross.txt.hf
  rawMovieGross.txt from	 117272 to	 53833 in	 0.051
  compressing to: /Users/rachelyun/Desktop/cs314/HuffmanCoding/BooksAndHTML/quotes.htm.hf
  quotes.htm from	 61563 to	 38423 in	 0.036
  --------
  total bytes read: 9788581
  total compressed bytes 5828112
  total percent compression 40.460
  compression time: 5.223
