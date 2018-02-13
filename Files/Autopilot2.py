#  Copyright 2016 The TensorFlow Authors. All Rights Reserved.
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#   http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
"""An Example of a DNNClassifier for the Iris dataset."""
from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import argparse
import tensorflow as tf
import serial
import time
import numpy as np
import keyboard

import sample_data

parser = argparse.ArgumentParser()
parser.add_argument('--batch_size', default=100, type=int, help='batch size')
parser.add_argument('--train_steps', default=1000, type=int,
                    help='number of training steps')

def main(argv):
    args = parser.parse_args(argv[1:])

    # Fetch the data
    (train_x, train_y), (test_x, test_y) = sample_data.load_data()

    # Feature columns describe how to use the input.
    my_feature_columns = []
    for key in train_x.keys():
        my_feature_columns.append(tf.feature_column.numeric_column(key=key))

    # Build 2 hidden layer DNN with 10, 10 units respectively.
    classifier = tf.estimator.DNNClassifier(
        feature_columns=my_feature_columns,
        # Two hidden layers of 10 nodes each.
        hidden_units=[10, 10],
        # The model must choose between 5 classes.
        n_classes=4)

    # Train the Model.
    classifier.train(
        input_fn=lambda:sample_data.train_input_fn(train_x, train_y,
                                                 args.batch_size),
        steps=args.train_steps)

    # Evaluate the model.
    eval_result = classifier.evaluate(
        input_fn=lambda:sample_data.eval_input_fn(test_x, test_y,
                                                args.batch_size))

    print('\nTest set accuracy: {accuracy:0.3f}\n'.format(**eval_result))

    # Generate predictions from the model
    # Opening serial port communcation for Arduino
    ser = serial.Serial(
        port='COM9',
        baudrate=115200,
        parity=serial.PARITY_NONE,
        stopbits=serial.STOPBITS_ONE,
        bytesize=serial.EIGHTBITS,
        timeout=1
    )

    print("Serial port is opened.. Waiting three seconds for initialization")
    time.sleep(3)
    
    expected = ['Forward', 'Backward', 'Right', 'Left']

    while (1):
        if keyboard.is_pressed('s'):
            break
        elif len(ser.readline().decode("utf-8").strip().split(",")) == 19:
            # Modified the input line so it doesn't contain the carspeed and the label "state"
            modifiedInputLine = ser.readline().decode("utf-8").strip().split(",")
            print(modifiedInputLine)
            del modifiedInputLine[-1]
            del modifiedInputLine[16]
        
            modifiedInputLine = np.array(modifiedInputLine)
            modifiedInputLine = modifiedInputLine.astype(float)

            predict_x = {
                'middleUltrasonic': [modifiedInputLine[0]],
                'leftUltrasonic': [modifiedInputLine[1]],
                'rightUltrasonic': [modifiedInputLine[2]],
                'backUltrasonic': [modifiedInputLine[3]],
                'L0': [modifiedInputLine[4]],
                'L1': [modifiedInputLine[5]],
                'L2': [modifiedInputLine[6]],
                'xAccel': [modifiedInputLine[7]],
                'yAccel': [modifiedInputLine[8]],
                'zAccel': [modifiedInputLine[9]],
                'xGyro': [modifiedInputLine[10]],
                'yGyro': [modifiedInputLine[11]],
                'zGyro': [modifiedInputLine[12]],
                'xMag': [modifiedInputLine[13]],
                'yMag': [modifiedInputLine[14]],
                'zMag': [modifiedInputLine[15]],
                'servoAngle': [modifiedInputLine[16]],
            }

             
            predictions = classifier.predict(input_fn=lambda:sample_data.eval_input_fn(predict_x,
                                                    labels=None,
                                                    batch_size=args.batch_size))


            prediction = ''

            for pred_dict, expec in zip(predictions, expected):

                class_id = pred_dict['class_ids'][0]

                prediction = sample_data.COMMANDS[class_id]

            print(prediction)

            # 0 is Stop, 1 is Forward, 2 is Backward, 3 is Right, 4 is Left
            if prediction == "Forward":
                ser.write(bytes(b'f'))
            elif prediction == "Backward":
                ser.write(bytes(b'b'))
            elif prediction == "Right":
                ser.write(bytes(b'r'))
            elif prediction == "Left":
                ser.write(bytes(b'l'))

            time.sleep(0.400)
            

    ser.write(bytes(b's'))
    ser.flush()
    ser.close()


if __name__ == '__main__':
    tf.logging.set_verbosity(tf.logging.INFO)
    tf.app.run(main)
