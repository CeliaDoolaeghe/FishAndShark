set terminal svg
set output 'target/fishAndSharks.svg'
set   autoscale
set xtic auto                          # set xtics automatically
set ytic auto                          # set ytics automatically
set title "Fish And Sharks Simulation"
set xlabel "Fish"
set ylabel "Sharks"
set autoscale x
set autoscale y
set key off

# start value for H
h1 = 117/360.0
# end value for H
h2 = 227/360.0
# creating the palette by specifying H,S,V
set palette defined ( 0 1 1 1, 1 0 0 0 )

plot  "target/simulationTime.csv" using 2:3:1 w line lc palette
