
# coding: utf-8

# In[ ]:

# Importing libraries for creating dataframes and serial communication for Arduino
import numpy as np
import pandas as pd
from sklearn.preprocessing import scale
from sklearn.neural_network import MLPClassifier
from sklearn.model_selection import train_test_split
import serial
import time

print("Creating a trained model from the Sample.csv file...")

# Creating a dataframe from the Sample.csv and displaying every 20th data from the dataframe
sample_df = pd.read_csv('Sample.csv');


# In[ ]:

# Selected feature columns
feature_cols = list(sample_df.columns.values)
feature_cols.remove('state')

# Create a feature matrix to show data from these feature columns
X = sample_df[feature_cols]

# Create a target vector using label "state"
y = sample_df['state']

# scale/normalize data to help model converge faster and prevent feature bias (MLP sensitive to feature scaling)
# a feature with a broad range of values could skew results
X = scale(X, axis=0, with_mean=True, with_std=True, copy=True)

# split X and y into training and testing sets (40% goes to testing; 60% goes to training)
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=0)

# instantiate model using:
# a maximum of 1000 iterations (default = 200)
# an alpha of 0.5 (default = 0.001)
# and a random state of 42 (for reproducibility)
mlp = MLPClassifier(max_iter=1000, alpha = 0.5,hidden_layer_sizes = (len(feature_cols),), random_state=42)

# fit the model with the training set
mlp.fit(X_train, y_train)

print("Done...")


# In[ ]:

# Opening serial port communcation for Arduino
ser = serial.Serial(
    port='COM8',
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
        print(modifiedInputLine)
        del modifiedInputLine[-1]
        del modifiedInputLine[16]
        
        modifiedInputLine = np.array(modifiedInputLine)
        modifiedInputLine = modifiedInputLine.astype(float)
        print(modifiedInputLine)
        modifiedInputLine = modifiedInputLine.reshape(1,-1)
        
        y_prediction = mlp.predict(modifiedInputLine)
        
        prediction = str(y_prediction[0])
        print(prediction)

        # 0 is Forward, 1 is Backward, 2 is Right, 3 is Left
        if prediction == "0":
            ser.write(bytes(b'f'))
        elif prediction == "1":
            ser.write(bytes(b'b'))
        elif prediction == "2":
            ser.write(bytes(b'r'))
        elif prediction == "3":
            ser.write(bytes(b'l'))

        time.sleep(0.250)
        
ser.write(bytes(b's'))
ser.flush()
ser.close()
