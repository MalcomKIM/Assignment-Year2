# -*- coding: utf-8 -*-
"""
Created on Tue Oct 22 16:20:18 2019

@author: sgmjin2
"""

from sklearn import datasets
from sklearn.externals import joblib
from matplotlib import pyplot as plt
#from sourcecode import myKNN

digits = datasets.load_digits()


def qurey(choice,index):
    test_sample=digits.data[index]
    if(choice==1):
        model=joblib.load('myKNN.joblib')
        predict_y=model.predict([test_sample])
    
    if(choice==2):
        model=joblib.load('sklearnKNN.joblib')
        predict_y=model.predict([test_sample])
        
    plt.gray()
    plt.imshow(test_sample.reshape(8,8))
    plt.title('predict='+str(predict_y[0])+' target='+str(digits.target[index]))
    plt.show()
    

def main():
    print("Information of handwritten digits dataset\n"
      "=================   =====================\n"+
      "Classes                                10\n"+
      "Samples per class                    ~180\n"+
      "Samples total                        1797\n"+
      "Dimensionality                         64\n"+
      "Features            integers Min:0 Max:16\n"+
      "=================   =====================\n")
    
    print('Input 1 or 2 to choose the testing model')
    print('1. my KNN model')
    print('2. sklearn KNN model')
    choice=input('input:')
    
    print('Input the index of test dataset')
    print('MIN:0   max:1796')
    index=input('input:')
    
    qurey(int(choice),int(index))


if __name__=="__main__":
    main()