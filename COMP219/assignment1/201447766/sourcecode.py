# -*- coding: utf-8 -*-
"""
Created on Mon Oct 14 19:41:36 2019

@author: sgmjin2
"""

from sklearn import datasets
from collections import Counter
from sklearn.externals import joblib
from matplotlib import pyplot as plt
from datetime import datetime
import numpy as np

from sklearn.model_selection import train_test_split
from sklearn.neighbors import KNeighborsClassifier as KNN


def print_analysis(X, predict_y, target_y):
    # this method is for printing accuracy and counting errors after predicting
    errors_index = []
    for i in range(0, len(predict_y)):
        if (predict_y[i] != target_y[i]):
            errors_index.append(i)

    accuracy = 100 * (len(predict_y) - len(errors_index)) / len(predict_y)
    print('accuracy: %4lf' % accuracy + '%')
    print('There are ' + str(len(errors_index)) + ' errors in this set')


def for_test():
    # simple test UI
    print('\nFor test to query')
    while 1:
        print('Input 1 or 2 to choose the testing model')
        print('0. quit')
        print('1. my KNN model')
        print('2. sklearn KNN model')
        choice = input('input:')

        if choice == '0':
            break

        print('Input the index of test dataset')
        print('MIN:0   max:1796')
        index = input('input:')

        query(int(choice), int(index))


def query(choice, index):
    # for testers to query the model 
    digits = datasets.load_digits()
    test_sample = digits.data[index]
    if (choice == 1):
        # load my KNN model
        model = joblib.load('myKNN.joblib')
        predict_y = model.predict([test_sample])

    if (choice == 2):
        # load sklearn KNN model
        model = joblib.load('sklearnKNN.joblib')
        predict_y = model.predict([test_sample])

    plt.gray()
    plt.imshow(test_sample.reshape(8, 8))   #show the query image
    plt.title('predict=' + str(predict_y[0]) + ' target=' + str(digits.target[index])) # add prediction and target to the title 
    plt.show()


class myKNN():
    # KNN algorithm is implemented in this class

    def __init__(self, k=5, train_X=[], train_y=[]):
        # Initialize parameters
        self.k = k  # k determins how many nearest neighbours will vote for the final prediction
        self.train_X = train_X  # training dataset
        self.train_y = train_y  # traget values of training dataset

    def distance(self, sample, train_X):
        # Measure distances between test sample and training data
        sample = sample.reshape(1, -1)  # reshape the sample data into one row
        sample_mat = np.tile(sample, (
            train_X.shape[0], 1))  # append this one-row ‘sample’ to itself until its shape fits the shape of ‘train_X’
        diff = sample_mat - train_X  # calculate the difference between ‘sample_mat’ and  ‘train_X’
        distances = np.power(diff, 2).sum(axis=1)  # get the sum of each row

        return distances  # return the distances

    def get_klabels(self, k, train_y, distances):
        # Get labels of the k nearest neighbours
        labels = []  # initialize an empty array to record labels
        k_min_distances = np.sort(distances)[:k]  # sort the 'distances' to get k minimum distances

        # Find corresponding labels and append them together
        for i in range(0, distances.shape[0]):
            for j in k_min_distances:
                if (distances[i] == j):
                    label = train_y[i]
                    labels.append(label)

        return labels  # return the labels of the k nearest neighbours

    def predict(self, test_X):
        # Predict labels of test samples
        res = []  # initialize an empty array to record predicting labels

        for sample in test_X:
            distances = self.distance(sample, self.train_X)  # call method 'distance'
            labels = self.get_klabels(self.k, self.train_y, distances)  # call method 'get_klabels'
            most_frequent_label = Counter(labels).most_common(1)[0][
                0]  # find  the most frequent element in the k labels
            res.append(most_frequent_label)  # append the prediction together

        return res  # return the results


def main():
    print("Information of handwritten digits dataset\n"
          "=================   =====================\n" +
          "Classes                                10\n" +
          "Samples per class                    ~180\n" +
          "Samples total                        1797\n" +
          "Dimensionality                         64\n" +
          "Features            integers Min:0 Max:16\n" +
          "=================   =====================\n")

    print("Using 80% as train datasets and 20% as test datasets\n")

    digits = datasets.load_digits()  # load the data of handwritten digit
    X = digits.data
    y = digits.target
    train_X, test_X, train_y, test_y = train_test_split(X, y, test_size=0.2,
                                                        random_state=33)  # split raw data into 20% test samples and 80% training samples randomly

    myknn = myKNN(5, train_X, train_y)  # initialize myknn model
    joblib.dump(myknn, 'myKNN.joblib')  # save the model as 'myKNN.joblib'
    myknn2 = joblib.load('myKNN.joblib')  # load this model from file

    myknn_start_time = datetime.now()  # start the timer for myknn model
    # Output train model analysis
    print('========train model analysis of "myKNN"=======')
    predict_train_y = myknn2.predict(train_X)
    print_analysis(train_X, predict_train_y, train_y)

    # Output test model analysis
    print('========test model analysis of "myKNN"========')
    predict_test_y = myknn2.predict(test_X)
    print_analysis(test_X, predict_test_y, test_y)

    myknn_time_elapsed = datetime.now() - myknn_start_time  # calculate time cost for myknn model prediction
    print('Time elapsed (hh:mm:ss.ms) {}'.format(myknn_time_elapsed))

    print('\n')

    sklearnKNN = KNN(n_neighbors=5)  # initialize sklearnKNN model
    sklearnKNN.fit(train_X, train_y)  # input training samples and corresponding labels
    joblib.dump(sklearnKNN, 'sklearnKNN.joblib')  # save the model as 'sklearnKNN.joblib'
    sklearnKNN2 = joblib.load('sklearnKNN.joblib')  # load this model from file

    sklearnKNN_start_time = datetime.now()  # start the timer for sklearnKNN model
    # Output train model analysis
    print('========train model analysis of "sklernKNN"=======')
    predict_train_y = sklearnKNN2.predict(train_X)
    print_analysis(train_X, predict_train_y, train_y)

    # Output test model analysis
    print('========test model analysis of "sklernKNN"========')
    predict_test_y = sklearnKNN2.predict(test_X)
    print_analysis(test_X, predict_test_y, test_y)

    sklearnKNN_time_elapsed = datetime.now() - sklearnKNN_start_time
    print('Time elapsed (hh:mm:ss.ms) {}'.format(
        sklearnKNN_time_elapsed))  # calculate time cost for sklearnKNN model prediction

    for_test()


if __name__ == "__main__":
    main()
