from __future__ import print_function
import numpy as np
import tensorflow as tf
import math as math
import argparse

parser = argparse.ArgumentParser()
parser.add_argument('dataset')
args = parser.parse_args()

def file_len(fname):
    with open(fname) as f:
        for i, l in enumerate(f):
            pass
            #print(l)
            #print("pass")
            #print(i)
    return i + 1

def read_from_csv(filename_queue):
  reader = tf.TextLineReader(skip_header_lines=1)
  _, csv_row = reader.read(filename_queue)
  record_defaults = [[0.0], [0.0], [0.0], [0.0], [0.0], [0.0], [0.0], [0.0], [0.0], [0.0], [0.0],[0.0], [0.0], [0.0], [0.0], [0.0], [0.0], [0.0], [0.0], [0.0], [0.0],[0.0], [0.0], [0.0], [0.0], [0.0], [0.0], [0.0], [0.0], [0.0], [0.0],[0.0], [0.0], [0.0], [0.0], [0.0], [0.0], [0.0], [0.0], [0.0], [0.0],[0.0], [0.0], [0.0], [0.0], [0.0], [0.0], [0.0], [0.0], [0.0], [0.0],[0.0], [0.0], [0.0], [0.0], [0.0], [0.0], [0.0]]
  col0,col1, col2, col3, col4, col5,col6, col7, col8, col9, col10,col11, col12, col13, col14, col15,col16, col17, col18, col19, col20,col21, col22, col23, col24, col25,col26, col27, col28, col29, col30,col31, col32, col33, col34, col35,col36, col37, col38, col39, col40,col41, col42, col43, col44, col45,col46, col47, col48, col49, col50,col51, col52, col53, col54, col55,col56, colLabel = tf.decode_csv(csv_row, record_defaults=record_defaults)
  features = tf.stack([col0,col1, col2, col3, col4, col5,col6, col7, col8, col9, col10,col11, col12, col13, col14, col15,col16, col17, col18, col19, col20,col21, col22, col23, col24, col25,col26, col27, col28, col29, col30,col31, col32, col33, col34, col35,col36, col37, col38, col39, col40,col41, col42, col43, col44, col45,col46, col47, col48, col49, col50,col51, col52, col53, col54, col55,col56])  
  label = tf.stack([colLabel])  
  return features, label

def input_pipeline(batch_size, num_epochs=1):
  #filename_queue = tf.train.string_input_producer([args.dataset], num_epochs=num_epochs, shuffle=True)  
  
  [args.dataset]
  filename_queue = tf.train.string_input_producer([args.dataset], num_epochs=1, shuffle=True)  

  example, label = read_from_csv(filename_queue)
  min_after_dequeue = 4598
  #capacity = min_after_dequeue + 3 * batch_size
  capacity = 4599
  example_batch, label_batch = tf.train.shuffle_batch(
      [example, label], batch_size=batch_size, capacity=capacity,
      min_after_dequeue=min_after_dequeue)
  return example_batch, label_batch
  
def input_pipeline_test(batch_size, num_epochs=1):
  #filename_queue = tf.train.string_input_producer([args.dataset], num_epochs=num_epochs, shuffle=True)  
  
  
  print("filename_queue_test")
  filename_queue_test = tf.train.string_input_producer([args.dataset], num_epochs=1, shuffle=True)  

  example, label = read_from_csv(filename_queue_test)
  print("filename_queue_test end")
  min_after_dequeue = 1379
  #capacity = min_after_dequeue + 3 * batch_size
  capacity = 1380
  example_batch, label_batch = tf.train.shuffle_batch(
      [example, label], batch_size=batch_size, capacity=capacity,
      min_after_dequeue=min_after_dequeue)
  print(example_batch)
  return example_batch, label_batch
#####################################################################################################################################
#####################################################################################################################################
#####################################################################################################################################
# Parameters
learning_rate = 0.6
training_epochs = 1
batch_size = 1000
display_step = 1

