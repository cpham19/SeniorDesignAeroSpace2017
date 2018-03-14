from keras.models import Sequential
from keras.models import load_model
import numpy
import time
import serial

# Load trained model
model = load_model('mlp_model.h5')

# Opening serial port communcation for Arduino
ser = serial.Serial(
    port='COM5',
    baudrate=115200,
    parity=serial.PARITY_NONE,
    stopbits=serial.STOPBITS_ONE,
    bytesize=serial.EIGHTBITS,
    timeout=None
)

print("Serial port is opened.. Waiting three seconds for initialization")
time.sleep(3)

while (1):
    if len(ser.readline().decode("utf-8").strip().split(",")) == 20:
        # Modified the input line
        modifiedInputLine = ser.readline().decode("utf-8").strip().split(",")
        #print(modifiedInputLine)

        del modifiedInputLine[19] # state
        del modifiedInputLine[18] # servoAngle
        del modifiedInputLine[17] # carSpeed
        del modifiedInputLine[16] # zMag
        del modifiedInputLine[15] # yMag
        del modifiedInputLine[14] # xMag
        del modifiedInputLine[13] # zGyro
        del modifiedInputLine[12] # yGyro
        del modifiedInputLine[11] # xGyro
        del modifiedInputLine[10] # zAccel
        del modifiedInputLine[9] # yAccel
        del modifiedInputLine[8] # xAccel
        del modifiedInputLine[7]  # db1
        del modifiedInputLine[6]  # db2
        del modifiedInputLine[5]  # db3
        #del modifiedInputLine[4]  # rightUltrasonic
        #del modifiedInputLine[3]  # upperRightUltrasonic
        #del modifiedInputLine[2]  # middleUltrasonic
        #del modifiedInputLine[1]  # upperLeftUltrasonic
        #del modifiedInputLine[0]  # leftUltrasonic

        modifiedInputLine = numpy.array(modifiedInputLine)
        modifiedInputLine = modifiedInputLine.astype(float)
        modifiedInputLine = modifiedInputLine.reshape(1,-1)
        print(modifiedInputLine)

        prediction = model.predict(modifiedInputLine)
        prediction = numpy.argmax(prediction[0])
        print(prediction)

        # 0 is Forward, 1 is Left, 2 is Right, 3 is Backward, 4 is Stop
        if prediction == 0:
            ser.write(bytes(b'f'))
        elif prediction == 1:
            ser.write(bytes(b'l'))
        elif prediction == 2:
            ser.write(bytes(b'r'))
        elif prediction == 3:
            ser.write(bytes(b'b'))
        elif prediction == 4:
            ser.write(bytes(b's'))

        #time.sleep(0.200)
            


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
