from keras.models import Sequential
from keras.layers import Dense, Dropout
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
feature_cols.remove('leftUltrasonic')
feature_cols.remove('rightUltrasonic')
feature_cols.remove('backUltrasonic')
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
labels = ['0','1']

# split into input (X) and output (Y) variables
X = numpy.array(dataset[feature_cols])
Y = numpy.array(dataset['state'])
Y = to_categorical(Y)
print(Y)

# create model
model = Sequential()
model.add(Dense(len(feature_cols), input_dim=len(feature_cols), activation='sigmoid'))
model.add(Dense(math.ceil((len(feature_cols) + len(labels))/ 2), activation='sigmoid'))
model.add(Dense(len(labels), activation='softmax'))
# model.add(Dropout(0.2))

# Compile model
model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])

# Fit the model
history = model.fit(X, Y, epochs=500, batch_size=math.ceil(numberOfRows * 0.10), validation_split=0.10).history

print("Accuracy: " + str(numpy.mean(history['acc'])))
print("Cross Validation Accuracy: " + str(numpy.mean(history['val_acc'])))
print()

# Opening serial port communcation for Arduino
ser = serial.Serial(
    port='COM5',
    baudrate=115200,
    parity=serial.PARITY_NONE,
    stopbits=serial.STOPBITS_ONE,
    bytesize=serial.EIGHTBITS,
    timeout=1
)

print("Serial port is opened.. Waiting three seconds for initialization")
time.sleep(3)

while (1):
    if len(ser.readline().decode("utf-8").strip().split(",")) == 19:
        # Modified the input line so it doesn't contain the carspeed and the label "state"
        modifiedInputLine = ser.readline().decode("utf-8").strip().split(",")
        del modifiedInputLine[18] # state 18
        del modifiedInputLine[17] # servoAngle 17
        del modifiedInputLine[16] # carSpeed 16
        del modifiedInputLine[15] # zMag 15
        del modifiedInputLine[14] # yMag 14
        del modifiedInputLine[13] # xMag 13
        del modifiedInputLine[12] # zGyro 12
        del modifiedInputLine[11] # yGyro 11
        del modifiedInputLine[10] # xGyro 10
        del modifiedInputLine[9] # zAccel 9
        del modifiedInputLine[8] # yAccel 8
        del modifiedInputLine[7] # xAccel 7
        del modifiedInputLine[6] # L2 6
        del modifiedInputLine[5] # L1 5
        del modifiedInputLine[4] # L0 4
        del modifiedInputLine[3]  # backUltrasonic 3
        del modifiedInputLine[2]  # rightUltrasonic 2
        del modifiedInputLine[1]  # leftUltrasonic 1
        #del modifiedInputLine[0]  # middleUltrasonic 0

        print(modifiedInputLine)
        array = modifiedInputLine
        modifiedInputLine = numpy.array(modifiedInputLine)
        modifiedInputLine = modifiedInputLine.astype(float)
        modifiedInputLine = modifiedInputLine.reshape(1,-1)

        prediction = model.predict(modifiedInputLine)
        prediction = numpy.argmax(prediction[0])
        print(prediction)

        # 0 is Stop, 1 is Forward, 2 is Backward, 3 is Right, 4 is Left
        if prediction == 0:
            ser.write(bytes(b's'))
        elif prediction == 1:
            ser.write(bytes(b'f'))
        elif prediction == 2:
            ser.write(bytes(b'b'))
        elif prediction == 3:
            ser.write(bytes(b'r'))
        elif prediction == 4:
            ser.write(bytes(b'l'))

        time.sleep(0.100)
            


ser.write(bytes(b's'))
ser.flush()
ser.close()

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
