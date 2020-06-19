# CPUScheduling
This program is a CPU Scheduling Algorithm made in Netbeans, using java.
When you run it, it will create 8 bands with a burst size of 200m and 4 with a burst size of 10m.
Once the bands are created, a random number of tasks with a random size will be created that each band has to process.
Each band will run through their tasks independently of each other with the help of multithreading.
Each band will complete the tasks given to them in a round robin manner.
Once each band has finished their tasks it will output a window with the id of the task, in this case the tasks were named numerically, the size of the task, as well as the total waiting time and turn around time it took to be processed. Lastly the average wait time and turn around time is displayed. The names of these windows is the burst size of the band so you know what size band was used to process these tasks.
Written to the output window is the real time it took for your computer to create each band and have those bands complete their tasks. 
After you are done looking over each information window you can either end the program by typing n or tell it to run again by typing anything else.
