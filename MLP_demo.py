# import built-in cancer dataset (supervised learning/classification problem)
from sklearn.datasets import load_breast_cancer

# save "bunch" object containing cancer dataset and its attributes
cancer = load_breast_cancer()

# print cancer data
print(cancer.data)

# print the names of the 30 features
print(cancer.feature_names)

# print integers representing the type of each observation
print(cancer.target)

# print the encoding scheme for type: 0 = malignant, 1 = benign
print(cancer.target_names)

# store feature matrix in "X"
X = cancer.data

# store response vector in "y"
y = cancer.target

# split X and y into training and testing sets (40% goes to testing; 60% goes to training)
from sklearn.model_selection import train_test_split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.4, random_state=0)
print(X_train.shape)
print(X_test.shape)

# scale/normalize data to help model converge faster and prevent feature bias (MLP sensitive to feature scaling)
# a feature with a broad range of values could skew results
from sklearn.preprocessing import StandardScaler
scaler = StandardScaler()
X_train = scaler.fit(X_train).transform(X_train)
X_test = scaler.fit(X_test).transform(X_test)

print(X_train)
print(X_test)

# import multilayer perceptron model
from sklearn.neural_network import MLPClassifier

# instantiate model using:
# a maximum of 500 iterations (default = 200)
# an alpha of 0.05 (default = 0.001)
# 1 hidden layer with 10 nodes
# and a random state of 42 (for reproducibility)
mlp = MLPClassifier(max_iter=500, alpha=0.05, hidden_layer_sizes=10, random_state=42)
print(mlp)

# fit the model with the training set
mlp.fit(X_train, y_train)
# store predicted response values for the testing set
y_predictions = mlp.predict(X_test)

# compute testing accuracy and generate performance report for the multilayer perceptron model
# precision: ability not to label as positive a sample that is negative
# recall: ability to find positive samples
# f1-score: the harmonic mean of the precision and recall
# support: number of occurrences of each label
from sklearn import metrics
print("\nNeural Net Performance")
print("Accuracy: ", metrics.accuracy_score(y_test,y_predictions))
print(metrics.classification_report(y_test,y_predictions))

# import Matplotlib (scientific plotting library)
import matplotlib.pyplot as plt

# plot coefficient values for each of the nodes in the hidden layer
plt.figure(figsize=(15, 6))
plt.imshow(mlp.coefs_[0], cmap="GnBu")
plt.yticks(range(30), cancer.feature_names)
plt.xlabel("Columns in weight matrix")
plt.ylabel("Input feature")
plt.colorbar()
plt.show()