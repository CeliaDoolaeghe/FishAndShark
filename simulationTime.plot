set terminal svg
set output 'target/simulationTime.svg'
set   autoscale
set xtic auto                          # set xtics automatically
set ytic auto                          # set ytics automatically
set title "Fish And Sharks Simulation"
set xlabel "Time (chronons)"
set ylabel "Population"
set autoscale x
set autoscale y

plot  "target/simulationTime.csv" using 1:2 title 'Fish' with line, \
      "" using 1:3 title 'Sharks' with line