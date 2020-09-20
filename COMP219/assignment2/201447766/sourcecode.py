# -*- coding: utf-8 -*-
"""
Created on Fri Nov 15 21:03:20 2019

@author: sgmjin2
"""

from sklearn import datasets
from sklearn.metrics import roc_curve, auc
from sklearn.neighbors import KNeighborsClassifier as sklearnKNN
from tensorflow import keras
import numpy as np
from collections import Counter
import matplotlib.pyplot as plt
from matplotlib.ticker import MultipleLocator

# load training and testing data
digits=datasets.load_digits()
X = digits.data
y = digits.target

# split training and testing data into 5 subsets for cross validation
folds=5
folds_X = np.array_split(X, folds)
folds_y = np.array_split(y, folds)

# class name for predicting the results of classification
class_names = ['0', '1', '2', '3', '4',
               '5', '6', '7', '8', '9']

# KNN model copied from Assignment 1
class myKNN():
    ''' KNN algorithm is implemented in this class '''

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
            most_frequent_label = Counter(labels).most_common(1)[0][0]  # find  the most frequent element in the k labels
            res.append(most_frequent_label)  # append the prediction together
    
        return res  # return the results



def model_info(choice):
    ''' provide basic information of chosen model '''
    if choice == 1:
        #load DNN without convolutional layer
        model=keras.models.load_model("DNN.h5")
        print('Information of DNN without convolutional layer')
        model.summary()
        
    elif choice == 2:
        # load DNN with convolutional layer
        model=keras.models.load_model("CNN.h5")
        print('Information of DNN with convolutional layer:')
        model.summary()
        
    elif choice == 3:
        # Load my KNN model
        print('Load my KNN model.\n')
        
    elif choice == 4:
        # Load sklearn KNN model
        print('Load sklearn KNN model.\n')
        

def match(predict_y):
    ''' select the most confident prediction as the final result '''
    prediction=[]
    for row in predict_y:
        most_confident=int(class_names[np.argmax(row)])
        prediction.append(most_confident)
    return prediction

    
def confusion_matrix(expected, predicted):
    ''' calculate the confusion matrix '''
    # initialize the confusion matrix with 0s
    cm=np.zeros((10,10))
    cm=cm.astype(int)
    # update the confusion matrix with each pair of expectation and prediction
    for i,j in zip(expected, predicted):
        cm[i][j] = cm[i][j]+1
    return cm

def plot_CM(class_names,CM):
    ''' plot the confusion matrix '''
    plt.style.use('default')
    fig=plt.figure()
    ax=fig.add_subplot(111)
    # set colorbar
    cax=ax.imshow(CM,cmap=plt.cm.Blues)
    fig.colorbar(cax)
    # set locations
    ax.xaxis.set_major_locator(MultipleLocator(1))
    ax.yaxis.set_major_locator(MultipleLocator(1))
    # add text to the matrix image
    for i in range(CM.shape[0]):
        for j in range(CM.shape[1]):
            ax.text(j,i,str(CM[i,j]),va='center',ha='center')
    # add class name to x and y axis
    ax.set_xticklabels(['']+class_names)
    ax.set_yticklabels(['']+class_names)
    # add title and label
    plt.title('Confusion matrix')
    plt.xlabel('Predicted')
    plt.ylabel('Expected')
    plt.show()


def trim_axs(axs, N):
    ''' helper to massage the axs list to have correct length '''
    axs = axs.flat
    for ax in axs[N:]:
        ax.remove()
    return axs[:N]


def plot_ROC(validation_y, predict_y):
    ''' plot the ROC curve for each class '''
    # use dictionary to store false, true postive rate and accuracy
    fpr = dict()
    tpr = dict()
    roc_auc = dict()
    
    for i in range(10):
        # convert 10 classes into positive and negative (one class vs. all other classes)
        temp=np.zeros(validation_y.shape)
        for j in range(len(validation_y)):
            if validation_y[j]==i:
                temp[j]=1
        # acquire fpr, tpr and auc for each class
        fpr[i], tpr[i], _ = roc_curve(temp, predict_y[:,i])
        roc_auc[i] = auc(fpr[i], tpr[i])
    
    # start to plot
    fig,axs = plt.subplots(5, 2, figsize=(8,20), constrained_layout=True)
    axs = trim_axs(axs, 10)
    
    for i in range(10):
        # plot each curve according to its fpr and tpr
        axs[i].plot(fpr[i], tpr[i])
        # plot the line y=x
        axs[i].plot([0, 1], [0, 1], 'k--')
        # add the label which indicate the accuracy
        axs[i].legend(['ROC curve (area = %0.3f)' % roc_auc[i]],loc='lower right')
        # set the limit of x and y axis
        axs[i].set_xlim([0.0, 1.0])
        axs[i].set_ylim([0.0, 1.05])
        # add title and label
        axs[i].set_xlabel('FPR')
        axs[i].set_ylabel('TPR')
        axs[i].set_title('ROC curve for digit '+str(i))

    plt.show()
      

    
