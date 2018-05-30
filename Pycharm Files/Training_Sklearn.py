# Importing machine learning algorithms and libraries for creating dataframes
#from sklearn.linear_model import LogisticRegression
#from sklearn.linear_model import LinearRegression
from sklearn.ensemble import RandomForestClassifier
from sklearn.tree import DecisionTreeClassifier
from sklearn import tree
import graphviz
from sklearn.neighbors import KNeighborsClassifier
from sklearn.neural_network import MLPClassifier
from sklearn.metrics import classification_report
from sklearn.svm import LinearSVC, SVC
from sklearn.datasets import make_classification
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
from sklearn.cross_validation import cross_val_score
from sklearn.preprocessing import scale
import numpy as np
import pandas as pd

dataset = pd.read_csv('Sample.csv')
# print(dataset)

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
# feature_cols.remove('previousState')
# feature_cols.remove('previousState2')
# feature_cols.remove('previousState3')
labels = ['0','1','2']

numberOfFeatures = len(feature_cols)
numberOfLabels = len(labels)

# Filter the dataframe to show data from these feature columns
X = dataset[feature_cols];
#print(X)

# Create a label vector on label "AHD" and displaying every 50th data from the vector
y = dataset['state'];
#print(y)


# Split the dataframe dataset. 70% of the data is training data and 30% is testing data using random_state 2
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=2)


# Instantiating DecisionTreeClassifier object
k = 5
knn = KNeighborsClassifier(n_neighbors=k) 

# Fit method is used for creating a trained model on the training sets for decisiontreeclassifier
knn.fit(X_train, y_train)

# Predict method is used for creating a predictive model using testing set
y_predict_knn = knn.predict(X_test)

# Accuracy of testing data on predictive model
knn_accuracy = accuracy_score(y_test, y_predict_knn)

# Print accuracy
print("Accuracy for KNeighbors Classifier: " + str(knn_accuracy))
# print(classification_report(y_test, y_predict_knn, target_names=labels))
# print()

# Instantiating DecisionTreeClassifier object
my_DecisionTree = DecisionTreeClassifier(random_state=2)

# Fit method is used for creating a trained model on the training sets for decisiontreeclassifier
my_DecisionTree.fit(X_train, y_train)

# Predict method is used for creating a predictive model using testing set
y_predict_dt = my_DecisionTree.predict(X_test)

# Accuracy of testing data on predictive model
dt_accuracy = accuracy_score(y_test, y_predict_dt)

# Print accuracy
print("Accuracy for Decision Tree Classifier: " + str(dt_accuracy))
# print(classification_report(y_test, y_predict_dt, target_names=labels))
dot_data = tree.export_graphviz(my_DecisionTree, out_file=None,
                         feature_names=feature_cols,
                         class_names=labels,
                         filled=True, rounded=True,
                         special_characters=True)
graph = graphviz.Source(dot_data)
graph.render("Sample")
# print()

# Instantiating RandomForestClassifier object
my_RandomForest = RandomForestClassifier(n_estimators = 19, bootstrap = True, random_state=2)

# Fit method is used for creating a trained model on the training sets for RandomForestClassifier
my_RandomForest.fit(X_train, y_train)

# Predict method is used for creating a prediction on testing data
y_predict_rf = my_RandomForest.predict(X_test)

# Accuracy of testing data on pr    edictive model
rf_accuracy = accuracy_score(y_test, y_predict_rf)

# Print accuracy
print("Accuracy for RandomForest Classfier: " + str(rf_accuracy))
#print(classification_report(y_test, y_predict_rf, target_names=labels))
#print()

# LinearSVC classifier
my_SVC = LinearSVC(random_state=0)

my_SVC.fit(X_train, y_train)

# Predict method is used for creating a prediction on testing data
y_predict_SVC = my_SVC.predict(X_test)

# Accuracy of testing data on predictive model
svc_accuracy = accuracy_score(y_test, y_predict_SVC)

# Print accuracy
print("Accuracy for SVC Classfier: " + str(svc_accuracy))
# print(classification_report(y_test, y_predict_SVC, target_names=labels))
# print()

# Different SVC classifier
my_SVC2 = SVC()

my_SVC2.fit(X_train, y_train)

# Predict method is used for creating a prediction on testing data
y_predict_SVC2 = my_SVC2.predict(X_test)

# Accuracy of testing data on predictive model
svc2_accuracy = accuracy_score(y_test, y_predict_SVC2)

# Print accuracy
print("Accuracy for SVC Classfier #2: " + str(svc2_accuracy))
# print(classification_report(y_test, y_predict_SVC2, target_names=labels))
# print()

# instantiate model using:
# a maximum of 1000 iterations (default = 200)
# an alpha of 1e-5 (default = 0.001)
# and a random state of 42 (for reproducibility)
my_MLP = MLPClassifier(max_iter=1000, alpha = 1e-5,hidden_layer_sizes = ((int((numberOfFeatures + numberOfLabels)/2)),),random_state=42)

# fit the model with the training set
my_MLP.fit(X_train, y_train)

# Predict method is used for creating a prediction on testing data
y_predict_mlp = my_MLP.predict(X_test)

# Accuracy of testing data on predictive model
mlp_accuracy = accuracy_score(y_test, y_predict_mlp)

# Print accuracy
print("Accuracy for MLP Classifier: " + str(mlp_accuracy))
# print(classification_report(y_test, y_predict_mlp, target_names=labels))
# print()

# Add 10-fold Cross Validation with Supervised Learning
# accuracy_list_knn = cross_val_score(knn, X, y, cv=10, scoring='accuracy')
# accuracy_list_dt = cross_val_score(my_DecisionTree, X, y, cv=10, scoring='accuracy')
# accuracy_list_rf = cross_val_score(my_RandomForest, X, y, cv=10, scoring='accuracy')
# accuracy_list_SVC = cross_val_score(my_SVC, X, y, cv=10, scoring='accuracy')
# accuracy_list_SVC2 = cross_val_score(my_SVC2, X, y, cv=10, scoring='accuracy')
# accuracy_list_mlp = cross_val_score(my_MLP, X, y, cv=10, scoring='accuracy')

# print("Cross Validation for KNN: " + str(accuracy_list_knn.mean()))
# print("Cross Validation for Decision Tree: " + str(accuracy_list_dt.mean()))
# print("Cross Validation for RandomForest: " + str(accuracy_list_rf.mean()))
# print("Cross Validation for LinearSVC: " + str(accuracy_list_SVC.mean()))
# print("Cross Validation for SVC #2: " + str(accuracy_list_SVC2.mean()))
# print("Cross Validation for MLP: " + str(accuracy_list_mlp.mean()))


