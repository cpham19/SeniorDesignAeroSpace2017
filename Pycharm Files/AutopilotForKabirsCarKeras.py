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
        # a = datetime.datetime.now()
        modifiedInputLine = ser.readline().decode("utf-8").strip().split(",")
        if len(modifiedInputLine) == 18:
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

            # modifiedInputLine contains Strings so we convert each element to int and then encode them to 0 or 1
            # modifiedInputLine = convertToBooleans(modifiedInputLine)

            # Turn modifiedInputLine into a numpy.array so we can plug it into our predictive model
            modifiedInputLine = numpy.array(modifiedInputLine)
            modifiedInputLine = modifiedInputLine.astype(float)
            modifiedInputLine = modifiedInputLine.reshape(1,-1)
            print(modifiedInputLine)

            # Plug the car sensor reading to predictive model
            prediction = model.predict(modifiedInputLine)
            prediction = numpy.argmax(prediction[0])

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

            # time.sleep(0.100)

            # b = datetime.datetime.now()
            # delta = b - a
            # print(int(delta.total_seconds() * 1000))  # time elasped in miliseconds


ser.write(bytes(b's'))
ser.flush()
ser.close()

