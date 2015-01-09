set terminal pngcairo  transparent font "arial,10" fontscale 1.0 size 600, 400
set output 'target/fishAndSharks.png'
set   autoscale
set xtic auto                          # set xtics automatically
set ytic auto                          # set ytics automatically
set title "Fish And Sharks Simulation"
set xlabel "Time (chronons)"
set ylabel "Number of Agents"
set autoscale x
set autoscale y
set key off
plot  "target/fishAndSharks.csv" with line