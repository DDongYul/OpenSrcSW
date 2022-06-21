import csv
import numpy as np
import matplotlib.pyplot as plt
from numpy import *

xdata = np.linspace(-np.pi,np.pi,100)       #구간을 개수만큼 나눔
ydata = []
for i in xdata:
    ydata.append(np.sin(i))
plt.plot(xdata,ydata)       #선그래프
plt.show()
# plt.bar(xdata,ydata)        #막대그래프(세로)
#
# plt.barh(xdata,ydata)       #막대그래프(가로)
#
# plt.hist([1,1,2,3,4,5,6,7,8,10]) #히스토그램
#
# plt.pie([10,20,30])         #원형
#
# plt.scatter(xdata,ydata)        #w점
# plt.show()

# print(np.arange(1,10,2))
# print(np.random.choice(10,5))
#
# print(np.random.choice(np.arange(1,46),5))

a = np.array([[1,1,1],[1,1,1]]) #2*3
b = np.array([[1,1],[1,1],[1,1]]) #3*2
c= np.dot(a,b)
print(c)