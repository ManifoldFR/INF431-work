set xlabel "no of Threads"
set ylabel "Throughput (ops/s)"
plot "scalability.data" using 1:2 title 'coarse' with linespoints,       \
     "scalability.data" using 1:3 title 'lock-coupling' with linespoints, \
     "scalability.data" using 1:4 title 'optimistic' with linespoints,   \
     "scalability.data" using 1:5 title 'lazy' with linespoints,   \
     "scalability.data" using 1:6 title 'versioned' with linespoints,   \
     "scalability.data" using 1:7 title 'lock-free' with linespoints
pause -1
