
# coding: utf-8

# In[8]:


# Importing libraries for creating dataframes
import numpy as np
import pandas as pd

# Creating a dataframe from the Sample.csv and displaying every 20th data from the dataframe
sample_df = pd.read_csv('Sample.csv');
sample_df[0::20]


# In[11]:


# Selected feature columns
feature_cols = list(sample_df.columns.values)
feature_cols.remove('time')
feature_cols.remove('state')

# Create a feature matrix to show data from these feature columns
X = sample_df[feature_cols]

# Create a target vector using label "state"
y = sample_df['state']

X[0::20]


# In[13]:


# Import Preprocessing to scale our feature dataset
from sklearn.preprocessing import scale

# scale/normalize data to help model converge faster and prevent feature bias (MLP sensitive to feature scaling)
# a feature with a broad range of values could skew results
X = scale(X, axis=0, with_mean=True, with_std=True, copy=True)

# split X and y into training and testing sets (40% goes to testing; 60% goes to training)
from sklearn.model_selection import train_test_split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.4, random_state=0)
print(X_train.shape)
print(X_test.shape)


# In[14]:


# import multilayer perceptron model
from sklearn.neural_network import MLPClassifier

# instantiate model using:
# a maximum of 1000 iterations (default = 200)
# an alpha of 0.5 (default = 0.001)
# and a random state of 42 (for reproducibility)
mlp = MLPClassifier(max_iter=1000, alpha = 0.5,hidden_layer_sizes = (len(feature_cols),), random_state=42)
print(mlp)


# In[15]:


# fit the model with the training set
mlp.fit(X_train, y_train)
# store predicted response values for the testing set
y_predictions = mlp.predict(X_test)


# In[16]:


# compute testing accuracy and generate performance report for the multilayer perceptron model
# precision: ability not to label as positive a sample that is negative
# recall: ability to find positive samples
# f1-score: the harmonic mean of the precision and recall
# support: number of occurrences of each label
from sklearn import metrics
print("\nNeural Net Performance")
print("Accuracy: ", metrics.accuracy_score(y_test,y_predictions))
print(metrics.classification_report(y_test,y_predictions))


# In[18]:


# import Matplotlib (scientific plotting library)
import matplotlib.pyplot as plt

# plot coefficient values for each of the nodes in the hidden layers (100 by default)
plt.figure(figsize=(16, 6))
plt.imshow(mlp.coefs_[0], cmap="GnBu")
plt.yticks(range(30), feature_cols)
plt.xlabel("Columns in weight matrix")
plt.ylabel("Input feature")
plt.colorbar()
plt.show()

