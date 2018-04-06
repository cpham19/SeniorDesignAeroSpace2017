from keras.models import load_model
import numpy

# Load trained model
model = load_model('mlp_model.h5')

string = "18,1,1,1,73,0"
modifiedInputLine = numpy.array(string.split(","))
modifiedInputLine = modifiedInputLine.astype(float)
modifiedInputLine = modifiedInputLine.reshape(1, -1)

prediction = model.predict(modifiedInputLine)
prediction = numpy.argmax(prediction[0])
print(prediction)