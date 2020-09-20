# -*- coding: utf-8 -*-
"""
Spyder Editor

This is a temporary script file.
"""

from sklearn import datasets
from sklearn.model_selection import train_test_split
from tensorflow import keras
from tensorflow.keras.layers import Conv2D, MaxPooling2D


digits=datasets.load_digits()
X = digits.data
y = digits.target
train_X, test_X, train_y, test_y = train_test_split(X, y, test_size=0.2, random_state=33)

class_names = ['0', '1', '2', '3', '4',
               '5', '6', '7', '8', '9']

train_X=train_X.reshape(train_X.shape[0],8,8,1)
test_X=test_X.reshape(test_X.shape[0],8,8,1)

train_X=train_X/16.0
test_X=test_X/16.0

model=keras.Sequential()
model.add(Conv2D(64, kernel_size=3, activation='relu', input_shape=(8,8,1), padding='same'))
model.add(MaxPooling2D(pool_size=(2,2)))
model.add(keras.layers.Flatten(input_shape=(4, 4)))
model.add(keras.layers.Dense(10, activation="softmax"))

model.compile(optimizer="adam", loss="sparse_categorical_crossentropy", metrics=["accuracy"])

model.save("conv_digits.h5")
model=keras.models.load_model("conv_digits.h5")




'''
for i in range(10):
    plt.grid(False)
    plt.gray()
    plt.imshow(test_X[i].reshape(8,8))
    plt.title("prediction: "+class_names[np.argmax(prediction[i])]+ " target: "+str(test_y[i]))
    plt.show()
'''
