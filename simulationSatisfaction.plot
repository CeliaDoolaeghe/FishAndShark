set terminal svg
set output 'target/simulationSatisfaction.svg'
set   autoscale
set xtic auto                          # set xtics automatically
set ytic auto                          # set ytics automatically
set title "Fish And Sharks Simulation"
set xlabel "Time (chronons)"
set ylabel "Satisfaction"
set autoscale x
set autoscale y
set key top left

plot  "target/simulationSatisfaction.csv" using 1:2 title 'Satisfaction' with line