# Network Parameters
n_hidden_1 = 100# 1st layer number of features
n_hidden_2 = 30# 2nd layer number of features
n_input = 1339 # MNIST data input (img shape: 28*28)
n_classes = 1 # MNIST total classes (0-9 digits)


# Create model
def multilayer_perceptron(x, weights, biases):
    # Hidden layer with RELU activation
    layer_1 = tf.add(tf.matmul(x, weights), biases)
    layer_1 = tf.nn.relu(layer_1)
    # Hidden layer with RELU activation
    layer_2 = tf.add(tf.matmul(layer_1, weights), biases)
    layer_2 = tf.nn.relu(layer_2)
    # Output layer with linear activation
    out_layer = tf.matmul(layer_2, weights) + biases
    return out_layer

# Store layers weight & bias
'''weights = {
    'h1': tf.Variable(tf.random_normal([n_input, n_hidden_1])),
    'h2': tf.Variable(tf.random_normal([n_hidden_1, n_hidden_2])),
    'out': tf.Variable(tf.random_normal([n_hidden_2, n_classes]))
}
biases = {
    'b1': tf.Variable(tf.random_normal([n_hidden_1])),
    'b2': tf.Variable(tf.random_normal([n_hidden_2])),
    'out': tf.Variable(tf.random_normal([n_classes]))
}
'''
weights = tf.Variable(tf.zeros([1339, 10]))
biases = tf.Variable(tf.zeros([10]))



#########################################################################################################################################
#########################################################################################################################################
#print("file_len(args.dataset) - 1")
#print(file_len(args.dataset) - 1)
file_length = file_len(args.dataset) - 1
#file_length = 4599
examples, labels = input_pipeline(file_length, 1)
examples_batch=tf.global_variables()
labels_batch=tf.global_variables()
examples_batch_test=tf.global_variables()
labels_batch_test=tf.global_variables()
x = tf.placeholder("float", [None, 1339])
y = tf.placeholder(tf.float32, [None, 10])
y_ = tf.nn.softmax(tf.matmul(x, weights) + biases)
#pred = multilayer_perceptron(x, weights, biases)
cost = tf.reduce_mean(-tf.reduce_sum(y_ * tf.log(y_), reduction_indices=[1]))
optimizer = tf.train.GradientDescentOptimizer(learning_rate=learning_rate).minimize(cost)
config=tf.ConfigProto(inter_op_parallelism_threads=2)
with tf.Session(config=config) as sess:
  init_op = tf.group(tf.initialize_all_variables(),tf.initialize_local_variables())
  sess.run(init_op)
  #tf.initialize_all_variables().run()

  # start populating filename queue
  coord = tf.train.Coordinator()
  threads = tf.train.start_queue_runners(coord=coord)

  try:
    while not coord.should_stop():
      examples_batch, labels_batch = sess.run([examples, labels])
    #x, y = sess.run([examples, labels])
     
  except tf.errors.OutOfRangeError:
    print('Done training, epoch reached')
    #print(examples_batch)
    #print(labels_batch)

    # Training cycle
    for epoch in range(training_epochs):
        avg_cost = 0.0
        total_batch = 1
        # Loop over all batches
        for i in range(total_batch):
            # Run optimization op (backprop) and cost op (to get loss value)
            _, c = sess.run([optimizer, cost], feed_dict={x: examples_batch,
                                                          y: labels_batch})
            print("c")
            print(c)
            #c = {x: batch_x,y: batch_y}
            # Compute average loss
            #print(c / total_batch)
            avg_cost = c / total_batch
        # Display logs per epoch step
        if epoch % display_step == 0:
            print("Epoch:", '%04d' % (epoch+1), "cost=", \
                "{:.9f}".format(avg_cost))
    print("Optimization Finished!")
    print("TEST BLOCK!")
    try:
        file_length_test=1379
        examples_test, labels_test = input_pipeline_test(file_length_test, 1)
        print("file print")
        print(sess.run(labels_test))
        print("file print end")
    except:
        print("not working")
        
    coord.request_stop()




coord.join(threads) 
