
# coding: utf-8

# In[1]:


# Importing machine learning algorithms and libraries for creating dataframes
from sklearn.linear_model import LogisticRegression
from sklearn.linear_model import LinearRegression
from sklearn.tree import DecisionTreeClassifier
from sklearn.neighbors import KNeighborsClassifier
import numpy as np
import pandas as pd

# Creating a dataframe from the Heart_s.csv and displaying every 50th data from the dataframe
sample_df = pd.read_csv('Sample.csv');
sample_df[0::20]


# In[8]:


# Feature columns that are numerical
feature_cols = list(sample_df.columns.values)
feature_cols.remove('time')
feature_cols.remove('state')

# Filter the dataframe to show data from these feature columns
X = sample_df[feature_cols];
X[0::20]


# In[9]:


# Create a label vector on label "AHD" and displaying every 50th data from the vector
y = sample_df['state'];
y[0::20]


# ### We create our testing and training sets using train_test_split method. 70% of data is training data and 30% is testing data.

# In[3]:


# Importing method to split our testing and training data set and importing library for accuracy
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score

# Split the dataframe dataset. 70% of the data is training data and 30% is testing data using random_state 2
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=2)


# ### We instantiate our KNN object and create our trained model. Then we create our predictive model using our testing data. Last, we calculate the accuracy of our predictive model

# In[4]:


# Importing machine learning algorithm
from sklearn.neighbors import KNeighborsClassifier

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


# ### We instantiate our DecisionTree object and create our trained model. Then we create our predictive model using our testing data. Last, we calculate the accuracy of our predictive model.

# In[5]:


# Importing machine learning algorithm
from sklearn.tree import DecisionTreeClassifier

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


# ### We instantiate our Logistics Regression object and create our trained model. Then we create our predictive model using our testing data. Last, we calculate the accuracy of our predictive model.

# In[6]:


# Import Linear Regression for our machine learning algorithm
from sklearn.linear_model import LogisticRegression

# Create our Logistic Regression machine learning algorithm
lr = LogisticRegression()

# Fit method is used for creating a trained model on the training sets for logisticregression
lr.fit(X_train, y_train)

# Predict method is used for creating a prediction on testing data
y_predict_lr = lr.predict(X_test)

# Accuracy of testing data on predictive model
lr_accuracy = accuracy_score(y_test, y_predict_lr)
print("Accuracy for Logistic Regression Classifier: " + str(lr_accuracy))


# ### We instantiate our RandomTree object and create our trained model. Then we create our predictive model using our testing data. Last, we calculate the accuracy of our predictive model.

# In[7]:


from sklearn.ensemble import RandomForestClassifier

# Instantiating RandomForestClassifier object
my_RandomForest = RandomForestClassifier(n_estimators = 19, bootstrap = True, random_state=2)

# Fit method is used for creating a trained model on the training sets for RandomForestClassifier
my_RandomForest.fit(X_train, y_train)

# Predict method is used for creating a prediction on testing data
y_predict_rf = my_RandomForest.predict(X_test)

# Accuracy of testing data on predictive model
rf_accuracy = accuracy_score(y_test, y_predict_rf)

# Print accuracy
print("Accuracy for RandomForest Classfier: " + str(rf_accuracy))


# ### Add 10-fold Cross Validation with Supervised Learning

# In[20]:


# Importing the method:
from sklearn.cross_validation import cross_val_score

accuracy_list_knn = cross_val_score(knn, X, y, cv=10, scoring='accuracy')
accuracy_list_dt = cross_val_score(my_DecisionTree, X, y, cv=10, scoring='accuracy')
accuracy_list_lr = cross_val_score(lr, X, y, cv=10, scoring='accuracy')
accuracy_list_rf = cross_val_score(my_RandomForest, X, y, cv=10, scoring='accuracy')

print("Cross Validation for KNN: " + str(accuracy_list_knn.mean()))
print("Cross Validation for Decision Tree: " + str(accuracy_list_dt.mean()))
print("Cross Validation for Logistics Regression: " + str(accuracy_list_lr.mean()))
print("Cross Validation for RandomForest: " + str(accuracy_list_rf.mean()))


