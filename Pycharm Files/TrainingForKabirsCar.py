from keras.models import Sequential
from keras.layers import Dense, Dropout
from keras.utils import to_categorical
import pandas as pd
import numpy
import math

# fix random seed for reproducibility
numpy.random.seed(10)

# load ARIA training data
dataset = pd.read_csv('Sample.csv')
numberOfRows = len(dataset.index)
feature_cols = list(dataset.columns.values)
#feature_cols.remove('leftUltrasonic')
#feature_cols.remove('upperLeftUltrasonic')
#feature_cols.remove('middleUltrasonic')
#feature_cols.remove('upperRightUltrasonic')
#feature_cols.remove('rightUltrasonic')
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
#feature_cols.remove('previousState')
print(feature_cols)
# 0 is Forward, 1 is Left, 2 is Right, 3 is Backward, 4 is Stop
labels = ['0','1','2']

def indicateNearObjects(x):
    if int(x) < 20:
        return 1
    else:
        return 0

# for feature in feature_cols:
#     dataset[feature] = dataset[feature].apply(indicateNearObjects)


# split into input (X) and output (Y) variables
X = numpy.array(dataset[feature_cols])
Y = numpy.array(dataset['state'])
Y = to_categorical(Y)
print(X)
print(Y)

# create model
model = Sequential()
# Input layer
model.add(Dense(len(feature_cols), input_dim=len(feature_cols), activation='sigmoid'))
# Hidden Lay er
model.add(Dense(math.ceil((len(feature_cols) + len(labels))/ 2), activation='sigmoid'))
# Output layer
model.add(Dense(len(labels), activation='softmax'))
# model.add(Dropout(0.2))

# Compile model
model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])

# Fit the model
history = model.fit(X, Y, epochs=500, batch_size=int(numberOfRows * 0.10), validation_split=0.10).history

print("Accuracy: " + str(numpy.mean(history['acc'])))
print("Cross Validation Accuracy: " + str(numpy.mean(history['val_acc'])))
#score = model.evaluate(X_test, Y_test, batch_size=int(len(testing_dataset.index * 0.10)))
#print("Accuracy using evaluate model: " + str(score))

# Save the model
model.save('mlp_model.h5')