def DNN_cross_validation(choice):
    ''' Cross validation for deep neural network '''
    # 'errors' is used for store all the errors detected in cross validation
    errors=0
    
    for i in range(folds):
        if choice==1:
            # choose not-trained deep neural network without convolutional layer as the model
            model=keras.models.load_model("DNN.h5")
        elif choice==2:
            # choose not-trained deep neural network with convolutional layer as the model
            model=keras.models.load_model("CNN.h5")
        
        # select one subset as test set, and the rest will be used as training set
        train_X = np.vstack(folds_X[:i] + folds_X[i+1:])
        train_y = np.hstack(folds_y[:i] + folds_y[i+1:])
        validation_X=folds_X[i]
        validation_y=folds_y[i]

        # normalization
        if choice==1:
            train_X=train_X.reshape(train_X.shape[0],8,8)
            validation_X=validation_X.reshape(validation_X.shape[0],8,8)
        elif choice==2:
            train_X=train_X.reshape(train_X.shape[0],8,8,1)
            validation_X=validation_X.reshape(validation_X.shape[0],8,8,1)
        
        train_X=train_X/16.0
        validation_X=validation_X/16.0
        
        # start training
        print('\n')
        print('Cross validation '+str(i+1))
        model.fit(train_X, train_y, epochs=5,verbose=2)
        print('\n')
        
        # record the prediction results
        predict_y = model.predict(validation_X)
        prediction = match(predict_y)
        
        # detect the errors
        errors_thisCross=count_errors(prediction, validation_y)
        print('Errors detected in cross validation '+str(i+1)+': '+str(errors_thisCross))
        errors=errors+errors_thisCross
    
    # calculate accuracy and print it
    print('Total errors count: '+str(errors))    
    print('Cross validation accuracy: %.2f' % (100.0*(1-errors/len(y)))+'%\n')
    return predict_y


def KNN_cross_validation(choice):
    ''' Cross validation for k-nearest neighbors '''
    # 'errors' is used for store all the errors detected in cross validation
    errors=0
    
    for i in range(folds):
        # normalization
        train_X = np.vstack(folds_X[:i] + folds_X[i+1:])
        train_y = np.hstack(folds_y[:i] + folds_y[i+1:])
        validation_X=folds_X[i]
        validation_y=folds_y[i]
        
        if choice==3:
            # load and train myKNN
            model=myKNN(5,train_X,train_y)
        elif choice==4:
            # load and train sklearn KNN
            model=sklearnKNN(5)
            model.fit(train_X,train_y)
        
        print('Cross validation '+str(i+1))
        # record the prediction results
        predict_y = model.predict(validation_X)
        # detect the errors
        errors_thisCross=count_errors(predict_y, validation_y)
        print('Errors detected in cross validation '+str(i+1)+': '+str(errors_thisCross)+'\n')
        errors=errors+errors_thisCross
    
    # calculate accuracy and print it
    print('Total errors count: '+str(errors))    
    print('Cross validation accuracy: %.2f' % (100.0*(1-errors/len(y)))+'%\n')
    return predict_y


def count_errors(prediction, validation_y):
    ''' helper for counting errors happen in one cross validation'''
    errors=0
    for i,j in zip(prediction, list(validation_y)):
        if i != j:
            errors=errors+1
    
    return errors
    

def choose_model():
    ''' simple user interface for testers'''
    print('\nFor tester to load model')
    while 1:
        print('Input 1,2,3 or 4 to choose the testing model')
        print('0. quit')
        print('1. deep neural network without convolutional layer')
        print('2. deep neural network with convolutional layer')
        print('3. my KNN model')
        print('4. sklearn KNN model')
        
        choice = input('input:')

        if choice == '0':
            break
        elif choice == '1':
            DNN_withoutconv()
        elif choice == '2':
            DNN_withconv()
        elif choice == '3':
            my_KNN()
        elif choice == '4':
            sklearn_KNN()
            
            
def DNN_withoutconv():
    ''' deep neural network without convolutional layer '''
    model_info(1);
    
    predict_y = DNN_cross_validation(1)
    
    validation_y=folds_y[4]
    CM=confusion_matrix(validation_y,match(predict_y))
    plot_CM(class_names,CM)
    
    plot_ROC(validation_y, predict_y)
    
    
def DNN_withconv():
    ''' deep neural network with convolutional layer '''
    model_info(2);
    
    predict_y = DNN_cross_validation(2)
    
    validation_y=folds_y[4]
    CM=confusion_matrix(validation_y,match(predict_y))
    plot_CM(class_names,CM)
    
    plot_ROC(validation_y, predict_y)
    

def my_KNN():
    ''' my KNN model '''
    model_info(3);
    
    predict_y = KNN_cross_validation(3)
    
    validation_y=folds_y[4]
    CM=confusion_matrix(validation_y,predict_y)
    plot_CM(class_names,CM)
    

def sklearn_KNN():
    ''' sklearn KNN model '''
    model_info(4);
    
    predict_y = KNN_cross_validation(4)
    
    validation_y=folds_y[4]
    CM=confusion_matrix(validation_y,predict_y)
    plot_CM(class_names,CM)    
    
    
def main():
    choose_model() 
    
if __name__ == '__main__':
    main()
    