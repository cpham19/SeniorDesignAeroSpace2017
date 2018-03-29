from keras.models import load_model
import numpy
import time
import serial
import datetime

def convertToBooleans(array):
    for i in range(0, len(array)):
        element = int(array[i])
        if (element <= 30):
            array[i] = 1
        else:
            array[i] = 0

# Load trained model
model = load_model('mlp_model.h5')

# Opening serial port communcation for Arduino
ser = serial.Serial(
    port='COM5',
    baudrate=115200,
    parity=serial.PARITY_NONE,
    stopbits=serial.STOPBITS_ONE,
    bytesize=serial.EIGHTBITS,
    write_timeout=0.15,
    timeout=None
)

print("Serial port is opened.. Waiting three seconds for initialization")
time.sleep(3)

while (True):
    if (ser.inWaiting() > 0):  # if incoming bytes are waiting to be read from the serial input buffer
        # a = datetime.datetime.now()
        modifiedInputLine = ser.readline().decode("utf-8").strip().split(",")

        if len(modifiedInputLine) == 20:
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
            del modifiedInputLine[4]  # rightUltrasonic
            #del modifiedInputLine[3]  # upperRightUltrasonic
            #del modifiedInputLine[2]  # middleUltrasonic
            #del modifiedInputLine[1]  # upperLeftUltrasonic
            del modifiedInputLine[0]  # leftUltrasonic

            # modifiedInputLine contains Strings so we convert each element to int and then encode them to 0 or 1
            modifiedInputLine = convertToBooleans(modifiedInputLine)

            # Turn modifiedInputLine into a numpy.array so we can plug it into our predictive model
            modifiedInputLine = numpy.array(modifiedInputLine)
            modifiedInputLine = modifiedInputLine.astype(float)
            modifiedInputLine = modifiedInputLine.reshape(1,-1)
            print(modifiedInputLine)

            # Plug the car sensor reading to predictive model
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

            # b = datetime.datetime.now()
            # delta = b - a
            # print(int(delta.total_seconds() * 1000))  # time elasped in miliseconds

            # # Flush of file like objects. In this case, wait until all data is written.
            # ser.flush()
            # # Discard contents of input buffer
            # ser.flushInput()
            # # Discard contents of output buffer
            # ser.flushOutput()

            

ser.write(bytes(b's'))
ser.flush()
ser.close()

