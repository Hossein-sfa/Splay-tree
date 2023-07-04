from matplotlib import pyplot as plt


x = []
y = []
y2 = []

# reading dataset from file that is written by java program
with open("data.txt", "r") as file:
    lines = file.readlines()
    for line in lines:
        words = line.split()
        x.append(int(words[0]))
        y.append(int(words[1]))
        y2.append(int(words[2]))

# showing 2 plots in 1 figure
figure, axis = plt.subplots(2, 1)
axis[0].plot(x, y)
axis[0].set_title("Uniform")
axis[1].plot(x, y2)
axis[1].set_title("Normal")

plt.xlabel("Inputs")
plt.ylabel("Time (ms)")
plt.show()
