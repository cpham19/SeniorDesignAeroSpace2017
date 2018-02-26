from keras.models import Sequential
from keras.layers import Dense, Dropout, LSTM
from keras.utils import to_categorical
import matplotlib.pyplot as plt
import seaborn as sns
import pandas as pd
from pylab import rcParams
import numpy
import time
import serial
import math

# fix random seed for reproducibility
numpy.random.seed(10)

# load ARIA training data
dataset = pd.read_csv('Sample.csv')
numberOfRows = len(dataset.index)
feature_cols = list(dataset.columns.values)
feature_cols.remove('L0')
feature_cols.remove('L1')
feature_cols.remove('L2')
feature_cols.remove('xAccel')
feature_cols.remove('yAccel')
feature_cols.remove('zAccel')
feature_cols.remove('xGyro')
feature_cols.remove('yGyro')
feature_cols.remove('zGyro')
feature_cols.remove('xMag')
feature_cols.remove('yMag')
feature_cols.remove('zMag')
feature_cols.remove('servoAngle')
feature_cols.remove('state')
labels = ['0', '1', '2', '3','4']

# split into input (X) and output (Y) variables
X = numpy.array(dataset[feature_cols])
X = numpy.reshape(X, X.shape + (1,))
Y = numpy.array(dataset['state'])
Y = to_categorical(Y)
print(Y)

# create model
model = Sequential()
## Input layer
model.add(LSTM(len(feature_cols), return_sequences=True, input_shape=X.shape[1:]))
## Hidden layer
model.add(LSTM(len(feature_cols), return_sequences=True))
## Output layer
model.add(Dense(len(labels), activation='softmax', input_shape=X.shape[1:]))
# model.add(Dropout(0.2))

# Compile model
model.compile(loss='categorical_crossentropy', optimizer='rmsprop', metrics=['accuracy'])

# Fit the model
history = model.fit(X, Y, epochs=500, batch_size=math.ceil(numberOfRows * 0.10), validation_split=0.10).history

print("Accuracy: " + str(numpy.mean(history['acc'])))
print("Cross Validation Accuracy: " + str(numpy.mean(history['val_acc'])))
print()

### we can now plot how our accuracy and loss change over training epochs
##sns.set(style='whitegrid', palette='muted', font_scale=1.5)
##rcParams['figure.figsize'] = 12, 5

##plt.plot(history['acc'])
##plt.plot(history['val_acc'])
##plt.title('ARIA model accuracy')
##plt.ylabel('accuracy')
##plt.xlabel('epoch')
##plt.legend(['train', 'test'], loc='upper left')
##plt.show()
##
##plt.plot(history['loss'])
##plt.plot(history['val_loss'])
##plt.title('ARIA model loss')
##plt.ylabel('loss')
##plt.xlabel('epoch')
##plt.legend(['train', 'test'], loc='upper right')
##plt.show()



