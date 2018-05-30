# Importing machine learning algorithms and libraries for creating dataframes
from sklearn.ensemble import RandomForestClassifier
from sklearn.tree import DecisionTreeClassifier
from sklearn.neural_network import MLPClassifier
from sklearn.neighbors import KNeighborsClassifier
from sklearn.model_selection import train_test_split
import numpy
import pandas as pd
import serial
import datetime
import time
from sklearn.preprocessing import scale

dataset = pd.read_csv('Sample.csv')
dataset.head()

# Feature columns that are numerical
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
feature_cols.remove('previousState')
feature_cols.remove('previousState2')
feature_cols.remove('previousState3')

# 0 is Forward, 1 is Left, 2 is Right, 3 is Backward, 4 is Stop
labels = ['0','1','2']

# Filter the dataframe to show data from these feature columns
X = dataset[feature_cols];
#print(X)

# Create a label vector on label "AHD" and displaying every 50th data from the vector
y = dataset['state'];
#print(y)

#Split the dataframe dataset. 70% of the data is training data and 30% is testing data using random_state 2
# X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=2)

# k = 5
# knn = KNeighborsClassifier(n_neighbors=k)
# knn.fit(X_train, y_train)

# my_DecisionTree = DecisionTreeClassifier(random_state=2)
# my_DecisionTree.fit(X_train, y_train)

# my_RandomForest = RandomForestClassifier(n_estimators = 19, bootstrap = True, random_state=2)
# my_RandomForest.fit(X_train, y_train)

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.1, random_state=42)
my_MLP = MLPClassifier(max_iter=500, alpha = 1e-5,hidden_layer_sizes = ((int((len(feature_cols)+ len(labels))/2)),),random_state=42)
my_MLP.fit(X_train, y_train)

# Opening serial port communcation for Arduino
ser = serial.Serial(
    port='COM5',
    baudrate=115200,
    parity=serial.PARITY_NONE,
    stopbits=serial.STOPBITS_ONE,
    bytesize=serial.EIGHTBITS,
    write_timeout=None,
    timeout=None
)

#Read size bytes from the serial port. If a timeout is set it may
#return less characters as requested. With no timeout it will block
#until the requested number of bytes is read.

print("Serial port is opened.. Waiting three seconds for initialization")
time.sleep(3)

while (True):
    if (ser.inWaiting() > 0):
        a = datetime.datetime.now()
        modifiedInputLine = ser.readline().decode("utf-8").strip().split(",")
        if len(modifiedInputLine) == 20:
            del modifiedInputLine[19]  # previousState3
            del modifiedInputLine[18]  # previousState2
            del modifiedInputLine[17]  # previousState
            del modifiedInputLine[16] # state
            del modifiedInputLine[15] # servoAngle
            del modifiedInputLine[14] # carSpeed
            del modifiedInputLine[13] # zMag
            del modifiedInputLine[12] # yMag
            del modifiedInputLine[11] # xMag
            del modifiedInputLine[10] # zGyro
            del modifiedInputLine[9] # yGyro
            del modifiedInputLine[8] # xGyro
            del modifiedInputLine[7] # zAccel
            del modifiedInputLine[6] # yAccel
            del modifiedInputLine[5] # xAccel
            #del modifiedInputLine[4]  # rightUltrasonic
            #del modifiedInputLine[3]  # upperRightUltrasonic
            #del modifiedInputLine[2]  # middleUltrasonic
            #del modifiedInputLine[1]  # upperLeftUltrasonic
            #del modifiedInputLine[0]  # leftUltrasonic

            # Turn modifiedInputLine into a numpy.array so we can plug it into our predictive model
            modifiedInputLine = numpy.array(modifiedInputLine)
            modifiedInputLine = modifiedInputLine.astype(float)
            modifiedInputLine = modifiedInputLine.reshape(1,-1)
            print(modifiedInputLine)

            # Plug the car sensor reading to predictive model
            # prediction = knn.predict(modifiedInputLine)
            # prediction = my_DecisionTree.predict(modifiedInputLine)
            # prediction = my_RandomForest.predict(modifiedInputLine)
            prediction = my_MLP.predict(modifiedInputLine)

            # 0 is Forward, 1 is Left, 2 is Right, 3 is Backward, 4 is Stop
            if prediction == 0:
                print("Forward")
                ser.write(bytes(b'f'))
            elif prediction == 1:
                print("Left")
                ser.write(bytes(b'l'))
            elif prediction == 2:
                print("Right")
                ser.write(bytes(b'r'))
            elif prediction == 3:
                print("Backward")
                ser.write(bytes(b'b'))
            elif prediction == 4:
                print("Stop")
                ser.write(bytes(b's'))

            # Flush of file like objects. In this case, wait until all data is written.
            ser.flush()
            # Discard contents of input buffer
            ser.flushInput()
            # Discard contents of output buffer
            ser.flushOutput()

            #time.sleep(0.100)

            # b = datetime.datetime.now()
            # delta = b - a
            # print(int(delta.total_seconds() * 1000))  # time elasped in miliseconds


ser.write(bytes(b's'))
ser.flush()
ser.close()