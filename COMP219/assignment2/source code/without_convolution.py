# -*- coding: utf-8 -*-
"""
Spyder Editor

This is a temporary script file.
"""

from sklearn import datasets
from sklearn.model_selection import train_test_split
from tensorflow import keras
import numpy as np



digits=datasets.load_digits()
X = digits.data
y = digits.target
train_X, test_X, train_y, test_y = train_test_split(X, y, test_size=0.2, random_state=33)

class_names = ['0', '1', '2', '3', '4',
               '5', '6', '7', '8', '9']

def print_analysis(predict_y, target_y):
    # this method is for printing accuracy and counting errors after predicting
    
    errors_index = []
    for i in range(0, len(predict_y)):
        if int(class_names[np.argmax(predict_y[i])]) != target_y[i]:
            errors_index.append(i)

    accuracy = 100 * (len(predict_y) - len(errors_index)) / len(predict_y)
    print('accuracy: %4lf' % accuracy + '%')
    print('There are ' + str(len(errors_index)) + ' errors in this set')

model=keras.Sequential()
model.add(keras.layers.Flatten(input_shape=(8, 8)))
model.add(keras.layers.Dense(64, activation="relu"))
model.add(keras.layers.Dense(10, activation="softmax"))

model.compile(optimizer="adam", loss="sparse_categorical_crossentropy", metrics=["accuracy"])

#trained_model=model.fit(train_X, train_y, epochs=5)

model.save("digits.h5")



#=======================cross validation=========================
'''
folds=5
folds_X = np.array_split(X, folds)
folds_y = np.array_split(y, folds)

for i in range(folds):
    train_X = np.vstack(folds_X[:i] + folds_X[i+1:])
    train_y = np.hstack(folds_y[:i] + folds_y[i+1:])
    validation_X=folds_X[i]
    validation_y=folds_y[i]
    
    train_X=train_X.reshape(train_X.shape[0],8,8)
    validation_X=validation_X.reshape(validation_X.shape[0],8,8)
    
    train_X=train_X/16.0
    test_X=test_X/16.0
    
    print (train_X.shape, train_y.shape, validation_X.shape, validation_y.shape)
    model.fit(train_X, train_y, epochs=5)
    
    predict_y = model.predict(validation_X)
    print(predict_y.shape)
    
    print_analysis(predict_y, validation_y)

'''
    
    
    
    
    
    
    